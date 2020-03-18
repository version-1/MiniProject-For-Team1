package team1.chess_game;

public class Rook extends Piece {

    public Rook(boolean isWhite) {
        super(5, isWhite);
    }

    @Override
    public String render() {
       return isWhite ? "♖" : "♜";
    }

    public String toString() {
        return "Rook{value=’" + getValue() + "\'" + "}";
    }

    public void move() {
        System.out.println("Horizontally or vertically");
    }


    @Override
    public boolean isValidMove(Position newPosition){
        if (!super.isValidMove(newPosition)){
            return false;
        }
        if (newPosition.getCol() == this.position.getCol() && newPosition.getRow() == this.position.getRow()) {
            return false;
        } else return newPosition.getCol() == this.position.getCol() || newPosition.getRow() == this.position.getRow();
    }

}
