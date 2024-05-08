package com.affarituoi.app.affarituoi.Pacco;

public class Pacco {
    private int valore;
    private int id;
    private boolean isOpen = false;


    public Pacco(int id, int valore) {
        this.valore = valore;
        this.id = id;
    }

    public int getID() {
        return this.id;
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

