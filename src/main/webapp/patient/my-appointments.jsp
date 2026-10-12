<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Mes rendez-vous</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav class="navbar">
    <span>🏥 ClinicManager</span>
    <div>
        <a href="${pageContext.request.contextPath}/patient/dashboard">Dashboard</a>
        <a href="${pageContext.request.contextPath}/auth/logout">Déconnexion</a>
    </div>
</nav>
<div class="container">
    <h1>📋 Mes rendez-vous</h1>

    <c:if test="${not empty sessionScope.success}">
        <div class="alert alert-success">${sessionScope.success}</div>
        <c:remove var="success" scope="session"/>
    </c:if>
    <c:if test="${not empty sessionScope.error}">
        <div class="alert alert-error">${sessionScope.error}</div>
        <c:remove var="error" scope="session"/>
    </c:if>

    <table class="table">
        <thead>
            <tr>
                <th>Date</th>
                <th>Médecin</th>
                <th>Spécialité</th>
                <th>Type</th>
                <th>Statut</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="a" items="${appointments}">
                <tr>
                    <td>${a.dateRdv}</td>
                    <td>${a.doctorNom}</td>
                    <td>${a.specialtyNom}</td>
                    <td>${a.type}</td>
                    <td><span class="badge badge-${a.statut}">${a.statut}</span></td>
                    <td>
                        <c:if test="${a.statut == 'PLANNED'}">
                            <form method="post"
                                  action="${pageContext.request.contextPath}/appointment/cancel"
                                  style="display:inline">
                                <input type="hidden" name="id" value="${a.id}">
                                <button class="btn btn-danger" type="submit">Annuler</button>
                            </form>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty appointments}">
                <tr><td colspan="6">Aucun rendez-vous.</td></tr>
            </c:if>
        </tbody>
    </table>
</div>
</body>
</html>