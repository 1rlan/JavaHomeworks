package org.example.game;
import org.example.*;
import org.example.display.Cell;
import org.example.display.Field;
import org.example.enums.CellState;
import org.example.enums.GameMode;
import org.example.enums.PlayerTurn;

import java.util.*;
import static java.lang.Math.max;

public class GamePlayers extends Game {
    public GamePlayers() {
        field = new Field(GameMode.withPlayer);
        while (!somebodyWin) {
            switchMove();
        }
        endOfTheGame();
    }

    protected void switchMove() {
        if (playerTurn == PlayerTurn.first) {
            infoOutput.firstPlayerMoveInfo();
            playerMove(PlayerTurn.first);
            playerTurn = PlayerTurn.second;
        } else {
            infoOutput.secondPlayerMoveInfo();
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

    @Override
    void endOfTheGame() {
        SetGame.bestScore = max(firstPlayerScore, secondPlayerScore);
        infoOutput.congratulations();
        SetGame.bestPlayer = scanner.nextLine();
    }
}

