package com.diagnosa.stroke.kelas;

public class cJarak {
    private String kriteria;
    private double Dplus;
    private double Dmin;
    private double hasil;

    public cJarak(String kriteria, double dPlus, double dMin, double hasil) {
        this.kriteria = kriteria;
        this.Dplus = dPlus;
        this.Dmin = dMin;
        this.hasil = hasil;
    }

    public String getKriteria() {
        return kriteria;
    }

    public void setKriteria(String kriteria) {
        this.kriteria = kriteria;
    }

    public double getDplus() {
        return Dplus;
    }

    public void setDplus(double dPlus) {
        this.Dplus = dPlus;
    }

    public double getDmin() {
        return Dmin;
    }

    public void setDmin(double dmin) {
        this.Dmin = dmin;
    }

    public double getHasil(){
        return hasil;
    }

    public void setHasil(double hasil){
        this.hasil = hasil;
    }
}
