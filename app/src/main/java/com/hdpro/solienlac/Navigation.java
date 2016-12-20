package com.hdpro.solienlac;

/**
 * Created by User on 03/11/2016.
 */

public class Navigation {
    private String txtTieude;
    private int resIcon;

    public Navigation(String txtTieude, int resIcon) {
        this.txtTieude = txtTieude;
        this.resIcon = resIcon;
    }

    public int getResIcon() {
        return resIcon;
    }

    public void setResIcon(int resIcon) {
        this.resIcon = resIcon;
    }

    public String getTxtTieude() {
        return txtTieude;
    }

    public void setTxtTieude(String txtTieude) {
        this.txtTieude = txtTieude;
    }
}
