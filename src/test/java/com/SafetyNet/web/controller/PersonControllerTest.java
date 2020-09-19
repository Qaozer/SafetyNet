package com.SafetyNet.web.controller;

import com.SafetyNet.dto.PersonDto;
import com.SafetyNet.model.Person;
import com.SafetyNet.service.PersonService;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.skyscreamer.jsonassert.JSONAssert;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PersonControllerTest {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders httpHeaders = new HttpHeaders();

    @Autowired
    private PersonService personService;

    @Autowired
    private ModelMapper modelMapper;

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

    @Test
    public void addPersonTest(){
        PersonDto person = createPersonDto();
        assertFalse(personService.contains(modelMapper.map(person, Person.class)));
        HttpEntity<PersonDto> entity = new HttpEntity<PersonDto>(person, httpHeaders);
        ResponseEntity response = restTemplate.exchange(
                createURLWithPort("person"),HttpMethod.POST, entity, String.class
        );
        assertTrue(personService.contains(modelMapper.map(person, Person.class)));
    }

    @Test
    public void updatePersonTest(){
        addPersonTest();
        String email = personService.getPersonsList().stream().filter(
                p-> p.getFirstName().equals("Bob") && p.getLastName().equals("Morane")
        ).findFirst().get().getEmail();
        String address = personService.getPersonsList().stream().filter(
                p-> p.getFirstName().equals("Bob") && p.getLastName().equals("Morane")
        ).findFirst().get().getAddress();
        List<Integer> stations = personService.getPersonsList().stream().filter(
                p-> p.getFirstName().equals("Bob") && p.getLastName().equals("Morane"))
                .findFirst().get().getStations();
        assertEquals("BobM@Aventure.fr", email);
        assertEquals("1 rue du trou", address);
        assertEquals(Collections.emptyList(), stations);
        String nMail = "MoraneM@Aventure.fr";
        String nAddress = "1509 Culver St";
        PersonDto person = createPersonDto();
        person.setEmail(nMail);
        person.setAddress(nAddress);
        HttpEntity<PersonDto> entity = new HttpEntity<PersonDto>(person, httpHeaders);
        ResponseEntity response = restTemplate.exchange(
                createURLWithPort("person"),HttpMethod.PUT, entity, String.class
        );
        email = personService.getPersonsList().stream().filter(
                p-> p.getFirstName().equals("Bob") && p.getLastName().equals("Morane")
        ).findFirst().get().getEmail();
        stations = personService.getPersonsList().stream().filter(
                p-> p.getFirstName().equals("Bob") && p.getLastName().equals("Morane"))
                .findFirst().get().getStations();
        address = personService.getPersonsList().stream().filter(
                p-> p.getFirstName().equals("Bob") && p.getLastName().equals("Morane")
        ).findFirst().get().getAddress();
        assertEquals(nMail, email);
        assertEquals(nAddress, address);
        assertEquals(Arrays.asList(3),stations);
    }

    @Test
    public void deletePersonTest(){
        addPersonTest();
        PersonDto person = createPersonDto();
        Person actualP = modelMapper.map(person, Person.class);
        assertTrue(personService.contains(actualP));
        HttpEntity<String> entity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity response = restTemplate.exchange(
                createURLWithPort("person?firstName=Bob&lastName=Morane"), HttpMethod.DELETE, entity, String.class
        );
        assertFalse(personService.contains(actualP));
    }


    private PersonDto createPersonDto(){
        PersonDto person = new PersonDto();
        person.setFirstName("Bob");
        person.setLastName("Morane");
        person.setEmail("BobM@Aventure.fr");
        person.setPhone("0000000000");
        person.setZip(12345);
        person.setCity("Minas Tirith");
        person.setAddress("1 rue du trou");
        return person;
    }
    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }


}
