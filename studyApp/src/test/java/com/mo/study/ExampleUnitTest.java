package com.mo.study;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);

//        Class t = PUser.class;
//        //使用反射来获取注解的信息
//        for (Field f : t.getDeclaredFields()){
//            if (f.isAnnotationPresent(TestAnno.class)){
//                TestAnno name = f.getAnnotation(TestAnno.class);
//                System.out.println(name.name());
//                System.out.println(name.value());
//            }
//        }
        float result = 1008f * 1008 * (420/320) * 4;
//        float a = 4064256 / result;
        System.out.println(result);
        float b = result/1024/1024;
        System.out.println(b);
    }
}