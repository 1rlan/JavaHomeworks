package org.example.display;

import org.example.enums.CellState;

public final class Cell {
    public CellState state;
    private int position;
    public int xPos;
    public int yPos;

    public Cell(CellState state) {
        this.state = state;
    }

    public Cell(CellState state, int xPos, int yPos, int position) {
        this.state = state;
        this.xPos = xPos;
        this.yPos = yPos;
        this.position = position;
    }

    public Cell(CellState state, int xPos, int yPos) {
        this.state = state;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public String positionInfo() {
        return String.format("%s%s", xPos, (char)('A' + yPos - 1));
    }

    @Override
    public String toString() {
        switch (state) {
            case free:
                return "0";
            case corner:
                return " ";
            case bot:
                return Colors.ANSI_PURPLE + "■" + Colors.ANSI_RESET;
            case firstPlayer:
                return  Colors.ANSI_RED + "■" + Colors.ANSI_RESET;
            case secondPlayer:
                return Colors.ANSI_GREEN + "■" + Colors.ANSI_RESET;
            case numberPosition:
                return String.valueOf(position);
            case letterPosition:
                return String.valueOf((char)('A' + position - 1));
            default:
                return "?";
        }
    }
}
