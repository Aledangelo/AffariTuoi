package com.affarituoi.app.affarituoi.Pacchista;

import com.affarituoi.app.affarituoi.Pacco.Pacco;;

public class Pacchista {
    private String nome;
    private Pacco paccoCorrente;

    public Pacchista(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setPaccoCorrente(Pacco pacco) {
        this.paccoCorrente = pacco;
    }

    public Pacco getPaccoCorrente() {
        return paccoCorrente;
    }
}
