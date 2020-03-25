package com.wenka.mdsc.generator.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created with IDEA
 *
 * @author wenka wkwenka@gmail.com
 * @date 2020/03/23  下午 04:02
 * @description:
 */
public class PropertiesUtil {

    private static Properties properties;

    static {
        properties = new Properties();
        InputStream resourceAsStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("generator.properties");
        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                resourceAsStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getValue(String name) {
        return properties.getProperty(name);
    }

    public static void putIfAbsent(String key, String value) {
        properties.putIfAbsent(key, value);
    }

}
