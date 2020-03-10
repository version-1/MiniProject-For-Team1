package lab3;

import java.util.Objects;

public class Pawn extends Piece {
    private boolean promoted;
    private Piece newPiece;

    public Pawn(boolean isWhite) {
        super(1, isWhite);
        this.promoted = false;
        this.newPiece = null;
    }

    public void promote(Piece newPiece) {
        if (newPiece.equals(new King(this.isWhite))|| newPiece.equals(new Pawn(this.isWhite))) {
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

    public boolean isPromoted() {
        return promoted;
    }

    public Piece getNewPiece() {
        return newPiece;
    }
}
