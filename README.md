# Affari Tuoi

Welcome to the GitHub repository for "Affari Tuoi," a command-line-based simulation of the popular game show. This game is implemented in Java and allows players to experience the thrill of making deals and choosing the right briefcases to maximize their potential earnings.

## Game Description

In "Affari Tuoi," each player starts the game with a choice of numbered briefcases, each containing a predefined amount of money. The objective is to either choose a briefcase and find out the prize inside or make a deal with the banker based on the offers provided throughout the game.

## How to Play

* **Starting the Game**: Upon launching the game, you are presented with a series of briefcases to choose from.
* **Choosing a Briefcase**: Players select one of the briefcases as their own.
* **Game Rounds**: Each round, players choose other briefcases to open, revealing the amount inside, which influences the banker's offer.
* **Banker's Offer**: After each round, the banker will make an offer that you can accept to end the game or decline to continue opening more briefcases.

## Installation

Follow these instructions to set up and start playing "Affari Tuoi":

1. **Clone the Repository**
```
git clone https://github.com/yourusername/affarituoi.git
cd affarituoi
```
2. **Compile the Source Code** (Java JDK should be installed)

```
javac AffariTuoiApplication.java
```

3. **Run the Game**

```
java AffariTuoiApplication
```

---

# Code

* **Game Initialization**: The game initializes by setting up the briefcases and their contents, which are managed in the GameService class:

```java 
public class GameService {
    private List<Pacco> pacchi;
    private Pacchista pacchista;
    private List<Integer> premi;

    public GameService() {
        this.premi = new ArrayList<>(Arrays.asList(0, 1, 5, 10, 20, 50, 75, 100, 200, 500, 5000, 10000, 15000, 20000, 30000, 50000, 75000, 100000, 200000, 300000));
        initGame();
    }

    private void initGame() {
        List<Integer> premi_temp = new ArrayList<>(this.premi);
        Collections.shuffle(premi_temp);    
        pacchi = IntStream.range(0, 20)
                .mapToObj(i -> new Pacco(i + 1, premi_temp.get(i)))
                .collect(Collectors.toList());
        Collections.shuffle(pacchi);
    }
```

* **Handling Player Choice**: Player input is processed in **openNext** function.

```java
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
            this.premi.remove(Integer.valueOf(p.getValore()));
            return true;
        }
        else {
            System.out.println("Il pacco selezionato non è valido");
            return false;
        }
    }
```

* **Handling Banker's offer**: in the **manageOffer** function the choice of offer is managed.

```java
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
```