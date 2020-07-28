package com.SafetyNet.dto;

import java.util.List;

public class FireAlertDto {
    private List<FirePersonDto> personsList;
    private List<Integer> stationNumberList;

    public FireAlertDto() {
    }

    public List<FirePersonDto> getPersonsList() {
        return personsList;
    }

    public void setPersonsList(List<FirePersonDto> personsList) {
        this.personsList = personsList;
    }

    public List<Integer> getStationNumberList() {
        return stationNumberList;
    }

    public void setStationNumberList(List<Integer> stationNumberList) {
        this.stationNumberList = stationNumberList;
    }
}
