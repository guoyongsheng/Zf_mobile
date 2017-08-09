package com.zfsoftmh.di;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author wesley
 * @date: 2017/3/16
 * @Description: 自定义注解---请求参数不需要加密时 创建的Retrofit对象
 */

@Qualifier
@Documented
@Retention(RUNTIME)
public @interface ForNoEncodeRetrofit {
    /** The name. */
    String value() default "";
}