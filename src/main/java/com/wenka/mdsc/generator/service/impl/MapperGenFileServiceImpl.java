package com.wenka.mdsc.generator.service.impl;

import com.wenka.mdsc.generator.annotation.Bean;
import com.wenka.mdsc.generator.constants.Contants;
import com.wenka.mdsc.generator.constants.PropertiesKey;
import com.wenka.mdsc.generator.context.GeneratorContext;
import com.wenka.mdsc.generator.model.Column;
import com.wenka.mdsc.generator.model.TableInfo;
import com.wenka.mdsc.generator.service.BaseGenFileService;
import com.wenka.mdsc.generator.service.DBService;
import com.wenka.mdsc.generator.util.FolderUtil;
import com.wenka.mdsc.generator.util.PropertiesUtil;

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
@Bean
public class MapperGenFileServiceImpl extends BaseGenFileService {
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
        return "template/mapper.vm";
    }

    /**
     * 模板数据
     *
     * @param tableInfo
     * @return
     */
    @Override
    public Map<String, Object> templateData(TableInfo tableInfo) {
        String className = tableInfo.getClassName();
        String parentPackage = PropertiesUtil.getValue(PropertiesKey.PARENT_PACKAGE);
        String modelPackage = PropertiesUtil.getValue(PropertiesKey.MODEL_PACKAGE);
        String daoPackage = PropertiesUtil.getValue(PropertiesKey.DAO_PACKAGE);
        Map<String, Object> args = new HashMap<>();
        args.put("model", parentPackage + "." + modelPackage + "." + className);
        String daoName = Contants.DEFAULT_MAPPER_NAME.replace("#", className);
        args.put("dao", parentPackage + "." + daoPackage + "." + daoName);
        args.put("tableName", tableInfo.getTableName());

        DBService dbService = GeneratorContext.getBean(DBServiceImpl.class);
        List<Column> tableColumns = dbService.getTableColumns(tableInfo.getTableName());
        args.put("columns", tableColumns);
        return args;
    }


}
