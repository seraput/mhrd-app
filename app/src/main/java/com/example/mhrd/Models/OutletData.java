package com.example.mhrd.Models;

public class OutletData {

    private String id, nama, type, telp, provinsi, kota, kec, alamat, status;

    public OutletData() {
    }

    public OutletData(String id, String nama, String type, String telp, String provinsi, String kota, String kec, String alamat, String status) {
        this.id = id;
        this.nama = nama;
        this.type = type;
        this.telp = telp;
        this.provinsi = provinsi;
        this.kota = kota;
        this.kec = kec;
        this.alamat = alamat;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
