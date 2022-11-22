package org.example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public final class Field {
    private final int fieldDimension = 10;
    private final static HashMap<Character, Integer> letterToInt = new HashMap<Character, Integer>(
            Map.of('A', 1, 'B', 2, 'C', 3, 'D', 4, 'E', 6, 'F', 7, 'H', 8)
    );
    public Cell[][] field = new Cell[fieldDimension][fieldDimension];

    Cell parsePosition(String position) {
        return returnAtPos(position.charAt(0), Character.getNumericValue(position.charAt(1)));
    }
    public GameMode gameMode;

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

    public Cell returnAtPos(int i, int j) {
        return field[i][j];
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
//        field[5][5] = new Cell(CellState.secondPlayer, 5, 5);
//        field[5][4] = new Cell(CellState.secondPlayer, 5, 4);
//        field[5][3] = new Cell(CellState.secondPlayer, 5, 3);
//
//        field[4][5] = new Cell(CellState.firstPlayer, 4, 5);
//        field[4][4] = new Cell(CellState.firstPlayer, 4, 4);
//        field[3][4] = new Cell(CellState.firstPlayer, 3, 4);
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

    public Map<Direction, Boolean> checkForMoves(Cell cell, CellState base, CellState findForClose) {
        return Map.of(
               Direction.Up, checkUp(returnAtPos(cell.xPos, cell.yPos - 1), base, findForClose, false),
               Direction.Down, checkDown(returnAtPos(cell.xPos, cell.yPos + 1), base, findForClose, false),
               Direction.Left, checkLeft(returnAtPos(cell.xPos - 1, cell.yPos), base, findForClose, false),
               Direction.Right, checkRight(returnAtPos(cell.xPos + 1, cell.yPos), base, findForClose, false),
               Direction.LeftUp, checkLeftUp(returnAtPos(cell.xPos - 1, cell.yPos - 1), base, findForClose, false),
               Direction.RightUp, checkRightUp(returnAtPos(cell.xPos + 1, cell.yPos - 1), base, findForClose, false),
               Direction.LeftDown, checkLeftDown(returnAtPos(cell.xPos - 1, cell.yPos + 1), base, findForClose, false),
               Direction.RightDown, checkRightDown(returnAtPos(cell.xPos + 1, cell.yPos + 1), base, findForClose, false)
        );

    }

    public boolean checkLeft(Cell cell, CellState base, CellState findForClose, boolean canReturn) {
        if (cell.state != base && cell.state != findForClose) {
            return false;
        }
        if (cell.state == findForClose) {
            return checkLeft(returnAtPos(cell.xPos - 1, cell.yPos), base, findForClose, true);
        }
        else if (cell.state == base && canReturn) {
            return true;
        }
        return false;
    }

    public boolean checkRight(Cell cell, CellState base, CellState findForClose, boolean canReturn) {
        if (cell.state != base && cell.state != findForClose) {
            return false;
        }
        if (cell.state == findForClose) {
            return checkRight(returnAtPos(cell.xPos + 1, cell.yPos), base, findForClose, true);
        }
        else if (cell.state == base && canReturn) {
            return true;
        }
        return false;
    }

    public boolean checkUp(Cell cell, CellState base, CellState findForClose, boolean canReturn) {
        if (cell.state != base && cell.state != findForClose) {
            return false;
        }
        if (cell.state == findForClose) {
            return checkUp(returnAtPos(cell.xPos, cell.yPos - 1), base, findForClose, true);
        }
        else if (cell.state == base && canReturn) {
            return true;
        }
        return false;
    }

    public boolean checkDown(Cell cell, CellState base, CellState findForClose, boolean canReturn) {
        if (cell.state != base && cell.state != findForClose) {
            return false;
        }
        if (cell.state == findForClose) {
            return checkDown(returnAtPos(cell.xPos, cell.yPos + 1), base, findForClose, true);
        }
        else if (cell.state == base && canReturn) {
            return true;
        }
        return false;
    }

    public boolean checkLeftUp(Cell cell, CellState base, CellState findForClose, boolean canReturn) {
        if (cell.state != base && cell.state != findForClose) {
            return false;
        }
        if (cell.state == findForClose) {
            return checkLeftUp(returnAtPos(cell.xPos - 1, cell.yPos - 1), base, findForClose, true);
        }
        else if (cell.state == base && canReturn) {
            return true;
        }
        return false;
    }

    public boolean checkRightUp(Cell cell, CellState base, CellState findForClose, boolean canReturn) {
        if (cell.state != base && cell.state != findForClose) {
            return false;
        }
        if (cell.state == findForClose) {
            return checkRightUp(returnAtPos(cell.xPos + 1, cell.yPos - 1), base, findForClose, true);
        }
        else if (cell.state == base && canReturn) {
            return true;
        }
        return false;
    }

    public boolean checkLeftDown(Cell cell, CellState base, CellState findForClose, boolean canReturn) {
        if (cell.state != base && cell.state != findForClose) {
            return false;
        }
        if (cell.state == findForClose) {
            return checkLeftDown(returnAtPos(cell.xPos - 1, cell.yPos + 1), base, findForClose, true);
        }
        else if (cell.state == base && canReturn) {
            return true;
        }
        return false;
    }

    public boolean checkRightDown(Cell cell, CellState base, CellState findForClose, boolean canReturn) {
        if (cell.state != base && cell.state != findForClose) {
            return false;
        }
        if (cell.state == findForClose) {
            return checkRightDown(returnAtPos(cell.xPos + 1, cell.yPos + 1), base, findForClose, true);
        }
        else if (cell.state == base && canReturn) {
            return true;
        }
        return false;
    }


//    public void setLeft(Cell cell, CellState base, CellState findToReplace) {
//        if (cell.state == findToReplace) {
//            cell.state = base;
//            setLeft(returnAtPos(cell.xPos - 1, cell.xPos), base, findToReplace);
//        }
//    }

    @Override
    public String toString() {
        return Arrays.stream(field).map(row -> Arrays.stream(row).map(cell -> cell.toString())
                .collect(Collectors.joining(" ")))
                .collect(Collectors.joining("\n"));
    }
}





