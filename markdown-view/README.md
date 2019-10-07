# ControllerAdvice 控制器切面演示程序 

在spring 3.2中，新增了@ControllerAdvice 注解，并应用到所有@RequestMapping中, 
可以用于定义
 - @ExceptionHandler 处理异常情况，根据异常类型选择处理方法；
 - @InitBinder  数据类型转换等， 如前端请求所有参数都是字符串， 后端需要日期，则可以在此设定统一转换格式；
 - @ModelAttribute 视图中共用的变量。

@ControllerAdvice 定义了多个时，通过  @Order/@Priority  指定执行顺序。


## 问题1： 如何根据前端要求返回不同的数据类型，如如html 或 json?

@ExceptionHandler 需要返回不同类型数据，如html 或 json， 可以通过`forward:error` 实现，forward:error会进入

org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController 

里面分别有：
```
@RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
	public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
		HttpStatus status = getStatus(request);
		Map<String, Object> model = Collections
				.unmodifiableMap(getErrorAttributes(request, isIncludeStackTrace(request, MediaType.TEXT_HTML)));
		response.setStatus(status.value());
		ModelAndView modelAndView = resolveErrorView(request, response, status, model);
		return (modelAndView != null) ? modelAndView : new ModelAndView("error", model);
	}

	@RequestMapping
	public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
		Map<String, Object> body = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL));
		HttpStatus status = getStatus(request);
		return new ResponseEntity<>(body, status);
	}
```
方法。
但进入这个Contrller有个前提，是要设置 request.setAttribute("javax.servlet.error.status_code", 500);

用户名输入exception,触发后端报错，可以比较`提交`和`Json 提交`返回数据的差别。

## 问题2：如何将json改为jsonp支持跨域？
可以通过ResponseBodyAdvice我们可以很方便的将json数据改成jsonp进行返回。读者可自行尝试。

## 问题3：相较Controller, ControllerAdvice执行的时机是什么？
InitBinder 与 ModelAttribute 在Controller方法之前执行，ExceptionHandler在发生异常时执行。



附件 Spring MVC 之处理程序映射详解：

# Spring MVC HandlerMappings 处理程序映射详解

HandlerMappings 解决的问题是http请求的url对应处理的controller类或方法的映射关系查找, 也就是

    输入URL -> 输出Controller(或Controller的方法)

## 本项目中 DispatcherServlet 中涉及的 HandlerMappings

如果断点在 DispatcherServlet.doDispatch的 getHandler上， 会发现有以下变理值
this.handlerMappings
0 = {SimpleUrlHandlerMapping@6497} 
1 = {RequestMappingHandlerMapping@6498} 
2 = {BeanNameUrlHandlerMapping@6499} 
3 = {SimpleUrlHandlerMapping@6500} 
4 = {WelcomePageHandlerMapping@6501} 

下面对这几类映射方式作一下简单介绍.

HandlerMapping是一个由定义请求和处理程序对象之间映射的对象实现的接口。默认情况下，  DispatcherServlet使用  BeanNameUrlHandlerMapping和DefaultAnnotationHandlerMapping。在Spring中，我们主要使用以下处理程序映射


 - **BeanNameUrlHandlerMapping**, bean name作为url映射 
 - **ControllerClassNameHandlerMapping**, Controller小写名字作为url, 新版本中已去掉
 - **SimpleUrlHandlerMapping**, url和controller映射关系保存在配置列表中
 - **DefaultAnnotationHandlerMapping**, 新版本(>3.2)中已由RequestMappingHandlerMapping替换 
 - **RequestMappingHandlerMapping**, 注解方式映射, 这里请求映射到**方法**，其他几个是映射到**类**
 - **WelcomePageHandlerMapping**, 主要用于访问根目录"/"时跳转到相应的index.html或index视图模板
 
 当容器中有多个handlerMapping实例时，会根据实例的优先级从高到底依次遍历，一旦找到对应 handler 后就会结束。优先级通过mapping的order属性设置，值越小，优先级越高。若不显示设置，默认就是max，即优先级最低。范围 Ordered.HIGHEST_PRECEDENCE -- Ordered.LOWEST_PRECEDENCE


