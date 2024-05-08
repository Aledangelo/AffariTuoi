package com.affarituoi.app.affarituoi.GameService;

import com.affarituoi.app.affarituoi.Pacchista.Pacchista;
import com.affarituoi.app.affarituoi.Pacco.Pacco;
import org.springframework.stereotype.Service;

import java.util.*;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class GameService {
    private List<Pacco> pacchi;
    private Pacchista pacchista;
    private List<Integer> premi;

    public GameService() {
        this.premi = Arrays.asList(0, 1, 5, 10, 20, 50, 75, 100, 200, 500, 5000, 10000, 15000, 20000, 30000, 50000, 75000, 100000, 200000, 300000);
        initGame();
    }

    private void initGame() {
        Collections.shuffle(this.premi);
        pacchi = IntStream.range(0, 20)
                .mapToObj(i -> new Pacco(i, this.premi.get(i)))
                .collect(Collectors.toList());
        Collections.shuffle(pacchi); // Mescola i pacchi per rendere casuale la loro posizione
    }

    public void setPacchista(String nome) {
        this.pacchista = new Pacchista(nome);
    }

    public Pacco getPaccoScelto() {
        return this.pacchista.getPaccoCorrente();
    }

    public void assegnaPacco(Pacco pacco) {
        this.pacchista.setPaccoCorrente(pacco);
    }

    public Pacco cambioPacco(int id) {
        Pacco p_old = this.pacchista.getPaccoCorrente();
        p_old.setOpen(false);
        this.pacchi.add(p_old);
        Pacco p = getPaccoById(id);
        this.pacchi.remove(p);
        this.pacchista.setPaccoCorrente(p);
        return p;
    }

    public Pacchista getPacchista() {
        return pacchista;
    }

    public Pacco scegliPacco(int id) {
        Pacco pacco = getPaccoById(id);
        if (pacco == null || pacco.isOpen()) {
            return null;
        }
        pacco.setOpen(true);
        return pacco;
    }

    private Pacco getPaccoById(int id) {
        Optional<Pacco> pacco = this.pacchi.stream()
                .filter(p -> p.getID() == id)
                .findFirst();
        return pacco.orElse(null); // Ritorna null se nessun pacco corrisponde all'ID fornito
    }

    public List<Pacco> getClosedPacchi() {
        return this.pacchi.stream()
                .filter(pacco -> !pacco.isOpen())
                .collect(Collectors.toList());
    }
}
