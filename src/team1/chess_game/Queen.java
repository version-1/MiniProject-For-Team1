package team1.chess_game;

public class Queen extends Piece {
    private static final int VALUE = 9;


    public Queen(boolean isWhite) {
        super(VALUE, isWhite);
    }

    @Override
    public String render() {
       return isWhite ?  "♕" : "♛";
    }

    public String toString() {
        return "Queen{value=’" + getValue() + "\'" + "}";
    }


    public void move() {
        System.out.println("Like Bishop and rook");
    }

    @Override
    public boolean isValidMove(Position newPosition) {
        if (!super.isValidMove(position)) {
            return false;
        }
        if (newPosition.getCol() == this.position.getCol() && newPosition.getRow() == this.position.getRow()) {
            return false;
        } else return (this.position.getCol() == newPosition.getCol() || this.position.getRow() == newPosition.getRow()) ||
                (Math.abs(newPosition.getCol() - this.position.getCol()) == Math.abs(newPosition.getRow() - this.position.getRow()));
    }
}
