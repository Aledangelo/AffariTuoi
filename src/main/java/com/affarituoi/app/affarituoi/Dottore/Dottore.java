package com.affarituoi.app.affarituoi.Dottore;

import com.affarituoi.app.affarituoi.Offerta.Offerta;
import com.affarituoi.app.affarituoi.Pacco.Pacco;

import java.util.ArrayList;
import java.util.List;

public class Dottore {
    private List<Pacco> listaPacchi;
    private List<Offerta> storicoOfferte;

    public Dottore(List<Pacco> listaPacchi) {
        this.listaPacchi = listaPacchi;
        this.storicoOfferte = new ArrayList<>();
    }

    public List<Offerta> getStoricoOfferte() {
        return storicoOfferte;
    }

    public List<Pacco> getListaPacchi() {
        return listaPacchi;
    }

    public void setListaPacchi(List<Pacco> listaPacchi) {
        this.listaPacchi = listaPacchi;
    }

    public void setStoricoOfferte(List<Offerta> storicoOfferte) {
        this.storicoOfferte = storicoOfferte;
    }

    private int calculateAverage() {
        // Al momento faccio una semplice media
        int totale = 0;
        for (Pacco pacco : this.listaPacchi) {
            totale += pacco.getValore();
        }
        float media = (float) totale / this.listaPacchi.size();
        return (int) Math.floor(media);
    }
}
