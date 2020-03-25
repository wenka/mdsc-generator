
package com.wenka.mdsc.generator.service.impl;

import com.wenka.mdsc.generator.annotation.Bean;
import com.wenka.mdsc.generator.config.DBConfig;
import com.wenka.mdsc.generator.context.GeneratorContext;
import com.wenka.mdsc.generator.model.Column;
import com.wenka.mdsc.generator.service.DBService;
import com.wenka.mdsc.generator.util.StringUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IDEA
 *
 * @author wenka wkwenka@gmail.com
 * @date 2020/03/23  下午 04:17
 * @description:
 */
@Bean
public class DBServiceImpl implements DBService {

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
        List<Column> columnList = new LinkedList<>();
        DBConfig dbConfig = GeneratorContext.getBean(DBConfig.class);
        dbConfig.execute(metaData -> {
            try {
                ResultSet columns = metaData.getColumns(null, "%", tableName, "%");
                while (columns.next()) {
                    String columnName = columns.getString("COLUMN_NAME");
                    int digits = columns.getInt("DECIMAL_DIGITS");
                    int dataType = columns.getInt("DATA_TYPE");
                    String remarks = columns.getString("REMARKS");
                    String jdbcType = columns.getString("TYPE_NAME");
                    if ("DATETIME".equals(jdbcType)){
                        jdbcType = "TIMESTAMP";
                    }
                    String columnType = dbConfig.getFieldType(dataType, digits);
                    String humpName = StringUtil.getHumpName(columnName);
                    columnList.add(new Column(columnName, humpName, columnType, jdbcType, remarks));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return true;
        });
        GeneratorContext.TABLE_COLUMN_MAP.putIfAbsent(tableName, columnList);
        return columnList;
    }
}
