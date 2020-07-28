package com.SafetyNet.model;

import java.util.List;

public class Home {
    private String Address;
    private List<Person> personList;

    public Home(String address, List<Person> personList) {
        Address = address;
        this.personList = personList;
    }

    public Home() {
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }
}
