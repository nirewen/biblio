<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<div class="card mb-3" style="max-width: 540px;">
  <div class="row g-0">
    <div class="col-md-4">
      <img src="${book.getCover().startsWith("data:image/") ? book.getCover() : "/biblio/image/default_cover.png"}" class="img-fluid rounded-start" alt="...">
    </div>
    <div class="col-md-8">
      <div class="card-body">
        <h5 class="card-title">
            <a href="/biblio/book?id=${book.getId()}">${book.getName()}</a>
        </h5>
        <p class="card-text">${book.getSynopsis()}</p>
        <p class="card-text">${book.getAuthor()}</p>
        <p class="card-text"><small class="text-muted">${book.getYear()}</small></p>
      </div>
    </div>
  </div>
</div>