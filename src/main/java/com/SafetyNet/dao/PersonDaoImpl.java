package com.SafetyNet.dao;

import com.SafetyNet.Data;
import com.SafetyNet.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonDaoImpl implements PersonDao {
    @Autowired
    private Data data;

    @Override
    public List<Person> getPersonsList() {
        return data.getPersons();
    }
}
