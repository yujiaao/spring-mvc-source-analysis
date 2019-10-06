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