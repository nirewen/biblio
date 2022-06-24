<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<a href="/biblio/book?id=${book.getId()}" class="text-decoration-none text-body">
  <div class="card" style="max-width: 540px;">
    <img src="${book.getCover()}" class="card-img-top" alt="Capa de ${book.getName()}" style="aspect-ratio: 2 / 3;">
    <div class="card-body">
      <h5 class="card-title" style="height: 3rem; display: -webkit-box; -webkit-line-clamp: 2; -webkit-box-orient: vertical; overflow: hidden;">${book.getName()}</h5>
      <p class="card-text">${book.getAuthor()}</p>
    </div>
  </div>
</a>