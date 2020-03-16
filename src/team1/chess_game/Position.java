package team1.chess_game;

public class Position {
    private int row;
    private int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public String toString() {
        int colInt = col + 'a';
        char colLetter = (char)colInt;
        return "(" + colLetter +
                (row + 1) +
                ')';
    }
}