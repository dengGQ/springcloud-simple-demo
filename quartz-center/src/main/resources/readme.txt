通用代码生成器：
1、创建config.properties
	1.配置数据库连接信息
	2.通用Mapper相关配置
2、创建generatorConfig.xml
	1. <properties resource="config.properties"/>引入config.propertes
	2. mapper.plugin 加载
	3. jdbcConnection 配置
	4. 指定model package： javaModelGenerator
	5. 指定Mapper Xml package: sqlMapGenerator
	6. 指定Mapper package: javaClientGenerator
3、pom.xml中添加mybatis-generator-maven-plugin插件
3、cmd pom文件夹同层执行：mvn mybatis-generator:generate