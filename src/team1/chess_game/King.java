package team1.chess_game;

public class King extends Piece {

    public King(boolean isWhite) {
        super(1000, isWhite);
    }

    @Override
    public String render() {
        return isWhite ?  "♔" : "♚";
    }

    public void move() {
        // System.out.println("One square");
    }

    @Override
    public boolean isValidMove(Position newPosition) {
        if (!super.isValidMove(position)) {
            return false;
        }

        if (Math.abs(newPosition.getCol() - this.position.getCol()) == 1
                || Math.abs(newPosition.getRow() - this.position.getRow()) == 1
                || Math.abs(newPosition.getCol() - this.position.getCol()) == 1 &&
                Math.abs(newPosition.getRow() - this.position.getRow()) == 1) {
            return true;

        } else {
            return false;
        }
    }
}