package com.app.dao.impl;

import com.app.dao.AppointmentDAO;
import com.app.model.Appointment;
import com.app.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppointmentDAOImpl implements AppointmentDAO {

    @Override
    public boolean createAppointment(Appointment appointment) {
        String sql = "INSERT INTO appointments " +
                "(appointment_number, patient_id, dentist_id, treatment_id, appointment_date, appointment_time, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, appointment.getAppointmentNumber());
            ps.setInt(2, appointment.getPatientId());
            ps.setInt(3, appointment.getDentistId());
            ps.setInt(4, appointment.getTreatmentId());
            ps.setDate(5, appointment.getAppointmentDate());
            ps.setTime(6, appointment.getAppointmentTime());
            ps.setString(7, "SCHEDULED");

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Appointment findByAppointmentNumber(String appointmentNumber) {
        String sql = "SELECT a.*, p.patient_name, p.address, p.contact_number, " +
                "d.dentist_name, t.treatment_name, t.consultation_fee " +
                "FROM appointments a " +
                "JOIN patients p ON a.patient_id = p.patient_id " +
                "JOIN dentists d ON a.dentist_id = d.dentist_id " +
                "JOIN treatments t ON a.treatment_id = t.treatment_id " +
                "WHERE a.appointment_number = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, appointmentNumber);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Appointment a = new Appointment();
                    a.setAppointmentId(rs.getInt("appointment_id"));
                    a.setAppointmentNumber(rs.getString("appointment_number"));
                    a.setPatientId(rs.getInt("patient_id"));
                    a.setDentistId(rs.getInt("dentist_id"));
                    a.setTreatmentId(rs.getInt("treatment_id"));
                    a.setAppointmentDate(rs.getDate("appointment_date"));
                    a.setAppointmentTime(rs.getTime("appointment_time"));
                    a.setStatus(rs.getString("status"));

                    // joined display fields
                    a.setPatientName(rs.getString("patient_name"));
                    a.setDentistName(rs.getString("dentist_name"));
                    a.setTreatmentName(rs.getString("treatment_name"));

                    return a;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean isSlotTaken(int dentistId, String appointmentDate, String appointmentTime) {
        String sql = "SELECT COUNT(*) FROM appointments " +
                "WHERE dentist_id = ? AND appointment_date = ? AND appointment_time = ? " +
                "AND status != 'CANCELLED'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, dentistId);
            ps.setDate(2, java.sql.Date.valueOf(appointmentDate));
            ps.setTime(3, java.sql.Time.valueOf(appointmentTime + ":00"));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public String generateNextAppointmentNumber() {
        String sql = "SELECT appointment_id FROM appointments ORDER BY appointment_id DESC LIMIT 1";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            int nextId = 1;
            if (rs.next()) {
                nextId = rs.getInt("appointment_id") + 1;
            }
            // Formats like APT-0001, APT-0002, ... APT-0123
            return String.format("APT-%04d", nextId);

        } catch (SQLException e) {
            e.printStackTrace();
            return "APT-0001";
        }
    }
}
