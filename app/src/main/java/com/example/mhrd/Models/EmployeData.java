package com.example.mhrd.Models;

public class EmployeData {

    private String nik, nama, email, telp, kelamin, provinsi, kota, kec, alamat, level, status;

    public EmployeData() {
    }

    public EmployeData(String nik, String nama, String email, String telp, String kelamin, String provinsi, String kota, String kec, String alamat, String level, String status) {
        this.nik = nik;
        this.nama = nama;
        this.email = email;
        this.telp = telp;
        this.kelamin = kelamin;
        this.provinsi = provinsi;
        this.kota = kota;
        this.kec = kec;
        this.alamat = alamat;
        this.level = level;
        this.status = status;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }

    public String getKelamin() {
        return kelamin;
    }

    public void setKelamin(String kelamin) {
        this.kelamin = kelamin;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getKec() {
        return kec;
    }

    public void setKec(String kec) {
        this.kec = kec;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
