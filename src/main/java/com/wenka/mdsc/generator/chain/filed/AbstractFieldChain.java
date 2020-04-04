package com.wenka.mdsc.generator.chain.filed;

import java.lang.reflect.Field;

/**
 * Created with IDEA
 *
 * @author wenka wkwenka@gmail.com
 * @date 2020/04/04  上午 10:59
 * @description:
 */
public abstract class AbstractFieldChain {
    /**
     * 给实例指定字段设置值
     *
     * @param object
     * @param field
     * @return
     */
    public abstract <T> boolean setValue(Field field, T object) throws Exception;
}
