package com.example.mhrd.Models;

public class AbsenData {
    private String id, u_id, u_nama, p_id, p_nama, tanggal, jam, image, keterangan;

    public AbsenData() {
    }

    public AbsenData(String id, String u_id, String u_nama, String p_id, String p_nama, String tanggal, String jam, String image, String keterangan) {
        this.id = id;
        this.u_id = u_id;
        this.u_nama = u_nama;
        this.p_id = p_id;
        this.p_nama = p_nama;
        this.tanggal = tanggal;
        this.jam = jam;
        this.image = image;
        this.keterangan = keterangan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getU_nama() {
        return u_nama;
    }

    public void setU_nama(String u_nama) {
        this.u_nama = u_nama;
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public String getP_nama() {
        return p_nama;
    }

    public void setP_nama(String p_nama) {
        this.p_nama = p_nama;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
