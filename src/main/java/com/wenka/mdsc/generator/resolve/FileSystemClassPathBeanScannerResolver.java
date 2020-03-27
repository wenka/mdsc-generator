package com.wenka.mdsc.generator.resolve;

import com.wenka.mdsc.generator.annotation.Bean;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IDEA
 *
 * @author wenka wkwenka@gmail.com
 * @date 2020/03/27  下午 01:26
 * @description: 文件系统下 bean class 扫描器
 */
public class FileSystemClassPathBeanScannerResolver implements ClassPathBeanScanner {


    /**
     * 扫描路径下复核规格的 bean class
     *
     * @param packageName
     * @return
     */
    public Set<Class<?>> scanBeanClasses(String packageName) {
        String path = packageName.replace(".", "/");
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource(path);
        return this.fileRead(url, packageName);
    }

    public Set<Class<?>> fileRead(URL url, String currentPackageName) {
        Set<Class<?>> classSet = new HashSet<>();
        try {

            if ("file".equals(url.getProtocol())) {
                System.out.println("file 类型扫描");
                // 获取包的物理路径
                String filePath = null;
                filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                // 以文件的方式扫描整个包下的文件 并添加到集合中
                this.findAndAddClassesInPackageByFile(currentPackageName, filePath, classSet);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return classSet;
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
}
