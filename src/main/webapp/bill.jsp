<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.app.model.User" %>
<%@ page import="com.app.model.Bill" %>
<%
    User loggedInUser = (User) session.getAttribute("loggedInUser");
    if (loggedInUser == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
    String ctx = request.getContextPath();
    request.setAttribute("activePage", "search");

    Bill bill = (Bill) request.getAttribute("bill");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Bill / Receipt</title>
    <link rel="stylesheet" href="<%= ctx %>/resources/css/style.css">
</head>
<body>

    <div class="no-print"><jsp:include page="/sidebar.jsp" /></div>

    <div class="main">
        <a class="back-link no-print" href="<%= ctx %>/searchAppointment.jsp">&larr; Back to Search</a>
        <div class="page-title">Sunrise Dental Clinic</div>
        <div class="page-sub">Patient bill / receipt</div>

        <div class="form-box">
            <table>
                <tr><td class="label">Bill No.</td><td>#<%= bill.getBillId() %></td></tr>
                <tr><td class="label">Appointment No.</td><td><%= bill.getAppointmentNumber() %></td></tr>
                <tr><td class="label">Patient Name</td><td><%= bill.getPatientName() %></td></tr>
                <tr><td class="label">Dentist</td><td><%= bill.getDentistName() %></td></tr>
                <tr><td class="label">Treatment</td><td><%= bill.getTreatmentName() %></td></tr>
                <tr><td class="label">Bill Date</td><td><%= bill.getBillDate() %></td></tr>
                <tr class="total-row"><td>Total Amount</td><td>Rs. <%= bill.getTotalAmount() %></td></tr>
            </table>

            <button class="no-print" onclick="window.print()">Print Bill</button>
        </div>
    </div>

</body>
</html>
