package lab3;

public abstract class Piece {
    private int value;
    boolean isWhite;

    public Piece(int value, boolean isWhite) {
        this.isWhite = isWhite;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public abstract void move();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Piece)) return false;
        return this.value == ((Piece) o).value &&
                this.isWhite == ((Piece) o).isWhite;
    }
}
