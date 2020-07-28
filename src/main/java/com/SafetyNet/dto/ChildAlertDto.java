package com.SafetyNet.dto;

import java.util.List;

public class ChildAlertDto {
    private List<NameAgeDto> childrenList;
    private List<NameDto> adultsList;

    public ChildAlertDto() {
    }

    public List<NameAgeDto> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(List<NameAgeDto> childrenList) {
        this.childrenList = childrenList;
    }

    public List<NameDto> getAdultsList() {
        return adultsList;
    }

    public void setAdultsList(List<NameDto> adultsList) {
        this.adultsList = adultsList;
    }

    @Override
    public String toString() {
        return "ChildAlertDto{" +
                "childrenList=" + childrenList +
                ", adultsList=" + adultsList +
                '}';
    }
}
