package com.example.mhrd.Helper.Retrofit;

import com.google.gson.annotations.SerializedName;

public class Absent {

    @SerializedName("id")
    private int id;
    @SerializedName("u_id")
    private String userId;
    @SerializedName("u_nama")
    private String userNama;
    @SerializedName("p_id")
    private String projectId;
    @SerializedName("p_nama")
    private String projectNama;
    @SerializedName("tanggal")
    private String tanggal;
    @SerializedName("jam")
    private String jam;
    @SerializedName("image")
    private String image;
    @SerializedName("keterangan")
    private String keterangan;
    @SerializedName("value")
    private String value;
    @SerializedName("message")
    private String massage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNama() {
        return userNama;
    }

    public void setUserNama(String userNama) {
        this.userNama = userNama;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectNama() {
        return projectNama;
    }

    public void setProjectNama(String projectNama) {
        this.projectNama = projectNama;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }
}
