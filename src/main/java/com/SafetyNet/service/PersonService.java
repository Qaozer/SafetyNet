package com.SafetyNet.service;

import com.SafetyNet.dao.MedicalRecordDao;
import com.SafetyNet.dao.PersonDao;
import com.SafetyNet.model.ChildAlert;
import com.SafetyNet.model.MedicalRecord;
import com.SafetyNet.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonService implements IPersonService {

    @Autowired
    private PersonDao personDao;

    @Autowired
    private MedicalRecordDao medicalRecordDao;

    public List<Person> getPersonsList(){
        return personDao.getPersonsList();
    }

    public List<Person> getPersonsListFilteredByFirstAndLastName(String firstName, String lastName){
        List<Person> personList = personDao.getPersonsList().stream().filter(p -> p.getFirstName().equals(firstName) &&
                p.getLastName().equals(lastName)).collect(Collectors.toList());
        return personList;
    }

    public ChildAlert getChildAlert(String address) {
        List<Person> personList = personDao.getPersonsList().stream().filter(p -> p.getAddress().equals(address))
                .collect(Collectors.toList());
        ChildAlert childAlert = new ChildAlert(new ArrayList<>(), new ArrayList<>());
        for(Person p : personList){
            int age = p.getAge();
            if(age < 18){
                childAlert.getChildrenList().add(p);
            } else {
                childAlert.getAdultsList().add(p);
            }
        }
        return childAlert;
    }

    public List<String> getEmailsByCity(String city){
        List<Person> personList = personDao.getPersonsList().stream().filter(p -> p.getCity().equals(city)).collect(Collectors.toList());
        List<String> emailList = new ArrayList<>();
        personList.stream().forEach(p -> emailList.add(p.getEmail()));
        return emailList;
    }

    public void delete(String firstName, String lastName){
        personDao.delete(firstName, lastName);
        medicalRecordDao.delete(firstName, lastName);
    }

    public void add(Person person){
        Optional<MedicalRecord> medicalRecord = medicalRecordDao.getMedicalRecords().stream().filter(
                m-> m.getFirstName().equals(person.getFirstName()) && m.getLastName().equals(person.getLastName())
        ).findFirst();
        if (medicalRecord.isPresent()){
            person.setMedicalRecord(medicalRecord.get());
        }
        personDao.add(person);
    }

    public void update(Person person){
        personDao.update(person);
    }
}
