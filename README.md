# mdsc-generator
mdsc(model、dao、service、controller)。根据数据库表自动生成基于Spring注解，Mybatis对应model、dao、service、controller 6个文件。

# 工具初衷
1. 不满足 mybatis-generator 只生成 Dao 层。
2. 使用 mybatis-plus 虽可生成mdsc，但是生成的类强依赖于mybatis-plus。
3. 想拥有一款可以不过渡依赖第三放jar，不耦合项目主体，可定制化生成自己想要的此类工具。
4. 锻炼自己。

# 工具目标
## 1. 基础目标
> 生成可供开发使用的代码，拥有通用的增删改查操作。(已实现)
- model.java
- dao.java
- mapper.xml
- interface service.java
- serviceIml.java
- controller.java

## 2. 长远目标
> 作为真正的代码生成工具使用。(未实现)
- 开发 idea 插件，将功能集成与插件中，直接在 IDE 中使用鼠标操作使用。
- 模板自定义，可自定义任何类型模板，生成各类型文本文件。
- 不局限于生成java后端代码，期望生成基于Vue+(ElementUI | IviewUI)的前端代码，包括列表页、详情页，并对接后端生成的接口。

# 工具使用
> 参考 generator.xml 配置即可。
> main方法直接调用：com.wenka.mdsc.generator.CodeGenerator.gen();

配置说明
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<setting xmlns="http://www.w3school.com.cn"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://www.wenka.top/xsd/generator-1.0.0.xsd">
  <properties>
    <precision>high</precision>
    <author>作者</author>
    <jdbc.driver>数据库驱动</jdbc.driver>
    <jdbc.url>数据库URL</jdbc.url>
    <jdbc.username>数据库用户名</jdbc.username>
    <jdbc.password>数据库密码</jdbc.password>
    <generate-level>controller</generate-level>
    <parent.package>所生成java文件的共有父包(如：com.mall.app.identity)</parent.package>
    <dao.package>所生成*Dao.java的包名(如：dao)</dao.package>
    <service.package>所生成*Service.java的包名(如：service)</service.package>
    <controller.package>所生成*Controller.java的包名(如：controller)</controller.package>
    <xml.path>所生成*Mapper.xml的包名(如：mappers)</xml.path>
    <module.path>所生成文件的根路径</module.path>
    <base-model>Model的基类(如：com.mall.model.BaseModel)</base-model>
    <ignore-column>Model忽略的数据列如：（UPDATE_TIME,CREATE_TIME已在基类存在）</ignore-column>
    <id-util>Id生成的方法(如：com.mall.commons.util.IDUtil.bulidId())</id-util>
    <result-model>Controller的统一返回，必须有success(),error(),success(Object o)方法（如：com.mall.commons.vo.Result）</result-model>
  </properties>

  <tables>
    <table table-name="数据表名称" class-name="对应的Model类名" />
  </tables>
</setting>
```
