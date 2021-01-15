package com.example.mhrd.Models;

public class JobsData {

    private String id, branch, project, outlet, user_id, user_nama, start, end, status;

    public JobsData() {
    }

    public JobsData(String id, String branch, String project, String outlet, String user_id, String user_nama, String start, String end, String status) {
        this.id = id;
        this.branch = branch;
        this.project = project;
        this.outlet = outlet;
        this.user_id = user_id;
        this.user_nama = user_nama;
        this.start = start;
        this.end = end;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
