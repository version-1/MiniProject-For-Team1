package team1.chess_game;

import java.util.ArrayList;
import java.util.List;

public class Position {
    private int row;
    private int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public String toString() {
        int colInt = col + 'a';
        char colLetter = (char)colInt;
        return "(" + colLetter +
                (8 - row) +
                ')';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        Position pos = (Position)o;
        return getRow() == pos.getRow() && getCol() == pos.getCol();
    }

    public List<Position> getPath(Position position) {
      List<Position> list = new ArrayList<Position>();

      int row = Math.abs(getRow() - position.getRow());
      int col = Math.abs(getCol() - position.getCol());
      if (getCol() == position.getCol()) {
          for (int i = 1; i <= row; i++) {
              int delta = getRow() > position.getRow() ? -i : i;
              int newRow = getRow() + delta;
              list.add(new Position(newRow, getCol()));
          }
          return list;
      }

      if (getRow() == position.getRow()) {
          for (int i = 1; i <= col; i++) {
              int delta = getCol() > position.getCol() ? -i : i;
              int newCol = getCol() + delta;
              list.add(new Position(getRow(), newCol));
          }
          return list;
      }

      int rowIndex = getRow();
      int colIndex = getCol();
      while(rowIndex == position.getRow() && colIndex == position.getCol()) {
          rowIndex = getRow() > position.getRow() ? rowIndex + 1 : rowIndex - 1;
          colIndex = getCol() > position.getCol() ? colIndex + 1 : colIndex - 1;

          if (rowIndex > 7 || rowIndex < 0) {
              break;
          }
      }

      return list;
    }
}