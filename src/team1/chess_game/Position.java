package team1.chess_game;

public class Position {
    private int row;
    private int col;

    public Position(int col, int row) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}