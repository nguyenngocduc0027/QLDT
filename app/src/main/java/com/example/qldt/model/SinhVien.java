package com.example.qldt.model;

import java.io.Serializable;

public class SinhVien implements Serializable {

    private String image, mssv, name, gt, email, dob, phone, address, password;

    @Override
    public String toString() {
        return "SinhVien{" +
                "image='" + image + '\'' +
                ", mssv='" + mssv + '\'' +
                ", name='" + name + '\'' +
                ", gt='" + gt + '\'' +
                ", email='" + email + '\'' +
                ", dob='" + dob + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public SinhVien() {
    }

    public SinhVien(String image, String mssv, String name, String gt, String email, String dob, String phone, String address, String password) {
        this.image = image;
        this.mssv = mssv;
        this.name = name;
        this.gt = gt;
        this.email = email;
        this.dob = dob;
        this.phone = phone;
        this.address = address;
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMssv() {
        return mssv;
    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGt() {
        return gt;
    }

    public void setGt(String gt) {
        this.gt = gt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
