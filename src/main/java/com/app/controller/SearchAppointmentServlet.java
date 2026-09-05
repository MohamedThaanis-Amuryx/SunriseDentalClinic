package com.app.controller;

import com.app.dao.AppointmentDAO;
import com.app.dao.impl.AppointmentDAOImpl;
import com.app.model.Appointment;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/searchAppointment")
public class SearchAppointmentServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final AppointmentDAO appointmentDAO = new AppointmentDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getSession().getAttribute("loggedInUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String appointmentNumber = request.getParameter("appointmentNumber");

        if (appointmentNumber == null || appointmentNumber.trim().isEmpty()) {
            request.getRequestDispatcher("/searchAppointment.jsp").forward(request, response);
            return;
        }

        Appointment appointment = appointmentDAO.findByAppointmentNumber(appointmentNumber.trim());

        if (appointment == null) {
            request.setAttribute("error", "No appointment found with number: " + appointmentNumber);
            request.getRequestDispatcher("/searchAppointment.jsp").forward(request, response);
            return;
        }

        request.setAttribute("appointment", appointment);
        request.getRequestDispatcher("/appointmentDetails.jsp").forward(request, response);
    }
}
