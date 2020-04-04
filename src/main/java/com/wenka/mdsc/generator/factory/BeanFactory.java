package com.wenka.mdsc.generator.factory;

import com.wenka.mdsc.generator.chain.BeanFieldChain;
import com.wenka.mdsc.generator.chain.filed.AbstractFieldChain;
import com.wenka.mdsc.generator.context.GeneratorContext;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created with IDEA
 *
 * @author wenka wkwenka@gmail.com
 * @date 2020/03/27  下午 02:10
 * @description: bean 工厂
 */
public class BeanFactory extends AbstractFactory {

    /**
     * 创建 bean
     *
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> T createBean(Class<T> tClass) {
        T t = null;
        try {
            t = tClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return t;
    }
}
