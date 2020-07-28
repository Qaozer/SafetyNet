package com.SafetyNet;

import com.SafetyNet.model.FireStation;
import com.SafetyNet.model.MedicalRecord;
import com.SafetyNet.model.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Data {
    //Nom des variables en minuscules pour l'utilisation de Jackson
    private List<Person> persons;
    private List<FireStation> firestations;
    private List<MedicalRecord> medicalrecords;
    private static final Logger logger = Logger.getLogger(Data.class);

    public Data() {
    }

    public Data(String path) {
        Data data = new Data();
        ObjectMapper om = new ObjectMapper();
        om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        try {
            data = om.readValue(new File(path), Data.class);
        } catch (IOException e) {
            logger.info("[ERROR] Couldn't read data.json.");
            e.printStackTrace();
        }
        this.medicalrecords = data.medicalrecords;
        this.persons = data.persons;
        this.firestations = data.firestations;

        for (Person p: this.persons) {
            //association d'une Person avec son MedicalRecord
            for (MedicalRecord m: this.medicalrecords){
                if (m.getFirstName().equals(p.getFirstName()) && m.getLastName().equals(p.getLastName())){
                    p.setMedicalRecord(m);
                    break;
                }
            }
            //association d'une Person avec ses FireStations
            p.setStations(new ArrayList<>());
            for(FireStation f: this.firestations){
                if (p.getAddress().equals(f.getAddress())){
                    p.getStations().add(f.getStation());
                }
            }
            try {
                p.setAge(p.ageCalc());
            } catch (ParseException e) {
                logger.info("[ERROR] Couldn't calculate age for: "+p.getFirstName()+" "+p.getLastName()+".");
                e.printStackTrace();
            }

        }
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public List<FireStation> getFirestations() {
        return firestations;
    }

    public void setFirestations(List<FireStation> firestations) {
        this.firestations = firestations;
    }

    public List<MedicalRecord> getMedicalrecords() {
        return medicalrecords;
    }

    public void setMedicalrecords(List<MedicalRecord> medicalrecords) {
        this.medicalrecords = medicalrecords;
    }
}
