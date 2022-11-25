package org.example.display;
import org.example.enums.CellState;
import org.example.enums.Direction;
import org.example.enums.GameMode;

import java.util.List;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Map;


public final class Field {
    private final int fieldDimension = 10;
    private final static HashMap<Character, Integer> letterToInt = new HashMap<>(
            Map.of('A', 1, 'B', 2, 'C', 3, 'D', 4, 'E', 5, 'F', 6, 'G', 7, 'H', 8)
    );
    public Cell[][] field = new Cell[fieldDimension][fieldDimension];

    public Cell parsePosition(String position) {
        System.out.println();
        System.out.println(letterToInt.get(position.charAt(1)));
        return returnAtPos(position.charAt(0) - 48, letterToInt.get(position.charAt(1)));
    }
    public GameMode gameMode;

    public Field(GameMode gameMode) {
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
                Direction.Up, checkUp(returnAtPos(cell.xPos, cell.yPos - 1), base, findForClose, false, false),
                Direction.Down, checkDown(returnAtPos(cell.xPos, cell.yPos + 1), base, findForClose, false, false),
                Direction.Left, checkLeft(returnAtPos(cell.xPos - 1, cell.yPos), base, findForClose, false, false),
                Direction.Right, checkRight(returnAtPos(cell.xPos + 1, cell.yPos), base, findForClose, false, false),
                Direction.LeftUp, checkLeftUp(returnAtPos(cell.xPos - 1, cell.yPos - 1), base, findForClose, false, false),
                Direction.RightUp, checkRightUp(returnAtPos(cell.xPos + 1, cell.yPos - 1), base, findForClose, false, false),
                Direction.LeftDown, checkLeftDown(returnAtPos(cell.xPos - 1, cell.yPos + 1), base, findForClose, false, false),
                Direction.RightDown, checkRightDown(returnAtPos(cell.xPos + 1, cell.yPos + 1), base, findForClose, false, false)
        );
    }

    public void restoreCell(Cell cell, Direction direction, CellState base, CellState findForClose) {
        switch (direction) {
            case Up -> checkUp(returnAtPos(cell.xPos, cell.yPos - 1), base, findForClose, false, true);
            case Down -> checkDown(returnAtPos(cell.xPos, cell.yPos + 1), base, findForClose, false, true);
            case Left -> checkLeft(returnAtPos(cell.xPos - 1, cell.yPos), base, findForClose, false, true);
            case Right -> checkRight(returnAtPos(cell.xPos + 1, cell.yPos), base, findForClose, false, true);
            case LeftUp -> checkLeftUp(returnAtPos(cell.xPos - 1, cell.yPos - 1), base, findForClose, false, true);
            case RightUp -> checkRightUp(returnAtPos(cell.xPos + 1, cell.yPos - 1), base, findForClose, false, true);
            case LeftDown -> checkLeftDown(returnAtPos(cell.xPos - 1, cell.yPos + 1), base, findForClose, false, true);
            case RightDown -> checkRightDown(returnAtPos(cell.xPos + 1, cell.yPos + 1), base, findForClose, false, true);
            default -> throw new IllegalStateException("Unexpected value: " + direction);
        }
    }


    public boolean checkLeft(Cell cell, CellState base, CellState findForClose, boolean canReturn, boolean shouldChange) {
        if (cell.state != base && cell.state != findForClose) {
            return false;
        }
        if (cell.state == findForClose) {
            boolean check = checkLeft(returnAtPos(cell.xPos - 1, cell.yPos), base, findForClose, true, false);
            if (shouldChange) {
                if (check) {
                    cell.state = base;
                }
            }
        }
        return canReturn;
    }

    public boolean checkRight(Cell cell, CellState base, CellState findForClose, boolean canReturn, boolean shouldChange) {
        if (cell.state != base && cell.state != findForClose) {
            return false;
        }
        if (cell.state == findForClose) {
            boolean check = checkRight(returnAtPos(cell.xPos + 1, cell.yPos), base, findForClose, true, false);
            if (shouldChange) {
                if (check) {
                    cell.state = base;
                }
            }
            return check;
        }
        return canReturn;
    }

    public boolean checkUp(Cell cell, CellState base, CellState findForClose, boolean canReturn, boolean shouldChange) {
        if (cell.state != base && cell.state != findForClose) {
            return false;
        }
        if (cell.state == findForClose) {
            boolean check = checkUp(returnAtPos(cell.xPos, cell.yPos - 1), base, findForClose, true, false);
            if (shouldChange) {
                if (check) {
                    cell.state = base;
                }
            }
            return check;
        }
        return canReturn;
    }

    public boolean checkDown(Cell cell, CellState base, CellState findForClose, boolean canReturn, boolean shouldChange) {
        if (cell.state != base && cell.state != findForClose) {
            return false;
        }
        if (cell.state == findForClose) {
            boolean check = checkDown(returnAtPos(cell.xPos, cell.yPos + 1), base, findForClose, true, false);
            if (shouldChange) {
                if (check) {
                    cell.state = base;
                }
            }
            return check;
        }
        return canReturn;
    }

    public boolean checkLeftUp(Cell cell, CellState base, CellState findForClose, boolean canReturn, boolean shouldChange) {
        if (cell.state != base && cell.state != findForClose) {
            return false;
        }
        if (cell.state == findForClose) {
            boolean check = checkLeftUp(returnAtPos(cell.xPos - 1, cell.yPos - 1), base, findForClose, true, false);
            if (shouldChange) {
                if (check) {
                    cell.state = base;
                }
            }
            return check;
        }
        return canReturn;
    }

    public boolean checkRightUp(Cell cell, CellState base, CellState findForClose, boolean canReturn, boolean shouldChange) {
        if (cell.state != base && cell.state != findForClose) {
            return false;
        }
        if (cell.state == findForClose) {
            boolean check = checkRightUp(returnAtPos(cell.xPos + 1, cell.yPos - 1), base, findForClose, true, false);
            if (shouldChange) {
                if (check) {
                    cell.state = base;
                }
            }
            return check;
        }
        return canReturn;
    }

    public boolean checkLeftDown(Cell cell, CellState base, CellState findForClose, boolean canReturn, boolean shouldChange) {
        if (cell.state != base && cell.state != findForClose) {
            return false;
        }
        if (cell.state == findForClose) {
            boolean check = checkLeftDown(returnAtPos(cell.xPos - 1, cell.yPos + 1), base, findForClose, true, false);
            if (shouldChange) {
                if (check) {
                    cell.state = base;
                }
            }
            return check;
        }
        return canReturn;
    }

    public boolean checkRightDown(Cell cell, CellState base, CellState findForClose, boolean canReturn, boolean shouldChange) {
        if (cell.state != base && cell.state != findForClose) {
            return false;
        }
        if (cell.state == findForClose) {
            boolean check = checkRightDown(returnAtPos(cell.xPos + 1, cell.yPos + 1), base, findForClose, true, false);
            if (shouldChange) {
                if (check) {
                    cell.state = base;
                }
            }
            return check;
        }
        return canReturn;
    }

//    public Cell findBestMove(List<Cell> ableMoves) {
//
//    }


    @Override
    public String toString() {
        return Arrays.stream(field).map(row -> Arrays.stream(row).map(cell -> cell.toString())
                        .collect(Collectors.joining(" ")))
                        .collect(Collectors.joining("\n"));
    }
}