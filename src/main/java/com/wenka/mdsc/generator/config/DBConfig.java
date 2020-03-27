package com.wenka.mdsc.generator.config;

import com.wenka.mdsc.generator.annotation.Bean;
import com.wenka.mdsc.generator.annotation.Value;
import com.wenka.mdsc.generator.constants.PrecisionEnum;
import com.wenka.mdsc.generator.constants.PropertiesKey;
import com.wenka.mdsc.generator.util.PropertiesUtil;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.*;
import java.util.function.Function;

/**
 * Created with IDEA
 *
 * @author wenka wkwenka@gmail.com
 * @date 2020/03/23  下午 02:56
 * @description:
 */
@Data
@Accessors(chain = true)
@Bean
public class DBConfig {

    @Value(PropertiesKey.JDBC_URL)
    private String url;

    @Value(PropertiesKey.JDBC_DRIVER)
    private String driver;

    @Value(PropertiesKey.JDBC_USERNAME)
    private String username;

    @Value(PropertiesKey.JDBC_PASSWORD)
    private String password;

    /**
     * 获取数据类型对应的java字段类型
     *
     * @param dbType
     * @param digits
     * @return
     */
    public String getFieldType(int dbType, int digits) {
        String fieldType;
        if (dbType == Types.INTEGER || dbType == Types.SMALLINT || dbType == Types.TINYINT || dbType == Types.BIT || (dbType == Types.NUMERIC && dbType == 0)) {
            fieldType = "Integer";
        } else if (dbType == Types.BIGINT) {
            fieldType = "Long";
        } else if (dbType == Types.DOUBLE || dbType == Types.REAL || dbType == Types.FLOAT || (dbType == Types.NUMERIC && dbType > 0)) {
            fieldType = this.getPrecisionType();
        } else if (dbType == Types.DECIMAL) {
            fieldType = "BigDecimal";
        } else if (dbType == Types.TIME || dbType == Types.TIMESTAMP) {
            fieldType = "Date";
        } else {
            fieldType = "String";
        }
        return fieldType;
    }


    private String getPrecisionType() {
        String dataType;
        if (PrecisionEnum.high.toString().equalsIgnoreCase(PropertiesUtil.getValue(PropertiesKey.PRECISSION))) {
            dataType = "BigDecimal";
        } else {
            dataType = "Double";
        }
        return dataType;
    }

    /**
     * 获取数据库连接
     *
     * @return
     */
    private Connection getConnection() {
        Connection connection;
        try {
            Class.forName(this.getDriver());
            connection = DriverManager.getConnection(this.getUrl(), this.getUsername(), this.getPassword());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    private DatabaseMetaData getDatabaseMetaData(Connection connection) {
        DatabaseMetaData databaseMetaData;
        try {
            databaseMetaData = connection.getMetaData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return databaseMetaData;
    }

    /**
     * 执行
     *
     * @param function
     */
    public void execute(Function<DatabaseMetaData, Boolean> function) {
        Connection connection = this.getConnection();
        DatabaseMetaData databaseMetaData = this.getDatabaseMetaData(connection);
        try {
            function.apply(databaseMetaData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
