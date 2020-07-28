package com.SafetyNet.dto;

public class FirestationDto {

    private String address;
    private int station;

    public FirestationDto() {
    }

    public FirestationDto(String address, int station) {
        this.address = address;
        this.station = station;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStation() {
        return station;
    }

    public void setStation(int station) {
        this.station = station;
    }
}
