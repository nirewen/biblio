<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<html>
    <head>
        <jsp:include page="../shared/head.jsp" />
        <title>Dashboard</title>
    </head>
    <body>
        <jsp:include page="../components/navbar.jsp" />
        <main class="container">
            <h1>Dashboard</h1>
            <p>Aqui estão seus livros alugados</p>
            <c:forEach items="${rentals}" var="rent">
                <c:set var="rent" value="${rent}" scope="request" />
                <jsp:include page="../components/rental_card.jsp" />
            </c:forEach>
        </main>
    </body>
</html>
