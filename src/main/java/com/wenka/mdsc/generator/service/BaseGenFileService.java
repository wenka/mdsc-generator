package com.wenka.mdsc.generator.service;

import com.wenka.mdsc.generator.chain.FileChain;
import com.wenka.mdsc.generator.model.TableInfo;
import com.wenka.mdsc.generator.util.FileUtil;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.StringWriter;
import java.util.Map;

/**
 * Created with IDEA
 *
 * @author wenka wkwenka@gmail.com
 * @date 2020/03/24  下午 02:40
 * @description:
 */
public abstract class BaseGenFileService implements GenFileService {

    /**
     * 生成文件
     *
     * @param tableInfo
     */
    @Override
    public void writeFile(TableInfo tableInfo, FileChain fileChain) {
        // 获取模板
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();
        Template template = ve.getTemplate(this.vmName(), "UTF-8");

        // 填充数据
        VelocityContext ctx = new VelocityContext();
        Map<String, Object> templateData = this.templateData(tableInfo);
        for (String key : templateData.keySet()) {
            ctx.put(key, templateData.get(key));
        }
        StringWriter writer = new StringWriter();
        template.merge(ctx, writer);
        IOUtils.closeQuietly(writer);
        String content = writer.toString();
        // 生成文件
        FileUtil.write(content, this.getFilePath(tableInfo.getClassName()));
        fileChain.execute(tableInfo);
    }

}
