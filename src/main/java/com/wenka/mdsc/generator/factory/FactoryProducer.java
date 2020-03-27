package com.wenka.mdsc.generator.factory;

import com.wenka.mdsc.generator.constants.FactoryType;

/**
 * Created with IDEA
 *
 * @author wenka wkwenka@gmail.com
 * @date 2020/03/27  下午 02:18
 * @description:
 */
public class FactoryProducer {

    public static AbstractFactory getFactory(FactoryType factoryType) {
        switch (factoryType) {
            case BEAN:
                return new BeanFactory();
            case RESOLVER:
                return new ResolverFactory();
        }
        return null;
    }

}
