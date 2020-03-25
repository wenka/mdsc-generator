package com.wenka.mdsc.generator.util;

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
            if ("_".equals(s)){
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

    public static void main(String[] args) {
        System.out.println(getHumpName("g_goods_appraises"));
    }
}
