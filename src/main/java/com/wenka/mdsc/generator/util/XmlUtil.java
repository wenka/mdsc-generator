package com.wenka.mdsc.generator.util;

import com.wenka.mdsc.generator.context.GeneratorContext;
import com.wenka.mdsc.generator.model.TableInfo;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.function.Function;

/**
 * Created with IDEA
 *
 * @author wenka wkwenka@gmail.com
 * @date 2020/03/24  上午 10:12
 * @description:
 */
public class XmlUtil {

    private final static String XML_NEW = "generator.xml";



    public static void readXml() {
        try {
            Document document = readXML(XML_NEW);
            Element documentElement = document.getDocumentElement();
            readTableSetting(documentElement);
            readPropertiesSetting(documentElement);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 读取 XML 文件
     *
     * @return
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    private static Document readXML(String xmlPath) throws ParserConfigurationException, IOException, SAXException {
        URL resource = XmlUtil.class.getClassLoader().getResource(xmlPath);
        File file = new File(resource.getFile());
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        Document document = documentBuilder.parse(new FileInputStream(file));
        return document;
    }

    private static void readNode(Node node, Function<Node, Boolean> function) {
        NodeList childNodes = node.getChildNodes();
        if (childNodes.getLength() > 1) {
//            System.out.println(node.getNodeName() + " 孩子节点个数：" + childNodes.getLength());
        } else {
            function.apply(node);
        }

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            if (item.getNodeType() == Node.ELEMENT_NODE) {
                readNode(childNodes.item(i), function);
            }
        }
    }

    /**
     * 读取 table 配置
     *
     * @param documentElement
     */
    private static void readTableSetting(Element documentElement) {
        NodeList table = documentElement.getElementsByTagName("tables");
        for (int i = 0; i < table.getLength(); i++) {
            readNode(table.item(i), node -> {
                NamedNodeMap attributes = node.getAttributes();
                TableInfo tableInfo = new TableInfo();
                String tableName = attributes.getNamedItem("table-name").getNodeValue();
                String className = attributes.getNamedItem("class-name").getNodeValue();
                tableInfo.setTableName(tableName);
                tableInfo.setClassName(className);
                GeneratorContext.addTable(tableName, tableInfo);
                return true;
            });
        }
    }

    /**
     * 读取 properties 配置
     *
     * @param documentElement
     */
    private static void readPropertiesSetting(Element documentElement) {
        NodeList properties = documentElement.getElementsByTagName("properties");
        for (int i = 0; i < properties.getLength(); i++) {
            readNode(properties.item(i), node -> {
                String textContent = node.getTextContent();
                PropertiesUtil.put(node.getNodeName(), textContent);
                return true;
            });
        }
    }

    public static void main(String[] args) {
        readXml();
    }
}
