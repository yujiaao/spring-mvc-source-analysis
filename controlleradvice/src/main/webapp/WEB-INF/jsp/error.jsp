<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<html>
<head>
<title> Global Error </title>
</head>
	<body>
	<h1>${exception.message}</h1>
	<code>

    <c:if test="${exception != null}">
      <h4>${exception}</h4>
      <c:forEach var="stackTraceElem" items="${exception.stackTrace}">
        <c:if test="${!fn:startsWith(stackTraceElem, 'org')}" >
         <c:out value="${stackTraceElem}"/><br>
        </c:if>
      </c:forEach>
    </c:if>

  	</code>
	</body>
</html> 