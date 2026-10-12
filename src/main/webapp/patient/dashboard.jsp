<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Dashboard Patient</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav class="navbar">
    <span>🏥 ClinicManager — Patient</span>
    <div>
        <span>👤 ${sessionScope.userName}</span>
        <a href="${pageContext.request.contextPath}/profile">Profil</a>
        <a href="${pageContext.request.contextPath}/appointment/list">Mes RDV</a>
        <a href="${pageContext.request.contextPath}/auth/logout">Déconnexion</a>
    </div>
</nav>
<div class="container">
    <h1>Bienvenue, ${sessionScope.userName} !</h1>
    <div class="cards">
        <a href="${pageContext.request.contextPath}/patient/book-appointment.jsp" class="card">
            📅 Prendre un rendez-vous
        </a>
        <a href="${pageContext.request.contextPath}/appointment/list" class="card">
            📋 Mes rendez-vous
        </a>
        <a href="${pageContext.request.contextPath}/patient/history" class="card">
            📜 Mon historique
        </a>
        <a href="${pageContext.request.contextPath}/profile" class="card">
            👤 Mon profil
        </a>
    </div>
</div>
</body>
</html>