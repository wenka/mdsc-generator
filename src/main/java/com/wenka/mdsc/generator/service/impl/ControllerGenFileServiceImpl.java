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
@Bean
public class ControllerGenFileServiceImpl extends BaseGenFileService {
    /**
     * 获取文件生成位置
     *
     * @param className
     * @return
     */
    @Override
    public String getFilePath(String className) {
        String fileName = Contants.DEFAULT_CONTROLLER_NAME.replace("#", className);
        String classPath = FolderUtil.getModulePath();
        StringBuilder path = new StringBuilder(classPath)
                .append(Contants.PATH_SEPARATOR)
                .append(PropertiesUtil.getValue(PropertiesKey.CONTROLLER_PACKAGE))
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
        return "template/controller.vm";
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
        String controllerPackage = PropertiesUtil.getValue(PropertiesKey.CONTROLLER_PACKAGE);
        String servicePackage = PropertiesUtil.getValue(PropertiesKey.SERVICE_PACKAGE);
        Map<String, Object> args = new HashMap<>();
        args.put("model", parentPackage + "." + modelPackage + "." + className);
        char[] classNameChars = className.toCharArray();
        classNameChars[0] = String.valueOf(classNameChars[0]).toLowerCase().charAt(0);
        args.put("baseUrl", String.valueOf(classNameChars));
        args.put("simpleModel", className);
        args.put("package", parentPackage + "." + controllerPackage);
        args.put("responseModel", PropertiesUtil.getValue(PropertiesKey.RESULT_MODEL));
        args.put("SimplResponseModel", "Result");
        String serviceName = Contants.DEFAULT_SERVICE_NAME.replace("#", className);
        args.put("service", parentPackage + "." + servicePackage + "." + serviceName);
        args.put("simpleService", serviceName);
        char[] chars = serviceName.toCharArray();
        chars[0] = String.valueOf(chars[0]).toLowerCase().charAt(0);
        args.put("simpleServiceV", String.valueOf(chars));
        return args;
    }


}
