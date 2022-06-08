<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<html>
    <head>
        <jsp:include page="../shared/head.jsp" />
        <title>Adicionar novo livro</title>
    </head>
    <body>
        <jsp:include page="../components/navbar.jsp" />
        <main class="container d-flex flex-column align-items-center mt-5">
            <div class="d-flex flex-column w-50">
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
                <form method="post" action="./book" enctype="multipart/form-data">
                    <input type="hidden" name="option" value="new">
                    <div class="mb-3">
                        <label for="cover">Capa</label>
                        <input type="file" class="form-control ${not empty error ? 'is-invalid' : ''}" name="cover" id="cover" />
                    </div>
                    <div class="mb-3">
                        <label for="name">Nome</label>
                        <input type="text" class="form-control" name="name" id="name" required />
                    </div>
                    <div class="mb-3">
                        <label for="synopsis">Sinopse</label>
                        <textarea class="form-control" rows="4" name="synopsis" id="synopsis"></textarea>
                    </div>
                    <div class="d-flex gap-3">
                        <div class="mb-3 flex-fill">
                            <label for="pages">Páginas</label>
                            <input type="number" class="form-control" name="pages" id="pages" />
                        </div>
                        <div class="mb-3 flex-fill">
                            <label for="chapters">Capítulos</label>
                            <input type="number" class="form-control" name="chapters" id="chapters" />
                        </div>
                    </div>
                    <div class="mb-3">
                        <label for="author">Autor</label>
                        <input type="text" class="form-control" name="author" id="author" required />
                    </div>
                    <div class="mb-3">
                        <label for="publisher">Editora</label>
                        <input type="text" class="form-control" name="publisher" id="publisher" />
                    </div>
                    <div class="mb-3">
                        <label for="year">Ano</label>
                        <input type="number" class="form-control" name="year" id="year" />
                    </div>
                    <button type="submit" class="btn btn-primary">Adicionar</button>
                </form>
            </div>
        </main>
    </body>
</html>