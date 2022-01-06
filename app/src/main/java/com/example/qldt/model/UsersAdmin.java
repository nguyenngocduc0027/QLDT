package com.example.qldt.model;

public class UsersAdmin {

    String email, name,  dob, phone, address;

    @Override
    public String toString() {
        return "UsersAdmin{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", dob='" + dob + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public UsersAdmin() {
    }

    public UsersAdmin(String email, String name, String dob, String phone, String address) {
        this.email = email;
        this.name = name;
        this.dob = dob;
        this.phone = phone;
        this.address = address;
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
}
