package com.SafetyNet.dao;

import com.SafetyNet.model.MedicalRecord;

import java.util.List;

public interface MedicalRecordDao {
    public void add (MedicalRecord medicalRecord);
    public void update (MedicalRecord medicalRecord);
    public void delete (String firstName, String lastName);
    public List<MedicalRecord> getMedicalRecords();
}
