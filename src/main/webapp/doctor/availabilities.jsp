<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Mes disponibilités</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav class="navbar">
    <span>🏥 ClinicManager</span>
    <div><a href="${pageContext.request.contextPath}/doctor/dashboard">Dashboard</a></div>
</nav>
<div class="container">
    <h1>🕐 Mes disponibilités</h1>

    <c:if test="${not empty sessionScope.success}">
        <div class="alert alert-success">${sessionScope.success}</div>
        <c:remove var="success" scope="session"/>
    </c:if>

    <table class="table">
        <thead>
            <tr><th>Jour</th><th>Début</th><th>Fin</th><th>Statut</th><th>Action</th></tr>
        </thead>
        <tbody>
            <c:forEach var="a" items="${availabilities}">
                <tr>
                    <td>${a.jour.label}</td>
                    <td>${a.heureDebut}</td>
                    <td>${a.heureFin}</td>
                    <td>${a.statut}</td>
                    <td>
                        <form method="post"
                              action="${pageContext.request.contextPath}/availability/delete"
                              style="display:inline">
                            <input type="hidden" name="id" value="${a.id}">
                            <button class="btn btn-danger">Supprimer</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <h2>Ajouter une disponibilité</h2>
    <form method="post" action="${pageContext.request.contextPath}/availability/add">
        <div class="form-group">
            <label>Jour</label>
            <select name="jour" required>
                <option value="MONDAY">Lundi</option>
                <option value="TUESDAY">Mardi</option>
                <option value="WEDNESDAY">Mercredi</option>
                <option value="THURSDAY">Jeudi</option>
                <option value="FRIDAY">Vendredi</option>
                <option value="SATURDAY">Samedi</option>
            </select>
        </div>
        <div class="form-group">
            <label>Heure début</label>
            <input type="time" name="heureDebut" required>
        </div>
        <div class="form-group">
            <label>Heure fin</label>
            <input type="time" name="heureFin" required>
        </div>
        <button class="btn btn-primary">Ajouter</button>
    </form>
</div>
</body>
</html>