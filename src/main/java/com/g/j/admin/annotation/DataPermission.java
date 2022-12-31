package com.g.j.admin.annotation;

import java.lang.annotation.*;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataPermission {
    String value() default "";
}
