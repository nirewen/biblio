<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<html>
    <head>
        <jsp:include page="../shared/head.jsp" />
        <title>Criar uma conta</title>
    </head>
    <body>
        <jsp:include page="../shared/navbar.jsp" />
        <main class="container d-flex flex-column align-items-center mt-5">
            <div class="d-flex flex-column w-50">
                <h1>Criar uma conta</h1>
                <form method="post" action="./signup">
                    <div class="mb-3">
                        <label for="email" class="form-label ${not empty error ? 'text-danger' : ''}">Email</label>
                        <input type="email" class="form-control ${not empty error ? 'border-danger' : ''}" id="email" name="email" placeholder="insira o email" value="${email}">
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label ${not empty error ? 'text-danger' : ''}">Senha</label>
                        <input type="password" class="form-control ${not empty error ? 'border-danger' : ''}" id="password" name="password" placeholder="insira a senha">
                    </div>
                     <c:if test="${not empty error}">
                        <div class="alert alert-danger d-flex align-items-center" role="alert">
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-exclamation-triangle-fill flex-shrink-0 me-2" viewBox="0 0 16 16" role="img" aria-label="Info:">
                                <path d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
                            </svg>
                            <div>
                                ${error.getMessage()}
                                <c:if test='${error.getType().name().equals("DUPLICATE_USER")}'>
                                    <a href="./login">Logar?</a>
                                </c:if>
                            </div>
                        </div>
                    </c:if>
                    <button type="submit" class="btn btn-primary">Cadastrar</button>
                </form>
            </div>
        </main>
    </body>
</html>
