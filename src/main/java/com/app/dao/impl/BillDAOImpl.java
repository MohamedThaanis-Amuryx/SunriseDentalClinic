package com.app.dao.impl;

import com.app.dao.BillDAO;
import com.app.model.Bill;
import com.app.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BillDAOImpl implements BillDAO {

    @Override
    public boolean createBill(Bill bill) {
        String sql = "INSERT INTO bills (appointment_id, total_amount) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, bill.getAppointmentId());
            ps.setBigDecimal(2, bill.getTotalAmount());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Bill findByAppointmentNumber(String appointmentNumber) {
        String sql = "SELECT b.*, a.appointment_number, p.patient_name, d.dentist_name, t.treatment_name " +
                "FROM bills b " +
                "JOIN appointments a ON b.appointment_id = a.appointment_id " +
                "JOIN patients p ON a.patient_id = p.patient_id " +
                "JOIN dentists d ON a.dentist_id = d.dentist_id " +
                "JOIN treatments t ON a.treatment_id = t.treatment_id " +
                "WHERE a.appointment_number = ? " +
                "ORDER BY b.bill_id DESC LIMIT 1";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, appointmentNumber);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Bill bill = new Bill();
                    bill.setBillId(rs.getInt("bill_id"));
                    bill.setAppointmentId(rs.getInt("appointment_id"));
                    bill.setTotalAmount(rs.getBigDecimal("total_amount"));
                    bill.setBillDate(rs.getTimestamp("bill_date"));
                    bill.setAppointmentNumber(rs.getString("appointment_number"));
                    bill.setPatientName(rs.getString("patient_name"));
                    bill.setDentistName(rs.getString("dentist_name"));
                    bill.setTreatmentName(rs.getString("treatment_name"));
                    return bill;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
