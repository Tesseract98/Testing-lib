package test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class ReflectionHelper {

    private ReflectionHelper() {
    }

    static <T> T initiate(Class<T> type, Object... args) {
        try {
            if(args.length == 0) {
                return type.getDeclaredConstructor().newInstance();
            } else {
                Class<?>[] classes = toClass(args);
                return type.getDeclaredConstructor(classes).newInstance(args);
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    static Object getFieldValue(Object obj, String name){
        Field field = null;
        boolean isAccessible = true;
        try {
            field = obj.getClass().getDeclaredField(name);
            isAccessible = field.isAccessible();
            field.setAccessible(true);
            return field.get(obj);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Class<?>[] toClass(Object[] args) {
        return (Class<?>[]) Arrays.stream(args).map(Object::getClass).toArray();
    }

}
