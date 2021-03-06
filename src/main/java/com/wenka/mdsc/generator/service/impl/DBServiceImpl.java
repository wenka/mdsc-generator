
package com.wenka.mdsc.generator.service.impl;

import com.wenka.mdsc.generator.annotation.Bean;
import com.wenka.mdsc.generator.annotation.Importer;
import com.wenka.mdsc.generator.config.DBConfig;
import com.wenka.mdsc.generator.context.GeneratorContext;
import com.wenka.mdsc.generator.model.Column;
import com.wenka.mdsc.generator.service.DBService;
import com.wenka.mdsc.generator.util.StringUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created with IDEA
 *
 * @author wenka wkwenka@gmail.com
 * @date 2020/03/23  下午 04:17
 * @description:
 */
@Bean("dbService")
public class DBServiceImpl implements DBService {

    @Importer
    private DBConfig dbConfig;

    /**
     * 获取表字段及对应的java类型
     *
     * @param tableName
     * @return
     */
    @Override
    public List<Column> getTableColumns(String tableName) {
        if (GeneratorContext.TABLE_COLUMN_MAP.containsKey(tableName)) {
            return GeneratorContext.TABLE_COLUMN_MAP.get(tableName);
        }
        Map<String, Integer> pkMap = this.getTablePrimaryKey(tableName);
        List<Column> columnList = this.dbConfig.execute(metaData -> {
            List<Column> columnLinkedList = new LinkedList<>();
            try {
                ResultSet columns = metaData.getColumns(null, metaData.getConnection().getSchema(), tableName, "%");
                while (columns.next()) {
                    String columnName = columns.getString("COLUMN_NAME");
                    int digits = columns.getInt("DECIMAL_DIGITS");
                    int dataType = columns.getInt("DATA_TYPE");
                    String remarks = columns.getString("REMARKS");
                    String jdbcType = columns.getString("TYPE_NAME");
                    if ("DATETIME".equals(jdbcType)) {
                        jdbcType = "TIMESTAMP";
                    } else if ("TEXT".equals(jdbcType)) {
                        jdbcType = "VARCHAR";
                    }
                    String columnType = this.dbConfig.getFieldType(dataType, digits);
                    String humpName = StringUtil.getHumpName(columnName);

                    boolean isPk = pkMap.containsKey(columnName);
                    columnLinkedList.add(new Column(columnName, humpName, columnType, jdbcType, remarks, isPk, pkMap.get(columnName)));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return columnLinkedList;
        });
        GeneratorContext.TABLE_COLUMN_MAP.putIfAbsent(tableName, columnList);
        return columnList;
    }

    /**
     * 获取表注释
     *
     * @param tableName
     * @return
     */
    @Override
    public String getTableRemark(String tableName) {
        String remarks = this.dbConfig.execute(metaData -> {
            try {
                ResultSet resultSet = metaData.getTables(null, metaData.getConnection().getSchema(), tableName, new String[]{"TABLE"});
                if (resultSet.next()) {
                    return resultSet.getString("REMARKS");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return "";
        });
        return remarks;
    }

    /**
     * 获取表的主键
     *
     * @param tableName
     * @return
     */
    @Override
    public Map<String, Integer> getTablePrimaryKey(String tableName) {
        Map<String, Integer> execute = this.dbConfig.execute(metaData -> {
            Map<String, Integer> pkMap = new HashMap<>();
            try {
                ResultSet primaryKeys = metaData.getPrimaryKeys(null, metaData.getConnection().getSchema(), tableName);
                while (primaryKeys.next()) {
                    String columnName = primaryKeys.getString("COLUMN_NAME");
                    Integer keySeq = primaryKeys.getInt("KEY_SEQ");
                    pkMap.put(columnName, keySeq);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return pkMap;
        });
        return execute;
    }
}
