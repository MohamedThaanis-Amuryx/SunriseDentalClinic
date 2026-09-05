<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.app.model.User" %>
<%@ page import="com.app.model.Dentist" %>
<%@ page import="com.app.model.Treatment" %>
<%@ page import="java.util.List" %>
<%
    User loggedInUser = (User) session.getAttribute("loggedInUser");
    if (loggedInUser == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
    String ctx = request.getContextPath();
    request.setAttribute("activePage", "register");

    List<Dentist> dentists = (List<Dentist>) request.getAttribute("dentists");
    List<Treatment> treatments = (List<Treatment>) request.getAttribute("treatments");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Register Appointment</title>
    <link rel="stylesheet" href="<%= ctx %>/resources/css/style.css">
</head>
<body>

    <jsp:include page="/sidebar.jsp" />

    <div class="main">
        <a class="back-link" href="<%= ctx %>/dashboard">&larr; Back to Dashboard</a>
        <div class="page-title">Register new appointment</div>
        <div class="page-sub">Add a patient and book a dentist slot.</div>

        <div class="form-box">
            <% if (request.getAttribute("error") != null) { %>
                <p class="error"><%= request.getAttribute("error") %></p>
            <% } %>
            <% if (request.getAttribute("success") != null) { %>
                <p class="success"><%= request.getAttribute("success") %></p>
            <% } %>

            <form action="<%= ctx %>/registerAppointment" method="post" novalidate>

                <label for="patientName">Patient Name</label>
                <input type="text" id="patientName" name="patientName" pattern="[a-zA-Z .'\-]{2,100}" title="2-100 letters, spaces, dots or hyphens only" value="<%= request.getAttribute("patientName") != null ? request.getAttribute("patientName") : "" %>" required>

                <label for="address">Address</label>
                <input type="text" id="address" name="address" minlength="5" maxlength="255" value="<%= request.getAttribute("address") != null ? request.getAttribute("address") : "" %>" required>

                <label for="contactNumber">Contact Number</label>
                <input type="tel" id="contactNumber" name="contactNumber" pattern="[0-9]{10}" title="Enter exactly 10 digits" placeholder="0771234567" value="<%= request.getAttribute("contactNumber") != null ? request.getAttribute("contactNumber") : "" %>" required>

                <label for="dentistId">Dentist</label>
                <select id="dentistId" name="dentistId" required>
                    <option value="">-- Select Dentist --</option>
                    <% if (dentists != null) {
                        for (Dentist d : dentists) { %>
                            <option value="<%= d.getDentistId() %>" <%= String.valueOf(d.getDentistId()).equals(request.getAttribute("selectedDentistId")) ? "selected" : "" %>><%= d.getDentistName() %> (<%= d.getSpecialization() %>)</option>
                    <%  }
                    } %>
                </select>

                <label for="treatmentId">Treatment Type</label>
                <select id="treatmentId" name="treatmentId" required>
                    <option value="">-- Select Treatment --</option>
                    <% if (treatments != null) {
                        for (Treatment t : treatments) { %>
                            <option value="<%= t.getTreatmentId() %>" <%= String.valueOf(t.getTreatmentId()).equals(request.getAttribute("selectedTreatmentId")) ? "selected" : "" %>><%= t.getTreatmentName() %> (Rs. <%= t.getConsultationFee() %>)</option>
                    <%  }
                    } %>
                </select>

                <label for="appointmentDate">Appointment Date</label>
                <input type="date" id="appointmentDate" name="appointmentDate" value="<%= request.getAttribute("appointmentDate") != null ? request.getAttribute("appointmentDate") : "" %>" required>

                <label for="appointmentTime">Appointment Time (8:00 AM - 6:00 PM)</label>
                <input type="time" id="appointmentTime" name="appointmentTime" min="08:00" max="18:00" value="<%= request.getAttribute("appointmentTime") != null ? request.getAttribute("appointmentTime") : "" %>" required>

                <button type="submit">Save Appointment</button>
            </form>
        </div>
    </div>

    <script>
        var dateInput = document.getElementById('appointmentDate');
        var timeInput = document.getElementById('appointmentTime');

        dateInput.min = new Date().toISOString().split('T')[0];

        function restrictPastTime() {
            var today = new Date().toISOString().split('T')[0];
            if (dateInput.value === today) {
                var now = new Date();
                var hh = String(now.getHours()).padStart(2, '0');
                var mm = String(now.getMinutes()).padStart(2, '0');
                timeInput.min = (hh + ':' + mm > '08:00') ? (hh + ':' + mm) : '08:00';
            } else {
                timeInput.min = '08:00';
            }
        }

        dateInput.addEventListener('change', restrictPastTime);
        restrictPastTime();

        document.querySelector('form').addEventListener('submit', function(e) {
            var invalidField = this.querySelector(':invalid');
            if (invalidField) {
                e.preventDefault();
                showFormError(invalidField.title || invalidField.validationMessage);
            }
        });

        function showFormError(message) {
            var existing = document.querySelector('.js-error');
            if (existing) existing.remove();

            var p = document.createElement('p');
            p.className = 'error js-error';
            p.textContent = message;
            document.querySelector('form').prepend(p);
        }
    </script>
</body>
</html>
