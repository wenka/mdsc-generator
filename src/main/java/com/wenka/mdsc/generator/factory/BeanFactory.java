package com.wenka.mdsc.generator.factory;

import com.wenka.mdsc.generator.annotation.Value;
import com.wenka.mdsc.generator.util.PropertiesUtil;

import java.lang.reflect.Field;

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
            // 有属性的话 需给带有@Value注解的属性注入值
            Field[] declaredFields = tClass.getDeclaredFields();
            if (declaredFields != null && declaredFields.length > 0) {
                for (Field field : declaredFields) {
                    Value value = field.getAnnotation(Value.class);
                    if (value == null) {
                        continue;
                    }
                    // 获取字段类型
                    Class<?> type = field.getType();
                    String propertiesKey = value.value();
                    String v = PropertiesUtil.getValue(propertiesKey);
                    field.setAccessible(true);
                    if (String.class.isAssignableFrom(type)) { // String 类型
                        field.set(t, v);
                    }
                    // todo 其他字段类型 else
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return t;
    }
}
