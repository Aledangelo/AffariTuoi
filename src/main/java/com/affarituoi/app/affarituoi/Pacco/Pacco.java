package com.affarituoi.app.affarituoi.Pacco;

public class Pacco {
    private int valore;
    private boolean isOpen = false;

    public Pacco(int valore) {
        this.valore = valore;
    }

    public int getValore() {
        return valore;
    }

    public void setOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public boolean isOpen() {
        return isOpen;
    }
}

