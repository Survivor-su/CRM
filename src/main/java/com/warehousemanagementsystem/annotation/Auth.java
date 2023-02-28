package com.warehousemanagementsystem.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * 自定义注解,用于控制用户权限
 * */
@Target({ElementType.TYPE, ElementType.METHOD})//设置注解使用的位置,这里表示既可以用在类也可以用在方法
@Retention(RetentionPolicy.RUNTIME)//设置注解的生命周期,这里表示在运行时获取注解信息
public @interface Auth {
    //定义注解属性,在使用该注解时给属性赋值,这里定义的role()表示用于具体的角色
    String role() default "";
}