## Spring MVC  RequestMappingHandlerMapping 示例

这是现在用得最多的一种，如下所示:
```
 @Controller
 @RequestMapping("/")
 public class MyController{
  @RequestMapping(value="/user/adduser", method={RequestMethod.POST})
  public ModelAndView mymethod(){
     return new ModelAndView("myview");
  }
}
```
本文不做详细介绍。


## Spring MVC BeanNameUrlHandlerMapping示例

在本文的BeanNameUrlHandlerMapping中。在这里，我们将每个请求直接映射到Bean。


下面演示 BeanNameUrlHandlerMapping 使用。在这里，我们将直接将每个请求映射到Bean，如下所示
```
<bean class="org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping"/>
     <bean name="/helloWorld.htm" 
          class="com.github.yujiaao.HelloWorldController" />
     <bean name="/hello*.htm" 
         class="com.github.yujiaao.HelloWorldController" />
```            
         
在这里，我们可以看到我们提到了使用BeanNameUrlHandlerMapping的  Spring容器，并且已经将每个可能的请求映射到控制器。

1. 文件夹结构：创建一个动态Web项目 “SpringMVCHandlerMappingTutorial”，  并为我们的src文件下“com.github.yujiaao” 创建一个包；
2. 将Spring 3 jar文件放在WEB-INF/lib下 
    >commons-logging-1.1.1.jar
    log4j-1.2.16.jar
    slf4j-api-1.7.5.jar
    slf4j-log4j12-1.7.5.jar
    spring-aspects-3.2.4.RELEASE.jar
    spring-beans-3.2 .4.RELEASE.jar
    spring-context-3.2.4.RELEASE.jar
    spring-core-3.2.4.RELEASE.jar
    spring-expression-3.2.4.RELEASE.jar
    spring-web-3.2.4.RELEASE.jar
    spring-webmvc-3.2.4.RELEASE.jar

3. 在com.github.yujiaao文件夹下创建Java类HelloWorldController.java和WelcomeController.java；   
4. 将 SpringConfig-servlet.xml和web.xml 放在  WEB-INF目录下；
5. 视图模板文件helloWorld.jsp 和welcome.jsp  放在 WEB-INF/jsp 下的子目录下。

### HelloWorldController.java
我们的HelloWorldController类扩展了AbstractController类，并覆盖了“ handleRequestInternal（）”方法。在方法内部，我们将创建一个ModelAndView对象，该对象具有重定向页面（helloWorld），并且用户传递一个将在视图页面上使用的String。

```
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class HelloWorldController extends AbstractController
{

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        System.out.println("Inside HelloWorldController");
        ModelAndView model = new ModelAndView("helloWorld");
        model.addObject("msg", "HandlerMappingDemo");
        
        return model;
    }
}
```
### WelcomeController.java
WelcomeController与HelloWorldController几乎相同，除了重定向页面和传递的String。

```
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class WelcomeController extends AbstractController
{
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        System.out.println("Inside WelcomeController");

        ModelAndView model = new ModelAndView("welcome");
        model.addObject("msg", "HandlerMappingDemo");
        
        return model;
    }
}
```
### helloWorld.jsp
```
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
       <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
       <title>Insert title here</title>
    </head>
    <body>
       <h2>Hello World ${msg}</h2> 
    </body>
</html>
```
### welcome.jsp
```
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Insert title here</title>
    </head>
    <body>
        <h2> Welcome to ${msg}</h2>
    </body>
</html>
```
### web.xml
web.xml包含服务器需要了解的有关应用程序的所有内容，这些内容位于WEB-INF目录下。<servlet-name> 包含SpringConfiguration的名称，初始化DispatcherServlet 时，框架将尝试在WEB-INF目录下加载配置文件“ [servlet-name] -servlet.xml” 。

