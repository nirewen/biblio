<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<html lang="pt-br">
    <head>
        <jsp:include page="../shared/head.jsp" />
        <title>Livro ${book.getId()}</title>
    </head>
    <body>
        <jsp:include page="../components/navbar.jsp" />
        <main class="container d-flex flex-column mt-5">
            <div class="d-flex gap-5">
                <div class="w-25">
                    <img src="${book.getCover()}" class="rounded w-100" alt="Capa de ${book.getName()}">
                </div>
                <div class="d-flex flex-column w-100">
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
                    <div class="d-flex justify-content-between align-items-center">
                        <h1>${book.getName()} <small>(${book.getYear()})</small></h1>
                        <c:if test="${user.getPermission() == 8}">
                        <a href="/biblio/book/${book.getId()}/edit" class="btn btn-primary">Editar</a>
                        </c:if>
                    </div>
                    <span><em>${book.getAuthor()}</em></span>
                    <p class="bg-light rounded mt-2 p-2">${book.getSynopsis()}</p>
                    <span><b>${book.getPages()}</b> páginas</span>
                    <span><b><fmt:formatNumber value="${book.getChapters()}" minFractionDigits="0" maxFractionDigits="1"/></b> capítulos</span>
                    <small>${book.getPublisher()}</small>
                    <c:if test="${not empty user}">
                    <div class="d-flex justify-content-end mt-auto">
                        <c:choose>
                            <c:when test="${not empty loan and loan.isActive()}">
                                <a class="btn btn-primary" href="../loan/${loan.getId()}/postpone">Prorrogar</a>
                            </c:when>
                            <c:otherwise>
                                <a class="btn btn-primary" href="../loan/${book.getId()}">Alugar livro</a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    </c:if>
                </div>
            </div>
        </main>
    </body>
</html>