package com.diagnosa.stroke.kelas;

public class cSolusiIdeal {
    private String kriteria;
    private double Yplus;
    private double Ymin;

    public cSolusiIdeal(String kriteria, double yPlus, double yMin) {
        this.kriteria = kriteria;
        this.Yplus = yPlus;
        this.Ymin = yMin;
    }

    public String getKriteria() {
        return kriteria;
    }

    public void setKriteria(String kriteria) {
        this.kriteria = kriteria;
    }

    public double getYplus() {
        return Yplus;
    }

    public void setYplus(double yPlus) {
        this.Yplus = yPlus;
    }

    public double getYmin() {
        return Ymin;
    }

    public void setYmin(double ymin) {
        this.Ymin = ymin;
    }
}
