<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Dashboard Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav class="navbar">
    <span>🏥 ClinicManager — Admin</span>
    <div>
        <span>👤 ${sessionScope.userName}</span>
        <a href="${pageContext.request.contextPath}/auth/logout">Déconnexion</a>
    </div>
</nav>
<div class="container">
    <h1>Administration</h1>
    <div class="cards">
        <a href="${pageContext.request.contextPath}/admin/patients" class="card">👥 Patients</a>
        <a href="${pageContext.request.contextPath}/admin/doctors" class="card">🩺 Médecins</a>
        <a href="${pageContext.request.contextPath}/admin/specialties" class="card">🔬 Spécialités</a>
        <a href="${pageContext.request.contextPath}/admin/departments" class="card">🏢 Départements</a>
        <a href="${pageContext.request.contextPath}/admin/users" class="card">👤 Utilisateurs</a>
    </div>
</div>
</body>
</html>