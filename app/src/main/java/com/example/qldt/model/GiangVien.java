package com.example.qldt.model;

import java.io.Serializable;

public class GiangVien implements Serializable  {

    private String image, msnv,email,name, gt, dob, phone, address, password;

    @Override
    public String toString() {
        return "GiangVien{" +
                "image='" + image + '\'' +
                "msnv='" + msnv + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", gt='" + gt + '\'' +
                ", dob='" + dob + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public GiangVien() {
    }

    public GiangVien(String image,String msnv, String email, String name, String gt, String dob, String phone, String address, String password) {
        this.image = image;
        this.msnv = msnv;
        this.email = email;
        this.name = name;
        this.gt = gt;
        this.dob = dob;
        this.phone = phone;
        this.address = address;
        this.password = password;
    }

    public String getMsnv() {
        return msnv;
    }

    public void setMsnv(String msnv) {
        this.msnv = msnv;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
