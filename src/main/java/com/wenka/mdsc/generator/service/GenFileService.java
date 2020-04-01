package com.wenka.mdsc.generator.service;

import com.wenka.mdsc.generator.chain.FileChain;
import com.wenka.mdsc.generator.model.TableInfo;

import java.util.Map;

/**
 * Created with IDEA
 *
 * @author wenka wkwenka@gmail.com
 * @date 2020/03/24  下午 01:53
 * @description:
 */
public interface GenFileService {

    /**
     *
     * @return
     */
    boolean support();

    /**
     * 获取文件生成位置
     *
     * @param className
     * @return
     */
    String getFilePath(String className);

    /**
     * 获取模板名称
     *
     * @return
     */
    String vmName();

    /**
     * 模板数据
     *
     * @param tableInfo
     * @return
     */
    Map<String, Object> templateData(TableInfo tableInfo);

    /**
     * 生成文件
     *
     * @param tableInfo
     */
    void writeFile(TableInfo tableInfo, FileChain fileChain);
}
