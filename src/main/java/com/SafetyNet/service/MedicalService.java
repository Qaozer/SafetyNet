package com.SafetyNet.service;

import com.SafetyNet.dao.MedicalRecordDao;
import com.SafetyNet.dao.PersonDao;
import com.SafetyNet.model.MedicalRecord;
import com.SafetyNet.model.Person;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class MedicalService implements IMedicalService {

    private static final Logger LOGGER = Logger.getLogger(MedicalService.class);
    @Autowired
    private MedicalRecordDao medicalRecordDao;

    @Autowired
    private PersonDao personDao;

    @Override
    public MedicalRecord add(MedicalRecord medicalRecord) {
        medicalRecordDao.add(medicalRecord);
        Optional<Person> person = personDao.getPersonsList().stream().filter(p-> p.getFirstName().equals(medicalRecord.getFirstName()) &&
                p.getLastName().equals(medicalRecord.getLastName())).findFirst();
        if (person.isPresent()){
            person.get().setMedicalRecord(medicalRecord);
        }
        LOGGER.info("Medical record added.");
        return medicalRecord;
    }

    @Override
    public MedicalRecord update(MedicalRecord medicalRecord) {
        medicalRecordDao.update(medicalRecord);
        return medicalRecord;
    }

    @Override
    public void delete(String firstName, String lastName) {
        medicalRecordDao.delete(firstName, lastName);
        Optional<Person> optionalP = personDao.getPersonsList().stream().filter(
                p-> p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)
        ).findFirst();
        if (optionalP.isPresent()){
            optionalP.get().setMedicalRecord(null);
        }
    }

    @Override
    public boolean contains (MedicalRecord medicalRecord){
        List<MedicalRecord> medicalRecords = medicalRecordDao.getMedicalRecords();
        return medicalRecords.contains(medicalRecord);
    }
}
