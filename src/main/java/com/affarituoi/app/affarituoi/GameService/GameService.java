package com.affarituoi.app.affarituoi.GameService;

import com.affarituoi.app.affarituoi.Pacchista.Pacchista;
import com.affarituoi.app.affarituoi.Pacco.Pacco;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

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
        Random rand = new Random();
        pacchi = IntStream.range(0, 20)
                .mapToObj(i -> new Pacco(i, (rand.nextInt(20) + 1) * 5000)) // Esempio semplice di incremento valore
                .collect(Collectors.toList());
        Collections.shuffle(pacchi); // Mescola i pacchi per rendere casuale la loro posizione
    }

    public void setPacchista(String nome) {
        this.pacchista = new Pacchista(nome);
    }

    public Pacchista getPacchista() {
        return pacchista;
    }
    /*
    public Pacco scegliPacco(int indice) {
        Pacco pacco = pacchi.get(indice);
        pacchista.setPaccoCorrente(pacco);
        return pacco;
    }
    */
    public Pacco scegliPacco(int id) {
        Pacco pacco = getPaccoById(id);
        if (pacco == null || pacco.isOpen()) {
            return null;
        }
        pacco.setOpen(true);
        return pacco;
    }

    private Pacco getPaccoById(int id) {
        Optional<Pacco> pacco = pacchi.stream()
                .filter(p -> p.getID() == id)
                .findFirst();
        return pacco.orElse(null); // Ritorna null se nessun pacco corrisponde all'ID fornito
    }

    public List<Pacco> getClosedPacchi() {
        return pacchi.stream()
                .filter(pacco -> !pacco.isOpen())
                .collect(Collectors.toList());
    }
}
