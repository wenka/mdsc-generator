package com.wenka.mdsc.generator.service;

import com.wenka.mdsc.generator.model.Column;

import java.util.List;

/**
 * Created with IDEA
 *
 * @author wenka wkwenka@gmail.com
 * @date 2020/03/23  下午 02:58
 * @description:
 */
public interface DBService {

    /**
     * 获取表字段及对应的java类型
     *
     * @param table
     * @return
     */
    List<Column> getTableColumns(String table);


    /**
     * 获取表注释
     *
     * @param tableName
     * @return
     */
    String getTableRemark(String tableName);

    /**
     * 获取表的主键
     *
     * @param tableName
     * @return
     */
    List<Column> getTablePrimaryKey(String tableName);

}
