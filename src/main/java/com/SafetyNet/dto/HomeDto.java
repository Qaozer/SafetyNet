package com.SafetyNet.dto;

import java.util.List;

public class HomeDto {
    private String address;
    private List<FirePersonDto> personList;

    public HomeDto() {
    }

    public HomeDto(String address, List<FirePersonDto> personList) {
        this.address = address;
        this.personList = personList;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<FirePersonDto> getPersonList() {
        return personList;
    }

    public void setPersonList(List<FirePersonDto> personList) {
        this.personList = personList;
    }
}
