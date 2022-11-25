package org.example.game;

import org.example.display.Cell;
import org.example.enums.CellState;
import org.example.display.Field;
import org.example.display.InfoOutput;
import org.example.enums.PlayerTurn;

import java.util.*;

public abstract class Game {
    protected Scanner scanner = new Scanner(System.in);
    protected InfoOutput infoOutput = new InfoOutput();
    protected PlayerTurn playerTurn = PlayerTurn.first;
    protected boolean somebodyWin = false;
    boolean isBotWined = false;
    protected Field field;
    protected int firstPlayerScore = 0;
    protected int secondPlayerScore = 0;
    protected int botScore = 0;

    protected void updateScores() {
        firstPlayerScore = Arrays.stream(field.field).map(x -> Arrays.stream(x)
                .filter(y -> y.state == CellState.firstPlayer).count()).reduce(0L, Long::sum).intValue();
        secondPlayerScore = Arrays.stream(field.field).map(x -> Arrays.stream(x)
                .filter(y -> y.state == CellState.secondPlayer).count()).reduce(0L, Long::sum).intValue();
        botScore = Arrays.stream(field.field).map(x -> Arrays.stream(x)
                .filter(y -> y.state == CellState.bot).count()).reduce(0L, Long::sum).intValue();
        somebodyWin = firstPlayerScore + secondPlayerScore + botScore == 64;
        isBotWined = botScore > firstPlayerScore;
    }

    List<String> findAbleMoves(PlayerTurn playerTurn) {
        Set<String> positions = new HashSet<>();
        for (Cell[] row : field.field) {
            for (Cell cell : row) {
                if (playerTurn == PlayerTurn.first) {
                    if (cell.state == CellState.secondPlayer) {
                        addNear(cell, positions, CellState.firstPlayer, CellState.secondPlayer);
                    }
                } else if (playerTurn == PlayerTurn.second) {
                    if (cell.state == CellState.firstPlayer) {
                        addNear(cell, positions, CellState.secondPlayer, CellState.firstPlayer);
                    }
                } else if (playerTurn == PlayerTurn.bot) {
                    if (cell.state == CellState.firstPlayer) {
                        addNear(cell, positions, CellState.bot, CellState.firstPlayer);
                    }
                }
            }
        }
        return positions.stream().toList();
    }

    void addNear(Cell cell, Set<String> positions, CellState baseState, CellState findForClose) {
        int xPos = cell.xPos;
        int yPos = cell.yPos;

        for (int i = -1; i < 2; ++i) {
            for (int j = -1; j < 2; ++j) {
                if (i == 0 && j == 0) {
                    continue;
                }
                if (field.returnAtPos(xPos + i, yPos + j).state == CellState.free) {
                    if (field.isOneMove(field.returnAtPos(xPos + i, yPos + j), baseState, findForClose)) {
                        positions.add(field.returnAtPos(xPos + i, yPos + j).positionInfo());
                    }
                }
            }
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
    abstract void switchMove();

    abstract void endOfTheGame();
}
