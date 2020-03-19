package team1.chess_game;

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
        List<Position> whiteInvasion = game.blackInvader();
        List<Position> blackInvasion = game.whiteInvader();
        /**List<Position> blackInvasion = game.blackInvader();
        List<Position> whiteInvasion = game.whiteInvader();

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
        if (this.isWhite){
            /**boolean check = north.equals(blackInvasion.get(i)) || northEast.equals(blackInvasion.get(i)) || northWest.equals(blackInvasion.get(i)) || east.equals(blackInvasion.get(i))
             || west.equals(blackInvasion.get(i)) || southEast.equals(blackInvasion.get(i)) || southWest.equals(blackInvasion.get(i)) || south.equals(blackInvasion.get(i));**/
            for (Position value : blackInvasion) {
                if (newPosition.equals(value)) {
                    return false;
                } else if (Math.abs(newPosition.getRow() - this.position.getRow()) == 1 && newPosition.getCol() == this.position.getCol()
                            || Math.abs(newPosition.getCol() - this.position.getCol()) == 1 && newPosition.getRow() == this.position.getRow()
                            || Math.abs(newPosition.getCol() - this.position.getCol()) == 1 && Math.abs(newPosition.getRow() - this.position.getRow()) == 1)
                {

                    return true;
                }
            }

        }
        //Black King in check
          if (!this.isWhite) {
              /**boolean check = north.equals(blackInvasion.get(i)) || northEast.equals(blackInvasion.get(i)) || northWest.equals(blackInvasion.get(i)) || east.equals(blackInvasion.get(i))
               || west.equals(blackInvasion.get(i)) || southEast.equals(blackInvasion.get(i)) || southWest.equals(blackInvasion.get(i)) || south.equals(blackInvasion.get(i));**/
              for (Position value1 : whiteInvasion) {
                  if (this.position.equals(value1)) {
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
