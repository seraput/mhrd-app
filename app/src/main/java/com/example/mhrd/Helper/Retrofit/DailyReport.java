package com.example.mhrd.Helper.Retrofit;

import com.google.gson.annotations.SerializedName;

public class DailyReport {

    @SerializedName("id")
    private int id;
    @SerializedName("jobs_id")
    private String jobs_id;
    @SerializedName("branch")
    private String branch;
    @SerializedName("project")
    private String project;
    @SerializedName("outlet")
    private String outlet;
    @SerializedName("product")
    private String product;
    @SerializedName("qty")
    private String qty;
    @SerializedName("image")
    private String image;
    @SerializedName("tanggal")
    private String tanggal;
    @SerializedName("user_id")
    private String user_id;
    @SerializedName("user_nama")
    private String user_nama;
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

    public String getJobs_id() {
        return jobs_id;
    }

    public void setJobs_id(String jobs_id) {
        this.jobs_id = jobs_id;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getOutlet() {
        return outlet;
    }

    public void setOutlet(String outlet) {
        this.outlet = outlet;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
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
