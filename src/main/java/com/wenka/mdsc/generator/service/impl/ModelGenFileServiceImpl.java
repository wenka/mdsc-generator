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
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * Created with IDEA
 *
 * @author wenka wkwenka@gmail.com
 * @date 2020/03/24  下午 02:25
 * @description:
 */
@Bean(order = 0)
public class ModelGenFileServiceImpl extends BaseGenFileService {

    @Importer
    private DBService dbService;

    /**
     * @return
     */
    @Override
    public boolean support() {
        return true;
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
                .append(fileName)
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
        return "template/model.vm";
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
        Map<String, Object> args = new HashMap<>();
        args.put("author", PropertiesUtil.getValue(PropertiesKey.AUTHOR));

        Set<String> imports = new HashSet<>();
        List<Column> fields = new ArrayList<>();

        String ignorColumns = PropertiesUtil.getValue(PropertiesKey.IGNORE_COLUMN);
        List<String> ignoreColumns = Arrays.asList(ignorColumns.split(","));
        tableColumns.stream().forEach(column -> {
            if (ignoreColumns != null && !ignoreColumns.isEmpty() && !ignoreColumns.contains(column.getName())) {
                fields.add(column);
                if ("Date".equals(column.getJavaType())) {
                    imports.add("java.util.Date");
                } else if ("BigDecimal".equals(column.getJavaType())) {
                    imports.add("java.math.BigDecimal");
                } else if ("Timestamp".equals(column.getJavaType())) {
                    imports.add("java.sql.Timestamp");
                }
            }
        });
        String baseModel = PropertiesUtil.getValue(PropertiesKey.BASE_MODEL);
        if (StringUtils.isNotBlank(baseModel)) {
            imports.add(baseModel);
            args.put("extend", baseModel.substring(baseModel.lastIndexOf(".") + 1));
        }
        args.put("importSets", imports);
        args.put("fields", fields);
        return args;
    }


}
