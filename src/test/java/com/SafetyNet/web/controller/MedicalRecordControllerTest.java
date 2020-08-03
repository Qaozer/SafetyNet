package com.SafetyNet.web.controller;

import com.SafetyNet.model.MedicalRecord;
import com.SafetyNet.service.MedicalService;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MedicalRecordControllerTest {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders httpHeaders = new HttpHeaders();


    @Autowired
    private MedicalService medicalService;

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    private MedicalRecord createMedicalRecord(String medication, String allergie){
        MedicalRecord medicalRecord = new MedicalRecord();
        List<String> medications = new ArrayList<>();
        medications.add(medication);
        List<String> allergies = new ArrayList<>();
        allergies.add(allergie);
        medicalRecord.setFirstName("Bob");
        medicalRecord.setLastName("Morane");
        medicalRecord.setBirthdate("01/01/1982");
        medicalRecord.setMedications(medications);
        medicalRecord.setAllergies(allergies);
        return medicalRecord;
    }

    @Test
    public void addMedicalRecordTest() throws JSONException {
        MedicalRecord medicalRecord = createMedicalRecord("aznol:340mg", "peanuts");
        HttpEntity<MedicalRecord> entity = new HttpEntity<MedicalRecord>(medicalRecord, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("medicalRecord"), HttpMethod.POST,entity,String.class);
        assertTrue(medicalService.contains(medicalRecord));
    }

    @Test
    public void updateMedicalRecordTest() throws JSONException {
        addMedicalRecordTest();
        MedicalRecord medicalRecord = createMedicalRecord("aznol:350mg", "peanuts");
        HttpEntity<MedicalRecord> entity = new HttpEntity<MedicalRecord>(medicalRecord, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("medicalRecord"), HttpMethod.PUT,entity,String.class);
        assertTrue(medicalService.contains(medicalRecord));
    }

    @Test
    public void deleteMedicalRecordTest() throws JSONException {
        MedicalRecord medicalRecord = createMedicalRecord("aznol:340mg", "peanuts");
        addMedicalRecordTest();
        assertTrue(medicalService.contains(medicalRecord));
        HttpEntity<String> entity = new HttpEntity<String>(null, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("medicalRecord?firstName=Bob&lastName=Morane"), HttpMethod.DELETE,entity,String.class);
        assertFalse(medicalService.contains(medicalRecord));
    }
}
