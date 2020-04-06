package com.wenka.mdsc.generator.service.impl;

import com.wenka.mdsc.generator.annotation.Bean;
import com.wenka.mdsc.generator.annotation.Importer;
import com.wenka.mdsc.generator.constants.Contants;
import com.wenka.mdsc.generator.constants.PropertiesKey;
import com.wenka.mdsc.generator.model.Column;
import com.wenka.mdsc.generator.model.TableInfo;
import com.wenka.mdsc.generator.service.BaseGenFileService;
import com.wenka.mdsc.generator.service.DBService;
import com.wenka.mdsc.generator.util.FolderUtil;
import com.wenka.mdsc.generator.util.PropertiesUtil;
import com.wenka.mdsc.generator.util.StringUtil;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * Created with IDEA
 *
 * @author wenka wkwenka@gmail.com
 * @date 2020/04/06  上午 10:34
 * @description: model参数对象文件生成服务
 */
@Bean(order = 1)
public class ModelPoGenFileServiveImpl extends BaseGenFileService {

    @Importer
    private DBService dbService;

    /**
     * @return
     */
    @Override
    public boolean support() {
        return PropertiesUtil.getBooleanValue(PropertiesKey.MULI_CONDITIOM_QUERY);
    }

    /**
     * 获取文件生成位置
     *
     * @param className
     * @return
     */
    @Override
    public String getFilePath(String className) {
        String fileName = Contants.DEFAULT_MODEL_NAME.replace("#", className);
        String classPath = FolderUtil.getModulePath();
        StringBuilder path = new StringBuilder(classPath)
                .append(Contants.PATH_SEPARATOR)
                .append(PropertiesUtil.getValue(PropertiesKey.MODEL_PACKAGE))
                .append(Contants.PATH_SEPARATOR)
                .append(Contants.DEFAULT_MODEL_PO)
                .append(Contants.PATH_SEPARATOR)
                .append(fileName)
                .append("Po")
                .append(".java");
        return path.toString();
    }

    /**
     * 获取模板名称
     *
     * @return
     */
    @Override
    public String vmName() {
        return "template/modelPo.vm";
    }

    /**
     * 模板数据
     *
     * @param tableInfo
     * @return
     */
    @Override
    public Map<String, Object> templateData(TableInfo tableInfo) {
        List<Column> tableColumns = this.dbService.getTableColumns(tableInfo.getTableName());
        List<Column> columns = new ArrayList<>(tableColumns.size());
        Map<String, Object> args = new HashMap<>();
        args.put("author", PropertiesUtil.getValue(PropertiesKey.AUTHOR));

        Set<String> imports = new HashSet<>();
        List<Column> fields = new ArrayList<>();
        tableColumns.stream().forEach(column -> {

            // Model 的 String 类型字段的生成策略
            //likeField（模糊匹配）
            //eqField（等值匹配）
            //neField（等值不匹配）
            String name = column.getName();
            String humpName = StringUtil.initialsUpperCase(column.getHumpName());
            String javaType = column.getJavaType();
            String jdbcType = column.getJdbcType();
            String remark = column.getRemark();
            boolean primary = column.isPrimary();
            Integer pkSeq = column.getPkSeq();
            Column eqColumn = new Column(name, "eq" + humpName, javaType, jdbcType, "等值匹配" + remark, primary, pkSeq);
            Column neColumn = new Column(name, "ne" + humpName, javaType, jdbcType, "等值不匹配" + remark, primary, pkSeq);
            columns.add(eqColumn);
            columns.add(neColumn);
            if (column.getJavaType().equals("String")) {
                Column likeColumn = new Column(name, "like" + humpName, javaType, jdbcType, "模糊匹配" + remark, primary, pkSeq);
                columns.add(likeColumn);
            }
//            Model 的 其他类型字段（如：数值类型，日期类型）的生成策略
//            eqField（等值匹配）
//            neField（等值不匹配）
//            gtField（大于）
//            geField（大于等于）
//            ltField（小于）
//            leField（小于等于）
            else {
                Column gtColumn = new Column(name, "gt" + humpName, javaType, jdbcType, "大于" + remark, primary, pkSeq);
                Column geColumn = new Column(name, "ge" + humpName, javaType, jdbcType, "大于等于" + remark, primary, pkSeq);
                Column ltColumn = new Column(name, "lt" + humpName, javaType, jdbcType, "小于" + remark, primary, pkSeq);
                Column leColumn = new Column(name, "le" + humpName, javaType, jdbcType, "小于等于" + remark, primary, pkSeq);
                columns.add(gtColumn);
                columns.add(geColumn);
                columns.add(ltColumn);
                columns.add(leColumn);
            }
            fields.add(column);
            if ("Date".equals(column.getJavaType())) {
                imports.add("java.util.Date");
            } else if ("BigDecimal".equals(column.getJavaType())) {
                imports.add("java.math.BigDecimal");
            } else if ("Timestamp".equals(column.getJavaType())) {
                imports.add("java.sql.Timestamp");
            }
        });
        args.put("importSets", imports);
        args.put("fields", columns);
        return args;
    }
}
