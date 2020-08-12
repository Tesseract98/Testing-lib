package test;

import lombok.SneakyThrows;
import test.annotation.After;
import test.annotation.Before;
import test.annotation.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TestingCore {

    private static final Map<Class<?>, List<Method>> methodsWithAnnotation;

    static {
        methodsWithAnnotation = new HashMap<>();
        primaryMapInit();
    }

    @SneakyThrows
    public static void runClass(Class<?> clazz) {
        Object obj = ReflectionHelper.initiate(clazz);
        fillInMap(clazz.getMethods());
        for(Method method: getMethodsWithAnnotationTest()){
            getMethodWithAnnotationBefore().invoke(obj);
            if(isTestExpectedException(method)) {
                try {
                    method.invoke(obj);
                } catch (Exception exc) {
                    System.out.println(exc.getCause().getMessage());
                }
            } else {
                method.invoke(obj);
            }
            getMethodWithAnnotationAfter().invoke(obj);
        }
    }

    private static void fillInMap(Method[] methods) {
        for(Method method: methods) {
            Annotation[] asd = method.getAnnotations();
            if(asd.length != 0) {
                methodsWithAnnotation.get(asd[0].annotationType()).add(method);
            }
        }
    }

    private static Method getMethodWithAnnotationBefore() {
        return methodsWithAnnotation.get(Before.class).get(0);
    }

    private static List<Method> getMethodsWithAnnotationTest() {
        return methodsWithAnnotation.get(Test.class);
    }

    private static Method getMethodWithAnnotationAfter() {
        return methodsWithAnnotation.get(After.class).get(0);
    }

    private static Boolean isTestExpectedException(Method method){
        return method.getAnnotation(Test.class).expectedException().equals(Test.ExceptionStatus.TRUE);
    }

    private static void primaryMapInit() {
        methodsWithAnnotation.computeIfAbsent(Before.class, k -> new LinkedList<>());
        methodsWithAnnotation.computeIfAbsent(After.class, k -> new LinkedList<>());
        methodsWithAnnotation.computeIfAbsent(Test.class, k -> new LinkedList<>());
    }

}
