package com.affarituoi;

import com.affarituoi.app.affarituoi.Dottore.Dottore;
import com.affarituoi.app.affarituoi.GameService.GameService;
import com.affarituoi.app.affarituoi.Pacco.Pacco;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


@SpringBootApplication
public class AffariTuoiApplication implements CommandLineRunner {

	private final GameService gameService;
	private final Dottore dottore;

	public AffariTuoiApplication(GameService gameService, Dottore dottore) {
		this.gameService = gameService;
		this.dottore = dottore;
	}

	public static void main(String[] args) {
		SpringApplication.run(AffariTuoiApplication.class, args);
	}

	@Override
	public void run(String... args) {
		List<Integer> turni = Arrays.asList(6, 9, 12, 15, 18, 19);	// Turni in cui va effettuata un'offerta

		try (Scanner scanner = new Scanner(System.in)) {
			gameService.welcome(scanner);

			int cnt = 0;
			List<Pacco> pacchiNonAperti = gameService.getClosedPacchi();
			List<Integer> premiRimanenti = gameService.getPremi();
			while (true) {
				if (turni.contains(cnt)) {
					if (!gameService.manageOffer(dottore, pacchiNonAperti, scanner))
						break;
				}
				
				cnt++;
				pacchiNonAperti = gameService.getClosedPacchi();
				premiRimanenti = gameService.getPremi();

				if (pacchiNonAperti.isEmpty()) {
					System.out.println("Congratulazioni " + gameService.getPacchista().getNome() + ", hai vinto €" + gameService.getPaccoScelto().getValore());
					break;
				}

				System.out.println("\nTurno: " + cnt);
				System.out.println("Pacco: " + gameService.getPaccoScelto().getID() + "\n");
				System.out.println("Pacchi non aperti:");


				for (int i = 0; i < premiRimanenti.size(); i++) {
					try {
						System.out.println("€" + premiRimanenti.get(i) + "\t\t\t\tPacco: " + pacchiNonAperti.get(i).getID());
					} catch (IndexOutOfBoundsException e) {
						System.out.println("€" + premiRimanenti.get(i));
					}

				}

				while(!gameService.openNext(scanner));
			}
		}
	}
}
