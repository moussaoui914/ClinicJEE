<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Mon profil</title>
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
    <h1>👤 Mon profil</h1>

    <c:if test="${not empty sessionScope.success}">
        <div class="alert alert-success">${sessionScope.success}</div>
        <c:remove var="success" scope="session"/>
    </c:if>
    <c:if test="${not empty sessionScope.error}">
        <div class="alert alert-error">${sessionScope.error}</div>
        <c:remove var="error" scope="session"/>
    </c:if>

    <div class="profile-card">
        <h3>Informations personnelles</h3>
        <form method="post" action="${pageContext.request.contextPath}/profile/update">
            <div class="form-row">
                <div class="form-group">
                    <label>Prénom</label>
                    <input type="text" name="prenom" value="${user.prenom}" required>
                </div>
                <div class="form-group">
                    <label>Nom</label>
                    <input type="text" name="nom" value="${user.nom}" required>
                </div>
            </div>
            <div class="form-group">
                <label>Email (non modifiable)</label>
                <input type="email" value="${user.email}" disabled>
            </div>
            <div class="form-group">
                <label>Téléphone</label>
                <input type="tel" name="telephone" value="${user.telephone}">
            </div>
            <button type="submit" class="btn btn-primary">Enregistrer</button>
        </form>

        <hr>

        <h3>🔒 Changer le mot de passe</h3>
        <form method="post" action="${pageContext.request.contextPath}/profile/password">
            <div class="form-group">
                <label>Ancien mot de passe</label>
                <input type="password" name="ancienMotDePasse" required>
            </div>
            <div class="form-group">
                <label>Nouveau mot de passe (min. 6)</label>
                <input type="password" name="nouveauMotDePasse" minlength="6" required>
            </div>
            <div class="form-group">
                <label>Confirmer</label>
                <input type="password" name="confirmationMotDePasse" minlength="6" required>
            </div>
            <button type="submit" class="btn btn-warning">Changer</button>
        </form>
    </div>
</div>
</body>
</html>