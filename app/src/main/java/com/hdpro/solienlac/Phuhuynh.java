package com.hdpro.solienlac;

/**
 * Created by User on 14/12/2016.
 */

public class Phuhuynh {
    private int idPH;
    private String tenLop;
    private String tenPH;
    private String tenHS;

    public Phuhuynh(int idPH, String tenLop, String tenPH, String tenHS) {
        this.idPH = idPH;
        this.tenLop = tenLop;
        this.tenPH = tenPH;
        this.tenHS = tenHS;
    }

    public int getIdPH() {
        return idPH;
    }

    public void setIdPH(int idPH) {
        this.idPH = idPH;
    }

    public String getTenLop() {
        return tenLop;
    }

    public void setTenLop(String tenLop) {
        this.tenLop = tenLop;
    }

    public String getTenPH() {
        return tenPH;
    }

    public void setTenPH(String tenPH) {
        this.tenPH = tenPH;
    }

    public String getTenHS() {
        return tenHS;
    }

    public void setTenHS(String tenHS) {
        this.tenHS = tenHS;
    }
}
