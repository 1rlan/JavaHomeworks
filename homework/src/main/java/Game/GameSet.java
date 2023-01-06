package Game;

import Display.InfoOutput;

import java.util.Scanner;

public final class GameSet {
    private final InfoOutput rules = new InfoOutput();
    private final Scanner scanner = new Scanner(System.in);
    public static String bestPlayer;
    public static int bestScore = 0;
    public void prepareGame() {
        rules.programStart();
        rules.showCommands();
        switchMode(scanner.nextLine());
    }

    private void switchMode(String mode) {
        switch (mode) {
            case "/bot":
                new GameBot();
                reloadGame();
            case "/player":
                new GamePlayers();
                reloadGame();
            case "/best":
                rules.bestPlayer();
                reloadGame();
            case "/exit":
                rules.goodbyeWords();
                break;
            default:
                rules.incorrectInput();
                switchMode(scanner.nextLine());
                break;
        }
    }


    private void reloadGame() {
        rules.showCommands();
        switchMode(scanner.nextLine());
    }
}
