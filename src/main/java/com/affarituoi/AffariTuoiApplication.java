package com.affarituoi.app.affarituoi;

import com.affarituoi.app.affarituoi.GameService.GameService;
import com.affarituoi.app.affarituoi.Pacco.Pacco;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

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

			boolean continua = true;
			while (continua) {
				System.out.println("Scegli un'opzione:");
				System.out.println("1. Scegli un pacco");
				System.out.println("2. Apri il tuo pacco corrente");
				System.out.println("3. Esci");
				int scelta = scanner.nextInt();

				switch (scelta) {
					case 1:
						System.out.print("Scegli l'indice del pacco (0-19): ");
						int indice = scanner.nextInt();
						Pacco paccoScelto = gameService.scegliPacco(indice);
						System.out.println("Hai scelto il pacco all'indice " + indice);
						break;
					case 2:
						if (gameService.getPacchista().getPaccoCorrente() != null) {
							gameService.apriPacco(gameService.getPacchista().getPaccoCorrente());
							System.out.println("Il valore del tuo pacco è: " + gameService.getPacchista().getPaccoCorrente().getValore() + " euro");
						} else {
							System.out.println("Non hai ancora scelto un pacco!");
						}
						break;
					case 3:
						continua = false;
						System.out.println("Grazie per aver giocato!");
						break;
					default:
						System.out.println("Opzione non valida. Riprova.");
						break;
				}
			}
		}
	}
}