```
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns="http://java.sun.com/xml/ns/javaee" xmlns:web="http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
	xsi:schemaLocation="http://java.sun.com/xml/ns/javaee	http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
	id="WebApp_ID" version="2.5">
	<display-name>SpringMVCFormHandling</display-name>
	<welcome-file-list>
		<welcome-file>index.html</welcome-file>
		<welcome-file>index.htm</welcome-file>
		<welcome-file>index.jsp</welcome-file>
		<welcome-file>default.html</welcome-file>
		<welcome-file>default.htm</welcome-file>
		<welcome-file>default.jsp</welcome-file>
	</welcome-file-list>
	<servlet>
		<servlet-name>SpringConfig</servlet-name>
		<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
	</servlet>
	<servlet-mapping>
		<servlet-name>SpringConfig</servlet-name>
		<url-pattern>/</url-pattern>
	</servlet-mapping>
</web-app>
```
### SpringConfig-servlet.xml

该SpringConfig-servlet.xml中也被放置在WEB-INF目录下。
在这里，我们已将BeanNameUrlHandlerMapping配置为HandlerMapping
每个请求也都映射到一个控制器
```
<beans xmlns="http://www.springframework.org/schema/beans" 
xmlns:context="http://www.springframework.org/schema/context" 
xmlns:mvc="http://www.springframework.org/schema/mvc" 
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
xsi:schemaLocation=" http://www.springframework.org/schema/beans	http://www.springframework.org/schema/beans/spring-beans-3.0.xsd	
http://www.springframework.org/schema/context	http://www.springframework.org/schema/context/spring-context-3.0.xsd	
http://www.springframework.org/schema/mvc	http://www.springframework.org/schema/mvc/spring-mvc-3.0.xsd"> 
 
      <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
         <property name="prefix" value="/WEB-INF/Jsp/"/>
         <property name="suffix" value=".jsp"/>
     </bean>
 
     <bean class="org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping"/>
 
     <bean name="/helloWorld.htm" 
         class="com.github.yujiaao.HelloWorldController" />
 
     <bean name="/hello*" 
         class="com.github.yujiaao.HelloWorldController" /> 

     <bean name="/welcome.htm"
         class="com.github.yujiaao.WelcomeController"/>
 
</beans>
```
在上面的例子中

请求helloWorld.htm时，DispatcherServlet将其重定向到HelloWorldController。
请求hello123时 ，DispatcherServlet将其重定向到HelloWorldController。
请求welcome.htm时，DispatcherServlet将其重定向到WelcomeController。
请求welcome123 ， 因为没有映射，您将收到404错误。
输出量

当HelloWorldController调用时

    Hello World HandlerMappingDemo

当WelcomeController调用时

    Welcome to HandlerMappingDemo

## Spring MVC ControllerClassNameHandlerMapping示例
ControllerClassNameHandlerMapping  使用约定将请求的URL映射到Controller。这将需要的控制器名称，并把它们转换成 小写了领先的“/”

上一个例子中，我们了解了BeanNameUrlHandlerMapping的工作方式。 现在，让我们看一下ControllerClassHandlerMapping， 这种类型的HandlerMapping使用约定将请求的URL映射到Controller。这将需要的控制器名称，并把它们转换成“/”开头小写了的名字。

Using BeanNameUrlHandlerMapping
```
<bean class="org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping"/>
<bean name="/helloWorld.htm" 
     class="com.github.yujiaao.HelloWorldController" />
<bean name="/hello*.htm" 
     class="com.github.yujiaao.HelloWorldController" />  
Using ControllerClassNameHandlerMapping

 <bean class="org.springframework.web.servlet.mvc.support.ControllerClassNameHandlerMapping" />
 <bean class="com.github.yujiaao.HelloWorldController"></bean>
 <bean class="com.github.yujiaao.WelcomeController"></bean>
```
使用ControllerClassNameHandlerMapping时，不需要 bean名称


1. 文件夹结构：创建一个动态Web项目 “SpringMVCHandlerMappingTutorial”，  并为我们的src文件“ com.github.yujiaao  ”创建一个包；
2. 将Spring 3 jar文件放在WEB-INF/lib下

    >commons-logging-1.1.1.jar
    log4j-1.2.16.jar
    slf4j-api-1.7.5.jar
    slf4j-log4j12-1.7.5.jar
    spring-aspects-3.2.4.RELEASE.jar
    spring-beans-3.2.4.RELEASE.jar
    spring-context-3.2.4.RELEASE.jar
    spring-core-3.2.4.RELEASE.jar
    spring-expression-3.2.4.RELEASE.jar
    spring-web-3.2.4.RELEASE.jar
    spring-webmvc-3.2.4.RELEASE.jar


