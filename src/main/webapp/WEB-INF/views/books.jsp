<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<html>
    <head>
        <jsp:include page="../shared/head.jsp" />
        <title>Livros</title>
    </head>
    <body>
        <jsp:include page="../components/navbar.jsp" />
        <main class="container d-flex flex-column align-items-center mt-5">
            <div class="d-flex flex-column w-80">
                <h1>Livros</h1>

                <div class="gap-4" style="display: grid; grid-template-columns: repeat(3, 1fr);">
                    <c:forEach items="${books}" var="book">
                        <c:set var="book" value="${book}" scope="request" />
                        <jsp:include page="../components/book_card.jsp" />
                    </c:forEach>
                </div>
            </div>
        </main>
    </body>
</html>