<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Spécialités</title>
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
    <h1>🔬 Gestion des spécialités</h1>

    <c:if test="${not empty sessionScope.success}">
        <div class="alert alert-success">${sessionScope.success}</div>
        <c:remove var="success" scope="session"/>
    </c:if>
    <c:if test="${not empty sessionScope.error}">
        <div class="alert alert-error">${sessionScope.error}</div>
        <c:remove var="error" scope="session"/>
    </c:if>

    <h2>➕ Ajouter une spécialité</h2>
    <form method="post" action="${pageContext.request.contextPath}/admin/specialties/create" class="form-card">
        <div class="form-group">
            <label>Nom *</label>
            <input type="text" name="nom" required>
        </div>
        <div class="form-group">
            <label>Description</label>
            <textarea name="description"></textarea>
        </div>
        <div class="form-group">
            <label>Département *</label>
            <select name="departmentId" required>
                <c:forEach var="d" items="${departments}">
                    <option value="${d.id}">${d.nom}</option>
                </c:forEach>
            </select>
        </div>
        <button class="btn btn-primary">Ajouter</button>
    </form>

    <h2>📋 Liste des spécialités</h2>
    <table class="table">
        <thead>
            <tr><th>ID</th><th>Nom</th><th>Description</th><th>Département</th><th>Actions</th></tr>
        </thead>
        <tbody>
            <c:forEach var="s" items="${specialties}">
                <tr>
                    <td>${s.id}</td>
                    <td>${s.nom}</td>
                    <td>${s.description}</td>
                    <td>${s.departmentNom}</td>
                    <td>
                        <form method="post" action="${pageContext.request.contextPath}/admin/specialties/delete"
                              style="display:inline"
                              onsubmit="return confirm('Supprimer cette spécialité ?');">
                            <input type="hidden" name="id" value="${s.id}">
                            <button class="btn btn-danger">Supprimer</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty specialties}">
                <tr><td colspan="5">Aucune spécialité.</td></tr>
            </c:if>
        </tbody>
    </table>
</div>
</body>
</html>