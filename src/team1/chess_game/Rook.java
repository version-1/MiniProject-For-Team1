package team1.chess_game;

public class Rook extends Piece {

    public Rook(boolean isWhite) {
        super(5, isWhite);
    }


    public String toString() {
        return "Rook{value=’" + getValue() + "\'" + "}";
    }

    public void move() {
        System.out.println("Horizontally or vertically");
    }


    public boolean isValidMove(Position newPosition){
        if(newPosition.getCol() == this.position.getCol() || newPosition.getRow() == this.position.getRow()){
            return true;
        }
        else{
            return false;
        }
    }

}
