 Spring Boot 2 Quickstart Maven Archetype
=========================================
![alt text](AppArchtype.jpg)

说明
-------
本项目作 Spring Boot Web 项目的 Maven 原型生成器。
集成了开发中一些常见标准组件

- Java 1.8+
- Maven 3.5+
- Spring boot 2.1.0.RELEASE+
- Lombok 抽象层 
- JPA, 为方便说明问题， 集成了 H2 数据库 
- Swagger 2 API 文档器
- Spring 外部服务调用的重试与熔断机制
- REST API 模型数检 
- Spring cloud 外部 GIT REPO 配置
- 集成测试采用 Cucumber 与 Spring Boot test
- 多分枝项目的 Jenkins Pipeline
- Sonar 静态分析与发布管理
- 支持重试 retry 
- Logback 配置  


安装
------------

本地命令行执行:

```sh
$ git clone https://github.com/yujiaao/spring-boot-quickstart-archtype.git
$ cd spring-boot-quickstart-archtype
$ mvn clean install
```

创建项目
----------------

```sh
$ mvn archetype:generate \
     -DarchetypeGroupId=com.github.yujiaao.spring-boot-archetypes \
     -DarchetypeArtifactId=spring-boot-quickstart \
     -DarchetypeVersion=1.0.0 \
     -DgroupId=com.test \
     -DartifactId=sampleapp \
     -Dversion=1.0.0-SNAPSHOT \
     -DinteractiveMode=false
```

用 SWAGGER 进行浏览器测试
-------------------

```sh
http://localhost:8080/swagger-ui.html
```

更多源码参见:
-------------------
https://github.com/yujiaao/spring-mvc-source-analysis
