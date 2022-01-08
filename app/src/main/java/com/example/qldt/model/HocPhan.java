package com.example.qldt.model;

import java.io.Serializable;

public class HocPhan implements Serializable {
    String mahp, name, sotc, intro;

    @Override
    public String toString() {
        return "HocPhan{" +
                "mahp='" + mahp + '\'' +
                ", name='" + name + '\'' +
                ", sotc='" + sotc + '\'' +
                ", intro='" + intro + '\'' +
                '}';
    }

    public HocPhan() {
    }

    public HocPhan(String mahp, String name, String sotc, String intro) {
        this.mahp = mahp;
        this.name = name;
        this.sotc = sotc;
        this.intro = intro;
    }

    public String getMahp() {
        return mahp;
    }

    public void setMahp(String mahp) {
        this.mahp = mahp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSotc() {
        return sotc;
    }

    public void setSotc(String sotc) {
        this.sotc = sotc;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}
