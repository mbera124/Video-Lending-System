package com.example.admin.videolendingsystem.Customer;

/**
 * Created by ADMIN on 23/05/2017.
 */

public class Custadd {

    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private String natId;
    private String gender;
    private String registeredBy;
    private String custkey;

    public Custadd(){
    }

    public String getFName() {
        return firstName;
    }

    public void setFName(String firstName) {
        this.firstName = firstName;
    }

    public String getLName() {
        return lastName;
    }

    public void setLName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNatId() {
        return natId;
    }

    public void setNatId(String natId) {
        this.natId = natId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRegisteredBy() {
        return registeredBy;
    }

    public void setRegisteredBy(String registeredBy) {
        this.registeredBy = registeredBy;
    }

    public String getCustomerKey() {
        return custkey;
    }

    public void setCustomerKey(String custkey) {
        this.custkey = custkey;
    }
}



