<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<a href="/biblio/book?id=${book.getId()}" class="text-decoration-none text-body">
  <div class="card" style="max-width: 540px;">
    <img src="${book.getCover().startsWith("data:image/") ? book.getCover() : "/biblio/image/default_cover.png"}" class="card-img-top" alt="...">
    <div class="card-body">
      <h5 class="card-title">${book.getName()}</h5>
      <p class="card-text">${book.getAuthor()}</p>
    </div>
  </div>
</a>