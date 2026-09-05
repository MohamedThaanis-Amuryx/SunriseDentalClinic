<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.app.model.User" %>
<%
    User loggedInUser = (User) session.getAttribute("loggedInUser");
    if (loggedInUser == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
    String ctx = request.getContextPath();
    request.setAttribute("activePage", "search");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Search Appointment</title>
    <link rel="stylesheet" href="<%= ctx %>/resources/css/style.css">
</head>
<body>

    <jsp:include page="/sidebar.jsp" />

    <div class="main">
        <a class="back-link" href="<%= ctx %>/dashboard">&larr; Back to Dashboard</a>
        <div class="page-title">Search appointment</div>
        <div class="page-sub">Look up an appointment using its appointment number.</div>

        <div class="form-box">
            <% if (request.getAttribute("error") != null) { %>
                <p class="error"><%= request.getAttribute("error") %></p>
            <% } %>

            <form action="<%= ctx %>/searchAppointment" method="get">
                <label for="appointmentNumber">Appointment Number</label>
                <input type="text" id="appointmentNumber" name="appointmentNumber" placeholder="e.g. APT-0001" required>
                <button type="submit">Search</button>
            </form>
        </div>
    </div>

</body>
</html>
