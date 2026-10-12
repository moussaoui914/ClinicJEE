<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Médecins</title>
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
    <h1>🩺 Gestion des médecins</h1>

    <c:if test="${not empty sessionScope.success}">
        <div class="alert alert-success">${sessionScope.success}</div>
        <c:remove var="success" scope="session"/>
    </c:if>
    <c:if test="${not empty sessionScope.error}">
        <div class="alert alert-error">${sessionScope.error}</div>
        <c:remove var="error" scope="session"/>
    </c:if>

    <h2>➕ Ajouter un médecin</h2>
    <form method="post" action="${pageContext.request.contextPath}/admin/doctors/create" class="form-card">
        <div class="form-row">
            <div class="form-group"><label>Nom *</label><input type="text" name="nom" required></div>
            <div class="form-group"><label>Prénom *</label><input type="text" name="prenom" required></div>
        </div>
        <div class="form-row">
            <div class="form-group"><label>Email *</label><input type="email" name="email" required></div>
            <div class="form-group"><label>Téléphone</label><input type="tel" name="telephone"></div>
        </div>
        <div class="form-row">
            <div class="form-group"><label>Mot de passe * (min 6)</label><input type="password" name="motDePasse" minlength="6" required></div>
            <div class="form-group"><label>Matricule *</label><input type="text" name="matricule" required></div>
        </div>
        <div class="form-row">
            <div class="form-group"><label>Titre</label><input type="text" name="titre" placeholder="Dr, Pr..."></div>
            <div class="form-group">
                <label>Spécialité *</label>
                <select name="specialtyId" required>
                    <c:forEach var="s" items="${specialties}">
                        <option value="${s.id}">${s.nom}</option>
                    </c:forEach>
                </select>
            </div>
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

    <h2>📋 Liste des médecins</h2>
    <table class="table">
        <thead>
            <tr>
                <th>ID</th><th>Nom</th><th>Matricule</th><th>Titre</th>
                <th>Email</th><th>Spécialité</th><th>Département</th><th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="d" items="${doctors}">
                <tr>
                    <td>${d.id}</td>
                    <td>${d.nomComplet}</td>
                    <td>${d.matricule}</td>
                    <td>${d.titre}</td>
                    <td>${d.email}</td>
                    <td>${d.specialtyNom}</td>
                    <td>${d.departmentNom}</td>
                    <td>
                        <form method="post" action="${pageContext.request.contextPath}/admin/doctors/delete"
                              style="display:inline"
                              onsubmit="return confirm('Supprimer ce médecin ?');">
                            <input type="hidden" name="id" value="${d.id}">
                            <button class="btn btn-danger">Supprimer</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty doctors}">
                <tr><td colspan="8">Aucun médecin.</td></tr>
            </c:if>
        </tbody>
    </table>
</div>
</body>
</html>