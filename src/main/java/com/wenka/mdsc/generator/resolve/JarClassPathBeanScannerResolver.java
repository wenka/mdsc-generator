package com.wenka.mdsc.generator.resolve;

import com.wenka.mdsc.generator.CodeGenerator;
import com.wenka.mdsc.generator.annotation.Bean;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created with IDEA
 *
 * @author wenka wkwenka@gmail.com
 * @date 2020/03/27  下午 01:27
 * @description: jar 包下 bean class 扫描器
 */
public class JarClassPathBeanScannerResolver implements ClassPathBeanScanner {

    /**
     * 扫描路径下复核规格的 bean class
     *
     * @param packageName
     * @return
     */
    public Set<Class<?>> scanBeanClasses(String packageName) {
        String path = packageName.replace(".", "/");
        Set<Class<?>> classSet = new HashSet<>();
        File file = new File(CodeGenerator.class.getProtectionDomain().getCodeSource().getLocation().getFile());
        JarFile jfile = null;
        try {
            jfile = new JarFile(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Enumeration<JarEntry> entries = jfile.entries();
        try {

            while (entries.hasMoreElements()) {
                JarEntry jarEntry = entries.nextElement();
                String name = jarEntry.getName();
                if (name.contains(path) && name.endsWith(".class")) {
                    String className = name.substring(0, name.length() - 6);
                    className = className.replace("/", ".");
                    Class<?> aClass = Thread.currentThread().getContextClassLoader().loadClass(className);
                    Annotation annotation = aClass.getAnnotation(Bean.class);
                    if (annotation != null) {
                        classSet.add(aClass);
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return classSet;
    }
}
