<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.app.model.User" %>
<%
    User loggedInUser = (User) session.getAttribute("loggedInUser");
    if (loggedInUser == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
    String ctx = request.getContextPath();
    request.setAttribute("activePage", "help");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Help - Sunrise Dental Clinic</title>
    <link rel="stylesheet" href="<%= ctx %>/resources/css/style.css">
    <style>
        .help-box { background: #161b22; border: 1px solid #21262d; padding: 24px 28px; border-radius: 8px; max-width: 650px; }
        .help-box h3 { color: #58a6ff; margin-top: 22px; margin-bottom: 6px; font-size: 15px; }
        .help-box ol { margin-top: 6px; padding-left: 20px; }
        .help-box li { margin-bottom: 6px; line-height: 1.6; font-size: 14px; color: #c9d1d9; }
        .tip { background: #0d2847; border-left: 3px solid #58a6ff; padding: 10px 14px; margin-top: 10px; font-size: 13px; color: #c9d1d9; border-radius: 0 6px 6px 0; }
    </style>
</head>
<body>

    <jsp:include page="/sidebar.jsp" />

    <div class="main">
        <a class="back-link" href="<%= ctx %>/dashboard">&larr; Back to Dashboard</a>
        <div class="page-title">Help &amp; instructions</div>
        <div class="page-sub">A quick guide for new staff on using this system.</div>

        <div class="help-box">
            <h3>1. Logging In</h3>
            <ol>
                <li>Go to the login page.</li>
                <li>Enter the username and password given to you by the clinic administrator.</li>
                <li>Click Login. If your details are wrong, you'll see an error message, try again.</li>
            </ol>

            <h3>2. Registering a New Appointment</h3>
            <ol>
                <li>From the Dashboard, click "Register New Appointment".</li>
                <li>Fill in the patient's name, address, and contact number.</li>
                <li>Select the dentist and treatment type from the dropdown lists.</li>
                <li>Choose the appointment date and time.</li>
                <li>Click "Save Appointment". You'll get a new appointment number, e.g. APT-0001.</li>
            </ol>
            <div class="tip">Tip: dates can't be in the past, and appointment times must fall between 8:00 AM and 6:00 PM.</div>

            <h3>3. Searching for an Appointment</h3>
            <ol>
                <li>From the Dashboard, click "Search Appointment".</li>
                <li>Type in the appointment number.</li>
                <li>Click Search to see the full details.</li>
            </ol>

            <h3>4. Generating a Bill</h3>
            <ol>
                <li>Search for the appointment first.</li>
                <li>On the details page, click "Generate Bill".</li>
                <li>The total is calculated automatically from the treatment type.</li>
                <li>Click "Print Bill" to print a copy for the patient.</li>
            </ol>
            <div class="tip">Tip: generating a bill twice for the same appointment won't create a duplicate charge.</div>

            <h3>5. Logging Out</h3>
            <ol>
                <li>Click "Logout" in the sidebar when you're done for the day.</li>
            </ol>

            <h3>Need more help?</h3>
            <p style="font-size: 14px; color: #c9d1d9;">Contact the system administrator or your supervisor.</p>
        </div>
    </div>

</body>
</html>
