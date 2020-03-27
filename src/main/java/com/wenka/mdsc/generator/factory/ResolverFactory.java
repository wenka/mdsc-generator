package com.wenka.mdsc.generator.factory;

import com.wenka.mdsc.generator.CodeGenerator;
import com.wenka.mdsc.generator.resolve.FileSystemClassPathBeanScannerResolver;
import com.wenka.mdsc.generator.resolve.JarClassPathBeanScannerResolver;

import java.net.URL;

/**
 * Created with IDEA
 *
 * @author wenka wkwenka@gmail.com
 * @date 2020/03/27  下午 02:13
 * @description:
 */
public class ResolverFactory extends AbstractFactory {

    public <T> T createBean(Class<T> tClass) {
        Package aPackage = CodeGenerator.class.getPackage();
        //1. 获取当前包名
        String currentPackage = aPackage.getName();
        String path = currentPackage.replace(".", "/");
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource(path);
        if (url.toString().startsWith("file:")) {
            return (T) new FileSystemClassPathBeanScannerResolver();
        } else {
            return (T) new JarClassPathBeanScannerResolver();
        }
    }

}
