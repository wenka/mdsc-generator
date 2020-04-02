package com.wenka.mdsc.generator.constants;

/**
 * Created with IDEA
 *
 * @author wenka wkwenka@gmail.com
 * @date 2020/03/23  下午 04:12
 * @description: 配置key
 */
public class PropertiesKey {
    /**
     * 精度可选值 high,low
     */
    public final static String PRECISSION = "precission";
    public final static String AUTHOR = "author";

    /**
     * 数据库配置相关
     */
    public final static String JDBC_DRIVER = "jdbc.driver";
    public final static String JDBC_URL = "jdbc.url";
    public final static String JDBC_USERNAME = "jdbc.username";
    public final static String JDBC_PASSWORD = "jdbc.password";

    /**
     * 包配置
     */
    public final static String PARENT_PACKAGE = "parent.package";
    public final static String MODEL_PACKAGE = "model.package";
    public final static String DAO_PACKAGE = "dao.package";
    public final static String SERVICE_PACKAGE = "service.package";
    public final static String SERVICE_IMPL_PACKAGE = "service.impl.package";
    public final static String CONTROLLER_PACKAGE = "controller.package";

    /**
     * 路径配置
     */
    public final static String MODULE_PATH = "module.path";
    public final static String XML_PATH = "xml.path";

    /**
     * 其他配置
     */
    public final static String BASE_MODEL = "base-model";
    public final static String RESULT_MODEL = "result-model";
    public final static String IGNORE_COLUMN = "ignore-column";
    public final static String ID_UTIL = "id-util";

    /**
     * 生成文件等级 dao,service,controller
     */
    public final static String GENERATE_LEVEL = "generate-level";

}
