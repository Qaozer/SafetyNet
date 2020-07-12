package com.SafetyNet.web.controller;

import com.SafetyNet.dto.ChildAlertDto;
import com.SafetyNet.dto.NameAgeDto;
import com.SafetyNet.dto.PersonDto;
import com.SafetyNet.dto.PersonMedDto;
import com.SafetyNet.model.ChildAlert;
import com.SafetyNet.model.Person;
import com.SafetyNet.service.PersonService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PersonController {
    @Autowired
    private PersonService personService;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(value = "/persons", method = RequestMethod.GET)
    public List<PersonMedDto> personsList(){
        List<Person> personsList = personService.getPersonsList();
        return personsList.stream().map(this::personMedToDto).collect(Collectors.toList());
    }

    @GetMapping(value = "/personInfo")
    public List<PersonMedDto> personsListByFirstAndLastName (@RequestParam ("firstName") String firstName,
                                                          @RequestParam ("lastName") String lastName){
        List<Person> personsList = personService.getPersonsListFilteredByFirstAndLastName(firstName, lastName);
        return personsList.stream().map(this::personMedToDto).collect(Collectors.toList());
    }

    @GetMapping(value = "childAlert")
    public ChildAlertDto childListByAddress (@RequestParam ("address") String address) throws ParseException, UnsupportedEncodingException {
        String decodedAddress = URLDecoder.decode(address, StandardCharsets.UTF_8.toString());
        ChildAlert childAlert = personService.getChildAlert(decodedAddress);
        return childAlertToDto(childAlert);
    }

    @GetMapping(value="communityEmail")
    public List<String> communityEmail (@RequestParam("city") String city) throws UnsupportedEncodingException {
        String decoded = URLDecoder.decode(city, StandardCharsets.UTF_8.toString());
        List<String> emails = personService.getEmailsByCity(decoded);
        return emails;
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
