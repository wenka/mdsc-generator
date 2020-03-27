package com.wenka.mdsc.generator.resolve;

import java.util.Set;

/**
 * Created with IDEA
 *
 * @author wenka wkwenka@gmail.com
 * @date 2020/03/27  下午 01:22
 * @description: class path Bean扫描器，扫描带有 {@code @com.wenka.mdsc.generator.annotation.Bean}注解的 bean。
 */
public interface ClassPathBeanScanner {

    /**
     * 扫描当前包及其子包下符合规格的 bean class
     *
     * @param packageName
     * @return
     */
    Set<Class<?>> scanBeanClasses(String packageName);

}
