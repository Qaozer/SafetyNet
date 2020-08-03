package com.SafetyNet.service;

import com.SafetyNet.dao.FirestationDao;
import com.SafetyNet.dao.PersonDao;
import com.SafetyNet.model.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FirestationService implements IFirestationService {

    static final Logger LOGGER = Logger.getLogger(FirestationService.class);

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonDao personDao;

    @Autowired
    private FirestationDao firestationDao;

    @Override
    public void add(FireStation fireStation) {
        firestationDao.add(fireStation);
    }

    @Override
    public void update(FireStation fireStation) {
        firestationDao.update(fireStation);
    }

    @Override
    public void deleteStation(int stationNumber) {
        firestationDao.delete(stationNumber);
    }

    @Override
    public void deleteAddress(String address) {
        firestationDao.delete(address);
    }

    @Override
    public Coverage getCoverage(int station) {
        List<Person> personList = personService.getPersonsList().stream().filter(p -> p.getStations().contains(station)).collect(Collectors.toList());
        Coverage coverage = new Coverage(personList, 0, 0);
        for (Person p: coverage.getPersonList()){
            if (p.getAge() <= 18){
                coverage.addOneChild();
            } else {
                coverage.addOneAdult();
            }
        }
        return coverage;
    }

    @Override
    public List<String> getPhonesByStation(int station) {
        List<Person> personList = personDao.getPersonsList().stream().filter(p-> p.getStations().contains(station)).collect(Collectors.toList());
        List<String> phoneList = new ArrayList<>();
        for (Person p: personList){
            phoneList.add(p.getPhone());
        }
        return phoneList;
    }

    @Override
    public FireAlert getFireAlert(String address) {
        List<Person> personList = personDao.getPersonsList().stream().filter(p-> p.getAddress().equals(address)).collect(Collectors.toList());
        List<FireStation> fireStations = firestationDao.getFirestationsList().stream().filter(f -> f.getAddress().equals(address)).collect(Collectors.toList());
        List<Integer> stationNumberList = new ArrayList<>();
        for (FireStation f : fireStations){
            stationNumberList.add(f.getStation());
        }
        return new FireAlert(personList, stationNumberList);
    }

    @Override
    public List<Home> getFloodAlert(List<Integer> stationNumbers) {
        List<FireStation> fireStations = firestationDao.getFirestationsList().stream().filter(f -> stationNumbers.contains(f.getStation())).collect(Collectors.toList());
        List<Person> personList = personDao.getPersonsList().stream().filter(p->{
            boolean isTrue = false;
            for(int s : stationNumbers){
                if(p.getStations().contains(s)){
                    isTrue = true;
                    break;
                }
            }
            return isTrue;
        }).collect(Collectors.toList());
        List<Home> homes = new ArrayList<>();
        for (FireStation f : fireStations){
            if(!homes.stream().filter(h -> h.getAddress().equals(f.getAddress())).findFirst().isPresent()){
                List<Person> homePersonList = personList.stream().filter(p->p.getAddress().equals(f.getAddress())).collect(Collectors.toList());
                Home home = new Home(f.getAddress(), homePersonList);
                homes.add(home);
            }
        }
        return homes;
    }
}
