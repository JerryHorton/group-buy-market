package cn.cug.sxy.types.annotation;

import java.lang.annotation.*;

/**
 * @version 1.0
 * @Date 2025/4/17 13:44
 * @Description redis 缓存刷新 注解
 * @Author jerryhotton
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CacheRefresh {

    Class<?> type() default Object.class;

}
