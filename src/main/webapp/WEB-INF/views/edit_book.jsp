<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isELIgnored="false" %>

<html lang="pt-br">
    <head>
        <jsp:include page="../shared/head.jsp" />
        <title>Editando livro ${book.getId()}</title>
    </head>
    <body>
        <jsp:include page="../components/navbar.jsp" />
        <main class="container d-flex flex-column mt-5">
            <h1>Editando livro ${book.getName()}</h1>
            <div class="d-flex gap-5 justify-content-center">
                <div class="w-25">
                    <img src="${book.getCover()}" class="rounded w-100" id="cover_img">
                </div>
                <div class="d-flex flex-column flex-fill">
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
                    <form:form method="post" action="../${book.getId()}/edit" enctype="multipart/form-data" modelAttribute="book">
                        <form:input type="hidden" path="id" />
                        <div class="mb-3">
                            <form:label path="cover">Capa</form:label>
                            <input type="file" class="form-control ${not empty error ? 'is-invalid' : ''}" name="coverFile" id="cover" />
                        </div>
                        <div class="mb-3">
                            <form:label path="name">Nome</form:label>
                            <form:input type="text" class="form-control" path="name" id="name" required="true" />
                        </div>
                        <div class="mb-3">
                            <form:label path="synopsis">Sinopse</form:label>
                            <form:textarea class="form-control" rows="4" path="synopsis" id="synopsis"></form:textarea>
                        </div>
                        <div class="d-flex gap-3">
                            <div class="mb-3 flex-fill">
                                <form:label path="pages">Páginas</form:label>
                                <form:input type="number" class="form-control" path="pages" id="pages" />
                            </div>
                            <div class="mb-3 flex-fill">
                                <form:label path="chapters">Capítulos</form:label>
                                <form:input type="number" class="form-control" path="chapters" id="chapters" />
                            </div>
                        </div>
                        <div class="mb-3">
                            <form:label path="author">Autor</form:label>
                            <form:input type="text" class="form-control" path="author" id="author" required="true" />
                        </div>
                        <div class="mb-3">
                            <form:label path="publisher">Editora</form:label>
                            <form:input type="text" class="form-control" path="publisher" id="publisher" />
                        </div>
                        <div class="mb-3">
                            <form:label path="year">Ano</form:label>
                            <form:input type="number" class="form-control" path="year" id="year" />
                        </div>
                        <div class="d-flex justify-content-between">
                            <button type="submit" class="btn btn-primary">Salvar</button>
                            <a class="btn btn-danger" href="./book?id=${book.getId()}&option=delete" onclick="return confirm('Tem certeza que quer excluir esse livro?')">Excluir livro</a>
                        </div>
                    </form:form>
                </div>
            </div>
        </main>
    </body>

    <script>
        let coverInput = document.getElementById('cover')
        let coverImage = document.getElementById('cover_img')

        coverImage.addEventListener('click', event => {
            coverInput.click()
        })

        coverInput.addEventListener('change', event => {
            if (!['png', 'jpg', 'jpeg', 'webp'].some(ext => event.target.value.endsWith(ext))) {
                event.target.value = null
                
                return
            }

            let reader = new FileReader()

            reader.onload = () => {
                coverImage.src = reader.result
            }

            reader.readAsDataURL(coverInput.files[0])
        })
    </script>
</html>