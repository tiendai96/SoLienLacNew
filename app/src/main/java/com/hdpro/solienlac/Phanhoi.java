package com.hdpro.solienlac;

/**
 * Created by User on 11/11/2016.
 */

public class Phanhoi {
    private int idhs;
    private String tieudePH;
    private String noidungPH;
    private String ngayguiPH;

    public Phanhoi(int idhs, String tieudePH, String noidungPH, String ngayguiPH) {
        this.idhs = idhs;
        this.tieudePH = tieudePH;
        this.noidungPH = noidungPH;
        this.ngayguiPH = ngayguiPH;
    }

    public int getIdhs() {
        return idhs;
    }

    public void setIdhs(int idhs) {
        this.idhs = idhs;
    }

    public String getTieudePH() {
        return tieudePH;
    }

    public void setTieudePH(String tieudePH) {
        this.tieudePH = tieudePH;
    }

    public String getNoidungPH() {
        return noidungPH;
    }

    public void setNoidungPH(String noidungPH) {
        this.noidungPH = noidungPH;
    }

    public String getNgayguiPH() {
        return ngayguiPH;
    }

    public void setNgayguiPH(String ngayguiPH) {
        this.ngayguiPH = ngayguiPH;
    }
}
