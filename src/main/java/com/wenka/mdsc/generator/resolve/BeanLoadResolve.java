package com.wenka.mdsc.generator.resolve;

import com.wenka.mdsc.generator.CodeGenerator;
import com.wenka.mdsc.generator.chain.BeanFieldChain;
import com.wenka.mdsc.generator.constants.FactoryType;
import com.wenka.mdsc.generator.context.GeneratorContext;
import com.wenka.mdsc.generator.factory.AbstractFactory;
import com.wenka.mdsc.generator.factory.FactoryProducer;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Created with IDEA
 *
 * @author wenka wkwenka@gmail.com
 * @date 2020/04/04  上午 11:40
 * @description: bean 加载器
 * <p>
 * 1. 加载容器的 bean
 * 2. 扫描容器中的 bean 字段，给带有 容器注入注解 的字段赋值
 * </p>
 */
public class BeanLoadResolve {

    private volatile static BeanLoadResolve beanLoadResolve;

    private BeanLoadResolve() {
    }

    public static BeanLoadResolve getInstance() {
        if (beanLoadResolve == null) {
            synchronized (BeanLoadResolve.class) {
                if (beanLoadResolve == null) {
                    beanLoadResolve = new BeanLoadResolve();
                }
            }
        }
        return beanLoadResolve;
    }


    public void load() {
        // 1. 加载所有 bean 至容器
        this.loadBean();
        // 2. 字段赋值
        this.assignment();
    }

    private void assignment() {
        List<Object> beans = GeneratorContext.getBeans();
        if (Objects.isNull(beans) || beans.isEmpty()) {
            return;
        }
        Iterator<Object> iterator = beans.iterator();
        try {
            while (iterator.hasNext()) {
                Object o = iterator.next();
                Class<?> tClass = o.getClass();
                Field[] declaredFields = tClass.getDeclaredFields();
                if (declaredFields.length == 0) {
                    continue;
                }
                BeanFieldChain beanFieldChain = new BeanFieldChain();
                for (Field field : declaredFields) {
                    beanFieldChain.setValue(field, o);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void loadBean() {
        AbstractFactory resolverFactory = FactoryProducer.getFactory(FactoryType.RESOLVER);
        ClassPathBeanScanner beanScanner = resolverFactory.createBean(ClassPathBeanScanner.class);
        Set<Class<?>> classes = beanScanner.scanBeanClasses(CodeGenerator.class.getPackage().getName());

        AbstractFactory beanFactory = FactoryProducer.getFactory(FactoryType.BEAN);
        for (Class c : classes) {
            GeneratorContext.register(beanFactory.createBean(c));
        }
    }
}
