<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
                <img src="${book.getCover().startsWith("data:image/") ? book.getCover() : "/biblio/image/default_cover.png"}" class="rounded w-25" alt="Capa de ${book.getName()}">
                <div class="d-flex flex-column">
                    <h1>${book.getName()} <small>${book.getYear()}</small></h1>
                    <p>${book.getSynopsis()}</p>
                    <span>${book.getPages()}</span>
                    <span>${book.getChapters()}</span>
                    <span>${book.getAuthor()}</span>
                    <span>${book.getPublisher()}</span>
                </div>
            </div>
        </main>
    </body>
</html>