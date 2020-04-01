package com.wenka.mdsc.generator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IDEA
 *
 * @author wenka wkwenka@gmail.com
 * @date 2020/04/01  上午 09:13
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BeanInfo {

    /**
     * bean 名称
     */
    private String beanName;

    /**
     * bean class
     */
    private Class beanClass;

    /**
     * 优先级
     */
    private int order;

    private Object bean;
}
