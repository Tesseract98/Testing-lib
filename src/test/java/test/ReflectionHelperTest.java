package test;

import lombok.extern.slf4j.Slf4j;
import test.annotation.After;
import test.annotation.Before;
import test.annotation.Test;

import java.util.Objects;

@Slf4j
@SuppressWarnings("unused")
public class ReflectionHelperTest {

    private TestClass testClass;

    @Before
    public void setValues(){
        testClass = new TestClass();
        testClass.setNum(10);
        testClass.setStr("new obj");
        log.debug("Before annotation");
    }

    @After
    public void cleanAll(){
        testClass = null;
        log.debug("After annotation\n");
    }

    @Test
    public void initiate(){
        log.debug("Test initiate");
        TestClass testCl = ReflectionHelper.initiate(TestClass.class);
        assert testCl != null : "testCl is null";
    }

    @Test
    public void getFieldValue(){
        log.debug("Test getFieldValue");
        assert Objects.equals(ReflectionHelper.
                getFieldValue(testClass, "str"), "new obj") : "str value not equal 'new obj'";
        assert Objects.equals(ReflectionHelper.
                getFieldValue(testClass, "num"), 10) : "num value not equal 10";
    }

    @Test
    public void isEqual(){
        log.debug("Test isEqual");
        TestClass testCl = ReflectionHelper.initiate(TestClass.class, "new object", 10);
        assert Objects.equals(testCl, testClass) : "testCl is not equal testClass";
    }

    @Test(expectedException = Test.ExceptionStatus.TRUE)
    public void throwException() {
        log.debug("Test throwException");
        throw new RuntimeException("all right");
    }

}
