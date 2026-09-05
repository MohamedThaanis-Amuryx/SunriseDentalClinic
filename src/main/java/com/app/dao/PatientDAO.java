package com.app.dao;

import com.app.model.Patient;

public interface PatientDAO {

    /**
     * Saves a new patient and returns the generated patient_id.
     * Returns -1 if the save failed.
     */
    int createPatient(Patient patient);
}