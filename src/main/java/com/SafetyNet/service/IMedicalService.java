package com.SafetyNet.service;

import com.SafetyNet.model.MedicalRecord;

public interface IMedicalService {
    MedicalRecord add (MedicalRecord medicalRecord);
    MedicalRecord update(MedicalRecord medicalRecord);
    void delete(String firstName, String lastName);
    boolean contains (MedicalRecord medicalRecord);
}
