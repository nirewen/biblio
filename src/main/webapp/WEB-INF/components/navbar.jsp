<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>

<% request.setAttribute("currentPage", request.getAttribute("javax.servlet.forward.request_uri")); %>
<% request.setAttribute("pages", session.getAttribute("user") == null 
    ? new String[][]{
        { "/biblio/login", "Entrar", "btn btn-primary" },
    }
    : new String[][]{
        { "/biblio/dashboard", "Dashboard", "btn" }, 
        { "/biblio/profile", "Perfil", "btn" }, 
        { "/biblio/logout", "Sair", "btn" }
    }
); %>

<nav class="navbar navbar-expand-lg bg-light sticky-top">
    <div class="container-fluid">
        <a class="navbar-brand" href="/biblio/">bibl.io</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarText" aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarText">
            <ul class="navbar-nav mb-2 mb-lg-0 d-flex gap-2 flex-fill">
                <li class="nav-item">
                    <a class="${currentPage == '/biblio/' ? 'active' : ''} btn" href="/biblio/">Início</a>
                </li>
                <li class="nav-item">
                    <a class="${currentPage == '/biblio/books' ? 'active' : ''} btn" href="/biblio/books">Livros</a>
                </li>
            </ul>
            <ul class="navbar-nav d-flex gap-2 mb-2 mb-lg-0 ms-lg-auto">
            <c:forEach items="${pages}" var="page">
                <li class="nav-item">
                    <a class="${currentPage == page[0] ? 'active' : ''} ${page[2]}" href="${page[0]}">${page[1]}</a>
                </li>
            </c:forEach>
            </ul>
        </div>
    </div>
</nav>