<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<html>
    <head>
        <link rel="stylesheet" href="css/styles.css" />
    </head>
    <body>
        <form method="post" action="login">
            <label for="email">Email</label>
            <input id="email" name="email" type="email" placeholder="email" />
            <label for="senha">Senha</label>
            <input id="senha" name="password" type="password" placeholder="senha" />

            <button type="submit">Logar</button>
        </form>
    </body>
</html>
