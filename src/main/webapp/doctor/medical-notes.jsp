<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Notes médicales</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav class="navbar">
    <span>🏥 ClinicManager</span>
    <div><a href="${pageContext.request.contextPath}/doctor/dashboard">Dashboard</a></div>
</nav>
<div class="container">
    <h1>📝 Notes médicales</h1>
    <table class="table">
        <thead><tr><th>Date</th><th>Diagnostic</th><th>Validée</th><th>Action</th></tr></thead>
        <tbody>
            <c:forEach var="n" items="${notes}">
                <tr>
                    <td>${n.dateNote}</td>
                    <td>${n.diagnostic}</td>
                    <td>${n.validee}</td>
                    <td>
                        <c:if test="${!n.validee}">
                            <form method="post"
                                  action="${pageContext.request.contextPath}/medical-note/validate"
                                  style="display:inline">
                                <input type="hidden" name="id" value="${n.id}">
                                <button class="btn btn-primary">Valider</button>
                            </form>
                        </c:if>
                        <c:if test="${n.validee}">
                            <span class="badge badge-DONE">🔒 READ-ONLY</span>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>