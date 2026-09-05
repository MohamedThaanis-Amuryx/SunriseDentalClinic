package com.app.dao;

import com.app.model.Treatment;
import java.util.List;

public interface TreatmentDAO {
    List<Treatment> getAllTreatments();
    Treatment findById(int treatmentId);
}