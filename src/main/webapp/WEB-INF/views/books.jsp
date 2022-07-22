<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<html lang="pt-br">
    <head>
        <jsp:include page="../shared/head.jsp" />
        <title>Livros</title>
    </head>
    <body>
        <jsp:include page="../components/navbar.jsp" />
        <main class="container d-flex flex-column align-items-center mt-5">
            <div class="d-flex flex-column w-100 px-4">
                <div class="d-flex justify-content-between align-items-center">
                    <h1>Livros</h1>
                    <c:if test="${user.getPermission() == 8}">
                    <a href="./book/new" class="btn btn-primary">+ Livro</a>
                    </c:if>
                </div>

                <div class="gap-4" style="display: grid; grid-template-columns: repeat(6, 1fr);">
                    <c:forEach items="${books}" var="book">
                        <c:set var="book" value="${book}" scope="request" />
                        <jsp:include page="../components/book_card.jsp" />
                    </c:forEach>
                </div>
            </div>
        </main>
    </body>
</html>