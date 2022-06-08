<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<html>
    <head>
        <jsp:include page="../shared/head.jsp" />
        <title>Livro ${book.getId()}</title>
    </head>
    <body>
        <jsp:include page="../components/navbar.jsp" />
        <main class="container d-flex flex-column mt-5">
            <div class="d-flex gap-5">
                <div class="w-25">
                    <img src="${book.getCover()}" class="rounded" alt="Capa de ${book.getName()}">
                </div>
                <div class="d-flex flex-column">
                    <h1>${book.getName()} <small>(${book.getYear()})</small></h1>
                    <span><em>${book.getAuthor()}</em></span>
                    <p class="bg-light rounded mt-2 p-2">${book.getSynopsis()}</p>
                    <span><b>${book.getPages()}</b> páginas</span>
                    <span><b><fmt:formatNumber value="${book.getChapters()}" minFractionDigits="0" maxFractionDigits="1"/></b> capítulos</span>
                    <small>${book.getPublisher()}</small>
                </div>
            </div>
        </main>
    </body>
</html>