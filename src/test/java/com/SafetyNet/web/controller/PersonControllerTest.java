package com.SafetyNet.web.controller;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PersonControllerTest {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders httpHeaders = new HttpHeaders();

    @Test
    public void PersonInfoTest() throws JSONException {
        HttpEntity<String> entity = new HttpEntity<String>(null, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/personInfo?firstName=John&lastName=Boyd"), HttpMethod.GET, entity, String.class);
        String expected = "[{firstName: \"John\",lastName: \"Boyd\",address: \"1509 Culver St\",city: \"Culver\",zip: 97451,age: 36,email: \"jaboyd@email.com\",medicalRecord: {medications: [\"aznol:350mg\",\"hydrapermazol:100mg\"],allergies: [\"nillacilan\"]}}]";
        JSONAssert.assertEquals(expected, response.getBody(), true);
    }

    @Test
    public void ChildAlertTest() throws JSONException {
        HttpEntity<String> entity = new HttpEntity<String>(null, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("childAlert?address=1509%20Culver%20St"), HttpMethod.GET, entity, String.class);
        String expected = "{\"childrenList\":[{\"firstName\":\"Tenley\",\"lastName\":\"Boyd\",\"age\":8},{\"firstName\":\"Roger\",\"lastName\":\"Boyd\",\"age\":3}],\"adultsList\":[{\"firstName\":\"John\",\"lastName\":\"Boyd\"},{\"firstName\":\"Jacob\",\"lastName\":\"Boyd\"},{\"firstName\":\"Felicia\",\"lastName\":\"Boyd\"}]}";
        JSONAssert.assertEquals(expected, response.getBody(), true);
    }

    @Test
    public void CommunityEmailTest() throws JSONException {
        HttpEntity<String> entity = new HttpEntity<String>(null, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("communityEmail?city=Culver"), HttpMethod.GET, entity, String.class);
        String expected = "[\"jaboyd@email.com\",\"drk@email.com\",\"tenz@email.com\",\"jaboyd@email.com\",\"jaboyd@email.com\",\"drk@email.com\",\"tenz@email.com\",\"jaboyd@email.com\",\"jaboyd@email.com\",\"tcoop@ymail.com\",\"lily@email.com\",\"soph@email.com\",\"ward@email.com\",\"zarc@email.com\",\"reg@email.com\",\"jpeter@email.com\",\"jpeter@email.com\",\"aly@imail.com\",\"bstel@email.com\",\"ssanw@email.com\",\"bstel@email.com\",\"clivfd@ymail.com\",\"gramps@email.com\"]";
        JSONAssert.assertEquals(expected, response.getBody(), true);
    }


    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }


}
