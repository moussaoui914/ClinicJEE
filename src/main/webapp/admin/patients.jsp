<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Patients</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav class="navbar">
    <span>🏥 ClinicManager — Admin</span>
    <div>
        <a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a>
        <a href="${pageContext.request.contextPath}/auth/logout">Déconnexion</a>
    </div>
</nav>

<div class="container">
    <h1>👥 Gestion des patients</h1>

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
                <th>ID</th><th>Nom</th><th>Prénom</th><th>Email</th>
                <th>CIN</th><th>Téléphone</th><th>Genre</th><th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="p" items="${patients}">
                <tr>
                    <td>${p.id}</td>
                    <td>${p.nom}</td>
                    <td>${p.prenom}</td>
                    <td>${p.email}</td>
                    <td>${p.cin}</td>
                    <td>${p.telephone}</td>
                    <td>${p.genre}</td>
                    <td>
                        <form method="post" action="${pageContext.request.contextPath}/admin/patients/delete"
                              style="display:inline"
                              onsubmit="return confirm('Supprimer ce patient ?');">
                            <input type="hidden" name="id" value="${p.id}">
                            <button class="btn btn-danger">Supprimer</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty patients}">
                <tr><td colspan="8">Aucun patient.</td></tr>
            </c:if>
        </tbody>
    </table>
</div>
</body>
</html>