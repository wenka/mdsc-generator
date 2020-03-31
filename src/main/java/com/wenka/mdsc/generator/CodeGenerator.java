package com.wenka.mdsc.generator;


import com.wenka.mdsc.generator.chain.FileChain;
import com.wenka.mdsc.generator.constants.FactoryType;
import com.wenka.mdsc.generator.context.GeneratorContext;
import com.wenka.mdsc.generator.factory.AbstractFactory;
import com.wenka.mdsc.generator.factory.FactoryProducer;
import com.wenka.mdsc.generator.model.TableInfo;
import com.wenka.mdsc.generator.resolve.ClassPathBeanScanner;
import com.wenka.mdsc.generator.service.GenFileService;
import com.wenka.mdsc.generator.util.FolderUtil;
import com.wenka.mdsc.generator.util.XmlUtil;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IDEA
 *
 * @author wenka wkwenka@gmail.com
 * @date 2020/03/23  下午 02:57
 * @description:
 */
public class CodeGenerator {

    public static void run() {
        init();
    }


    /**
     * 初始化上下文
     */
    private static void initContext() {
        AbstractFactory resolverFactory = FactoryProducer.getFactory(FactoryType.RESOLVER);
        ClassPathBeanScanner beanScanner = resolverFactory.createBean(ClassPathBeanScanner.class);
        Set<Class<?>> classes = beanScanner.scanBeanClasses(CodeGenerator.class.getPackage().getName());

        AbstractFactory beanFactory = FactoryProducer.getFactory(FactoryType.BEAN);
        for (Class c : classes) {
            GeneratorContext.register(beanFactory.createBean(c));
        }
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
//        XmlUtil.readTableXml();
        XmlUtil.readXml();
    }

    private static void init() {
        initXMLValue();
        initGlobalValue();
        initContext();
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
