package com.app.dao.impl;

import com.app.dao.TreatmentDAO;
import com.app.model.Treatment;
import com.app.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TreatmentDAOImpl implements TreatmentDAO {

    @Override
    public List<Treatment> getAllTreatments() {
        List<Treatment> treatments = new ArrayList<>();
        String sql = "SELECT * FROM treatments";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Treatment t = new Treatment();
                t.setTreatmentId(rs.getInt("treatment_id"));
                t.setTreatmentName(rs.getString("treatment_name"));
                t.setConsultationFee(rs.getBigDecimal("consultation_fee"));
                treatments.add(t);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return treatments;
    }

    @Override
    public Treatment findById(int treatmentId) {
        String sql = "SELECT * FROM treatments WHERE treatment_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, treatmentId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Treatment t = new Treatment();
                    t.setTreatmentId(rs.getInt("treatment_id"));
                    t.setTreatmentName(rs.getString("treatment_name"));
                    t.setConsultationFee(rs.getBigDecimal("consultation_fee"));
                    return t;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}