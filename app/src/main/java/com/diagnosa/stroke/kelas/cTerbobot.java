package com.diagnosa.stroke.kelas;

public class cTerbobot {
    private String kriteria;
    private double s1;
    private double s2;
    private double s3;
    private double s4;

    public cTerbobot(String kriteria, double s1, double s2, double s3, double s4) {
        this.kriteria = kriteria;
        this.s1 = s1;
        this.s2 = s2;
        this.s3 = s3;
        this.s4 = s4;
    }

    public String getKriteria() {
        return kriteria;
    }

    public void setKriteria(String kriteria) {
        this.kriteria = kriteria;
    }

    public double getS1() {
        return s1;
    }

    public void setS1(int s1) {
        this.s1 = s1;
    }

    public double getS2() {
        return s2;
    }

    public void setS2(int s2) {
        this.s2 = s2;
    }

    public double getS3() {
        return s3;
    }

    public void setS3(int s3) {
        this.s3 = s3;
    }

    public double getS4() {
        return s4;
    }

    public void setS4(int s4) {
        this.s4 = s4;
    }
}
