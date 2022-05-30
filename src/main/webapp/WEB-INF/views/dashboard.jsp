<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<html>
    <head>
        <jsp:include page="../shared/head.jsp" />
        <title>Dashboard</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <span>${user.getName()}</span>
    </body>
</html>
