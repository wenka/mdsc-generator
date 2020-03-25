package com.wenka.mdsc.generator.util;

import java.io.*;

/**
 * Created with IDEA
 *
 * @author wenka wkwenka@gmail.com
 * @date 2020/03/23  下午 05:31
 * @description:
 */
public class FileUtil {

    /**
     * 写文件
     *
     * @param content
     * @param path
     */
    public static void write(String content, String path) {
        File file = new File(path);
        if (!file.exists()) {
            String parent = file.getParent();
            File parentFolder = new File(parent);
            if (!parentFolder.exists()) {
                parentFolder.mkdirs();
            }
        } else {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        OutputStreamWriter writerStream = null;
        try {
            writerStream = new OutputStreamWriter(new FileOutputStream(path), "UTF-8");
            BufferedWriter bufferedWriter = new BufferedWriter(writerStream);
            bufferedWriter.write(content);
            bufferedWriter.flush();
            bufferedWriter.close();
            System.out.println("=================" + path + " 生成成功！=====================");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
