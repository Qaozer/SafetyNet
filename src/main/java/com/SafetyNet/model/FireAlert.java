package com.SafetyNet.model;

import java.util.List;

public class FireAlert {
    private List<Person> personsList;
    private List<Integer> stationNumberList;

    public FireAlert() {
    }

    public FireAlert(List<Person> personsList, List<Integer> station) {
        this.personsList = personsList;
        this.stationNumberList = station;
    }

    public List<Person> getPersonsList() {
        return personsList;
    }

    public void setPersonsList(List<Person> personsList) {
        this.personsList = personsList;
    }

    public List<Integer> getStationNumberList() {
        return stationNumberList;
    }

    public void setStationNumberList(List<Integer> stationNumberList) {
        this.stationNumberList = stationNumberList;
    }
}
