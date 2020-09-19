package com.SafetyNet.dao;

import com.SafetyNet.Data;
import com.SafetyNet.model.FireStation;
import com.SafetyNet.model.Person;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class FirestationDaoImpl implements FirestationDao {

    private static final Logger LOGGER = Logger.getLogger(FirestationDaoImpl.class);

    @Autowired
    private Data data;

    @Autowired
    private PersonDao personDao;

    @Override
    public List<FireStation> getFirestationsList() {
        LOGGER.info("Accessed firestations list");
        return data.getFirestations();
    }

    @Override
    public void add(FireStation fireStation) {
        LOGGER.info("Added to firestations List address: "+fireStation.getAddress()+" station: "+fireStation.getStation()+".");
        data.getFirestations().add(fireStation);
        List<Person> persons = personDao.getPersonsList().stream().filter(p -> p.getAddress().equals(fireStation.getAddress())).collect(Collectors.toList());
        for (Person p: persons){
            addStationToPerson(p, fireStation.getStation());
        }
    }

    @Override
    public void update(FireStation fireStation) {
        Optional<FireStation> toUpdate = data.getFirestations().stream().filter(f-> f.getAddress().equals(fireStation.getAddress())).findFirst();
        if(toUpdate.isPresent()){
            FireStation stored = toUpdate.get();
            if (stored.getStation() != fireStation.getStation()){
                int oldStation = stored.getStation();
                List<Person> persons = personDao.getPersonsList().stream().filter(p-> p.getStations().contains(oldStation)).collect(Collectors.toList());
                for(Person p : persons){
                    removeStationFromPerson(p, oldStation);
                    addStationToPerson(p, fireStation.getStation());
                }
                stored.setStation(fireStation.getStation());
                LOGGER.info("Updated station number to "+fireStation.getStation()+" for address : "+fireStation.getAddress()+".");
            }
        }
    }

    @Override
    public void delete(int stationNumber) {
        Iterator itr = data.getFirestations().iterator();
        while (itr.hasNext()){
            FireStation fireStation = (FireStation) itr.next();
            if (fireStation.getStation() == stationNumber){
                itr.remove();
                LOGGER.info("Removed "+fireStation.getAddress()+", station: "+fireStation.getStation()+" from dataset.");
            }
        }
        itr = data.getPersons().iterator();
        while (itr.hasNext()){
            Person person = (Person) itr.next();
            removeStationFromPerson(person, stationNumber);
        }
    }


    @Override
    public void delete(String address) {
        Iterator itr = data.getFirestations().iterator();
        List<Person> personList = personDao.getPersonsList().stream().filter(p-> p.getAddress().equals(address)).collect(Collectors.toList());
        while (itr.hasNext()){
            FireStation fireStation = (FireStation) itr.next();
            if(fireStation.getAddress().equals(address)){
                for(Person p: personList){
                    removeStationFromPerson(p, fireStation.getStation());
                }
                itr.remove();
                LOGGER.info("Removed "+fireStation.getAddress()+", station: "+fireStation.getStation()+" from dataset.");
            }
        }
    }

    private void removeStationFromPerson(Person p, int stationNumber){
        for (int i = 0; i < p.getStations().size(); i++){
            if (p.getStations().get(i) == stationNumber){
                p.getStations().remove(i);
                LOGGER.info("Removed station "+stationNumber+" from stations list of "+p.getFirstName()+" "+p.getLastName()+".");
            }
        }
    }

    private void addStationToPerson(Person p, int stationNumber){
        if(!p.getStations().contains(stationNumber)){
            p.getStations().add(stationNumber);
        }
    }
}
