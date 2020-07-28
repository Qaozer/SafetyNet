package com.SafetyNet.dao;

import com.SafetyNet.Data;
import com.SafetyNet.model.MedicalRecord;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Iterator;

@Repository
public class MedicalRecordDaoImpl implements MedicalRecordDao{

    private static final Logger logger = Logger.getLogger(MedicalRecordDaoImpl.class);

    @Autowired
    private Data data;

    @Override
    public void delete(String firstName, String lastName) {
        Iterator itr = data.getMedicalrecords().iterator();
        while (itr.hasNext()){
            MedicalRecord p = (MedicalRecord)itr.next();
            if (p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)){
                itr.remove();
                logger.info("Medical records for "+ firstName +" "+lastName+" successfully removed from medical records list.");
            }
        }
    }
}
