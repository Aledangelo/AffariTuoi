package com.affarituoi.app.affarituoi.GameService;

import com.affarituoi.app.affarituoi.Pacchista.Pacchista;
import com.affarituoi.app.affarituoi.Pacco.Pacco;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class GameService {
    private List<Pacco> pacchi;
    private Pacchista pacchista;

    public GameService() {
        initGame();
    }

    private void initGame() {
        pacchi = IntStream.range(0, 20)
                .mapToObj(i -> new Pacco((i + 1) * 5000)) // Esempio semplice di incremento valore
                .collect(Collectors.toList());
        Collections.shuffle(pacchi); // Mescola i pacchi per rendere casuale la loro posizione
    }

    public void setPacchista(String nome) {
        this.pacchista = new Pacchista(nome);
    }

    public Pacchista getPacchista() {
        return pacchista;
    }

    public Pacco scegliPacco(int indice) {
        Pacco pacco = pacchi.get(indice);
        pacchista.setPaccoCorrente(pacco);
        return pacco;
    }

    public void apriPacco(Pacco pacco) {
        pacco.setOpen(true);
    }
}
