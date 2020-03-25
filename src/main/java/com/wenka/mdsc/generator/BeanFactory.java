package com.wenka.mdsc.generator;

import com.wenka.mdsc.generator.annotation.Bean;
import com.wenka.mdsc.generator.context.GeneratorContext;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created with IDEA
 *
 * @author wenka wkwenka@gmail.com
 * @date 2020/03/25  上午 09:38
 * @description: bean 创建 扫描当前包及其子包下的 带有 @Bean注解的java类，将起注册到 GeneratorContext 中。
 */
public class BeanFactory {
    private final static Set<Class<?>> classes = new LinkedHashSet<Class<?>>();

    public static void start() {
        try {

            Package aPackage = BeanFactory.class.getPackage();
            //1. 获取当前包名
            String currentPackage = aPackage.getName();
            // 第一个class类的集合
            //2. 将包名转为路径
            String path = currentPackage.replace(".", "/");
            Enumeration<URL> resource = Thread.currentThread().getContextClassLoader().getResources(path);
            while (resource.hasMoreElements()) {
                URL url = resource.nextElement();
                String protocol = url.getProtocol();
                System.out.println(url.getFile());
                if ("file".equals(protocol)) {
                    System.out.println("file 类型扫描");
                    // 获取包的物理路径
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    // 以文件的方式扫描整个包下的文件 并添加到集合中
                    findAndAddClassesInPackageByFile(currentPackage, filePath, classes);
                }
            }
            // 注册 bean
            register();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void findAndAddClassesInPackageByFile(String packageName, String packagePath, Set<Class<?>> classes) throws ClassNotFoundException {
        File dir = new File(packagePath);
        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println(dir + "下没有任何文件");
        }

        // 获取当前路径下的文件及文件夹
        File[] files = dir.listFiles((File file, String name) -> {
            return file.isDirectory() || name.endsWith(".class");
        });

        for (File file : files) {
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), classes);
            } else {
                // 如果是java类文件 去掉后面的.class 只留下类名
                String className = file.getName().substring(0, file.getName().length() - 6);
                Class<?> aClass = Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className);
                Annotation annotation = aClass.getAnnotation(Bean.class);
                if (annotation != null) {
                    classes.add(aClass);
                }
            }
        }
    }

    private static void register() throws IllegalAccessException, InstantiationException {
        for (Class c : classes) {
            GeneratorContext.register(c.newInstance());
        }
    }
}
