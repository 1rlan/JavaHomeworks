package Game;
import Display.Cell;
import Display.Field;
import States.CellState;
import States.GameMode;
import States.PlayerTurn;
import java.util.*;
import static java.lang.Math.max;

public class GamePlayers extends GameWith {
    public GamePlayers() {
        field = new Field(GameMode.withPlayer);
        while (!somebodyWin) {
            switchMove();
        }
        endOfTheGame();
    }

    @Override
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

    private void playerMove(PlayerTurn playerTurn) {
        Set<Cell> ableMoves = findAbleMoves(playerTurn, playerTurn == PlayerTurn.first ? PlayerTurn.second : PlayerTurn.first);
        ableMoves.forEach(x -> x.isRecommend = true);
        System.out.println(field);
        if (!ableMoves.isEmpty()) {
            infoOutput.ableMovesOutput(ableMoves);
            noMoves = 0;
        } else {
            infoOutput.noAbleMoves();
            if (++noMoves == 2) {
                somebodyWin = true;
            }
            return;
        }
        Cell cell = field.parsePosition(readMove(ableMoves));
        moveWithCell(cell, playerTurn == PlayerTurn.first ? CellState.firstPlayer : CellState.secondPlayer,
                           playerTurn == PlayerTurn.first ? CellState.secondPlayer : CellState.firstPlayer);
    }

    private void moveWithCell(Cell cell, CellState thisMove, CellState nextMove){
        for (var i : field.checkForMoves(cell, thisMove, nextMove).entrySet()) {
            if (i.getValue()) {
                field.restoreCell(cell, i.getKey(), thisMove, nextMove);
            }
        }
        cell.state = thisMove;
    }

    @Override
    void endOfTheGame() {
        GameSet.bestScore = max(firstPlayerScore, secondPlayerScore);
        infoOutput.congratulations(firstPlayerScore >= secondPlayerScore ? "первый игрок" : "второй игрок");
        GameSet.bestPlayer = scanner.nextLine();
    }
}

