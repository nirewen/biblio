<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<html>
    <head>
        <jsp:include page="../shared/head.jsp" />
    </head>
    <body>
        <c:if test="${not empty error}">
            <span>${error.getMessage()}
            
            <c:if test='${error.getType().name().equals("DUPLICATE_USER")}'>
                <a href="./login">Logar?</a>
            </c:if>
            </span>
        </c:if>
        <form method="post" action="signup">
            <label for="email">Email</label>
            <input id="email" name="email" type="email" placeholder="email" />
            <label for="senha">Senha</label>
            <input id="senha" name="password" type="password" placeholder="senha" />

            <button type="submit">Cadastrar</button>
        </form>
    </body>
</html>
