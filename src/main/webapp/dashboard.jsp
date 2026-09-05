<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.app.model.User" %>
<%
    User loggedInUser = (User) session.getAttribute("loggedInUser");
    if (loggedInUser == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
    String ctx = request.getContextPath();
    request.setAttribute("activePage", "dashboard");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard - Sunrise Dental Clinic</title>
    <link rel="stylesheet" href="<%= ctx %>/resources/css/style.css">
</head>
<body>

    <jsp:include page="/sidebar.jsp" />

    <div class="main">
        <div class="page-title">Welcome back, <%= loggedInUser.getFullName() %></div>
        <div class="page-sub">Here's what's happening at Sunrise Dental Clinic today.</div>

        <div class="cards">
            <div class="card">
                <div class="card-label">Today's appointments</div>
                <div class="card-value"><%= request.getAttribute("todayCount") != null ? request.getAttribute("todayCount") : "-" %></div>
                <span class="card-icon badge-info">Scheduled</span>
            </div>
            <div class="card">
                <div class="card-label">Total appointments</div>
                <div class="card-value"><%= request.getAttribute("totalCount") != null ? request.getAttribute("totalCount") : "-" %></div>
                <span class="card-icon badge-open">All time</span>
            </div>
            <div class="card">
                <div class="card-label">Registered patients</div>
                <div class="card-value"><%= request.getAttribute("patientCount") != null ? request.getAttribute("patientCount") : "-" %></div>
                <span class="card-icon badge-info">Total</span>
            </div>
            <div class="card">
                <div class="card-label">Dentists on staff</div>
                <div class="card-value"><%= request.getAttribute("dentistCount") != null ? request.getAttribute("dentistCount") : "-" %></div>
                <span class="card-icon badge-open">Active</span>
            </div>
        </div>

        <div class="section-title">Quick actions</div>
        <div class="quick-actions">
            <a class="action-card" href="<%= ctx %>/registerAppointment">
                <div class="action-title">Register new appointment</div>
                <div class="action-desc">Add a patient and book a slot</div>
            </a>
            <a class="action-card" href="<%= ctx %>/searchAppointment.jsp">
                <div class="action-title">Search appointment</div>
                <div class="action-desc">Look up by appointment number</div>
            </a>
            <a class="action-card" href="<%= ctx %>/help.jsp">
                <div class="action-title">Help guide</div>
                <div class="action-desc">Step-by-step instructions</div>
            </a>
        </div>
    </div>

</body>
</html>
