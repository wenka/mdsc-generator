package com.wenka.mdsc.generator.annotation;

import java.lang.annotation.*;

/**
 * Created with IDEA
 *
 * @author wenka wkwenka@gmail.com
 * @date 2020/03/25  上午 10:00
 * @description:
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Bean {

    /**
     * bean 名称：默认为当前类的 simpleName
     * @return
     */
    String value() default "";

    /**
     * 当容器中有相同父类的bean时，按照 order顺序优先选择获取 bean
     *
     * @return
     */
    int order() default 0;
}
