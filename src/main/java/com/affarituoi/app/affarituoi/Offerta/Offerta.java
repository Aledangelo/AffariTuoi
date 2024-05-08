package com.affarituoi.app.affarituoi.Offerta;

public class Offerta {
    private String type;
    private int value;

    public Offerta(String type, int value) {
        this.type = type;
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
