一、通用代码生成器配置：
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

二、启用http2
1、在使用undertow替换tomcat
2、生成证书：
keytool -genkey -alias undertow -storetype PKCS12 -keyalg RSA -keysize 2048 -keystore keystore.p12 -dname "CN=localhost, OU=local
host, O=localhost, L=DGQ, ST=BJ, C=CN"
将证书放到classpath下
3、applicaiton.yml增加配置：
server:
  port: 8080
  compression:
    enabled: true
  http2:
    enabled: true #启用http2
  ssl:
    enabled: true #启用https
    key-store: classpath:keystore.p12 #证书
    key-store-password: 123456 #证书密码
    key-store-type: PKCS12 #证书类型
    protocol: TLSv1.2 #协议
    key-alias: undertow #别名
4、配置同时支持http
实现WebServerFactoryCustomizer#customize方法：
factory.addBuilderCustomizers((UndertowBuilderCustomizer)builder->{
	builder.addHttpListener(8080, "0.0.0.0");
});