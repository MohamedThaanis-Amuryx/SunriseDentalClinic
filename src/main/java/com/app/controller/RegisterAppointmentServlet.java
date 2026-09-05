package com.app.controller;

import com.app.dao.AppointmentDAO;
import com.app.dao.DentistDAO;
import com.app.dao.PatientDAO;
import com.app.dao.TreatmentDAO;
import com.app.dao.impl.AppointmentDAOImpl;
import com.app.dao.impl.DentistDAOImpl;
import com.app.dao.impl.PatientDAOImpl;
import com.app.dao.impl.TreatmentDAOImpl;
import com.app.model.Appointment;
import com.app.model.Dentist;
import com.app.model.Patient;
import com.app.model.Treatment;
import com.app.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

@WebServlet("/registerAppointment")
public class RegisterAppointmentServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final PatientDAO patientDAO = new PatientDAOImpl();
    private final AppointmentDAO appointmentDAO = new AppointmentDAOImpl();
    private final DentistDAO dentistDAO = new DentistDAOImpl();
    private final TreatmentDAO treatmentDAO = new TreatmentDAOImpl();

    // Show the form with dentist/treatment dropdowns filled in
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getSession().getAttribute("loggedInUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        loadDropdownsAndForward(request, response);
    }

    // Handle the form submission
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
        if (loggedInUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String patientName = com.app.util.ValidationUtil.sanitize(request.getParameter("patientName"));
        String address = com.app.util.ValidationUtil.sanitize(request.getParameter("address"));
        String contactNumber = com.app.util.ValidationUtil.sanitize(request.getParameter("contactNumber"));
        String dentistIdStr = request.getParameter("dentistId");
        String treatmentIdStr = request.getParameter("treatmentId");
        String appointmentDateStr = request.getParameter("appointmentDate");
        String appointmentTimeStr = request.getParameter("appointmentTime");

        // ----- Server-side validation -----
        String validationError = validate(patientName, address, contactNumber,
                dentistIdStr, treatmentIdStr, appointmentDateStr, appointmentTimeStr);

        if (validationError != null) {
            request.setAttribute("error", validationError);
            request.setAttribute("patientName", patientName);
            request.setAttribute("address", address);
            request.setAttribute("contactNumber", contactNumber);
            request.setAttribute("selectedDentistId", dentistIdStr);
            request.setAttribute("selectedTreatmentId", treatmentIdStr);
            request.setAttribute("appointmentDate", appointmentDateStr);
            request.setAttribute("appointmentTime", appointmentTimeStr);
            loadDropdownsAndForward(request, response);
            return;
        }

        // ----- Business rule: no double-booking the same dentist at the same slot -----
        int dentistIdCheck = Integer.parseInt(dentistIdStr);
        if (appointmentDAO.isSlotTaken(dentistIdCheck, appointmentDateStr, appointmentTimeStr)) {
            request.setAttribute("error", "This dentist already has an appointment at that date and time. Please choose another slot.");
            request.setAttribute("patientName", patientName);
            request.setAttribute("address", address);
            request.setAttribute("contactNumber", contactNumber);
            request.setAttribute("selectedDentistId", dentistIdStr);
            request.setAttribute("selectedTreatmentId", treatmentIdStr);
            request.setAttribute("appointmentDate", appointmentDateStr);
            request.setAttribute("appointmentTime", appointmentTimeStr);
            loadDropdownsAndForward(request, response);
            return;
        }

        try {
            // 1. Save the patient first
            Patient patient = new Patient();
            patient.setPatientName(patientName.trim());
            patient.setAddress(address.trim());
            patient.setContactNumber(contactNumber.trim());

            int newPatientId = patientDAO.createPatient(patient);
            if (newPatientId == -1) {
                request.setAttribute("error", "Could not save patient details. Please try again.");
                request.setAttribute("patientName", patientName);
                request.setAttribute("address", address);
                request.setAttribute("contactNumber", contactNumber);
                request.setAttribute("selectedDentistId", dentistIdStr);
                request.setAttribute("selectedTreatmentId", treatmentIdStr);
                request.setAttribute("appointmentDate", appointmentDateStr);
                request.setAttribute("appointmentTime", appointmentTimeStr);
                loadDropdownsAndForward(request, response);
                return;
            }
            // 2. Build the appointment
            Appointment appointment = new Appointment();
            appointment.setAppointmentNumber(appointmentDAO.generateNextAppointmentNumber());
            appointment.setPatientId(newPatientId);
            appointment.setDentistId(Integer.parseInt(dentistIdStr));
            appointment.setTreatmentId(Integer.parseInt(treatmentIdStr));
            appointment.setAppointmentDate(Date.valueOf(appointmentDateStr));
            appointment.setAppointmentTime(Time.valueOf(appointmentTimeStr + ":00"));

            boolean created = appointmentDAO.createAppointment(appointment);

            if (created) {
                request.setAttribute("success",
                        "Appointment saved successfully. Appointment number: " + appointment.getAppointmentNumber());
            } else {
                request.setAttribute("error", "Could not save the appointment. Please try again.");
            }

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid dentist or treatment selection.");
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", "Invalid date or time format.");
        }

        loadDropdownsAndForward(request, response);
    }

    // Basic validation rules, returns an error message or null if everything is fine
    private String validate(String patientName, String address, String contactNumber,
                             String dentistIdStr, String treatmentIdStr,
                             String appointmentDateStr, String appointmentTimeStr) {

        com.app.util.ValidationUtil v = null; // just for readability, static methods used below

        if (com.app.util.ValidationUtil.isBlank(patientName) || com.app.util.ValidationUtil.isBlank(address)
                || com.app.util.ValidationUtil.isBlank(contactNumber) || com.app.util.ValidationUtil.isBlank(dentistIdStr)
                || com.app.util.ValidationUtil.isBlank(treatmentIdStr) || com.app.util.ValidationUtil.isBlank(appointmentDateStr)
                || com.app.util.ValidationUtil.isBlank(appointmentTimeStr)) {
            return "All fields are required.";
        }

        if (!com.app.util.ValidationUtil.isValidName(patientName)) {
            return "Patient name must be 2-100 letters (spaces, dots, hyphens allowed).";
        }

        if (!com.app.util.ValidationUtil.isValidAddress(address)) {
            return "Address must be between 5 and 255 characters.";
        }

        if (!com.app.util.ValidationUtil.isValidPhoneNumber(contactNumber)) {
            return "Contact number must be exactly 10 digits.";
        }

        if (!com.app.util.ValidationUtil.isValidFutureDate(appointmentDateStr)) {
            return "Appointment date must be a valid date and cannot be in the past.";
        }

        if (!com.app.util.ValidationUtil.isWithinClinicHours(appointmentTimeStr)) {
            return "Appointment time must be between "
                    + com.app.util.ValidationUtil.getClinicOpenTime() + " and "
                    + com.app.util.ValidationUtil.getClinicCloseTime() + " (clinic hours).";
        }
        if (!com.app.util.ValidationUtil.isNotPastDateTime(appointmentDateStr, appointmentTimeStr)) {
            return "Appointment time cannot be in the past for today's date.";
        }

        try {
            Integer.parseInt(dentistIdStr);
            Integer.parseInt(treatmentIdStr);
        } catch (NumberFormatException e) {
            return "Invalid dentist or treatment selection.";
        }

        return null;
    }

    private void loadDropdownsAndForward(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Dentist> dentists = dentistDAO.getAllDentists();
        List<Treatment> treatments = treatmentDAO.getAllTreatments();

        request.setAttribute("dentists", dentists);
        request.setAttribute("treatments", treatments);
        

        request.getRequestDispatcher("/registerAppointment.jsp").forward(request, response);
    }
}
