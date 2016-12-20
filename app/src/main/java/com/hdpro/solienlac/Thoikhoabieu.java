package com.hdpro.solienlac;

/**
 * Created by User on 05/11/2016.
 */

public class Thoikhoabieu {
    private int thu;
    private String monhoc;
    private int tiethoc;

    public Thoikhoabieu(int thu, String monhoc, int tiethoc) {
        this.thu = thu;
        this.monhoc = monhoc;
        this.tiethoc = tiethoc;
    }

    public int getThu() {
        return thu;
    }

    public void setThu(int thu) {
        this.thu = thu;
    }

    public String getMonhoc() {
        return monhoc;
    }

    public void setMonhoc(String monhoc) {
        this.monhoc = monhoc;
    }

    public int getTiethoc() {
        return tiethoc;
    }

    public void setTiethoc(int tiethoc) {
        this.tiethoc = tiethoc;
    }
}
