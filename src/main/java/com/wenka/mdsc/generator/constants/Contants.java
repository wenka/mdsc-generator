package com.wenka.mdsc.generator.constants;

/**
 * Created with IDEA
 *
 * @author wenka wkwenka@gmail.com
 * @date 2020/03/23  下午 04:11
 * @description:
 */
public class Contants {

    public final static String PATH_SEPARATOR = "\\";
    public final static String PACKAGE_SEPARATOR = "\\.";

    public final static String CLASS_PATH = "src\\main\\java"; // 类相对路径
    public final static String XML_PATH = "src\\main\\resources"; // 类相对路径

    /**
     * 包默认值
     */
    public final static String DEFAULT_MODEL = "model";
    public final static String DEFAULT_MODEL_PO = "po";
    public final static String DEFAULT_DAO = "mapper";
    public final static String DEFAULT_SERVICE = "service";
    public final static String DEFAULT_SERVICE_IMPL = "impl";
    public final static String DEFAULT_CONTROLLER = "controller";
    public final static String DEFAULT_XML = "mappers";

    /**
     * 文件名模板
     */
    public final static String DEFAULT_MODEL_NAME = "#";
    public final static String DEFAULT_DAO_NAME = "#Mapper";
    public final static String DEFAULT_SERVICE_NAME = "#Service";
    public final static String DEFAULT_SERVICE_IMPL_NAME = "#ServiceImpl";
    public final static String DEFAULT_CONTROLLER_NAME = "#Controller";
    public final static String DEFAULT_MAPPER_NAME = "#Mapper";
}
