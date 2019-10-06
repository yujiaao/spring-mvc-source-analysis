<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head><title> 启动演示</title></head>
<body>
Loading demo
<form:form action="save" method="post" modelAttribute="user">
<table>

	<tr>  <td>用户名:</td> <td><form:input  path="name"/> </td>
	      <td> <form:errors path="name" cssStyle="color: red;"/></td> </tr>
	<tr>  <td> 密码 :</td> <td><form:input path="password"/> </td>
	      <td> <form:errors path="password" cssStyle="color: red;"/> </td> </tr>
	<tr>  <td>  生日 :</td> <td><form:input path="dob"  type='text' placeholder="YYYY-MM-DD"
	required='required' pattern="((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])"
	 title="Enter a date in this format YYYY-MM-DD" /> </td>
	      <td> <form:errors path="dob" cssStyle="color: red;"/> </td> </tr>	     
	<tr>  <td colspan=3>   <input type="submit"> </td>
	</tr>

</table>
 </form:form>
</body>
</html>