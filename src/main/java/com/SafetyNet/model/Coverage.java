package com.SafetyNet.model;

import java.util.List;

public class Coverage {
    private List<Person> personList;
    private int children;
    private int adults;

    public Coverage() {
    }

    public Coverage(List<Person> personList, int children, int adults) {
        this.personList = personList;
        this.children = children;
        this.adults = adults;
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    public int getChildren() {
        return children;
    }

    public void setChildren(int children) {
        this.children = children;
    }

    public int getAdults() {
        return adults;
    }

    public void setAdults(int adults) {
        this.adults = adults;
    }

    public void addOneAdult(){
        this.adults++;
    }

    public void addOneChild(){
        this.children++;
    }
}
