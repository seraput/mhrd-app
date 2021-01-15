package com.example.mhrd.Models;

public class UserData {
    private String id, email, nama, branch;

    public UserData() {
    }

    public UserData(String id, String email, String nama, String branch) {
        this.id = id;
        this.email = email;
        this.nama = nama;
        this.branch = branch;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
}
