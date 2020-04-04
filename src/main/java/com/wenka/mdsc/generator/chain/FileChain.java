package com.wenka.mdsc.generator.chain;

import com.wenka.mdsc.generator.constants.Contants;
import com.wenka.mdsc.generator.constants.PropertiesKey;
import com.wenka.mdsc.generator.context.GeneratorContext;
import com.wenka.mdsc.generator.model.Column;
import com.wenka.mdsc.generator.model.TableInfo;
import com.wenka.mdsc.generator.service.DBService;
import com.wenka.mdsc.generator.service.GenFileService;
import com.wenka.mdsc.generator.util.PropertiesUtil;
import com.wenka.mdsc.generator.util.StringUtil;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * Created with IDEA
 *
 * @author wenka wkwenka@gmail.com
 * @date 2020/03/24  下午 02:45
 * @description:
 */
public class FileChain {

    private List<GenFileService> fileChain;
    private int index = 0;

    private Map<String, Object> args;

    public FileChain() {
        fileChain = new ArrayList<>(5);
    }

    public FileChain addChain(GenFileService genFileService) {
        this.fileChain.add(genFileService);
        return this;
    }

    public FileChain addChain(List<GenFileService> genFileServiceList) {
        for (GenFileService genFileService : genFileServiceList) {
            this.addChain(genFileService);
        }
        return this;
    }


    public void execute(TableInfo tableInfo) {
        // 责任链刚开始时，初始化当前表所对应文件模板的共同参数
        if (index == 0) {
            this.initParams(tableInfo);
        }
        if (index == this.fileChain.size()) {
            return;
        }
        GenFileService genFileService = this.fileChain.get(index);
        index++;
        genFileService.writeFile(tableInfo, this);
    }


    public Map<String, Object> getArgs() {
        return args;
    }

    /**
     * 初始化当前表对应文件的共有参数
     *
     * @param tableInfo
     */
    private void initParams(TableInfo tableInfo) {
        args = new LinkedHashMap<>();
        String className = tableInfo.getClassName();
        args.put("tableName", tableInfo.getTableName());
        // 获取表注释
        DBService dbService = GeneratorContext.getBean(DBService.class);
        String tableRemark = dbService.getTableRemark(tableInfo.getTableName());
        if (StringUtils.isBlank(tableRemark)) {
            tableRemark = tableInfo.getClassName();
        }
        args.put("tableRemark", tableRemark);

        // 加载列及主键
        List<Column> tableColumns = dbService.getTableColumns(tableInfo.getTableName());
        Column c = tableColumns.stream().filter(column -> {
            return column.isPrimary();
        }).sorted((c1, c2) -> {
            return c1.getPkSeq() < c2.getPkSeq() ? 1 : -1;
        }).findFirst().orElse(null);
        args.put("PkName", c.getHumpName());
        args.put("PkType", c.getJavaType());
        args.put("pk", c);

        String parentPackage = PropertiesUtil.getValue(PropertiesKey.PARENT_PACKAGE);
        // Model 全限定名  Model 所在包  Model 简单类名
        String modelPackage = parentPackage + "." + PropertiesUtil.getValue(PropertiesKey.MODEL_PACKAGE);
        args.put("ModelPackage", modelPackage);
        args.put("ModelName", modelPackage + "." + className);
        args.put("ModelSimpleName", className);
        args.put("ModelVariableName", StringUtil.initialsLowerCase(className));

        // Dao 全限定名  Dao 所在包  Dao 简单类名 Dao 类对应变量名(首字母小写的简单类名)
        String daoPackage = parentPackage + "." + PropertiesUtil.getValue(PropertiesKey.DAO_PACKAGE);
        args.put("DaoPackage", daoPackage);
        String daoName = Contants.DEFAULT_DAO_NAME.replace("#", className);
        args.put("DaoName", daoPackage + "." + daoName);
        args.put("DaoSimpleName", daoName);
        args.put("DaoVariableName", StringUtil.initialsLowerCase(daoName));

        // Service 全限定名  Service 所在包  Service 简单类名 Service 类对应变量名(首字母小写的简单类名)
        String servicePackage = parentPackage + "." + PropertiesUtil.getValue(PropertiesKey.SERVICE_PACKAGE);
        args.put("ServicePackage", servicePackage);
        String serviceName = Contants.DEFAULT_SERVICE_NAME.replace("#", className);
        args.put("ServiceName", servicePackage + "." + serviceName);
        args.put("ServiceSimpleName", serviceName);
        args.put("ServiceVariableName", StringUtil.initialsLowerCase(serviceName));

        // ServiceImpl 全限定名  ServiceImpl 所在包  ServiceImpl 简单类名 ServiceImpl 类对应变量名(首字母小写的简单类名)
        String serviceImplPackage = servicePackage + "." + PropertiesUtil.getValue(PropertiesKey.SERVICE_IMPL_PACKAGE);
        args.put("ServiceImplPackage", serviceImplPackage);
        String serviceImplName = Contants.DEFAULT_SERVICE_IMPL_NAME.replace("#", className);
        args.put("ServiceImplName", serviceImplPackage + "." + serviceImplName);
        args.put("ServiceImplSimpleName", serviceImplName);
        args.put("ServiceImplVariableName", StringUtil.initialsLowerCase(serviceImplName));

        // Controller 全限定名  Controller 所在包  Controller 简单类名 Controller 类对应变量名(首字母小写的简单类名)
        String controllerPackage = parentPackage + "." + PropertiesUtil.getValue(PropertiesKey.CONTROLLER_PACKAGE);
        args.put("ControllerPackage", controllerPackage);
        String controllerName = Contants.DEFAULT_CONTROLLER_NAME.replace("#", className);
        args.put("ControllerName", controllerPackage + "." + controllerName);
        args.put("ControllerSimpleName", controllerName);
        args.put("ControllerVariableName", StringUtil.initialsLowerCase(controllerName));
    }
}
