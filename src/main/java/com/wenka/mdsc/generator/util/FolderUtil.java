package com.wenka.mdsc.generator.util;

import com.wenka.mdsc.generator.constants.Contants;
import com.wenka.mdsc.generator.constants.PropertiesKey;
import org.apache.commons.lang.StringUtils;

/**
 * Created with IDEA
 *
 * @author wenka wkwenka@gmail.com
 * @date 2020/03/24  上午 09:38
 * @description: 变量读取
 */
public class FolderUtil {

    public static void readPackageValue() {
        PropertiesUtil.putIfAbsent(PropertiesKey.MODEL_PACKAGE, Contants.DEFAULT_MODEL);
        PropertiesUtil.putIfAbsent(PropertiesKey.DAO_PACKAGE, Contants.DEFAULT_DAO);
        PropertiesUtil.putIfAbsent(PropertiesKey.SERVICE_PACKAGE, Contants.DEFAULT_SERVICE);
        PropertiesUtil.putIfAbsent(PropertiesKey.SERVICE_IMPL_PACKAGE, Contants.DEFAULT_SERVICE_IMPL);
        PropertiesUtil.putIfAbsent(PropertiesKey.CONTROLLER_PACKAGE, Contants.DEFAULT_CONTROLLER);
        PropertiesUtil.putIfAbsent(PropertiesKey.XML_PATH, Contants.DEFAULT_XML);
    }

    /**
     * 获取类生成路径
     *
     * @return
     */
    private static String getClassPath() {
        String modulePath = PropertiesUtil.getValue(PropertiesKey.MODULE_PATH);
        return modulePath + Contants.PATH_SEPARATOR + Contants.CLASS_PATH;
    }

    /**
     * 获取资源生成路径
     *
     * @return
     */
    public static String getResourcePath() {
        String modulePath = PropertiesUtil.getValue(PropertiesKey.MODULE_PATH);
        return modulePath + Contants.PATH_SEPARATOR + Contants.XML_PATH;
    }

    /**
     * 获取代码生成跟路径
     *
     * @return
     */
    public static String getModulePath() {
        String parentPackage = PropertiesUtil.getValue(PropertiesKey.PARENT_PACKAGE);
        StringBuilder stringBuilder = new StringBuilder(getClassPath());
        if (StringUtils.isNotBlank(parentPackage)) {
            String[] packageArr = parentPackage.split(Contants.PACKAGE_SEPARATOR);
            for (int i = 0; i < packageArr.length; i++) {
                stringBuilder.append(Contants.PATH_SEPARATOR)
                        .append(packageArr[i]);
            }
        }
        return stringBuilder.toString();
    }
}
