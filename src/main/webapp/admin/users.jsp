<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Utilisateurs</title>
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
    <h1>👤 Gestion des utilisateurs</h1>

    <table class="table">
        <thead>
            <tr><th>ID</th><th>Nom</th><th>Email</th><th>Rôle</th><th>Actif</th><th>Actions</th></tr>
        </thead>
        <tbody>
            <c:forEach var="u" items="${users}">
                <tr>
                    <td>${u.id}</td>
                    <td>${u.nomComplet}</td>
                    <td>${u.email}</td>
                    <td><span class="badge badge-${u.role}">${u.role}</span></td>
                    <td>${u.actif ? '✅' : '❌'}</td>
                    <td>
                        <c:if test="${u.actif}">
                            <form method="post" action="${pageContext.request.contextPath}/admin/users/delete"
                                  style="display:inline"
                                  onsubmit="return confirm('Désactiver cet utilisateur ?');">
                                <input type="hidden" name="id" value="${u.id}">
                                <button class="btn btn-danger">Désactiver</button>
                            </form>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>