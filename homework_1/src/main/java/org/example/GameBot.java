package org.example;

import java.util.List;

import static java.lang.Math.max;

public class GameBot extends Game {
    public GameBot() {
        field = new Field(GameMode.withPlayer);
        while (!somebodyWin) {
            switchMove();
        }
        endOfTheGame();
    }

    protected void switchMove() {
        if (playerTurn == PlayerTurn.first) {
            infoOutput.firstPlayerMoveInfo();
            playerMove();
            playerTurn = PlayerTurn.bot;
        } else {
            infoOutput.botMoveInfo();
            botMove();
            playerTurn = PlayerTurn.first;
        }
        updateScores();
    }

    void playerMove() {
        System.out.println(field);
        List<String> ableMoves = findAbleMoves(PlayerTurn.first);
        infoOutput.ableMovesOutput(ableMoves);
         Cell cell = field.parsePosition(readMove(ableMoves));
         for (var i : field.checkForMoves(cell, CellState.firstPlayer, CellState.bot).entrySet()) {
             if (i.getValue()) {
                 field.restoreCell(cell, i.getKey(), CellState.firstPlayer, CellState.bot);
             }
         }
         cell.state = CellState.firstPlayer;
    }

    void botMove() {
        System.out.println(field);
        List<Cell> ableMoves = (List<Cell>)findAbleMoves(PlayerTurn.bot).stream().map(x -> field.parsePosition(x));
//        Cell bestCell = field.findBestMove(ableMoves);
//        for (var i : field.checkForMoves(bestCell, CellState.firstPlayer, CellState.bot).entrySet()) {
//            if (i.getValue()) {
//                field.restoreCell(bestCell, i.getKey(), CellState.firstPlayer, CellState.bot);
//            }
//        }
//        bestCell.state = CellState.firstPlayer;
    }

    @Override
    void endOfTheGame() {
        if (isBotWined) {
            GameSet.bestScore = max(firstPlayerScore, botScore);
            infoOutput.botCongratulations();
            GameSet.bestPlayer = "Бот";
        } else {
            GameSet.bestScore = max(firstPlayerScore, secondPlayerScore);
            infoOutput.congratulations();
            GameSet.bestPlayer = scanner.nextLine();
        }
    }
}
