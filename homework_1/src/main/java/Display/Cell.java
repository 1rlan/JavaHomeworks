package Display;
import States.CellState;

public final class Cell {
    public CellState state;
    public boolean isRecommend = false;
    public int xPos() {
        return x;
    }
    public int yPos() {
        return y;
    }
    private int x;
    private int y;
    private final int sidePosition;

    public Cell(CellState state, int xPos, int yPos, int position) {
        this.state = state;
        this.sidePosition = position;
        this.x = xPos;
        this.y = yPos;
    }

    public Cell(CellState state, int xPos, int yPos) {
        this(state, xPos, yPos, 0);
    }

    public int countSS() {
        if ((x == 1 && y == 1) || (x == 1 && y == 8) || (x == 8 && y == 1) || (x == 8 && y == 8)) {
            return 8;
        }
        if (x == 1 || y == 1 || x == 8 || y == 8) {
            return 4;
        }
        return 0;
    }
    public int countS() {
        if (x == 1 || y == 1 || y == 8 || x == 8) {
            return 20;
        }
        return 10;
    }

    @Override
    public String toString() {
        String returnValue = "";
        switch (state) {
            case corner -> returnValue = " ";
            case bot -> returnValue = Colors.ANSI_PURPLE + "■" + Colors.ANSI_RESET;
            case firstPlayer -> returnValue = Colors.ANSI_RED + "■" + Colors.ANSI_RESET;
            case secondPlayer -> returnValue = Colors.ANSI_GREEN + "■" + Colors.ANSI_RESET;
            case numberPosition ->   returnValue = String.valueOf(sidePosition);
            case letterPosition -> returnValue = String.valueOf((char)('A' + sidePosition - 1));
            case free -> returnValue = (isRecommend ? Colors.ANSI_WHITE + "0" + Colors.ANSI_RESET : "0");
        }
        isRecommend = false;
        return returnValue;
    }

    public String positionInfo() {
        return String.format("%s%s", x, (char)('A' + y - 1));
    }
}
