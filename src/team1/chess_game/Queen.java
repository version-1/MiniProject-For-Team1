package lab3;

public class Queen extends Piece {
    private static final int VALUE = 9;


    public Queen(boolean isWhite) {
        super(VALUE, isWhite);
    }

    @Override
    public String toString() {
        return "Queen{value=’" + getValue() + "\'" + "}";
    }

    @Override
    public void move() {
        System.out.println("Like Bishop and rook");
    }

}
