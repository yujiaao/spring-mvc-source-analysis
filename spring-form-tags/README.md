# Spring FORM标签库

Spring MVC提供了一个JSP标签库（Spring Form），使将表单元素绑定到Model 数据变得更加容易。Spring Framework 提供了一些标签，用于显示 错误，设置主题和输出国际化消息。

## 使用Spring Form标签库的语法
需要在jsp文件头部加上：

	<%@taglib  uri="http://www.springframework.org/tags/form" prefix="form">

## 本示例中使用的表单标签

 - 呈现HTML “form” 标签，并向内部标签公开绑定路径以进行绑定。 
 - 使用绑定值呈现类型为“text”的HTML“input”标记。
 - 在HTML'span'标签中呈现字段错误。
 - 使用绑定值呈现类型为“ password”的HTML “input”标签。 
 - 呈现类型为“radio”的HTML“ input”标签。 
 - 呈现HTML“select”元素。支持数据绑定到所选选项。
 - 呈现单个HTML“option”。根据绑定值适当设置“selected”。 
 - 呈现HTML“textarea”。 
 - 呈现类型为“checkbox”的HTML“input”标签。

## 简单注册表单示例

1.修改web.xml以配置Dispatcher Servlet。
web.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app version="2.5" xmlns="http://java.sun.com/xml/ns/javaee"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd">
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>/WEB-INF/applicationContext.xml</param-value>
    </context-param>
    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>
    <servlet>
        <servlet-name>dispatcher</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <load-on-startup>2</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>dispatcher</servlet-name>
        <url-pattern>*.html</url-pattern>
    </servlet-mapping>
    <session-config>
        <session-timeout>
            30
        </session-timeout>
    </session-config>
    <welcome-file-list>
        <welcome-file>index.jsp</welcome-file>
        </welcome-file-list>
    </web-app>
```

2. 创建一个dispatcher-servlet.xml文件，其中包含所有用于处理用户请求的配置bean，它处理用户请求并将其分派到各个控制器。    

dispatcher-servlet.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:p="http://www.springframework.org/schema/p"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.5.xsd
       http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-2.5.xsd
http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-2.5.xsd">

    <bean class="org.springframework.web.servlet.mvc.support.ControllerClassNameHandlerMapping"/>

    <bean id="urlMapping" class="org.springframework.web.servlet.handler.SimpleUrlHandlerMapping">
      <property name="urlMap">
<map>
<entry  key="/index.html">
<ref bean="registrationController"/>
</entry>
</map>
</property>

 </bean>

<bean id="com.github.yujiaao.springformtags.registrationValidator" class="com.github.yujiaao.springformtags.registrationValidator"/>
<bean id="registrationController" class="com.github.yujiaao.springformtags.RegistrationFormController">

<property name="sessionForm"><value>false</value></property>

<property name="commandName" value="registration"></property>

<property name="commandClass" value="com.github.yujiaao.springformtags.Registration"></property>

<property name="validator"><ref bean="com.github.yujiaao.springformtags.registrationValidator"/></property>
<property name="formView"><value>index</value></property>

<property name="successView"><value>success</value></property>

</bean>

<bean id="messageSource" class="org.springframework.context.support.ResourceBundleMessageSource">
        <property name="basename" value="messages"/>
    </bean>

     <bean id="viewResolver"
          class="org.springframework.web.servlet.view.InternalResourceViewResolver"
          p:prefix="/WEB-INF/jsp/"
          p:suffix=".jsp" />
</beans>
```

3.创建一个Jsp文件以从用户index.jsp接收输入，该文件包含带有Spring Form标签的所有表单字段。

index.jsp

