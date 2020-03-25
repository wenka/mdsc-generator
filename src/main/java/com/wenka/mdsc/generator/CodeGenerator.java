package com.wenka.mdsc.generator;


import com.wenka.mdsc.generator.chain.FileChain;
import com.wenka.mdsc.generator.config.DBConfig;
import com.wenka.mdsc.generator.constants.PropertiesKey;
import com.wenka.mdsc.generator.context.GeneratorContext;
import com.wenka.mdsc.generator.model.TableInfo;
import com.wenka.mdsc.generator.service.GenFileService;
import com.wenka.mdsc.generator.util.FolderUtil;
import com.wenka.mdsc.generator.util.PropertiesUtil;
import com.wenka.mdsc.generator.util.XmlUtil;

import java.util.List;
import java.util.Map;

/**
 * Created with IDEA
 *
 * @author wenka wkwenka@gmail.com
 * @date 2020/03/23  下午 02:57
 * @description:
 */
public class CodeGenerator {

    /**
     * 模板存放文件夹
     */
    public static String VM_TARGET_PATH = "template";

    public static void run() {
        init();
        BeanFactory.start();
    }


    /**
     * 初始化上下文
     */
    private static void initContext() {
        String driver = PropertiesUtil.getValue(PropertiesKey.JDBC_DRIVER);
        String url = PropertiesUtil.getValue(PropertiesKey.JDBC_URL);
        String username = PropertiesUtil.getValue(PropertiesKey.JDBC_USERNAME);
        String password = PropertiesUtil.getValue(PropertiesKey.JDBC_PASSWORD);
        DBConfig dbConfig = new DBConfig().setDriver(driver).setUrl(url).setUsername(username).setPassword(password);
        GeneratorContext.register(dbConfig);

    }

    /**
     * 初始化全局配置
     */
    private static void initGlobalValue() {
        FolderUtil.readPackageValue();
    }

    /**
     * 初始化XML配置
     */
    private static void initXMLValue() {
        XmlUtil.readTableXml();
    }

    private static void init() {
        initGlobalValue();
        initContext();
        initXMLValue();
    }

    /**
     * 生成文件
     */
    public static void gen() {
        // 加载bean 读取配置
        run();

        // 注册责任链
        FileChain fileChain = new FileChain();
        List<GenFileService> beans = GeneratorContext.getBeans(GenFileService.class);
        fileChain.addChain(beans);

        // 生成文件
        Map<String, TableInfo> allTable = GeneratorContext.getAllTable();
        for (String table : allTable.keySet()) {
            TableInfo tableInfo = allTable.get(table);
            fileChain.execute(tableInfo);
        }

    }
}
