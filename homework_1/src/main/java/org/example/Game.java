package org.example;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public abstract class Game {
    protected Scanner scanner = new Scanner(System.in);
    protected InfoOutput infoOutput = new InfoOutput();
    protected PlayerTurn playerTurn = PlayerTurn.first;
    protected boolean somebodyWin = false;
    protected Field field;
    protected int firstPlayerScore = 0;
    protected int secondPlayerScore = 0;
    protected int botScore = 0;

    protected void updateScores() {
        firstPlayerScore = Arrays.stream(field.field).map(x -> Arrays.stream(x)
                .filter(y -> y.state == CellState.firstPlayer).count()).reduce(0L, (a, b) -> a + b).intValue();
        secondPlayerScore = Arrays.stream(field.field).map(x -> Arrays.stream(x)
                .filter(y -> y.state == CellState.secondPlayer).count()).reduce(0L, (a, b) -> a + b).intValue();
        botScore = Arrays.stream(field.field).map(x -> Arrays.stream(x)
                .filter(y -> y.state == CellState.bot).count()).reduce(0L, (a, b) -> a + b).intValue();
        if (firstPlayerScore + secondPlayerScore + botScore == 64) {
            somebodyWin = true;
        }
    }

    protected String readMove(List<String> ableMoves) {
        String position = scanner.nextLine();
        if (!ableMoves.contains(position)) {
            infoOutput.incorrectInput();
            return readMove(ableMoves);
        }
        return position;
    }
}
