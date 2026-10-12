<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Connexion — ClinicManager</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="auth-container">
    <div class="auth-card">
        <h1>🏥 ClinicManager</h1>
        <h2>Connexion</h2>

        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>
        <c:if test="${not empty sessionScope.success}">
            <div class="alert alert-success">${sessionScope.success}</div>
            <c:remove var="success" scope="session"/>
        </c:if>
        <c:if test="${param.logout == 'true'}">
            <div class="alert alert-info">Vous avez été déconnecté.</div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/auth/login">
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email"
                       value="${email}" required autofocus>
            </div>
            <div class="form-group">
                <label for="motDePasse">Mot de passe</label>
                <input type="password" id="motDePasse" name="motDePasse" required>
            </div>
            <button type="submit" class="btn btn-primary btn-block">Se connecter</button>
        </form>

        <p class="auth-link">
            Pas encore de compte ?
            <a href="${pageContext.request.contextPath}/auth/register.jsp">S'inscrire</a>
        </p>
    </div>
</div>
</body>
</html>