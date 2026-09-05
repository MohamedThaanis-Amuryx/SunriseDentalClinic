<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.app.model.User" %>
<%@ page import="com.app.model.Appointment" %>
<%
    User loggedInUser = (User) session.getAttribute("loggedInUser");
    if (loggedInUser == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
    String ctx = request.getContextPath();
    request.setAttribute("activePage", "search");

    Appointment appointment = (Appointment) request.getAttribute("appointment");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Appointment Details</title>
    <link rel="stylesheet" href="<%= ctx %>/resources/css/style.css">
</head>
<body>

    <jsp:include page="/sidebar.jsp" />

    <div class="main">
        <a class="back-link" href="<%= ctx %>/searchAppointment.jsp">&larr; New Search</a>
        <div class="page-title">Appointment details</div>
        <div class="page-sub">Full record for appointment <%= appointment.getAppointmentNumber() %></div>

        <div class="form-box">
            <table>
                <tr><td class="label">Appointment No.</td><td><%= appointment.getAppointmentNumber() %></td></tr>
                <tr><td class="label">Patient Name</td><td><%= appointment.getPatientName() %></td></tr>
                <tr><td class="label">Dentist</td><td><%= appointment.getDentistName() %></td></tr>
                <tr><td class="label">Treatment</td><td><%= appointment.getTreatmentName() %></td></tr>
                <tr><td class="label">Date</td><td><%= appointment.getAppointmentDate() %></td></tr>
                <tr><td class="label">Time</td><td><%= appointment.getAppointmentTime() %></td></tr>
                <tr><td class="label">Status</td><td><span class="status-pill"><%= appointment.getStatus() %></span></td></tr>
            </table>

            <a class="btn" href="<%= ctx %>/generateBill?appointmentNumber=<%= appointment.getAppointmentNumber() %>">Generate Bill</a>
        </div>
    </div>

</body>
</html>
