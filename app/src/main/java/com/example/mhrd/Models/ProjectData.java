package com.example.mhrd.Models;

public class ProjectData {
    private String id, nama, b_code, b_name, area, bulan, status;

    public ProjectData() {
    }

    public ProjectData(String id, String nama, String b_code, String b_name, String area, String bulan, String status) {
        this.id = id;
        this.nama = nama;
        this.b_code = b_code;
        this.b_name = b_name;
        this.area = area;
        this.bulan = bulan;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getB_code() {
        return b_code;
    }

    public void setB_code(String b_code) {
        this.b_code = b_code;
    }

    public String getB_name() {
        return b_name;
    }

    public void setB_name(String b_name) {
        this.b_name = b_name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getBulan() {
        return bulan;
    }

    public void setBulan(String bulan) {
        this.bulan = bulan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
