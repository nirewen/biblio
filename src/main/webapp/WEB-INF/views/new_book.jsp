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
                <form method="post" action="./book">
                    <input type="hidden" name="option" value="add">
                    <div class="mb-3">
                        <label for="name">Nome</label>
                        <input type="text" class="form-control" name="name" />
                    </div>
                    <div class="mb-3">
                        <label for="author">Author</label>
                        <input type="text" class="form-control" name="author" />
                    </div>
                    <button type="submit" class="btn btn-primary">Adicionar</button>
                </form>
            </div>
        </main>
    </body>
</html>