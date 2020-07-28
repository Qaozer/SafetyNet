package com.SafetyNet.dto;

public class PersonMedDto {
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private int zip;
    private int age;
    private String email;
    private MedDto medicalRecord;

    public PersonMedDto() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public MedDto getMedicalRecord() {
        return medicalRecord;
    }

    public void setMedicalRecord(MedDto medicalRecord) {
        this.medicalRecord = medicalRecord;
    }
}
