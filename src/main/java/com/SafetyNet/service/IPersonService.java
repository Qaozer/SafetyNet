package com.SafetyNet.service;

import com.SafetyNet.model.ChildAlert;
import com.SafetyNet.model.Person;

import java.util.List;

public interface IPersonService {
    List<Person> getPersonsList();
    List<Person> getPersonsListFilteredByFirstAndLastName(String firstName, String lastName);
    ChildAlert getChildAlert(String address);
    List<String> getEmailsByCity(String city);
    void delete(String firstName, String lastName);
    void add(Person person);
    void update(Person person);
    boolean contains(Person person);

}
