package org.example;

import java.util.Scanner;

public final class GameSet {
    private final InfoOutput rules = new InfoOutput();
    private final Scanner scanner = new Scanner(System.in);

    public static int bestScore = 0;
    public static String bestPlayer;


    public void prepareGame() {
        rules.programStart();
        rules.showCommands();
        switchMode(scanner.nextLine());
    }

    private void switchMode(String mode) {
        switch (mode) {
            case "/bot":
                new GameBot();
            case "/player":
                new GamePlayers();
            case "/exit":
                rules.goodbyeWords();
                break;
            default:
                rules.incorrectInput();
                switchMode(scanner.nextLine());
                break;
        }
    }
}
