import java.util.Random;
import java.util.Scanner;

public class Game {
    private final Board board;
    private final Player humanPlayer;
    private final Player computerPlayer;
    private boolean isHumanTurn;

    public Game() {
        this.board = new Board(6, 7); // Például 6x7-es tábla
        this.humanPlayer = new Player("Sárga", 'Y');
        this.computerPlayer = new Player("Piros", 'R');
        this.isHumanTurn = true; // A játékot a humán játékos kezdi
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Üdvözöllek a Connect4 játékban!");
        board.display();

        while (true) {
            int column = -1;
            if (isHumanTurn) {
                boolean validInput = false;

                // Felhasználói bemenet feldolgozása
                while (!validInput) {
                    System.out.println("A te lépésed, válassz egy oszlopot (0-6): ");
                    String input = scanner.nextLine().trim(); // teljes sor beolvasása és szóközök eltávolítása

                    // Bemenet darabolása szóközök alapján
                    String[] parts = input.split("\\s+");

                    // Ellenőrizzük, hogy csak egy szám van-e megadva
                    if (parts.length == 1) {
                        try {
                            column = Integer.parseInt(parts[0]); // csak egy számot várunk
                            validInput = true;
                        } catch (NumberFormatException e) {
                            System.out.println("Hibás bemenet, kérjük, csak egy számot adjon meg.");
                        }
                    } else {
                        System.out.println("Több szám lett megadva. Kérjük, egyszerre csak egy oszlopot adjon meg.");
                    }
                }

                // Próbáljuk a korongot az adott oszlopba ejteni
                if (!board.dropDisk(humanPlayer, column)) {
                    System.out.println("Érvénytelen lépés, próbáld újra.");
                    continue;
                }
            } else {
                System.out.println("Gép lépése következik...");
                column = new Random().nextInt(board.getColumns());
                while (!board.dropDisk(computerPlayer, column)) {
                    column = new Random().nextInt(board.getColumns());
                }
            }

            // Játéktér megjelenítése
            board.display();

            // Győzelem ellenőrzése
            if (board.checkWin(isHumanTurn ? humanPlayer : computerPlayer)) {
                System.out.println((isHumanTurn ? "Sárga" : "Piros") + " nyert!");
                break;
            }

            // Körváltás
            isHumanTurn = !isHumanTurn;
        }

        scanner.close();
    }
}
