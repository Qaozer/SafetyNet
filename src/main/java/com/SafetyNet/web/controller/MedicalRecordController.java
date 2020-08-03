package com.SafetyNet.web.controller;

import com.SafetyNet.dao.MedicalRecordDao;
import com.SafetyNet.model.MedicalRecord;
import com.SafetyNet.service.MedicalService;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MedicalRecordController {

    private static final Logger LOGGER = Logger.getLogger(MedicalRecordController.class);

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MedicalService medicalService;

    @PostMapping(value = "medicalRecord")
    public ResponseEntity<MedicalRecord> add (@RequestBody MedicalRecord medicalRecord){
        LOGGER.info("[POST] Request to add medical record to data set.");
        if (medicalService.contains(medicalRecord)){
            LOGGER.info("Medical record already in base.");
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.ok(medicalService.add(medicalRecord));
        }
    }

    @PutMapping(value = "medicalRecord")
    public ResponseEntity<MedicalRecord> update (@RequestBody MedicalRecord medicalRecord){
        LOGGER.info("[PUT] Request to update medical record.");
        return ResponseEntity.ok(medicalService.update(medicalRecord));
    }

    @DeleteMapping(value = "medicalRecord")
    public ResponseEntity delete (@RequestParam String firstName, @RequestParam String lastName){
        LOGGER.info("[DEL] Request to delete medical record.");
        medicalService.delete(firstName, lastName);
        return ResponseEntity.ok().build();
    }
}
