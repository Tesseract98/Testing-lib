package test;

import test.annotation.After;
import test.annotation.Before;
import test.annotation.Test;

import java.util.Objects;

@SuppressWarnings("unused")
public class ReflectionHelperTest {

    private TestClass testClass;

    @Before
    public void setValues(){
        testClass = new TestClass();
        testClass.setNum(10);
        testClass.setStr("new obj");
        System.out.println("Before annotation");
    }

    @After
    public void cleanAll(){
        testClass = null;
        System.out.println("After annotation\n");
    }

    @Test
    public void initiate(){
        System.out.println("Test initiate");
        TestClass testCl = ReflectionHelper.initiate(TestClass.class);
        assert testCl != null : "testCl is null";
    }

    @Test
    public void getFieldValue(){
        System.out.println("Test getFieldValue");
        assert Objects.equals(ReflectionHelper.
                getFieldValue(testClass, "str"), "new obj") : "str value not equal 'new obj'";
        assert Objects.equals(ReflectionHelper.
                getFieldValue(testClass, "num"), 10) : "num value not equal 10";
    }

    @Test
    public void isEqual(){
        System.out.println("Test isEqual");
        TestClass testCl = ReflectionHelper.initiate(TestClass.class, "new object", 10);
        assert Objects.equals(testCl, testClass) : "testCl is not equal testClass";
    }

    @Test(expectedException = Test.ExceptionStatus.TRUE)
    public void throwException() {
        System.out.println("Test throwException");
        throw new RuntimeException("all right");
    }

}
