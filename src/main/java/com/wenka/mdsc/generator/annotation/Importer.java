package com.wenka.mdsc.generator.annotation;

import java.lang.annotation.*;

/**
 * Created with IDEA
 *
 * @author wenka wkwenka@gmail.com
 * @date 2020/04/04  上午 10:31
 * @description: 容器注入注解：对象注入
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Importer {
}
