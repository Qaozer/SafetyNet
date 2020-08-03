package com.SafetyNet.dao;

import com.SafetyNet.Data;
import com.SafetyNet.model.MedicalRecord;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Repository
public class MedicalRecordDaoImpl implements MedicalRecordDao{

    private static final Logger LOGGER = Logger.getLogger(MedicalRecordDaoImpl.class);

    @Autowired
    private Data data;

    @Override
    public List<MedicalRecord> getMedicalRecords() {
        LOGGER.info("Accessed Medical Record List.");
        return data.getMedicalrecords();
    }

    @Override
    public void add(MedicalRecord medicalRecord) {
        LOGGER.info("Added Medical Record for "+medicalRecord.getFirstName()+ " "+medicalRecord.getLastName()+".");
        data.getMedicalrecords().add(medicalRecord);
    }

    @Override
    public void update(MedicalRecord medicalRecord) {
        Optional<MedicalRecord> optional = data.getMedicalrecords().stream().filter(
                m-> m.getFirstName().equals(medicalRecord.getFirstName()) && m.getLastName().equals(medicalRecord.getLastName())
        ).findFirst();
        if (optional.isPresent()){
            MedicalRecord stored = optional.get();
            if (stored != medicalRecord){
                LOGGER.info("Updating medical record for "+medicalRecord.getFirstName()+ " "+medicalRecord.getLastName()+".");
                if (!stored.getBirthdate().equals(medicalRecord.getBirthdate())){
                    stored.setBirthdate(medicalRecord.getBirthdate());
                    LOGGER.info("Updated birthdate.");
                }
                if (stored.getAllergies() != medicalRecord.getAllergies()){
                    stored.setAllergies(medicalRecord.getAllergies());
                    LOGGER.info("Updated allergies.");
                }
                if (stored.getMedications() != medicalRecord.getMedications()){
                    stored.setMedications(medicalRecord.getMedications());
                    LOGGER.info("Updated medications.");
                }
            }
        } else {
            LOGGER.info("Medical record not found.");
        }
    }

    @Override
    public void delete(String firstName, String lastName) {
        Iterator itr = data.getMedicalrecords().iterator();
        while (itr.hasNext()){
            MedicalRecord m = (MedicalRecord)itr.next();
            if (m.getFirstName().equals(firstName) && m.getLastName().equals(lastName)){
                itr.remove();
                LOGGER.info("Medical records for "+ firstName +" "+lastName+" successfully removed from medical records list.");
            }
        }
    }
}
