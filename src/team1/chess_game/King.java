package team1.chess_game;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


public class King extends Piece {


    public King(boolean isWhite) {
        super(1000, isWhite);


    }

    @Override
    public String render() {

        return isWhite ? "♔" : "♚";


    }

    public String toString() {
        return "King{value=’" + getValue() + "\'" + "}";
    }

    public void move() {
        System.out.println("One square");
    }






    @Override
    public boolean isValidMove(Position newPosition) {
        Game game = new Game();
        List<Integer> whiteInvasion = game.whiteInvader();
        List<Integer> blackInvasion = game.blackInvader();

/**
        Position north = new Position(this.position.getRow() + 1, this.position.getCol());
        Position northEast = new Position(this.position.getRow() + 1, this.position.getCol() + 1);
        Position northWest = new Position(this.position.getRow() + 1, this.position.getCol() - 1);
        Position east = new Position(this.position.getRow(), this.position.getCol() + 1);
        Position west = new Position(this.position.getRow(), this.position.getCol() - 1);
        Position southEast = new Position(this.position.getRow() - 1, this.position.getCol() + 1);
        Position southWest = new Position(this.position.getRow() - 1, this.position.getCol() - 1);
        Position south = new Position(this.position.getRow() - 1, this.position.getCol()); */

        if (!super.isValidMove(position)) {
            return false;
        }

        // White King is in check
        if (this.isWhite) {
           /** for (Position po : blackInvasion) {
                if (po.equals(north) || po.equals(northEast) || po.equals(northWest) ||
                        po.equals(east) || po.equals(west) ||
                        po.equals(southEast) || po.equals(southWest) || po.equals(south)) {
                    return false;
                }
            }*/
            for (Integer value : blackInvasion) {
                if (value.equals((this.position.getRow() + 1) + this.position.getCol()) || value.equals(this.position.getRow() - 1 + this.position.getRow())
                        || value.equals(this.position.getRow() + this.position.getCol() + 1) || value.equals(this.position.getRow() + this.position.getCol()- 1)
                        || value.equals(this.position.getRow() + 1 + this.position.getCol() + 1) || value.equals(this.position.getRow() -1 + this.position.getCol() - 1))  {
                    return false;
                }else if (Math.abs(newPosition.getRow() - this.position.getRow()) == 1 && newPosition.getCol() == this.position.getCol()
                            || Math.abs(newPosition.getCol() - this.position.getCol()) == 1 && newPosition.getRow() == this.position.getRow()
                            || Math.abs(newPosition.getCol() - this.position.getCol()) == 1 && Math.abs(newPosition.getRow() - this.position.getRow()) == 1) {

                        return true;
                    }
                }
            }


        //Black King in check
          if (!this.isWhite) {
              /**for (Position po : whiteInvasion) {
                  if (po.equals(north) || po.equals(northEast) || po.equals(northWest) ||
                          po.equals(east) || po.equals(west) ||
                          po.equals(southEast) || po.equals(southWest) || po.equals(south)) {
                      return false;
                  }*/

              for (Integer value1 : whiteInvasion) {
                  if (value1.equals((this.position.getRow() + 1) + this.position.getCol())) {
                      return false;
                  } else if (Math.abs(newPosition.getRow() - this.position.getRow()) == 1 && newPosition.getCol() == this.position.getCol()
                          || Math.abs(newPosition.getCol() - this.position.getCol()) == 1 && newPosition.getRow() == this.position.getRow()
                          || Math.abs(newPosition.getCol() - this.position.getCol()) == 1 && Math.abs(newPosition.getRow() - this.position.getRow()) == 1) {

                      return true;
                  }
              }
          }


        return true;
    }
}
