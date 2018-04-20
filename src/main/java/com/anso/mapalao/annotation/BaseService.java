package com.anso.mapalao.annotation;

import java.lang.annotation.*;

/**
 * Created by zhoufei on 2018/1/11.
 * 当需要使用baseService这一套的业务层，  就需要加这个注释
 * 在Spring启动的时候，会初始化BaseServiceImpl中的Mapper属性
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BaseService {
}
