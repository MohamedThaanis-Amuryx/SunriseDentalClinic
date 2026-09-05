package com.app.dao;

import com.app.model.Bill;

public interface BillDAO {

    boolean createBill(Bill bill);

    /**
     * Fetches a bill (joined with appointment/patient/dentist/treatment info)
     * using the appointment_number. Returns null if no bill exists yet
     * for that appointment.
     */
    Bill findByAppointmentNumber(String appointmentNumber);
}
