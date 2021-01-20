package com.example.mhrd.Models;

public class DailyData {
    private String id, jobs_id, branch, project, outlet, product, qty, image, tanggal, user_id, user_nama;

    public DailyData() {
    }

    public DailyData(String id, String jobs_id, String branch, String project, String outlet, String product, String qty, String image, String tanggal, String user_id, String user_nama) {
        this.id = id;
        this.jobs_id = jobs_id;
        this.branch = branch;
        this.project = project;
        this.outlet = outlet;
        this.product = product;
        this.qty = qty;
        this.image = image;
        this.tanggal = tanggal;
        this.user_id = user_id;
        this.user_nama = user_nama;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
}
