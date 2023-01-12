package com.diagnosa.stroke.kelas;

import android.content.Context;


public class cBobot {
    private String kriteria;
    private int bobot;

    public cBobot(String kriteria, int bobot) {
        this.kriteria = kriteria;
        this.bobot = bobot;
    }

    public String getKriteria() {
        return kriteria;
    }

    public void setKriteria(String kriteria) {
        this.kriteria = kriteria;
    }

    public int getBobot() {
        return bobot;
    }

    public void setBobot(int bobot) {
        this.bobot = bobot;
    }


}
