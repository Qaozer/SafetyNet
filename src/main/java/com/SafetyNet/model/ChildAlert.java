package com.SafetyNet.model;

import java.util.List;

public class ChildAlert {
    private List<Person> childrenList;
    private List<Person> adultsList;

    public ChildAlert(List<Person> cl, List<Person> al) {
        this.setAdultsList(al);
        this.setChildrenList(cl);
    }

    public List<Person> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(List<Person> childrenList) {
        this.childrenList = childrenList;
    }

    public List<Person> getAdultsList() {
        return adultsList;
    }

    public void setAdultsList(List<Person> adultsList) {
        this.adultsList = adultsList;
    }

    @Override
    public String toString() {
        return "ChildAlert{" +
                "childrenList=" + childrenList +
                ", adultsList=" + adultsList +
                '}';
    }
}
