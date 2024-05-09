package com.affarituoi.app.affarituoi.GameService;

import com.affarituoi.app.affarituoi.Dottore.Dottore;
import com.affarituoi.app.affarituoi.Offerta.Offerta;
import com.affarituoi.app.affarituoi.Pacchista.Pacchista;
import com.affarituoi.app.affarituoi.Pacco.Pacco;
import org.springframework.stereotype.Service;

import java.util.*;

import java.util.concurrent.ThreadLocalRandom;
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
        Collections.shuffle(this.premi);    // Mescola i pacchi per rendere casuale la loro posizione
        pacchi = IntStream.range(0, 20)
                .mapToObj(i -> new Pacco(i + 1, this.premi.get(i)))
                .collect(Collectors.toList());
        Collections.shuffle(pacchi);
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

    public void cambioPacco(int id) {
        Pacco p_old = this.pacchista.getPaccoCorrente();
        p_old.setOpen(false);
        this.pacchi.add(p_old);
        Pacco p = getPaccoById(id);
        this.pacchi.remove(p);
        this.pacchista.setPaccoCorrente(p);
    }

    public Pacchista getPacchista() {
        return pacchista;
    }

    public boolean openNext(Scanner scanner) {
        int scelta = 0;
        System.out.print("Inserisci il prossimo pacco da aprire: ");
        try {
            scelta = scanner.nextInt();
        } catch (InputMismatchException e) {
            // System.out.println("Input non valido. Per favore inserisci un numero intero.");
            scelta = -1;
            scanner.nextLine();
        }

        Pacco p = scegliPacco(scelta);
        if (p != null) {
            System.out.println("Hai aperto il pacco " + p.getID() + " con un valore di €" + p.getValore());
            return true;
        }
        else {
            System.out.println("Il pacco selezionato non è valido");
            return false;
        }
    }

    public boolean manageOffer(Dottore dottore, List<Pacco> pacchiNonAperti, Scanner scanner) {
        Offerta o = dottore.doOffer(pacchiNonAperti);
        if (o.getType().equals("Cambio")) {
            System.out.println("Offerta attuale: " + o.getType());
            System.out.print("Accetti l'offerta? [s/N] ");
            scanner.nextLine();
            String chsn = scanner.nextLine();
            if (chsn.equals("s") || chsn.equals("S")) {
                System.out.print("Inserisci l'ID del pacco che vuoi prendere: ");
                int id = scanner.nextInt();
                cambioPacco(id);
                // pacchiNonAperti = gameService.getClosedPacchi();
                return true;
            }
        } else {
            System.out.println("Offerta attuale: €" + o.getValue());
            System.out.print("Accetti l'offerta? [s/N] ");
            scanner.nextLine();
            String chsn = scanner.nextLine();
            if (chsn.equals("s") || chsn.equals("S")) {
                System.out.println("Hai accettato €" + o.getValue() + ". Il gioco termina qui!");
                // Qui deve uscire dal ciclo
                return false;
            }
        }
        return true;
    }

    public void welcome(Scanner scanner) {
        System.out.println("Benvenuto a Affari Tuoi!");
        System.out.print("Inserisci il tuo nome: ");
        String nome = scanner.nextLine();
        setPacchista(nome);

        System.out.println(nome + ", per iniziare ti verrà assegnato un pacco casuale");
        int my_index = ThreadLocalRandom.current().nextInt(1, 20);
        Pacco paccoiniziale = scegliPacco(my_index);
        assegnaPacco(paccoiniziale);
        System.out.println("Ti è stato assegnato il pacco " + paccoiniziale.getID() + "\n");
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
