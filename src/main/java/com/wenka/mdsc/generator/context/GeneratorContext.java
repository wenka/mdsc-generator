package com.wenka.mdsc.generator.context;

import com.wenka.mdsc.generator.model.Column;
import com.wenka.mdsc.generator.model.TableInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IDEA
 *
 * @author wenka wkwenka@gmail.com
 * @date 2020/03/23  下午 04:29
 * @description:
 */
public class GeneratorContext {

    private final static Map<Class, Object> CONTEXT = new HashMap();

    private final static Map<String, TableInfo> TABLE_MAP = new HashMap<>();

    public final static Map<String, List<Column>> TABLE_COLUMN_MAP = new HashMap<>();

    /**
     * 注册 bean
     *
     * @param t
     * @param <T>
     */
    public static synchronized <T> void register(T t) {
        CONTEXT.put(t.getClass(), t);
    }

    /**
     * 获取 bean
     *
     * @param tClass
     * @param <T>
     * @return
     */
    public static synchronized <T> T getBean(Class<T> tClass) {
        return (T) CONTEXT.get(tClass);
    }

    /**
     * 获取同个类型的 bean
     *
     * @param tClass
     * @param <T>
     * @return
     */
    public static synchronized <T> List<T> getBeans(Class<T> tClass) {
        List<T> resultList = new ArrayList<>();
        for (Object o: CONTEXT.values()){
            if (tClass.isAssignableFrom(o.getClass())){
                resultList.add((T)o);
            }
        }
        return resultList;
    }

    public static void addTable(String tableName, TableInfo tableInfo) {
        TABLE_MAP.put(tableName, tableInfo);
    }

    public static Map<String, TableInfo> getAllTable() {
        return TABLE_MAP;
    }
}
