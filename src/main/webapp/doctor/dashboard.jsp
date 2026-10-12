<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Dashboard Médecin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav class="navbar">
    <span>🏥 ClinicManager — Médecin</span>
    <div>
        <span>👤 ${sessionScope.userName}</span>
        <a href="${pageContext.request.contextPath}/auth/logout">Déconnexion</a>
    </div>
</nav>
<div class="container">
    <h1>Bienvenue Dr ${sessionScope.userName}</h1>
    <div class="cards">
        <a href="${pageContext.request.contextPath}/doctor/agenda" class="card">📅 Mon agenda</a>
        <a href="${pageContext.request.contextPath}/availability/list" class="card">🕐 Mes disponibilités</a>
        <a href="${pageContext.request.contextPath}/doctor/appointments" class="card">📋 Mes RDV</a>
        <a href="${pageContext.request.contextPath}/medical-note/list" class="card">📝 Notes médicales</a>
    </div>
</div>
</body>
</html>