3. 在com.github.yujiaao 文件夹下   创建Java类HelloWorldController.java和WelcomeController.java。  
4. 将  SpringConfig-servlet.xml和web.xml 放在 WEB-INF目录下
5. 视图模板文件 helloWorld.jsp 和 welcome.jsp  放在WEB-INF/jsp下的子目录下


### HelloWorldController.java
我们的HelloWorldController类扩展了AbstractController类，并覆盖了“ handleRequestInternal（）”方法。在方法内部，我们将创建一个ModelAndView对象，该对象具有重定向页面（helloWorld），并且传递一个将在视图页面使用的String msg。

```
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class HelloWorldController extends AbstractController
{

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        System.out.println("Inside HelloWorldController");
        ModelAndView model = new ModelAndView("helloWorld");
        model.addObject("msg", "HandlerMappingDemo");
        
        return model;
    }
}
```
### WelcomeController.java

WelcomeController与HelloWorldController几乎相同，除了重定向页面和传递的String。

```
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class WelcomeController extends AbstractController
{
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        System.out.println("Inside WelcomeController");

        ModelAndView model = new ModelAndView("welcome");
        model.addObject("msg", "HandlerMappingDemo");
        
        return model;
    }
}
```
### helloWorld.jsp
```
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
       <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
       <title>Insert title here</title>
    </head>
    <body>
       <h2>Hello World ${msg}</h2> 
    </body>
</html>
```
### welcome.jsp
```
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Insert title here</title>
    </head>
    <body>
        <h2> Welcome to ${msg}</h2>
    </body>
</html>
```
### web.xml
web.xml包含服务器需要了解的有关应用程序的所有内容，这些内容位于WEB-INF目录下。<servlet-name> 包含SpringConfiguration的名称，初始化DispatcherServlet 时，框架将尝试在WEB-INF目录下加载配置文件“ [servlet-name]-servlet.xml” 。

```
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns="http://java.sun.com/xml/ns/javaee" xmlns:web="http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
	xsi:schemaLocation="http://java.sun.com/xml/ns/javaee	http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
	id="WebApp_ID" version="2.5">
	<display-name>SpringMVCFormHandling</display-name>
	<welcome-file-list>
		<welcome-file>index.html</welcome-file>
		<welcome-file>index.htm</welcome-file>
		<welcome-file>index.jsp</welcome-file>
		<welcome-file>default.html</welcome-file>
		<welcome-file>default.htm</welcome-file>
		<welcome-file>default.jsp</welcome-file>
	</welcome-file-list>
	<servlet>
		<servlet-name>SpringConfig</servlet-name>
		<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
	</servlet>
	<servlet-mapping>
		<servlet-name>SpringConfig</servlet-name>
		<url-pattern>/</url-pattern>
	</servlet-mapping>
</web-app>
```
### SpringConfig-servlet.xml

 - 该SpringConfig-servlet.xml中也被放置在WEB-INF目录下。
 - 在这里，我们已将BeanNameUrlHandlerMapping配置为HandlerMapping
 - 每个请求也都映射到一个控制器

```
<beans xmlns="http://www.springframework.org/schema/beans" 
xmlns:context="http://www.springframework.org/schema/context" 
xmlns:mvc="http://www.springframework.org/schema/mvc" 
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
xsi:schemaLocation=" http://www.springframework.org/schema/beans	
http://www.springframework.org/schema/beans/spring-beans-3.0.xsd	
http://www.springframework.org/schema/context	
http://www.springframework.org/schema/context/spring-context-3.0.xsd	
http://www.springframework.org/schema/mvc	
http://www.springframework.org/schema/mvc/spring-mvc-3.0.xsd"> 
 
      <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
         <property name="prefix" value="/WEB-INF/Jsp/"/>
         <property name="suffix" value=".jsp"/>
     </bean>
 
      <bean class="org.springframework.web.servlet.mvc.support.ControllerClassNameHandlerMapping" />
      <bean class="com.github.yujiaao.HelloWorldController"></bean>
      <bean class="com.github.yujiaao.WelcomeController"></bean>
 
</beans>
```

