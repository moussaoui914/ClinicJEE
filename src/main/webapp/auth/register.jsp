<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Inscription — ClinicManager</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="auth-container">
    <div class="auth-card">
        <h1>🏥 ClinicManager</h1>
        <h2>Créer un compte patient</h2>

        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/auth/register">
            <div class="form-row">
                <div class="form-group">
                    <label for="prenom">Prénom *</label>
                    <input type="text" id="prenom" name="prenom" value="${prenom}" required>
                </div>
                <div class="form-group">
                    <label for="nom">Nom *</label>
                    <input type="text" id="nom" name="nom" value="${nom}" required>
                </div>
            </div>

            <div class="form-group">
                <label for="email">Email *</label>
                <input type="email" id="email" name="email" value="${email}" required>
            </div>

            <div class="form-group">
                <label for="telephone">Téléphone</label>
                <input type="tel" id="telephone" name="telephone" value="${telephone}">
            </div>

            <div class="form-group">
                <label for="motDePasse">Mot de passe * (min. 6 caractères)</label>
                <input type="password" id="motDePasse" name="motDePasse" minlength="6" required>
            </div>

            <div class="form-group">
                <label for="confirmationMotDePasse">Confirmer le mot de passe *</label>
                <input type="password" id="confirmationMotDePasse"
                       name="confirmationMotDePasse" minlength="6" required>
            </div>

            <button type="submit" class="btn btn-primary btn-block">S'inscrire</button>
        </form>

        <p class="auth-link">
            Déjà un compte ?
            <a href="${pageContext.request.contextPath}/auth/login.jsp">Se connecter</a>
        </p>
    </div>
</div>
</body>
</html>