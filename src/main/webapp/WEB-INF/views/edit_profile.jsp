<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isELIgnored="false" %>

<html lang="pt-br">
    <head>
        <jsp:include page="../shared/head.jsp" />
        <title>Conta de ${user.getName()}</title>
    </head>
    <body>
        <jsp:include page="../components/navbar.jsp" />
        <main class="container d-flex flex-column align-items-center mt-5">
            <div class="d-flex flex-column w-50">
                <form:form method="post" action="./edit" modelAttribute="user">
                    <div class="d-flex align-items-center justify-content-between">
                        <h1>Perfil de ${user.getName()}</h1>
                        <div class="d-flex gap-2">
                            <a href="../profile" class="btn btn-light">Cancelar</a>
                            <button type="submit" class="btn btn-primary">Salvar</button>
                        </div>
                    </div>
                    <form:input type="hidden" path="id" />
                    <div class="mb-3">
                        <form:label path="name" class="form-label">Nome</form:label>
                        <div class="d-flex gap-3">
                            <input type="text" class="form-control" value="${user.getName()}" readonly>
                            <form:input type="text" class="form-control" path="name" name="name" placeholder="não alterado" />
                        </div>
                    </div>
                    <div class="mb-3">
                        <form:label path="email" class="form-label ${not empty error && error.getType().name().equals('DUPLICATE_USER') ? 'text-danger' : ''}">Email</form:label>
                        <div class="d-flex gap-3">
                            <input type="email" class="form-control" value="${user.getEmail()}" readonly />
                            <form:input type="email" class="form-control ${not empty error && error.getType().name().equals('DUPLICATE_USER') ? 'border-danger' : ''}" path="email" name="email"  placeholder="não alterado" />
                        </div>
                    </div>
                    <div class="mb-3">
                        <form:label path="password" class="form-label">Senha</form:label>
                        <div class="d-flex gap-3">
                            <form:input type="password" class="form-control ${not empty error && error.getType().name().equals('INCORRECT_CREDENTIALS') ? 'border-danger' : ''}" path="password" name="password" placeholder="senha atual" required="true" />
                            <form:input type="password" class="form-control" path="newPassword" name="new_password"  placeholder="não alterada" />
                        </div>
                    </div>
                    <div class="mb-3">
                        <form:label path="dateOfBirth" class="form-label">Data de Nascimento</form:label>
                        <div class="d-flex gap-3">
                            <input type="date" class="form-control" value="${user.getDateOfBirth().toString()}" readonly />
                            <form:input type="date" class="form-control" path="dateOfBirth" name="date_of_birth" />
                        </div>
                    </div>
                     <c:if test="${not empty error}">
                        <div class="alert alert-danger d-flex align-items-center" role="alert">
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-exclamation-triangle-fill flex-shrink-0 me-2" viewBox="0 0 16 16" role="img" aria-label="Info:">
                                <path d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
                            </svg>
                            <div>
                                ${error.getMessage()}
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${not empty message}">
                        <div class="alert alert-primary d-flex align-items-center" role="alert">
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-exclamation-triangle-fill flex-shrink-0 me-2" viewBox="0 0 16 16" role="img" aria-label="Alerta:">
                                <path d="M8 16A8 8 0 1 0 8 0a8 8 0 0 0 0 16zm.93-9.412-1 4.705c-.07.34.029.533.304.533.194 0 .487-.07.686-.246l-.088.416c-.287.346-.92.598-1.465.598-.703 0-1.002-.422-.808-1.319l.738-3.468c.064-.293.006-.399-.287-.47l-.451-.081.082-.381 2.29-.287zM8 5.5a1 1 0 1 1 0-2 1 1 0 0 1 0 2z"/>
                            </svg>
                            <div>
                                ${message}
                            </div>
                        </div>
                    </c:if>
                </form:form>
            </div>
        </main>
    </body>
</html>
