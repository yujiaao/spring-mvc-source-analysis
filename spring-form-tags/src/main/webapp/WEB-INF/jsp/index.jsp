<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Spring Form Tags Demo</title>
    </head>

    <body bgcolor="DDDDDD">
        <h1>Spring Form tags 示例</h1>
        <form:form modelAttribute="registration" method="POST">

           用户名: <form:input path="username"/><font color="red">
               <form:errors path="username"/></font><br />
           密码: <form:password path="password"/><font color="red">
           <form:errors path="password"/></font><br />
           姓名: <form:input path="fname"/><br />
           性别: <form:radiobutton path="gender" value="male"/>男
               <form:radiobutton path="gender" value="female"/>女<br />
                   国家:<form:select path="country" >
                       <form:option value="China">China</form:option>
                       <form:option value="Brazil">Brazil</form:option>
                       <form:option value="Australia">Australia</form:option>
                   </form:select><br />
                   地址: <form:textarea path="addr"/><br />
                       选择 :<form:checkbox path="cb" value="checkbox1"/>
                        爱吃西瓜
                       <form:checkbox path="cb" value="checkbox2"/>
                        爱写代码<br />

            <input type="submit" value="提交"/>
        </form:form>

    </body>
</html>