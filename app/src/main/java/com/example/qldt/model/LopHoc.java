package com.example.qldt.model;

import java.io.Serializable;

public class LopHoc implements Serializable {

    String malh, tenlop, phonghoc, giangvienlh, thu, tgbatdau, tgketthuc;
    int slsinhvien;

    @Override
    public String toString() {
        return "LopHoc{" +
                "malh='" + malh + '\'' +
                ", tenlop='" + tenlop + '\'' +
                ", phonghoc='" + phonghoc + '\'' +
                ", giangvienlh='" + giangvienlh + '\'' +
                ", thu='" + thu + '\'' +
                ", tgbatdau='" + tgbatdau + '\'' +
                ", tgketthuc='" + tgketthuc + '\'' +
                '}';
    }

    public LopHoc() {
    }

    public LopHoc(String malh, String tenlop, String phonghoc, String giangvienlh, String thu, String tgbatdau, String tgketthuc, int slsinhvien) {
        this.malh = malh;
        this.tenlop = tenlop;
        this.phonghoc = phonghoc;
        this.giangvienlh = giangvienlh;
        this.thu = thu;
        this.tgbatdau = tgbatdau;
        this.tgketthuc = tgketthuc;
        this.slsinhvien = slsinhvien;
    }

    public String getMalh() {
        return malh;
    }

    public void setMalh(String malh) {
        this.malh = malh;
    }

    public String getTenlop() {
        return tenlop;
    }

    public void setTenlop(String tenlop) {
        this.tenlop = tenlop;
    }

    public String getPhonghoc() {
        return phonghoc;
    }

    public void setPhonghoc(String phonghoc) {
        this.phonghoc = phonghoc;
    }

    public String getGiangvienlh() {
        return giangvienlh;
    }

    public void setGiangvienlh(String giangvienlh) {
        this.giangvienlh = giangvienlh;
    }

    public String getThu() {
        return thu;
    }

    public void setThu(String thu) {
        this.thu = thu;
    }

    public String getTgbatdau() {
        return tgbatdau;
    }

    public void setTgbatdau(String tgbatdau) {
        this.tgbatdau = tgbatdau;
    }

    public String getTgketthuc() {
        return tgketthuc;
    }

    public void setTgketthuc(String tgketthuc) {
        this.tgketthuc = tgketthuc;
    }

    public int getSlsinhvien() {
        return slsinhvien;
    }

    public void setSlsinhvien(int slsinhvien) {
        this.slsinhvien = slsinhvien;
    }
}
