package com.SafetyNet.dao;

import com.SafetyNet.Data;
import com.SafetyNet.model.Person;

import java.util.List;
import java.util.stream.Stream;

public interface PersonDao {
    public List<Person> getPersonsList();

    public void delete(String firstName, String lastName);

    public void add(Person person);

    public void update(Person person);
}
