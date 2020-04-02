package com.wenka.mdsc.generator.service.impl;

import com.wenka.mdsc.generator.annotation.Bean;
import com.wenka.mdsc.generator.constants.Contants;
import com.wenka.mdsc.generator.constants.PropertiesKey;
import com.wenka.mdsc.generator.model.TableInfo;
import com.wenka.mdsc.generator.service.BaseGenFileService;
import com.wenka.mdsc.generator.util.FolderUtil;
import com.wenka.mdsc.generator.util.PropertiesUtil;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * Created with IDEA
 *
 * @author wenka wkwenka@gmail.com
 * @date 2020/03/24  下午 02:25
 * @description:
 */
@Bean(order = 4)
public class ServiceGenFileServiceImpl extends BaseGenFileService {
    /**
     * @return
     */
    @Override
    public boolean support() {
        String level = PropertiesUtil.getValue(PropertiesKey.GENERATE_LEVEL);
        return StringUtils.isNotBlank(level) && (level.equals("service") || level.equals("controller"));
    }

    /**
     * 获取文件生成位置
     *
     * @param className
     * @return
     */
    @Override
    public String getFilePath(String className) {
        String fileName = Contants.DEFAULT_SERVICE_NAME.replace("#", className);
        String classPath = FolderUtil.getModulePath();
        StringBuilder path = new StringBuilder(classPath)
                .append(Contants.PATH_SEPARATOR)
                .append(PropertiesUtil.getValue(PropertiesKey.SERVICE_PACKAGE))
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
        return "template/service.vm";
    }

    /**
     * 模板数据
     *
     * @param tableInfo
     * @return
     */
    @Override
    public Map<String, Object> templateData(TableInfo tableInfo) {
        return null;
    }


}
