package com.SafetyNet.web.controller;

import com.SafetyNet.dto.ChildAlertDto;
import com.SafetyNet.dto.NameAgeDto;
import com.SafetyNet.dto.PersonDto;
import com.SafetyNet.dto.PersonMedDto;
import com.SafetyNet.model.ChildAlert;
import com.SafetyNet.model.Person;
import com.SafetyNet.service.PersonService;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PersonController {

    static final Logger logger = Logger.getLogger(PersonController.class);

    @Autowired
    private PersonService personService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "/persons")
    public List<PersonMedDto> personsList(){
        logger.info("[GET] Request to get all persons infos from data.");
        List<Person> personsList = personService.getPersonsList();
        return personsList.stream().map(this::personMedToDto).collect(Collectors.toList());
    }

    @GetMapping(value = "/personInfo")
    public List<PersonMedDto> personsListByFirstAndLastName (@RequestParam ("firstName") String firstName,
                                                          @RequestParam ("lastName") String lastName){
        logger.info("[GET] Request to get infos on " + firstName+ " " + lastName +".");
        List<Person> personsList = personService.getPersonsListFilteredByFirstAndLastName(firstName, lastName);
        return personsList.stream().map(this::personMedToDto).collect(Collectors.toList());
    }

    @GetMapping(value = "childAlert")
    public ChildAlertDto childListByAddress (@RequestParam ("address") String address) throws ParseException, UnsupportedEncodingException {
        String decodedAddress = URLDecoder.decode(address, StandardCharsets.UTF_8.toString());
        logger.info("[GET] Request to get infos for childAlert at address: " + decodedAddress+".");
        ChildAlert childAlert = personService.getChildAlert(decodedAddress);
        return childAlertToDto(childAlert);
    }

    @GetMapping(value="communityEmail")
    public List<String> communityEmail (@RequestParam("city") String city) throws UnsupportedEncodingException {
        String decoded = URLDecoder.decode(city, StandardCharsets.UTF_8.toString());
        logger.info("[GET] Request to get emails from people living in: " + decoded +".");
        List<String> emails = personService.getEmailsByCity(decoded);
        return emails;
    }

    @PostMapping(value = "/person")
    public ResponseEntity addPerson (@RequestBody PersonDto person){
        logger.info("[POST] Request to add a person to dataset.");
        Person added = modelMapper.map(person, Person.class);
        if(personService.contains(added)){
            logger.info("Person already in dataset.");
        } else {
            personService.add(added);
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/person")
    public ResponseEntity updatePerson (@RequestBody PersonDto person){
        logger.info("[PUT] Request to update details on: "+person.getFirstName()+ " "+person.getLastName()+".");
        Person updated = modelMapper.map(person, Person.class);
        personService.update(updated);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/person")
    public ResponseEntity deletePerson (@RequestParam("firstName") String firstName,
                                       @RequestParam("lastName") String lastName){
        logger.info("[DELETE] Request to delete "+firstName+ " " +lastName+" from dataset.");
        personService.delete(firstName, lastName);
        return ResponseEntity.ok().build();
    }
    private PersonDto personToDto (Person person){
        PersonDto personDto = modelMapper.map(person, PersonDto.class);
        return personDto;
    }

    private PersonMedDto personMedToDto (Person person){
        PersonMedDto personMedDto = modelMapper.map(person, PersonMedDto.class);
        return personMedDto;
    }

    private NameAgeDto nameAgeToDto(Person person){
        NameAgeDto nameAgeDto = modelMapper.map(person, NameAgeDto.class);
        return nameAgeDto;
    }

    private ChildAlertDto childAlertToDto (ChildAlert childAlert){
        ChildAlertDto childAlertDto = modelMapper.map(childAlert, ChildAlertDto.class);
        return childAlertDto;
    }
}
