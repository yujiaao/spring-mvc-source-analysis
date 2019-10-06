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