package com.app.dao.impl;

import com.app.dao.DentistDAO;
import com.app.model.Dentist;
import com.app.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DentistDAOImpl implements DentistDAO {

    @Override
    public List<Dentist> getAllDentists() {
        List<Dentist> dentists = new ArrayList<>();
        String sql = "SELECT * FROM dentists";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Dentist d = new Dentist();
                d.setDentistId(rs.getInt("dentist_id"));
                d.setDentistName(rs.getString("dentist_name"));
                d.setSpecialization(rs.getString("specialization"));
                dentists.add(d);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dentists;
    }
}