package team1.chess_game;

public class Knight extends Piece {

    public Knight(boolean isWhite) {
        super(2, isWhite);
    }

    @Override
    public String render() {
        return isWhite ? "♘" : "♞";
    }

    @Override
    public String toString() {
        return "Knight{value=’" + getValue() + "\'" + "}";
    }

    @Override
    public void move() {
        System.out.println("Like an L");
    }

    public boolean isValidMove(Position newPosition) {
        if (!super.isValidMove(newPosition)) {
            return false;
        }
        int horizontalDistance = Math.abs(newPosition.getCol() - this.position.getCol());
        int verticalDistance = Math.abs(newPosition.getRow() - this.position.getRow());
        if (horizontalDistance * verticalDistance == 2 ) {
            return true;
        } else {
            return false;
        }
    }
}
