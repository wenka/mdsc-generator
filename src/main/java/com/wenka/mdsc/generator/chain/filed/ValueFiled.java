package com.wenka.mdsc.generator.chain.filed;

import com.wenka.mdsc.generator.annotation.Bean;
import com.wenka.mdsc.generator.annotation.Value;
import com.wenka.mdsc.generator.util.PropertiesUtil;

import java.lang.reflect.Field;

/**
 * Created with IDEA
 *
 * @author wenka wkwenka@gmail.com
 * @date 2020/04/04  上午 10:57
 * @description: 给带有 @Value 注解的字段设置值
 */
@Bean(order = 0)
public class ValueFiled extends AbstractFieldChain {
    /**
     * 给实例指定字段设置值
     *
     * @param field
     * @param object
     * @return
     */
    @Override
    public <T> boolean setValue(Field field, T object) throws IllegalAccessException {
        Value value = field.getAnnotation(Value.class);
        if (value == null) {
            return false;
        }
        // 获取字段类型
        Class<?> type = field.getType();
        String propertiesKey = value.value();
        String v = PropertiesUtil.getValue(propertiesKey);
        field.setAccessible(true);
        if (String.class.isAssignableFrom(type)) { // String 类型
            field.set(object, v);
        }
        return true;
    }
}
