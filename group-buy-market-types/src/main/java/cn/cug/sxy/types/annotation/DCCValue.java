package cn.cug.sxy.types.annotation;

import java.lang.annotation.*;

/**
 * @version 1.0
 * @Date 2025/3/31 10:20
 * @Description 动态配置中心标记 注解
 * @Author jerryhotton
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface DCCValue {

    String value() default "";

}
