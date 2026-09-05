<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.app.model.User" %>
<%
	User loggedInUser = (User) session.getAttribute("loggedInUser");
	if (loggedInUser == null) {
	    response.sendRedirect(request.getContextPath() + "/login");
	    return;
	}
    String ctx = request.getContextPath();
    String activePage = (String) request.getAttribute("activePage");
    if (activePage == null) activePage = "";
%>
<div class="sidebar">
    <div class="brand">
        <div class="brand-icon">SD</div>
        <div>
            <div class="brand-name">Sunrise Dental</div>
            <div class="brand-sub">Clinic Management</div>
        </div>
    </div>

    <div class="nav-section">
        <div class="nav-label">Menu</div>
        <a class="nav-link <%= activePage.equals("dashboard") ? "active" : "" %>" href="<%= ctx %>/dashboard"><span class="dot"></span> Dashboard</a>
        <a class="nav-link <%= activePage.equals("register") ? "active" : "" %>" href="<%= ctx %>/registerAppointment"><span class="dot"></span> Register Appointment</a>
        <a class="nav-link <%= activePage.equals("search") ? "active" : "" %>" href="<%= ctx %>/searchAppointment.jsp"><span class="dot"></span> Search Appointment</a>
        <a class="nav-link <%= activePage.equals("help") ? "active" : "" %>" href="<%= ctx %>/help.jsp"><span class="dot"></span> Help</a>
    </div>

    <div class="sidebar-footer">
        <div class="user-chip">
            <div class="avatar"><%= loggedInUser.getFullName().substring(0,1) %></div>
            <div>
                <div class="user-name"><%= loggedInUser.getFullName() %></div>
                <div class="user-role"><%= loggedInUser.getRole() %></div>
            </div>
        </div>
        <a class="logout-link" href="<%= ctx %>/logout">Logout</a>
    </div>
</div>
