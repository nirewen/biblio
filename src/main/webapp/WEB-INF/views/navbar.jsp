<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>

<% request.setAttribute("currentPage", request.getAttribute("javax.servlet.forward.request_uri")); %>
<% request.setAttribute("pages", new String[][]{
    { "/biblio/home", "Home" }, 
    { "/biblio/signup", "Sign Up" }, 
    { "/biblio/login", "Login" } 
}); %>

<nav>
    <div class="logo">bibl.io</div>
    <ul>
        <c:forEach items="${pages}" var="page">
            <li><a href="${page[0]}" class="${currentPage == page[0] ? 'current' : ''}">${page[1]}</a></li>
        </c:forEach>
    </ul>
</nav>