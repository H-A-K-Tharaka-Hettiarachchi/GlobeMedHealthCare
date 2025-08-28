package com.globemed.patient.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents a Patient entity, mapping to the 'patient' table in the database.
 */
public class Patient {

    private int id;
    private String fname;
    private String lname;
    private String nic;
    private String email;
    private String mobile;
    private LocalDate dateOfBirth;
    private int genderId;
    private String address;
    private int bloodTypeId;
    private LocalDateTime createdAt;
    private byte[] encryptedMedicalHistory;

    // Default constructor
    public Patient() {
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getGenderId() {
        return genderId;
    }

    public void setGenderId(int genderId) {
        this.genderId = genderId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getBloodTypeId() {
        return bloodTypeId;
    }

    public void setBloodTypeId(int bloodTypeId) {
        this.bloodTypeId = bloodTypeId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public byte[] getEncryptedMedicalHistory() {
        return encryptedMedicalHistory;
    }

    public void setEncryptedMedicalHistory(byte[] encryptedMedicalHistory) {
        this.encryptedMedicalHistory = encryptedMedicalHistory;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", nic='" + nic + '\'' +
                '}';
    }
}
