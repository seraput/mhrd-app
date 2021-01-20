package com.example.mhrd.Models;

public class JobsData {

    private String id, p_id, p_name, branch, user_id, user_nama, telp, outlet_id, outlet_name, alamat, kec, kota, provinsi, start, status;

    public JobsData() {
    }

    public JobsData(String id, String p_id, String p_name, String branch, String user_id, String user_nama, String telp, String outlet_id, String outlet_name, String alamat, String kec, String kota, String provinsi, String start, String status) {
        this.id = id;
        this.p_id = p_id;
        this.p_name = p_name;
        this.branch = branch;
        this.user_id = user_id;
        this.user_nama = user_nama;
        this.telp = telp;
        this.outlet_id = outlet_id;
        this.outlet_name = outlet_name;
        this.alamat = alamat;
        this.kec = kec;
        this.kota = kota;
        this.provinsi = provinsi;
        this.start = start;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_nama() {
        return user_nama;
    }

    public void setUser_nama(String user_nama) {
        this.user_nama = user_nama;
    }

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }

    public String getOutlet_id() {
        return outlet_id;
    }

    public void setOutlet_id(String outlet_id) {
        this.outlet_id = outlet_id;
    }

    public String getOutlet_name() {
        return outlet_name;
    }

    public void setOutlet_name(String outlet_name) {
        this.outlet_name = outlet_name;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getKec() {
        return kec;
    }

    public void setKec(String kec) {
        this.kec = kec;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
