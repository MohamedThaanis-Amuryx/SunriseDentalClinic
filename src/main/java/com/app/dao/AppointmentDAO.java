package com.app.dao;

import com.app.model.Appointment;

public interface AppointmentDAO {

    boolean createAppointment(Appointment appointment);

    /**
     * Fetches an appointment by its appointment_number, joined with
     * patient, dentist, and treatment details for display.
     */
    Appointment findByAppointmentNumber(String appointmentNumber);

    /**
     * Generates the next appointment number, e.g. APT-0001, APT-0002 ...
     */
    String generateNextAppointmentNumber();

    /**
     * Checks if the given dentist already has an appointment at that
     * exact date and time, so we can block double-booking.
     */
    boolean isSlotTaken(int dentistId, String appointmentDate, String appointmentTime);
}
