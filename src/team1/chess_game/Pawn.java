package team1.chess_game;

import java.util.Objects;

public class Pawn extends Piece {
    private boolean promoted;
    private Piece newPiece;

    public Pawn(boolean isWhite) {
        super(1, isWhite);
        this.promoted = false;
        this.newPiece = null;
    }

    @Override
    public String render() {
        return isWhite ? "♙" : "♟";
    }

    public void promote(Piece newPiece) {
        if (newPiece.equals(new King(this.isWhite)) || newPiece.equals(new Pawn(this.isWhite))) {
            System.out.println("Cannot be promoted to King or Pawn.");
        } else {
            this.promoted = true;
            setValue(newPiece.getValue());
            this.newPiece = newPiece;
        }
    }


    @Override
    public void move() {
        System.out.println("Forward 1");

    }

    @Override
    public String toString() {
        return "Pawn{value='" + getValue() + "\'" + ", is white=" + isWhite +
                ", is promoted=" + isPromoted() + ", new piece=" + newPiece + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pawn)) return false;
        if (!super.equals(o)) return false;
        Pawn pawn = (Pawn) o;
        return this.promoted == pawn.promoted &&
                Objects.equals(this.newPiece, pawn.newPiece);
    }

    @Override
    public boolean isValidMove(Position newPosition) {
        // Don't count with opponent's figure on the newPosition or in front of a pawn
        // The pawn reached the end and got promoted
        //    this.promote(newPiece);
        // starting position!
        if (!super.isValidMove(position)) {
            return false;
        }
        if ((this.position.getRow() == 1 && this.position.getCol() == newPosition.getCol() &&
                (this.position.getRow() + 2) == newPosition.getRow()) && !this.isWhite
                || (this.position.getRow() == 1 && this.position.getCol() == newPosition.getCol() &&
                (this.position.getRow() + 1) == newPosition.getRow() && !this.isWhite)
                || (this.position.getCol() == newPosition.getCol() &&
                (this.position.getRow() + 1) == newPosition.getRow() && !this.isWhite)) {
            return true;
        }

        if ((this.position.getRow() == 6 && this.position.getCol() == newPosition.getCol() &&
                (this.position.getRow() - 2) == newPosition.getRow() && this.isWhite)
                || (this.position.getRow() == 6 && this.position.getCol() == newPosition.getCol() &&
                (this.position.getRow() - 1) == newPosition.getRow() && this.isWhite)
                || (this.position.getCol() == newPosition.getCol() &&
                (this.position.getRow() - 1) == newPosition.getRow() && this.isWhite)) {

            return true;

        }

        return false;

    }

    public boolean isPromoted() {
        return promoted;
    }

    public Piece getNewPiece() {
        return newPiece;
    }
}
