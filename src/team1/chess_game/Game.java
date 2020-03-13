package team1.chess_game;

import java.util.ArrayList;

public class Game {
    private Piece [][] board;

    public Game(){

        board = new Piece[8][8];
    }

        public void newGame(){

            ArrayList<Piece> pieces = new ArrayList<>();
            pieces.add(new Pawn(true));
            pieces.add(new Rook(true));
            pieces.add(new Knight(true));
            pieces.add(new Bishop(true));
            pieces.add(new Queen(true));
            pieces.add(new King(true));

            pieces.add(new Pawn(false));
            pieces.add(new Rook(false));
            pieces.add(new Knight(false));
            pieces.add(new Bishop(false));
            pieces.add(new Queen(false));
            pieces.add(new King(false));

            for (Piece piece: pieces) {
                piece.move();
            }




        }
}