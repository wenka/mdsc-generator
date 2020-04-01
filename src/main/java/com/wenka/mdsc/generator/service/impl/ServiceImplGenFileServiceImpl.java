package com.wenka.mdsc.generator.service.impl;

import com.wenka.mdsc.generator.annotation.Bean;
import com.wenka.mdsc.generator.constants.Contants;
import com.wenka.mdsc.generator.constants.PropertiesKey;
import com.wenka.mdsc.generator.model.TableInfo;
import com.wenka.mdsc.generator.service.BaseGenFileService;
import com.wenka.mdsc.generator.util.FolderUtil;
import com.wenka.mdsc.generator.util.PropertiesUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IDEA
 *
 * @author wenka wkwenka@gmail.com
 * @date 2020/03/24  下午 02:25
 * @description:
 */
@Bean(order = 5)
public class ServiceImplGenFileServiceImpl extends BaseGenFileService {
    /**
     * 获取文件生成位置
     *
     * @param className
     * @return
     */
    @Override
    public String getFilePath(String className) {
        String fileName = Contants.DEFAULT_SERVICE_IMPL_NAME.replace("#", className);
        String classPath = FolderUtil.getModulePath();
        StringBuilder path = new StringBuilder(classPath)
                .append(Contants.PATH_SEPARATOR)
                .append(PropertiesUtil.getValue(PropertiesKey.SERVICE_PACKAGE))
                .append(Contants.PATH_SEPARATOR)
                .append(PropertiesUtil.getValue(PropertiesKey.SERVICE_IMPL_PACKAGE))
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
        return "template/serviceImpl.vm";
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
        String servicePackage = PropertiesUtil.getValue(PropertiesKey.SERVICE_PACKAGE);
        String serviceImplPackage = PropertiesUtil.getValue(PropertiesKey.SERVICE_IMPL_PACKAGE);
        String daoPackage = PropertiesUtil.getValue(PropertiesKey.DAO_PACKAGE);
        Map<String, Object> args = new HashMap<>();
        args.put("package", parentPackage + "." + servicePackage + "." + serviceImplPackage);
        args.put("model", parentPackage + "." + modelPackage + "." + className);
        String daoName = Contants.DEFAULT_MAPPER_NAME.replace("#", className);
        args.put("dao", parentPackage + "." + daoPackage + "." + daoName);

        String serviceName = Contants.DEFAULT_SERVICE_NAME.replace("#", className);
        args.put("service", parentPackage + "." + servicePackage + "." + serviceName);
        args.put("idUtil", PropertiesUtil.getValue(PropertiesKey.ID_UTIL));
        args.put("simpleModel", className);
        args.put("simpleDao", daoName);
        char[] chars = daoName.toCharArray();
        chars[0] = String.valueOf(chars[0]).toLowerCase().charAt(0);
        args.put("simpleDaoV", String.valueOf(chars));

        return args;
    }


}
