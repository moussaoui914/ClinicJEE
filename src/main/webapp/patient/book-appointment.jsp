<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Prendre un RDV</title>
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
    <h1>📅 Prendre un rendez-vous</h1>
    <form method="post" action="${pageContext.request.contextPath}/appointment/create">
        <div class="form-group">
            <label>ID Médecin</label>
            <input type="number" name="doctorId" required>
        </div>
        <div class="form-group">
            <label>Date et heure (ex : 2025-12-01T10:30)</label>
            <input type="datetime-local" name="dateRdv" required>
        </div>
        <div class="form-group">
            <label>Type</label>
            <select name="type" required>
                <option value="CONSULTATION">Consultation</option>
                <option value="FOLLOW_UP">Suivi</option>
                <option value="URGENT">Urgent</option>
            </select>
        </div>
        <div class="form-group">
            <label>Motif</label>
            <textarea name="motif"></textarea>
        </div>
        <button class="btn btn-primary">Créer le rendez-vous</button>
    </form>
</div>
</body>
</html>