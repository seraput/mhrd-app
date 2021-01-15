package com.example.mhrd.Models;

public class BranchData {
    private String id, name, alamat, status;

    public BranchData() {
    }

    public BranchData(String id, String name, String alamat, String status) {
        this.id = id;
        this.name = name;
        this.alamat = alamat;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
