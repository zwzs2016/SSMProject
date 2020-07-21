package com.uwan.SSM;

import java.io.Serializable;

public class User implements Serializable {
    private Integer id;
    private String post;
    private String wages;
    private String company;
    private String workinfo;
    private String companyinfo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getWages() {
        return wages;
    }

    public void setWages(String wages) {
        this.wages = wages;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getWorkinfo() {
        return workinfo;
    }

    public void setWorkinfo(String workinfo) {
        this.workinfo = workinfo;
    }

    public String getCompanyinfo() {
        return companyinfo;
    }

    public void setCompanyinfo(String companyinfo) {
        this.companyinfo = companyinfo;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", post='" + post + '\'' +
                ", wages='" + wages + '\'' +
                ", company='" + company + '\'' +
                ", workinfo='" + workinfo + '\'' +
                ", companyinfo='" + companyinfo + '\'' +
                '}';
    }
}
