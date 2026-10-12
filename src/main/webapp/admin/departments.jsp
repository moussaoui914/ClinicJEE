<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Départements</title>
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
    <h1>🏢 Gestion des départements</h1>

    <c:if test="${not empty sessionScope.success}">
        <div class="alert alert-success">${sessionScope.success}</div>
        <c:remove var="success" scope="session"/>
    </c:if>
    <c:if test="${not empty sessionScope.error}">
        <div class="alert alert-error">${sessionScope.error}</div>
        <c:remove var="error" scope="session"/>
    </c:if>

    <h2>➕ Ajouter un département</h2>
    <form method="post" action="${pageContext.request.contextPath}/admin/departments/create" class="form-card">
        <div class="form-group">
            <label>Nom *</label>
            <input type="text" name="nom" required>
        </div>
        <div class="form-group">
            <label>Description</label>
            <textarea name="description"></textarea>
        </div>
        <button class="btn btn-primary">Ajouter</button>
    </form>

    <h2>📋 Liste des départements</h2>
    <table class="table">
        <thead>
            <tr><th>ID</th><th>Nom</th><th>Description</th><th>Actions</th></tr>
        </thead>
        <tbody>
            <c:forEach var="d" items="${departments}">
                <tr>
                    <td>${d.id}</td>
                    <td>${d.nom}</td>
                    <td>${d.description}</td>
                    <td>
                        <form method="post" action="${pageContext.request.contextPath}/admin/departments/delete"
                              style="display:inline"
                              onsubmit="return confirm('Supprimer ce département ?');">
                            <input type="hidden" name="id" value="${d.id}">
                            <button class="btn btn-danger">Supprimer</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty departments}">
                <tr><td colspan="4">Aucun département.</td></tr>
            </c:if>
        </tbody>
    </table>
</div>
</body>
</html>