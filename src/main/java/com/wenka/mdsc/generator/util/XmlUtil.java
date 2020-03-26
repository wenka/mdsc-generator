package com.wenka.mdsc.generator.util;

import com.wenka.mdsc.generator.context.GeneratorContext;
import com.wenka.mdsc.generator.model.TableInfo;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

/**
 * Created with IDEA
 *
 * @author wenka wkwenka@gmail.com
 * @date 2020/03/24  上午 10:12
 * @description:
 */
public class XmlUtil {

    private final static String XML = "table.xml";
    private final static String DTD = "dtd\\table.dtd";

    public static void readTableXml() {
        try {

//            ClasspathResourceLoader classpathResourceLoader = new ClasspathResourceLoader();
//            InputStream stream = classpathResourceLoader.getResourceStream(XML);
            URL resource = XmlUtil.class.getClassLoader().getResource(XML);
            File file = new File(resource.getFile());
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            documentBuilder.setEntityResolver((String publicId, String systemId) -> {
                String suffix = ".dtd";
                if (systemId.contains(suffix)) {
                    InputStream inputStream = XmlUtil.class.getClassLoader().getResourceAsStream(DTD);
//                    InputStream inputStream = classpathResourceLoader.getResourceStream(XML);
                    return new InputSource(inputStream);
                }
                return null;
            });
            Document document = documentBuilder.parse(new FileInputStream(file));
//            Document document = documentBuilder.parse(stream);

            NodeList tables = document.getElementsByTagName("tables");

            for (int i = 0; i < tables.getLength(); i++) {
                String tableName = document.getElementsByTagName("table-name").item(i).getFirstChild().getNodeValue();
                String className = document.getElementsByTagName("class-name").item(i).getFirstChild().getNodeValue();
                TableInfo tableInfo = new TableInfo();
                tableInfo.setTableName(tableName);
                tableInfo.setClassName(className);
                GeneratorContext.addTable(tableName, tableInfo);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
