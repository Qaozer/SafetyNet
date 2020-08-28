package com.SafetyNet.dao;

import com.SafetyNet.Data;
import com.SafetyNet.model.Person;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Repository
public class PersonDaoImpl implements PersonDao {

    private static final Logger LOGGER = Logger.getLogger(PersonDaoImpl.class);
    @Autowired
    private Data data;

    @Override
    public List<Person> getPersonsList() {
        LOGGER.info("Accessed personsList.");
        return data.getPersons();
    }

    @Override
    public void delete(String firstName, String lastName){
        Iterator itr = data.getPersons().iterator();
        while (itr.hasNext()){
            Person p = (Person)itr.next();
            if (p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)){
                itr.remove();
                LOGGER.info(firstName+" "+lastName+" successfully removed from persons list.");
            }
        }
    }

    @Override
    public void add(Person person) {
        data.getPersons().add(person);
        LOGGER.info(person.getFirstName()+" "+person.getLastName()+" successfully added to dataset.");
    }

    @Override
    public void update(Person updated) {
        Optional<Person> stored = data.getPersons().stream().filter(p -> p.getFirstName().equals(updated.getFirstName()) && p.getLastName().equals(updated.getLastName())).findFirst();
        if (stored.isPresent()){
            Person personStored = stored.get();
            LOGGER.info("Updating "+personStored.getFirstName()+" "+personStored.getLastName()+".");
            if(!personStored.getAddress().equals(updated.getAddress())){
                personStored.setAddress(updated.getAddress());
                LOGGER.info("Address updated");
            }
            if(!personStored.getCity().equals(updated.getCity())){
                personStored.setCity(updated.getCity());
                LOGGER.info("City updated");
            }
            if (personStored.getZip() != updated.getZip()){
                personStored.setZip(updated.getZip());
                LOGGER.info("Zipcode updated");
            }
            if (!personStored.getPhone().equals(updated.getPhone())){
                personStored.setPhone(updated.getPhone());
                LOGGER.info("Phone updated");
            }
            if (!personStored.getEmail().equals(updated.getEmail())){
                personStored.setEmail(updated.getEmail());
                LOGGER.info("Email updated");
            }
            if(!personStored.getStations().equals(updated.getStations())){
                personStored.setStations(updated.getStations());
                LOGGER.info("Stations list updated");
            }
        }
    }
}
