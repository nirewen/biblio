<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<html lang="pt-br">
    <head>
        <jsp:include page="../shared/head.jsp" />
        <title>Dashboard</title>
    </head>
    <body>
        <jsp:include page="../components/navbar.jsp" />
        <main class="container mt-5">
            <h1>Dashboard</h1>
            <c:choose>
                <c:when test="${not empty loans}">
                    <p>Aqui estão seus livros alugados</p>
                    <c:forEach items="${loans}" var="loan">
                        <c:set var="loan" value="${loan}" scope="request" />
                        <jsp:include page="../components/loan_card.jsp" />
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div class="d-flex flex-column gap-2 align-items-start">
                        <h4>Você não alugou nenhum livro 😢</h4>
                        <a class="btn btn-primary" href="/biblio/books">Ir para livros</a>
                    </div>
                </c:otherwise>
            </c:choose>
        </main>
    </body>
</html>
