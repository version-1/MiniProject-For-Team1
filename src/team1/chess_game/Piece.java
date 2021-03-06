package team1.chess_game;

public abstract class Piece {
    private int value;
    boolean isWhite;
    protected Position position;
    private boolean jump = false;

    public Piece(int value, boolean isWhite) {
        this.isWhite = isWhite;
        this.value = value;
    }

    public Piece(int value, boolean isWhite, Position position) {
        this.value = value;
        this.isWhite = isWhite;
        this.position = position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public abstract String render();

    public int getValue() {
        return value;
    }

    public boolean getJump() {
        return jump;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public abstract void move();

    public boolean isValidMove(Position newPosition) {
        if (newPosition.getRow() >= 0 && newPosition.getCol() >= 0 && newPosition.getRow() < 8
                && newPosition.getCol() < 8) {
            return true;
        } else {
            return false;
        }
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Piece))
            return false;
        return this.value == ((Piece) o).value && this.isWhite == ((Piece) o).isWhite;
    }
}
