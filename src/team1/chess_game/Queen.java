package team1.chess_game;

public class Queen extends Piece {
    private static final int VALUE = 9;


    public Queen(boolean isWhite) {
        super(VALUE, isWhite);
    }


    public String toString() {
        return "Queen{value=’" + getValue() + "\'" + "}";
    }


    public void move() {
        System.out.println("Like Bishop and rook");
    }

}
