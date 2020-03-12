package team1.chess_game;

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

    @Override
    public boolean isValidMove(Position newPosition) {
        if (!super.isValidMove(position)) {
            return false;
        }
        if ((this.position.getCol() == newPosition.getCol() || this.position.getRow() == newPosition.getRow()) ||
        (Math.abs(newPosition.getCol() - this.position.getCol()) == Math.abs(newPosition.getRow() - this.position.getRow()))) {
        return true;
        } else {
            return false;
        }
    }
}
