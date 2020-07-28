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

    private static final Logger logger = Logger.getLogger(PersonDaoImpl.class);
    @Autowired
    private Data data;

    @Override
    public List<Person> getPersonsList() {
        logger.info("Accessed personsList.");
        return data.getPersons();
    }

    @Override
    public void delete(String firstName, String lastName){
        Iterator itr = data.getPersons().iterator();
        while (itr.hasNext()){
            Person p = (Person)itr.next();
            if (p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)){
                itr.remove();
                logger.info(firstName+" "+lastName+" successfully removed from persons list.");
            }
        }
    }

    @Override
    public void add(Person person) {
        data.getPersons().add(person);
        logger.info(person.getFirstName()+" "+person.getLastName()+" successfully added to dataset.");
    }

    @Override
    public void update(Person updated) {
        Optional<Person> stored = data.getPersons().stream().filter(p -> p.getFirstName().equals(updated.getFirstName()) && p.getLastName().equals(updated.getLastName())).findFirst();
        if (stored.isPresent()){
            Person personStored = stored.get();
            logger.info("Updating "+personStored.getFirstName()+" "+personStored.getLastName()+".");
            if(!personStored.getAddress().equals(updated.getAddress())){
                personStored.setAddress(updated.getAddress());
                logger.info("Address updated");
            }
            if(!personStored.getCity().equals(updated.getCity())){
                personStored.setCity(updated.getCity());
                logger.info("City updated");
            }
            if (personStored.getZip() != updated.getZip()){
                personStored.setZip(updated.getZip());
                logger.info("Zipcode updated");
            }
            if (!personStored.getPhone().equals(updated.getPhone())){
                personStored.setPhone(updated.getPhone());
                logger.info("Phone updated");
            }
            if (!personStored.getEmail().equals(updated.getEmail())){
                personStored.setEmail(updated.getEmail());
                logger.info("Email updated");
            }
        }
    }
}
