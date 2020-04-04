package com.wenka.mdsc.generator.chain;

import com.wenka.mdsc.generator.chain.filed.AbstractFieldChain;
import com.wenka.mdsc.generator.context.GeneratorContext;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IDEA
 *
 * @author wenka wkwenka@gmail.com
 * @date 2020/04/04  上午 10:51
 * @description: 给实例设置值责任链
 */
public class BeanFieldChain extends AbstractFieldChain {

    private List<AbstractFieldChain> fieldChainList;

    /**
     * 给实例指定字段设置值
     *
     * @param field
     * @param object
     * @return
     */
    @Override
    public <T> boolean setValue(Field field, T object) throws Exception {
        fieldChainList = GeneratorContext.getBeans(AbstractFieldChain.class);
        if (fieldChainList == null || fieldChainList.isEmpty()) {
            return false;
        }
        Iterator<AbstractFieldChain> iterator = fieldChainList.iterator();
        while (iterator.hasNext()) {
            AbstractFieldChain chain = iterator.next();
            if (chain.setValue(field, object)) {
                break;
            }
        }
        return true;
    }
}
