package com.SafetyNet.dto;

import java.util.List;

public class CoverageDto {
    private List<CoveragePersonDto> personList;
    private int children;
    private int adults;

    public CoverageDto() {
    }

    public CoverageDto(List<CoveragePersonDto> personList, int children, int adults) {
        this.personList = personList;
        this.children = children;
        this.adults = adults;
    }

    public List<CoveragePersonDto> getPersonList() {
        return personList;
    }

    public void setPersonList(List<CoveragePersonDto> personList) {
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
}
