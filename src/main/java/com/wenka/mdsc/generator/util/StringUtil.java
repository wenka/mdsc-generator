package com.wenka.mdsc.generator.util;

import org.apache.commons.lang.StringUtils;

/**
 * Created with IDEA
 *
 * @author wenka wkwenka@gmail.com
 * @date 2020/03/24  下午 01:33
 * @description:
 */
public class StringUtil {

    /**
     * 获取表字段驼峰命名
     *
     * @param columnName
     * @return
     */
    public static String getHumpName(String columnName) {
        StringBuilder builder = new StringBuilder();
        char[] chars = columnName.toLowerCase().toCharArray();
        boolean isLastChar_ = false;
        for (int i = 0; i < chars.length; i++) {
            String s = String.valueOf(chars[i]);
            if ("_".equals(s)) {
                isLastChar_ = true;
                continue;
            }
            if (isLastChar_) {
                builder.append(s.toUpperCase());
            } else {
                builder.append(s);
            }
            isLastChar_ = false;
        }
        return builder.toString();
    }

    /**
     * 首字母小写
     *
     * @param word
     * @return
     */
    public static String initialsLowerCase(String word) {
        if (StringUtils.isBlank(word)) {
            return "";
        }
        char[] chars = new char[1];
        chars[0] = word.charAt(0);
        String result = word;
        if (chars[0] >= 'A' && chars[0] <= 'Z') {
            String s = new String(chars);
            result = word.replaceFirst(s, s.toLowerCase());
        }
        return result;
    }

    /**
     * 首字母大写
     *
     * @param word
     * @return
     */
    public static String initialsUpperCase(String word) {
        if (StringUtils.isBlank(word)) {
            return "";
        }
        char[] chars = new char[1];
        chars[0] = word.charAt(0);
        String result = word;
        if (chars[0] >= 'a' && chars[0] <= 'z') {
            String s = new String(chars);
            result = word.replaceFirst(s, s.toUpperCase());
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(getHumpName("g_goods_appraises"));
    }
}
