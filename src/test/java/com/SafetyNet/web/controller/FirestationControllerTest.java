package com.SafetyNet.web.controller;

import com.SafetyNet.dao.FirestationDao;
import com.SafetyNet.dto.FirestationDto;
import com.SafetyNet.dto.PersonDto;
import com.SafetyNet.model.FireStation;
import com.SafetyNet.model.Person;
import com.SafetyNet.service.FirestationService;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class FirestationControllerTest {
    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders httpHeaders = new HttpHeaders();

    @Autowired
    private FirestationService firestationService;

    @Autowired
    private FirestationDao firestationDao;

    @Autowired
    private ModelMapper modelMapper;

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    @Test
    public void getCoverageTest() throws JSONException {
        HttpEntity<String> entity = new HttpEntity<String>(null, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/firestation?stationNumber=1"), HttpMethod.GET, entity, String.class);
        String expected = "{\"personList\":[{\"firstName\":\"Peter\",\"lastName\":\"Duncan\",\"address\":\"644 Gershwin Cir\",\"city\":\"Culver\",\"zip\":97451,\"phone\":\"841-874-6512\"},{\"firstName\":\"Reginold\",\"lastName\":\"Walker\",\"address\":\"908 73rd St\",\"city\":\"Culver\",\"zip\":97451,\"phone\":\"841-874-8547\"},{\"firstName\":\"Jamie\",\"lastName\":\"Peters\",\"address\":\"908 73rd St\",\"city\":\"Culver\",\"zip\":97451,\"phone\":\"841-874-7462\"},{\"firstName\":\"Brian\",\"lastName\":\"Stelzer\",\"address\":\"947 E. Rose Dr\",\"city\":\"Culver\",\"zip\":97451,\"phone\":\"841-874-7784\"},{\"firstName\":\"Shawna\",\"lastName\":\"Stelzer\",\"address\":\"947 E. Rose Dr\",\"city\":\"Culver\",\"zip\":97451,\"phone\":\"841-874-7784\"},{\"firstName\":\"Kendrik\",\"lastName\":\"Stelzer\",\"address\":\"947 E. Rose Dr\",\"city\":\"Culver\",\"zip\":97451,\"phone\":\"841-874-7784\"}],\"children\":1,\"adults\":5}";
        assertEquals(expected, response.getBody(), true);
    }

    @Test
    public void getPhonesTest() throws JSONException {
        HttpEntity<String> entity = new HttpEntity<String>(null, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/phoneAlert?firestation=1"), HttpMethod.GET, entity, String.class);
        String expected = "[\"841-874-6512\",\"841-874-8547\",\"841-874-7462\",\"841-874-7784\",\"841-874-7784\",\"841-874-7784\"]";
        assertEquals(expected, response.getBody(), true);
    }

    @Test
    public void getFireAlertTest() throws JSONException {
        HttpEntity<String> entity = new HttpEntity<String>(null, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/fire?address=1509 Culver St"), HttpMethod.GET, entity, String.class);
        String expected = "{\"personsList\":[{\"firstName\":\"John\",\"lastName\":\"Boyd\",\"phone\":\"841-874-6512\",\"age\":36,\"medicalRecord\":{\"medications\":[\"aznol:350mg\",\"hydrapermazol:100mg\"],\"allergies\":[\"nillacilan\"]}},{\"firstName\":\"Jacob\",\"lastName\":\"Boyd\",\"phone\":\"841-874-6513\",\"age\":31,\"medicalRecord\":{\"medications\":[\"pharmacol:5000mg\",\"terazine:10mg\",\"noznazol:250mg\"],\"allergies\":[]}},{\"firstName\":\"Tenley\",\"lastName\":\"Boyd\",\"phone\":\"841-874-6512\",\"age\":8,\"medicalRecord\":{\"medications\":[],\"allergies\":[\"peanut\"]}},{\"firstName\":\"Roger\",\"lastName\":\"Boyd\",\"phone\":\"841-874-6512\",\"age\":3,\"medicalRecord\":{\"medications\":[],\"allergies\":[]}},{\"firstName\":\"Felicia\",\"lastName\":\"Boyd\",\"phone\":\"841-874-6544\",\"age\":34,\"medicalRecord\":{\"medications\":[\"tetracyclaz:650mg\"],\"allergies\":[\"xilliathal\"]}}],\"stationNumberList\":[3]}";
        assertEquals(expected, response.getBody(), true);
    }

    @Test
    public void getFloodAlertTest() throws JSONException {
        HttpEntity<String> entity = new HttpEntity<String>(null, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("flood/stations?stations=1"), HttpMethod.GET, entity, String.class);
        String expected = "[{\"address\":\"644 Gershwin Cir\",\"personList\":[{\"firstName\":\"Peter\",\"lastName\":\"Duncan\",\"phone\":\"841-874-6512\",\"age\":20,\"medicalRecord\":{\"medications\":[],\"allergies\":[\"shellfish\"]}}]},{\"address\":\"908 73rd St\",\"personList\":[{\"firstName\":\"Reginold\",\"lastName\":\"Walker\",\"phone\":\"841-874-8547\",\"age\":41,\"medicalRecord\":{\"medications\":[\"thradox:700mg\"],\"allergies\":[\"illisoxian\"]}},{\"firstName\":\"Jamie\",\"lastName\":\"Peters\",\"phone\":\"841-874-7462\",\"age\":38,\"medicalRecord\":{\"medications\":[],\"allergies\":[]}}]},{\"address\":\"947 E. Rose Dr\",\"personList\":[{\"firstName\":\"Brian\",\"lastName\":\"Stelzer\",\"phone\":\"841-874-7784\",\"age\":45,\"medicalRecord\":{\"medications\":[\"ibupurin:200mg\",\"hydrapermazol:400mg\"],\"allergies\":[\"nillacilan\"]}},{\"firstName\":\"Shawna\",\"lastName\":\"Stelzer\",\"phone\":\"841-874-7784\",\"age\":40,\"medicalRecord\":{\"medications\":[],\"allergies\":[]}},{\"firstName\":\"Kendrik\",\"lastName\":\"Stelzer\",\"phone\":\"841-874-7784\",\"age\":6,\"medicalRecord\":{\"medications\":[\"noxidian:100mg\",\"pharmacol:2500mg\"],\"allergies\":[]}}]}]";
        assertEquals(expected, response.getBody(), true);
    }

    @Test
    public void addStationTest(){
        FirestationDto firestationDto = createFirestationDto();
        assertFalse(firestationService.contains(modelMapper.map(firestationDto, FireStation.class)));
        HttpEntity<FirestationDto> entity = new HttpEntity<>(firestationDto, httpHeaders);
        ResponseEntity response = restTemplate.exchange(
                createURLWithPort("firestation"),HttpMethod.POST, entity, String.class
        );
        assertTrue(firestationService.contains(modelMapper.map(firestationDto, FireStation.class)));
    }

    @Test
    public void updateStationTest(){
        addStationTest();
        assertTrue(firestationDao.getFirestationsList().stream().anyMatch(s-> s.getStation() == 42));
        FirestationDto firestationDto = createFirestationDto();
        firestationDto.setStation(43);
        HttpEntity<FirestationDto> entity = new HttpEntity<>(firestationDto, httpHeaders);
        ResponseEntity response = restTemplate.exchange(
                createURLWithPort("firestation"),HttpMethod.PUT, entity, String.class
        );
        assertFalse(firestationDao.getFirestationsList().stream().anyMatch(s-> s.getStation() == 42));
        assertTrue(firestationDao.getFirestationsList().stream().anyMatch(s-> s.getStation() == 43));
    }

    @Test
    public void deleteStationTest(){
        addStationTest();
        assertTrue(firestationDao.getFirestationsList().stream().anyMatch(s-> s.getStation() == 42));
        HttpEntity<String> entity = new HttpEntity<String>(null, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("firestation?address=1 rue du Trou"), HttpMethod.DELETE, entity, String.class);
        assertFalse(firestationDao.getFirestationsList().stream().anyMatch(s-> s.getStation() == 42));
        addStationTest();
        assertTrue(firestationDao.getFirestationsList().stream().anyMatch(s-> s.getStation() == 42));
        response = restTemplate.exchange(
                createURLWithPort("firestation?station=42"), HttpMethod.DELETE, entity, String.class);
        assertFalse(firestationDao.getFirestationsList().stream().anyMatch(s-> s.getStation() == 42));

    }

    private FirestationDto createFirestationDto(){
        FirestationDto station = new FirestationDto();
        station.setAddress("1 rue du Trou");
        station.setStation(42);
        return station;
    }
}
