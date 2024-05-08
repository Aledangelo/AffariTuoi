package com.affarituoi;

import com.affarituoi.app.affarituoi.GameService.GameService;
import com.affarituoi.app.affarituoi.Pacco.Pacco;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
public class AffariTuoiApplication implements CommandLineRunner {

	private final GameService gameService;

	public AffariTuoiApplication(GameService gameService) {
		this.gameService = gameService;
	}

	public static void main(String[] args) {
		SpringApplication.run(AffariTuoiApplication.class, args);
	}

	@Override
	public void run(String... args) {
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.println("Benvenuto a Affari Tuoi!");
			System.out.print("Inserisci il tuo nome: ");
			String nome = scanner.nextLine();
			gameService.setPacchista(nome);

			System.out.println(nome + ", per iniziare ti verrà assegnato un pacco casuale");
			int my_index = ThreadLocalRandom.current().nextInt(1, 20);
			Pacco paccoiniziale = gameService.scegliPacco(my_index);
			System.out.println("Ti è stato assegnato il pacco " + paccoiniziale.getID() + "\n");
			//Thread.sleep(1000);

			int cnt = 0;
			while (true) {
				cnt++;
				List<Pacco> pacchiNonAperti = gameService.getClosedPacchi();

				if (pacchiNonAperti.isEmpty()) {
					System.out.println("Congratulazioni " + nome + ", hai vinto €" + paccoiniziale.getValore());
					break;
				}

				System.out.println("Turno: " + cnt);
				System.out.println("Pacco: " + paccoiniziale.getID());
				System.out.println("Pacchi non aperti:");

				pacchiNonAperti.forEach(p -> System.out.println("Pacco ID: " + p.getID() /* + ", Valore: " + p.getValore() */ ));	// Il valore non va printato
				System.out.print("Inserisci il prossimo pacco da aprire: ");

				int scelta = 0;
				try {
					scelta = scanner.nextInt();
				} catch (InputMismatchException e) {
					// System.out.println("Input non valido. Per favore inserisci un numero intero.");
					scelta = -1;
				}

				Pacco p = gameService.scegliPacco(scelta);
				if (p != null)
					System.out.println("Hai aperto il pacco " + p.getID() + " con un valore di €" + p.getValore());
				else
					System.out.println("Il pacco selezionato non è valido");
			}
		}
	}
}
