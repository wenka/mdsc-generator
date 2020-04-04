package com.wenka.mdsc.generator.chain.filed;

import com.wenka.mdsc.generator.annotation.Bean;
import com.wenka.mdsc.generator.annotation.Importer;
import com.wenka.mdsc.generator.context.GeneratorContext;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * Created with IDEA
 *
 * @author wenka wkwenka@gmail.com
 * @date 2020/04/04  上午 11:22
 * @description: Bean 类型字段设置
 */
@Bean(order = 1)
public class BeanField extends AbstractFieldChain {
    /**
     * 给实例指定字段设置值
     *
     * @param field
     * @param object
     * @return
     */
    @Override
    public <T> boolean setValue(Field field, T object) throws Exception {
        Importer value = field.getAnnotation(Importer.class);
        if (value == null) {
            return false;
        }
        // 获取字段类型
        Class<?> type = field.getType();
        String name = field.getName();
        Object beanByName = GeneratorContext.getBeanByName(name, type);
        if (Objects.isNull(beanByName)) {
            throw new RuntimeException(type + " " + name + " : bean not found.");
        }
        field.setAccessible(true);
        if (type.isAssignableFrom(beanByName.getClass())) {
            field.set(object, beanByName);
        }else {
            throw new RuntimeException(type + " " + name + " : bean not found.");
        }
        return true;
    }
}
