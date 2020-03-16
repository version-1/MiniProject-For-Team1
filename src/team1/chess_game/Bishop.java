package team1.chess_game;

public class Bishop extends Piece {

    public Bishop(boolean isWhite) {
        super(3, isWhite);
    }

    @Override
    public String render() {
       return isWhite ?  "♗" : "♝";
    }

    @Override
    public String toString() {
        return "Bishop{value=’" + getValue() + "\'" + "}";
    }

    @Override
    public void move() {
        System.out.println("Diagonally");
    }

    @Override
    public boolean isValidMove(Position newPosition) {
        if (!super.isValidMove(newPosition)) {
            return false;
        }
        if (newPosition.getCol() == this.position.getCol() && newPosition.getRow() == this.position.getRow()) {
            return false;
        } else return (Math.abs(newPosition.getCol() - this.position.getCol()) == Math
                .abs(newPosition.getRow() - this.position.getRow()));
    }

}
