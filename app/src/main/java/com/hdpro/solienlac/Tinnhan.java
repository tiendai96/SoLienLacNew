package com.hdpro.solienlac;

/**
 * Created by User on 04/11/2016.
 */

public class Tinnhan {
    private int idTinnhan;
    private String noidungTinnhan;
    private String ngayguiTinnhan;
    private int trangthaiTinnhan;

    public Tinnhan(int idTinnhan, String noidungTinnhan, String ngayguiTinnhan, int trangthaiTinnhan) {
        this.idTinnhan = idTinnhan;
        this.noidungTinnhan = noidungTinnhan;
        this.ngayguiTinnhan = ngayguiTinnhan;
        this.trangthaiTinnhan = trangthaiTinnhan;
    }

    public int getIdTinnhan() {
        return idTinnhan;
    }

    public void setIdTinnhan(int idTinnhan) {
        this.idTinnhan = idTinnhan;
    }

    public String getNoidungTinnhan() {
        return noidungTinnhan;
    }

    public void setNoidungTinnhan(String noidungTinnhan) {
        this.noidungTinnhan = noidungTinnhan;
    }

    public String getNgayguiTinnhan() {
        return ngayguiTinnhan;
    }

    public void setNgayguiTinnhan(String ngayguiTinnhan) {
        this.ngayguiTinnhan = ngayguiTinnhan;
    }

    public int getTrangthaiTinnhan() {
        return trangthaiTinnhan;
    }

    public void setTrangthaiTinnhan(int trangthaiTinnhan) {
        this.trangthaiTinnhan = trangthaiTinnhan;
    }
}
