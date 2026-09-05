package com.app.controller;

import com.app.dao.AppointmentDAO;
import com.app.dao.BillDAO;
import com.app.dao.impl.AppointmentDAOImpl;
import com.app.dao.impl.BillDAOImpl;
import com.app.model.Appointment;
import com.app.model.Bill;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/generateBill")
public class GenerateBillServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final AppointmentDAO appointmentDAO = new AppointmentDAOImpl();
    private final BillDAO billDAO = new BillDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getSession().getAttribute("loggedInUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String appointmentNumber = request.getParameter("appointmentNumber");

        if (appointmentNumber == null || appointmentNumber.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/searchAppointment.jsp");
            return;
        }

        // Look up the appointment (this also gives us the treatment fee via the join)
        Appointment appointment = appointmentDAO.findByAppointmentNumber(appointmentNumber.trim());

        if (appointment == null) {
            request.setAttribute("error", "Appointment not found: " + appointmentNumber);
            request.getRequestDispatcher("/searchAppointment.jsp").forward(request, response);
            return;
        }

        // Check if a bill already exists for this appointment, avoid double billing
        Bill existingBill = billDAO.findByAppointmentNumber(appointmentNumber);

        if (existingBill == null) {
            // No bill yet, calculate and create one
            BigDecimal totalAmount = getTreatmentFee(appointment.getTreatmentId());

            Bill newBill = new Bill();
            newBill.setAppointmentId(appointment.getAppointmentId());
            newBill.setTotalAmount(totalAmount);

            billDAO.createBill(newBill);

            // fetch it back so we have the bill_id and bill_date for display
            existingBill = billDAO.findByAppointmentNumber(appointmentNumber);
        }

        request.setAttribute("bill", existingBill);
        request.getRequestDispatcher("/bill.jsp").forward(request, response);
    }

    // Small helper: get the fee straight from the treatments table via TreatmentDAO
    private BigDecimal getTreatmentFee(int treatmentId) {
        com.app.dao.TreatmentDAO treatmentDAO = new com.app.dao.impl.TreatmentDAOImpl();
        com.app.model.Treatment treatment = treatmentDAO.findById(treatmentId);
        return treatment != null ? treatment.getConsultationFee() : BigDecimal.ZERO;
    }
}
