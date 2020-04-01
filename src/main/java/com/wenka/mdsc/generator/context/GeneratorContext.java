package com.wenka.mdsc.generator.context;

import com.wenka.mdsc.generator.annotation.Bean;
import com.wenka.mdsc.generator.model.BeanInfo;
import com.wenka.mdsc.generator.model.Column;
import com.wenka.mdsc.generator.model.TableInfo;
import org.apache.commons.lang.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created with IDEA
 *
 * @author wenka wkwenka@gmail.com
 * @date 2020/03/23  下午 04:29
 * @description:
 */
public class GeneratorContext {

    private final static Map<String, BeanInfo> CONTEXT = new HashMap();

    private final static Map<String, TableInfo> TABLE_MAP = new HashMap<>();

    public final static Map<String, List<Column>> TABLE_COLUMN_MAP = new HashMap<>();

    /**
     * 注册 bean
     *
     * @param t
     * @param <T>
     */
    public static synchronized <T> void register(T t) {
        Class<?> tClass = t.getClass();
        Bean bean = tClass.getAnnotation(Bean.class);
        String beanName = bean.value();
        if (StringUtils.isBlank(beanName)) {
            beanName = tClass.getSimpleName();
        }
        int order = bean.order();
        BeanInfo beanInfo = new BeanInfo(beanName, tClass, order, t);
        CONTEXT.put(beanName, beanInfo);
    }

    /**
     * 通过具体类型获取 bean
     *
     * @param tClass
     * @param <T>
     * @return
     */
    public static synchronized <T> T getBean(Class<T> tClass) {
        Collection<BeanInfo> values = CONTEXT.values();
        BeanInfo info = values.stream().filter(beanInfo -> {
            return beanInfo.getBeanClass() == tClass;
        }).findAny().orElse(null);
        return Objects.isNull(info) ? null : (T) info.getBean();
    }

    /**
     * 获取同个类型的 bean
     *
     * @param tClass
     * @param <T>
     * @return
     */
    public static synchronized <T> List<T> getBeans(Class<T> tClass) {
        Collection<BeanInfo> values = CONTEXT.values();
        List<T> resultList = values.stream().filter(beanInfo -> {
            return tClass.isAssignableFrom(beanInfo.getBeanClass());
        }).sorted((info1, info2) -> {
            return info1.getOrder() > info2.getOrder() ? 1 : -1;
        }).map(beanInfo -> {
            return (T) beanInfo.getBean();
        }).collect(Collectors.toList());
        return resultList;
    }

    public static void addTable(String tableName, TableInfo tableInfo) {
        TABLE_MAP.put(tableName, tableInfo);
    }

    public static Map<String, TableInfo> getAllTable() {
        return TABLE_MAP;
    }
}