在上面的例子中

 - 请求helloworld时，DispatcherServlet将其重定向到HelloWorldController。
 - 请求helloworld123时 ，DispatcherServlet将其重定向到HelloWorldController。
 - 请求welcome时，DispatcherServlet将其重定向到WelcomeController。
 - 请求welcome123，DispatcherServlet将其重定向到WelcomeController。
 - 请求helloWorld ，您将收到404  错误，因为“ W”大写

HelloWorldController调用时的输出

    Hello World HandlerMappingDemo


当WelcomeController调用时


    Welcome to HandlerMappingDemo



## Spring MVC SimpleUrlHandlerMapping示例
SimpleUrlHandlerMapping 是所有处理程序映射中最简单的映射，它允许您指定URL模式和显式处理程序.


之前我们已经了解了  Spring MVC BeanNameUrlHandlerMapping 和Spring MVC  ControllerClassNameHandlerMapping。现在让我们看一下SimpleUrlHandlerMapping， 这种类型的HandlerMapping是所有处理程序映射中最简单的，它允许您显式指定URL模式和处理程序
有两种定义 SimpleUrlHandlerMapping的方法，即 使用<value>标签和<props>标签。 SimpleUrlHandlerMapping 有一个称为映射的属性，我们将向其传递URL模式。

### 使用<value>标签

左侧的URL模式 “=” 右侧bean的ID或name

```
<bean class="org.springframework.web.servlet.handler.SimpleUrlHandlerMapping">
  <property name="mappings">
     <value>
        /welcome.htm=welcomeController
        /welcome*=welcomeController
        /hell*=helloWorldController
        /helloWorld.htm=helloWorldController
      </value>
    </property>
 </bean>
```

### 使用<props>标签

该属性的键是URL模式,属性值是bean的引用或名称


```
 <bean class="org.springframework.web.servlet.handler.SimpleUrlHandlerMapping">
    <property name="mappings">
       <props>
          <prop key="/welcome.htm">welcomeController</prop>
          <prop key="/welcome*">welcomeController</prop>
          <prop key="/helloworld">helloWorldController</prop>
          <prop key="/hello*">helloWorldController</prop>
          <prop key="/HELLOworld">helloWorldController</prop>
     </props>
   </property>
 </bean>
```

1.文件夹结构：创建一个动态Web项目 “ SpringMVCHandlerMappingTutorial”，  并为我们的src文件“com.github.yujiaao ”创建一个包；
2. 将Spring 3 jar文件放在WEB-INF/lib下
  
    >commons-logging-1.1.1.jar
    log4j-1.2.16.jar
    slf4j-api-1.7.5.jar
    slf4j-log4j12-1.7.5.jar
    spring-aspects-3.2.4.RELEASE.jar
    spring-beans-3.2.4.RELEASE.jar
    spring-context-3.2.4.RELEASE.jar
    spring-core-3.2.4.RELEASE.jar
    spring-expression-3.2.4.RELEASE.jar
    spring-web-3.2.4.RELEASE.jar
    spring-webmvc-3.2.4.RELEASE.jar


3. 在com.github.yujiaao 文件夹下   创建Java类HelloWorldController.java和WelcomeController.java。  
4. 将  SpringConfig-servlet.xml和web.xml 放在  WEB-INF目录下
5. 视图模板文件helloWorld.jsp 和welcome.jsp  放在WEB-INF/jsp下的子目录下

### HelloWorldController.java

我们的HelloWorldController类扩展了AbstractController类，并覆盖了“ handleRequestInternal（）”方法。


```
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class HelloWorldController extends AbstractController
{

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        System.out.println("Inside HelloWorldController");
        ModelAndView model = new ModelAndView("helloWorld");
        model.addObject("msg", "HandlerMappingDemo");
        
        return model;
    }
}
```
### WelcomeController.java
WelcomeController与HelloWorldController几乎相同，除了重定向页面和传递的String。


