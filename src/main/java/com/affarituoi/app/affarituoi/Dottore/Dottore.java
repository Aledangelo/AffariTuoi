package com.affarituoi.app.affarituoi.Dottore;

import com.affarituoi.app.affarituoi.Offerta.Offerta;
import com.affarituoi.app.affarituoi.Pacco.Pacco;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class Dottore {
    private List<Offerta> storicoOfferte;

    public Dottore(List<Pacco> listaPacchi) {
        this.storicoOfferte = new ArrayList<>();
    }

    public List<Offerta> getStoricoOfferte() {
        return storicoOfferte;
    }

    public void setStoricoOfferte(List<Offerta> storicoOfferte) {
        this.storicoOfferte = storicoOfferte;
    }

    private int calculateAverage(List<Pacco> listaPacchi) {
        int totale = 0;
        for (Pacco pacco : listaPacchi) {
            totale += pacco.getValore();
        }
        float media = (float) totale / listaPacchi.size();
        return (int) Math.floor(media);
    }

    public Offerta doOffer(List<Pacco> listaPacchi) {
        int p = ThreadLocalRandom.current().nextInt(1, 101);
        Offerta offerta = new Offerta("", 0);
        if (p >= 70) {
            offerta.setType("Cambio");
        } else {
            int valore_offerta = calculateAverage(listaPacchi);
            // Modifica un po' questo valore in base a altri parametri
            offerta.setType("Denaro");
            offerta.setValue((int) (valore_offerta / 1000) * 1000);
        }
        return offerta;
    }
}