```jsp
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome to Spring Web MVC project</title>
    </head>

    <body bgcolor="DDDDDD">
        <h1>Spring Form tags Example</h1>
        <form:form commandName="registration" method="POST">

           User Name: <form:input path="username"/><font color="red">
               <form:errors path="username"/></font><br />
           Password: <form:password path="password"/><font color="red">
           <form:errors path="password"/></font><br />
           First Name: <form:input path="fname"/><br />
           Last Name: <form:input path="lname"/><br />
           Gender: <form:radiobutton path="gender" value="male"/>Male
               <form:radiobutton path="gender" value="female"/>Female<br />
                   Country :<form:select path="country" >
                       <form:option value="india">India</form:option>
                       <form:option value="india">USA</form:option>
                       <form:option value="india">Australia</form:option>
                   </form:select><br />
                   Address: <form:textarea path="addr"/><br />
                       Select any :<form:checkbox path="cb" value="checkbox1"/>
                       Check Box1
                       <form:checkbox path="cb" value="checkbox2"/>
                       Check Box2<br />

            <input type="submit" value="submit"/>
        </form:form>

    </body>
</html>
```

4.创建另一个Jsp文件success.jsp，它是用于显示输出的Spring视图。在此文件中，我们使用表达式语言来显示详细信息。

success.jsp

```jsp
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.Enumeration"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Spring Form Tags Example</title>
    </head>
    <body bgcolor="DDDDDD">
        <h1>Spring Form tags examples</h1>
        <h2>
         User Name: ${uname}<br />
         First Name: ${fname}<br />
         Last Name: ${lname}<br />
         Gender: ${gender}<br />
         Country: ${country}<br />
         Address: ${addr}<br />
         Selected Check box: ${cb}
</h2>

    </body>
</html>
```

5.创建一个Java类文件Registration.java，其中包含注册应用程序的业务逻辑。在这里，此文件包含8个私有变量，它们分别具有getter和setter方法，用于存储注册细节。

Registration.java
```java
public class Registration {

    private String username;
    private String password;
    private String fname;
    private String lname;
    private String gender;
    private String country;
    private String addr;
    private String cb;

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getCb() {
        return cb;
    }

    public void setCb(String cb) {
        this.cb = cb;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Registration() {
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
```

6.创建一个RegistrationFormController.java文件，该文件扩展了SimpleFormController以控制用户请求并返回相应的ModelAndView对象。

RegistrationFormController.java

```java

import org.springframework.web.servlet.mvc.SimpleFormController;

public class RegistrationFormController extends SimpleFormController {

@Override
    protected ModelAndView onSubmit(Object command) throws Exception {

        Registration reg=(Registration)command;

        String uname=reg.getUsername();
        String fname=reg.getFname();
        String lname=reg.getLname();

        String gender=reg.getGender();
        String country=reg.getCountry();
        String cb=reg.getCb();
        String addr=reg.getAddr();

         ModelAndView mv = new ModelAndView(getSuccessView());

          mv.addObject("uname",uname);
          mv.addObject("fname",fname);
          mv.addObject("lname",lname);
          mv.addObject("gender",gender);
          mv.addObject("country",country);
          mv.addObject("cb",cb);
          mv.addObject("addr",addr);

        return mv;
    }

}

```
7，创建一个registrationValidator.java文件来验证诸如用户名和密码之类的表单字段不能为空.DispatcherServlet负责赋予属性以将Validator添加到用户请求并执行验证。

registrationValidator.java

```java


public class registrationValidator implements Validator
{

    public boolean supports(Class cl) {
        return Registration.class.isAssignableFrom(cl);

    }

    public void validate(Object ob, Errors errors) {
       Registration reg=(Registration)ob;
       if (reg.getUsername() == null || reg.getUsername().length() == 0) {
            errors.rejectValue("username", "error.empty.username");
        }

       else if (reg.getPassword() == null || reg.getPassword().length() == 0) {
            errors.rejectValue("password", "error.empty.password");
        }

    }

}
```

8，创建或修改 messages.properties 文件，其中包含各自键值的消息，在此文件中，我们为两个键值编写消息，分别为空用户名和空密码。

messages.properties

```properties
error.empty.username=Please Enter User name
error.empty.password=Please Enter Password
```

9，构建并运行应用程序

输出

访问页面：http://localhost:8080/Registration/index.html

清空密码时，使用spring错误标签显示相应的错误消息。
只有在正确验证用户名和密码后，我们才能提交表单。
显示输出，其中包含所有提交注册的值







