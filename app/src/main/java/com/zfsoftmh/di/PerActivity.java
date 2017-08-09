package com.zfsoftmh.di;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author wesley
 * @date: 2017/3/13
 * @Description: 自定义注解---在同一个Activity中保证同一个对象只被实例化一次
 */

@Scope
@Documented
@Retention(RUNTIME)
public @interface PerActivity {
}
