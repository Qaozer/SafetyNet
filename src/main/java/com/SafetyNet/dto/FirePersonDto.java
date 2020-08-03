package com.SafetyNet.dto;

public class FirePersonDto {
    private String firstName;
    private String lastName;
    private String phone;
    private int age;
    private MedDto medicalRecord;

    public FirePersonDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public MedDto getMedicalRecord() {
        return medicalRecord;
    }

    public void setMedicalRecord(MedDto medicalRecord) {
        this.medicalRecord = medicalRecord;
    }
}
