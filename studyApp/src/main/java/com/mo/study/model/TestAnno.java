package com.mo.study.model;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义一个注解
 * Created by motw on 2017/2/6.
 */
@Target(ElementType.FIELD) //作用于那个元素(可以是 类，成员，方法，等)
@Retention(RetentionPolicy.RUNTIME)// 有效范围（源码，class文件，运行时)
@Documented
public @interface TestAnno {

    String name() default "";

    String value() default "";
}
