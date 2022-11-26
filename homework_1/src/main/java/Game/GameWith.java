package Game;
import Display.Cell;
import Display.Field;
import States.CellState;
import States.PlayerTurn;
import Display.InfoOutput;
import java.util.*;

public abstract class GameWith {
    protected Scanner scanner = new Scanner(System.in);
    protected InfoOutput infoOutput = new InfoOutput();
    protected PlayerTurn playerTurn = PlayerTurn.first;
    protected boolean somebodyWin = false;
    protected Field field;
    protected int firstPlayerScore = 0;
    protected int secondPlayerScore = 0;
    protected int botScore = 0;
    protected int noMoves = 0;
    protected boolean isBotWined = false;

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

    protected Set<Cell> findAbleMoves(PlayerTurn playerTurn, PlayerTurn playVersus) {
        Set<Cell> positions = new HashSet<>();
        for (var row : field.field) {
            for (var cell : row) {
                checkEnemy(positions, cell, playerTurn, playVersus);
            }
        }
        return positions;
    }

    private void checkEnemy(Set<Cell> positions, Cell cell, PlayerTurn playerTurn, PlayerTurn playVersus) {
        if (playerTurn == PlayerTurn.first) {
            if (cell.state == CellState.bot || cell.state == CellState.secondPlayer) {
                if (playVersus == PlayerTurn.second) {
                    addNear(cell, positions, CellState.firstPlayer, CellState.secondPlayer);
                } else {
                    addNear(cell, positions, CellState.firstPlayer, CellState.bot);
                }
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



    private void addNear(Cell cell, Set<Cell> positions, CellState baseState, CellState findForClose) {
        int xPos = cell.xPos();
        int yPos = cell.yPos();
        for (int i = -1; i < 2; ++i) {
            for (int j = -1; j < 2; ++j) {
                if (i == 0 && j == 0) {
                    continue;
                }
                if (field.returnAtPos(xPos + i, yPos + j).state == CellState.free &&
                        field.isOneMove(field.returnAtPos(xPos + i, yPos + j), baseState, findForClose)) {
                    positions.add(field.returnAtPos(xPos + i, yPos + j));
                }
            }
        }
    }

    protected String readMove(Set<Cell> ableMoves) {
        String position = scanner.nextLine();
        if (!ableMoves.contains(field.parsePosition(position))) {
            infoOutput.incorrectInput();
            return readMove(ableMoves);
        }
        return position;
    }

    abstract void switchMove();

    abstract void endOfTheGame();
}
