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
                <form method="post" action="./book" enctype="multipart/form-data">
                    <input type="hidden" name="option" value="new">
                    <div class="mb-3">
                        <label for="cover">Capa</label>
                        <input type="file" class="form-control" name="cover" id="cover" />
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