```
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class WelcomeController extends AbstractController
{
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        System.out.println("Inside WelcomeController");

        ModelAndView model = new ModelAndView("welcome");
        model.addObject("msg", "HandlerMappingDemo");
        
        return model;
    }
}
```
### helloWorld.jsp
```
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
       <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
       <title>Insert title here</title>
    </head>
    <body>
       <h2>Hello World ${msg}</h2> 
    </body>
</html>
```
###welcome.jsp
```
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Insert title here</title>
    </head>
    <body>
        <h2> Welcome to ${msg}</h2>
    </body>
</html>
```
### web.xml
web.xml包含服务器需要了解的有关应用程序的所有内容，这些内容位于WEB-INF目录下。<servlet-name> 包含SpringConfiguration的名称，初始化DispatcherServlet 时，框架将尝试在WEB-INF目录下加载配置文件“ [servlet-name] -servlet.xml” 。

```
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns="http://java.sun.com/xml/ns/javaee" xmlns:web="http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
	xsi:schemaLocation="http://java.sun.com/xml/ns/javaee	http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
	id="WebApp_ID" version="2.5">
	<display-name>SpringMVCFormHandling</display-name>
	<welcome-file-list>
		<welcome-file>index.html</welcome-file>
		<welcome-file>index.htm</welcome-file>
		<welcome-file>index.jsp</welcome-file>
		<welcome-file>default.html</welcome-file>
		<welcome-file>default.htm</welcome-file>
		<welcome-file>default.jsp</welcome-file>
	</welcome-file-list>
	<servlet>
		<servlet-name>SpringConfig</servlet-name>
		<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
	</servlet>
	<servlet-mapping>
		<servlet-name>SpringConfig</servlet-name>
		<url-pattern>/</url-pattern>
	</servlet-mapping>
</web-app>
```
### SpringConfig-servlet.xml

 - 该SpringConfig-servlet.xml中也被放置在WEB-INF目录下。
 - 在这里，我们已将SimpleUrlHandlerMapping配置 为HandlerMapping
 - 每个请求也都映射到一个控制器

```
<beans xmlns="http://www.springframework.org/schema/beans" 
xmlns:context="http://www.springframework.org/schema/context" 
xmlns:mvc="http://www.springframework.org/schema/mvc" 
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
xsi:schemaLocation=" http://www.springframework.org/schema/beans	
http://www.springframework.org/schema/beans/spring-beans-3.0.xsd	
http://www.springframework.org/schema/context	
http://www.springframework.org/schema/context/spring-context-3.0.xsd	
http://www.springframework.org/schema/mvc	
http://www.springframework.org/schema/mvc/spring-mvc-3.0.xsd"> 
 
      <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
         <property name="prefix" value="/WEB-INF/Jsp/"/>
         <property name="suffix" value=".jsp"/>
     </bean>
 
     <bean class="org.springframework.web.servlet.handler.SimpleUrlHandlerMapping">
        <property name="mappings">
            <props>
                <prop key="/welcome.htm">welcomeController</prop>
                <prop key="/welcome*">welcomeController</prop>
                <prop key="/helloworld">helloWorldController</prop>
                <prop key="/hello*">helloWorldController</prop>
                <prop key="/HELLOworld">helloWorldController</prop>
           </props>
       </property>
 </bean>
 
   <bean id="helloWorldController" class="com.github.yujiaao.HelloWorldController"></bean>
   <bean id="welcomeController" class="com.github.yujiaao.WelcomeController"></bean>
 </beans>
```
在上面的例子中


 - 请求helloworld时，DispatcherServlet将其重定向到HelloWorldController。
 - 请求hello123时 ，DispatcherServlet将其重定向到HelloWorldController。
 - 请求HELLOworld时 ，DispatcherServlet将其重定向到HelloWorldController。
 - 请求welcome.htm时，DispatcherServlet将其重定向到WelcomeController。
 - 请求welcome123，DispatcherServlet将其重定向到WelcomeController。
 - 请求hELLOWorld ，您将收到404  错误，因为我们为其添加了映射。

HelloWorldController调用时的输出


    Hello World HandlerMappingDemo
    
当WelcomeController调用时


    Welcome to HandlerMappingDemo
