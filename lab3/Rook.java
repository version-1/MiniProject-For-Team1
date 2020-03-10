package lab3;

public class Rook extends Piece {

    public Rook(boolean isWhite) {
        super(5, isWhite);
    }

    @Override
    public String toString() {
        return "Rook{value=’" + getValue() + "\'" + "}";
    }

    @Override
    public void move() {
        System.out.println("Horizontally or vertically");
    }

}
