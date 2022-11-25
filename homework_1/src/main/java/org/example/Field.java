package org.example;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public final class Field {
    public GameMode gameMode;
    private final int fieldDimension = 10;
    private final static HashMap<Character, Integer> letterToInt = new HashMap<Character, Integer>(
            Map.of('A', 1, 'B', 2, 'C', 3, 'D', 4,
                    'E', 5, 'F', 6, 'G', 7, 'H', 8)
    );
    public Cell[][] field = new Cell[fieldDimension][fieldDimension];

    public Cell parsePosition(String position) {
        System.out.println();
        System.out.println(letterToInt.get(position.charAt(1)));
        return returnAtPos(position.charAt(0) - 48, letterToInt.get(position.charAt(1)));
    }


    public Field(GameMode gameMode) {
        this.gameMode = gameMode;
        setField();
        setMiddleCells();
    }

    private void setField() {
        for (int i = 0; i < fieldDimension; ++i) {
            for (int j = 0; j < fieldDimension; ++j) {
                setCellByPostion(i, j);
            }
        }
    }

    public Cell returnAtPos(int x, int y) {
        return field[x][y];
    }

    private void setCellByPostion(int i, int j) {
        if (i == 0 && j == 0 || (i == 0 && j == 9) || (i == 9 && j == 0) || (i == 9 && j == 9)) {
            field[i][j] = new Cell(CellState.corner, i, j);
        } else if (i == 0 || i == 9) {
            field[i][j] = new Cell(CellState.letterPosition, i, j, j);
        } else if (j == 0 || j == 9) {
            field[i][j] = new Cell(CellState.numberPosition, i, j, i);
        } else {
            field[i][j] = new Cell(CellState.free, i , j);
        }
    }

    private void setMiddleCells() {
        field[4][5] = new Cell(CellState.firstPlayer, 4, 5);
        field[5][4] = new Cell(CellState.firstPlayer, 5, 4);
        if (gameMode == GameMode.withPlayer) {
            field[4][4] = new Cell(CellState.secondPlayer, 4, 4);
            field[5][5] = new Cell(CellState.secondPlayer, 5, 5);
        } else {
            field[4][4] = new Cell(CellState.bot, 4, 4);
            field[5][5] = new Cell(CellState.bot, 5, 5);
        }
    }

    public boolean isOneMove(Cell cell, CellState base, CellState findForClose) {
        return checkForMoves(cell, base, findForClose).entrySet().stream().anyMatch(x -> x.getValue());
    }
    public Map<Direction, Boolean> checkForMoves(Cell cell, CellState base, CellState findForClose) {
        return Map.of(
                Direction.Up, checkMove(Direction.Up, returnAtPos(cell.xPos, cell.yPos - 1), base, findForClose, false, false),
                Direction.Down, checkMove(Direction.Down, returnAtPos(cell.xPos, cell.yPos + 1), base, findForClose, false, false),
                Direction.Left, checkMove(Direction.Left, returnAtPos(cell.xPos - 1, cell.yPos), base, findForClose, false, false),
                Direction.Right, checkMove(Direction.Right, returnAtPos(cell.xPos + 1, cell.yPos), base, findForClose, false, false),
                Direction.LeftUp, checkMove(Direction.LeftUp, returnAtPos(cell.xPos - 1, cell.yPos - 1), base, findForClose, false, false),
                Direction.RightUp, checkMove(Direction.RightUp, returnAtPos(cell.xPos + 1, cell.yPos - 1), base, findForClose, false, false),
                Direction.LeftDown, checkMove(Direction.LeftDown, returnAtPos(cell.xPos - 1, cell.yPos + 1), base, findForClose, false, false),
                Direction.RightDown, checkMove(Direction.RightDown, returnAtPos(cell.xPos + 1, cell.yPos + 1), base, findForClose, false, false)
        );
    }

    public void restoreCell(Cell cell, Direction direction, CellState base, CellState findForClose) {
        switch (direction) {
            case Up -> checkMove(Direction.Up, returnAtPos(cell.xPos, cell.yPos - 1), base, findForClose, false, true);
            case Down -> checkMove(Direction.Down, returnAtPos(cell.xPos, cell.yPos + 1), base, findForClose, false, true);
            case Left -> checkMove(Direction.Left, returnAtPos(cell.xPos - 1, cell.yPos), base, findForClose, false, true);
            case Right -> checkMove(Direction.Right, returnAtPos(cell.xPos + 1, cell.yPos), base, findForClose, false, true);
            case LeftUp -> checkMove(Direction.LeftUp, returnAtPos(cell.xPos - 1, cell.yPos - 1), base, findForClose, false, true);
            case RightUp -> checkMove(Direction.RightUp, returnAtPos(cell.xPos + 1, cell.yPos - 1), base, findForClose, false, true);
            case LeftDown -> checkMove(Direction.LeftDown, returnAtPos(cell.xPos - 1, cell.yPos + 1), base, findForClose, false, true);
            case RightDown -> checkMove(Direction.RightDown, returnAtPos(cell.xPos + 1, cell.yPos + 1), base, findForClose, false, true);
        }
    }


    public boolean checkMove(Direction direction, Cell cell, CellState base, CellState findForClose, boolean canReturn, boolean shouldChange) {
        if (cell.state != base && cell.state != findForClose) {
            return false;
        }
        if (cell.state == findForClose) {
            boolean check = false;
            switch (direction) {
                case Up -> check = checkMove(Direction.Up, returnAtPos(cell.xPos, cell.yPos - 1), base, findForClose, true, shouldChange);
                case Down -> check = checkMove(Direction.Down, returnAtPos(cell.xPos, cell.yPos + 1), base, findForClose, true, shouldChange);
                case Left -> check = checkMove(Direction.Left, returnAtPos(cell.xPos - 1, cell.yPos), base, findForClose, true, shouldChange);
                case Right -> check = checkMove(Direction.Right, returnAtPos(cell.xPos + 1, cell.yPos), base, findForClose, true, shouldChange);
                case LeftUp -> check = checkMove(Direction.LeftUp, returnAtPos(cell.xPos - 1, cell.yPos - 1), base, findForClose, true, shouldChange);
                case RightUp -> check = checkMove(Direction.RightUp, returnAtPos(cell.xPos + 1, cell.yPos - 1), base, findForClose, true, shouldChange);
                case LeftDown -> check = checkMove(Direction.LeftDown, returnAtPos(cell.xPos - 1, cell.yPos + 1), base, findForClose, true, shouldChange);
                case RightDown -> check = checkMove(Direction.LeftDown, returnAtPos(cell.xPos + 1, cell.yPos + 1), base, findForClose, true, shouldChange);
            }
            if (shouldChange && check) {
                cell.state = base;
            }
            return check;
        }
        else return canReturn;
    }

    //void findBestMove();

    @Override
    public String toString() {
        return Arrays.stream(field).map(row -> Arrays.stream(row).map(Cell::toString)
                        .collect(Collectors.joining(" ")))
                .collect(Collectors.joining("\n"));
    }
}
