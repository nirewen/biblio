<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>

<div class="card mb-3">
  <div class="row g-0">
    <div class="col-md-1" style="width: 6%;">
      <img src="${loan.getBook().getCover()}" class="w-100 img-fluid rounded-start" alt="cover">
    </div>
    <div class="col-md-9">
      <div class="card-body">
        <a href="/biblio/book/${loan.getBook().getId()}" class="text-decoration-none text-body">
          <h5 class="card-title">${loan.getBook().getName()}</h5>
        </a>
        <p class="card-text d-flex flex-column gap-2">
          <small class="text-muted">Data do aluguel: ${loan.getDate().toString()}</small>
          <small class="text-muted">Data de entrega: ${loan.getDevolutionDate().toString()}</small>
        </p>
      </div>
    </div>
    <div class="col">
      <div class="card-body h-100 d-flex flex-column justify-content-between">
      <c:choose>
        <c:when test="${loan.isActive()}">
          <a class="btn btn-primary" href="/biblio/loan/${loan.getId()}/postpone" role="button">Prorrogar</a>
          <a class="btn btn-light" href="/biblio/loan/${loan.getId()}/return" role="button" onclick="return confirm('Deseja devolver ${loan.getBook().getName()}?')">Devolver</a>
        </c:when>
        <c:otherwise>
          <a class="btn btn-success disabled" href="#" role="button">Entregue</a>
        </c:otherwise>
      </c:choose>
      </div>
    </div>
  </div>
</div>