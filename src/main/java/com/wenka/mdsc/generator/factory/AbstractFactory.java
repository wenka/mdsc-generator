package com.wenka.mdsc.generator.factory;

/**
 * Created with IDEA
 *
 * @author wenka wkwenka@gmail.com
 * @date 2020/03/27  下午 02:04
 * @description:
 */
public abstract class AbstractFactory {

    /**
     * 创建 bean
     *
     * @param tClass
     * @param <T>
     * @return
     */
    public abstract <T> T createBean(Class<T> tClass);

}
