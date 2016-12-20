package com.hdpro.solienlac;

/**
 * Created by User on 03/11/2016.
 */

public class Diem {
    private String tenmonhoc;
    private String txtHeso1;
    private String txtHeso2;
    private String txtDiemthi;

    public Diem(String tenmonhoc, String txtHeso1, String txtHeso2, String txtDiemthi) {
        this.tenmonhoc = tenmonhoc;
        this.txtHeso1 = txtHeso1;
        this.txtHeso2 = txtHeso2;
        this.txtDiemthi = txtDiemthi;
    }

    public String getTenmonhoc() {
        return tenmonhoc;
    }

    public void setTenmonhoc(String tenmonhoc) {
        this.tenmonhoc = tenmonhoc;
    }

    public String getTxtHeso1() {
        return txtHeso1;
    }

    public void setTxtHeso1(String txtHeso1) {
        this.txtHeso1 = txtHeso1;
    }

    public String getTxtHeso2() {
        return txtHeso2;
    }

    public void setTxtHeso2(String txtHeso2) {
        this.txtHeso2 = txtHeso2;
    }

    public String getTxtDiemthi() {
        return txtDiemthi;
    }

    public void setTxtDiemthi(String txtDiemthi) {
        this.txtDiemthi = txtDiemthi;
    }
}
