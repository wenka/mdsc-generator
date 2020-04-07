package com.wenka.mdsc.generator.service.impl;

import com.wenka.mdsc.generator.annotation.Bean;
import com.wenka.mdsc.generator.annotation.Importer;
import com.wenka.mdsc.generator.constants.Contants;
import com.wenka.mdsc.generator.constants.PropertiesKey;
import com.wenka.mdsc.generator.model.Column;
import com.wenka.mdsc.generator.model.ParamColumn;
import com.wenka.mdsc.generator.model.TableInfo;
import com.wenka.mdsc.generator.service.BaseGenFileService;
import com.wenka.mdsc.generator.service.DBService;
import com.wenka.mdsc.generator.util.FolderUtil;
import com.wenka.mdsc.generator.util.PropertiesUtil;
import com.wenka.mdsc.generator.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IDEA
 *
 * @author wenka wkwenka@gmail.com
 * @date 2020/03/24  下午 02:25
 * @description:
 */
@Bean(order = 3)
public class MapperPlusGenFileServiceImpl extends BaseGenFileService {

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
        String fileName = Contants.DEFAULT_MAPPER_NAME.replace("#", className);
        String resourcePath = FolderUtil.getResourcePath();
        StringBuilder path = new StringBuilder(resourcePath)
                .append(Contants.PATH_SEPARATOR)
                .append(PropertiesUtil.getValue(PropertiesKey.XML_PATH))
                .append(Contants.PATH_SEPARATOR)
                .append(fileName)
                .append(".xml");
        return path.toString();
    }

    /**
     * 获取模板名称
     *
     * @return
     */
    @Override
    public String vmName() {
        return "template/mapper-plus.vm";
    }

    /**
     * 模板数据
     *
     * @param tableInfo
     * @return
     */
    @Override
    public Map<String, Object> templateData(TableInfo tableInfo) {
        Map<String, Object> args = new HashMap<>();
        List<Column> tableColumns = this.dbService.getTableColumns(tableInfo.getTableName());
        args.put("columns", tableColumns);
        List<Column> conditionColumns = new ArrayList<>(tableColumns.size());
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
            Column eqColumn = new ParamColumn("=")
                    .setName(name).setHumpName("eq" + humpName)
                    .setJavaType(javaType).setJdbcType(jdbcType)
                    .setRemark("等值匹配" + remark).setPrimary(primary).setPkSeq(pkSeq);
            conditionColumns.add(eqColumn);
            Column neColumn = new ParamColumn("&lt;&gt;")
                    .setName(name).setHumpName("ne" + humpName)
                    .setJavaType(javaType).setJdbcType(jdbcType)
                    .setRemark("等值不匹配" + remark).setPrimary(primary).setPkSeq(pkSeq);
            conditionColumns.add(neColumn);
            if (column.getJavaType().equals("String")) {
                Column likeColumn = new ParamColumn("like")
                        .setName(name).setHumpName("like" + humpName)
                        .setJavaType(javaType).setJdbcType(jdbcType)
                        .setRemark("模糊匹配" + remark).setPrimary(primary).setPkSeq(pkSeq);
                conditionColumns.add(likeColumn);
            }
            else {
                Column gtColumn = new ParamColumn("&gt;")
                        .setName(name).setHumpName("gt" + humpName)
                        .setJavaType(javaType).setJdbcType(jdbcType)
                        .setRemark("大于" + remark).setPrimary(primary).setPkSeq(pkSeq);
                Column geColumn = new ParamColumn("&gt;=")
                        .setName(name).setHumpName("ge" + humpName)
                        .setJavaType(javaType).setJdbcType(jdbcType)
                        .setRemark("大于等于" + remark).setPrimary(primary).setPkSeq(pkSeq);
                Column ltColumn = new ParamColumn("&lt;")
                        .setName(name).setHumpName("lt" + humpName)
                        .setJavaType(javaType).setJdbcType(jdbcType)
                        .setRemark("小于" + remark).setPrimary(primary).setPkSeq(pkSeq);

                Column leColumn = new ParamColumn("&lt;=")
                        .setName(name).setHumpName("le" + humpName)
                        .setJavaType(javaType).setJdbcType(jdbcType)
                        .setRemark("小于等于" + remark).setPrimary(primary).setPkSeq(pkSeq);
                conditionColumns.add(gtColumn);
                conditionColumns.add(geColumn);
                conditionColumns.add(ltColumn);
                conditionColumns.add(leColumn);
            }
        });
        args.put("conditionColumns", conditionColumns);

        return args;
    }


}
