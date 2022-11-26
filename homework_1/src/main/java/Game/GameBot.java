package Game;
import Display.Cell;
import Display.Field;
import States.CellState;
import States.GameMode;
import States.PlayerTurn;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import static java.lang.Math.max;

public class GameBot extends GameWith {
    public GameBot() {
        field = new Field(GameMode.withBot);
        while (!somebodyWin) {
            switchMove();
        }
        endOfTheGame();
    }

    @Override
    protected void switchMove() {
        if (playerTurn == PlayerTurn.first) {
            infoOutput.firstPlayerMoveInfo();
            playerMove();
            playerTurn = PlayerTurn.bot;
        } else {
            botMove();
            playerTurn = PlayerTurn.first;
        }
        updateScores();
    }

    private void playerMove() {
        Set<Cell> ableMoves = findAbleMoves(PlayerTurn.first, PlayerTurn.bot);
        ableMoves.forEach(x -> x.isRecommend = true);
        System.out.println(field);
        if (!ableMoves.isEmpty()) {
            noMoves = 0;
            infoOutput.ableMovesOutput(ableMoves);
        } else {
            infoOutput.noAbleMoves();
            if (++noMoves == 2) {
                somebodyWin = true;
            }
            return;
        }
        Cell cell = field.parsePosition(readMove(ableMoves));
        moveWithCell(cell, CellState.firstPlayer, CellState.bot);
    }

    private void botMove() {
        List<Cell> ableMoves = new ArrayList<>(findAbleMoves(PlayerTurn.bot, PlayerTurn.first));
        if (!ableMoves.isEmpty()) {
            noMoves = 0;
        } else {
            infoOutput.noAbleMoves();
            if (++noMoves == 2) {
                somebodyWin = true;
            }
            return;
        }
        Cell bestCell = field.findBestCell(ableMoves);
        moveWithCell(bestCell, CellState.bot, CellState.firstPlayer);
        infoOutput.botMoveInfo(bestCell);
    }

    private void moveWithCell(Cell cell, CellState thisMove, CellState nextMove) {
        for (var i : field.checkForMoves(cell, thisMove, nextMove).entrySet()) {
            if (i.getValue()) {
                field.restoreCell(cell, i.getKey(), thisMove, nextMove);
            }
        }
        cell.state = thisMove;
    }
    @Override
    protected void endOfTheGame() {
        if (isBotWined) {
            GameSet.bestScore = max(firstPlayerScore, botScore);
            infoOutput.botCongratulations();
            GameSet.bestPlayer = "Бот";
        } else {
            GameSet.bestScore = max(firstPlayerScore, botScore);
            infoOutput.congratulations("игрок");
            GameSet.bestPlayer = scanner.nextLine();
        }
    }
}
