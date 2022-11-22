package org.example;
import java.util.*;
import static java.lang.Math.max;

public class PlayersGame extends Game {
    public PlayersGame() {
        field = new Field(GameMode.withPlayer);
        while (!somebodyWin) {
            moveWithPlayer();
        }
        endOfTheGame();
    }


    private void moveWithPlayer() {
        if (playerTurn == PlayerTurn.first) {
            playerMove(PlayerTurn.first);
            playerTurn = PlayerTurn.second;
        } else {
            playerMove(PlayerTurn.second);
            playerTurn = PlayerTurn.first;
        }
        updateScores();
    }

    void playerMove(PlayerTurn playerTurn) {
        System.out.println(field);
        List<String> ableMoves = findAbleMoves(playerTurn);
        infoOutput.ableMovesOutput(ableMoves);
        Cell cell = field.parsePosition(readMove(ableMoves));
        CellState thisMove = playerTurn == PlayerTurn.first ? CellState.firstPlayer : CellState.secondPlayer;
        CellState nextMove = playerTurn == PlayerTurn.first ? CellState.secondPlayer : CellState.firstPlayer;
        for (var i : field.checkForMoves(cell, thisMove, nextMove).entrySet()) {
            if (i.getValue()) {
                field.restoreCell(cell, i.getKey(), thisMove, nextMove);
            }
        }
        cell.state = thisMove;
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



    private void endOfTheGame() {
        SetGame.bestScore = max(firstPlayerScore, secondPlayerScore);
        infoOutput.congratulations();
        SetGame.bestPlayer = scanner.nextLine();
    }
}

