<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>

<% request.setAttribute("currentPage", request.getAttribute("javax.servlet.forward.request_uri")); %>

<nav class="navbar navbar-expand-lg bg-light sticky-top">
    <div class="container">
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
            <c:if test="${not empty user}">
                <ul class="navbar-nav d-flex gap-2 mb-2 mb-lg-0 ms-lg-auto">
                    <li class="nav-item">
                        <a href="/biblio/dashboard" class="${currentPage == '/biblio/dashboard' ? 'active' : ''} btn">Dashboard</a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            ${user.getName()}
                        </a>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" href="/biblio/profile">Perfil</a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li><a class="dropdown-item" href="/biblio/logout">Sair</a></li>
                        </ul>
                    </li>
                </ul>
            </c:if>
        </div>
    </div>
</nav>