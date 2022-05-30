<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<html>
    <head>
        <jsp:include page="../shared/head.jsp" />
    </head>
    <body>
        <jsp:include page="./navbar.jsp" />
        <c:if test="${not empty error}">
            <span>${error}</span>
        </c:if>
        <c:if test="${not empty message}">
            <span>${message}</span>
        </c:if>
        <form method="post" action="login">
            <label for="email">Email</label>
            <input id="email" name="email" type="email" placeholder="email" />
            <label for="senha">Senha</label>
            <input id="senha" name="password" type="password" placeholder="senha" />

            <button type="submit">Logar</button>
        </form>
    </body>
</html>
