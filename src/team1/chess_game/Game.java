package team1.chess_game;

import java.util.Scanner;

public class Game {
    private Piece[][] board;
    private int handCount = 0;

    public Game() {
        this.board = new Piece[8][8];
        this.init();
    }

    public void incrementHandCount() {
        this.handCount++;
    }

    public boolean start(Scanner scan) {
        renderBoard();
        String ans = askUCI(scan);

        // TODO:
        // if input is help, show hekp
        // if input is board, show board ( just call renderBoard method)
        // if input is resign, resign and show regign message (if you return false, game will be over)
        // if input is moves, show movable positions
        // if input is UCI,  move piece and show board

        return false;
    }

    public String askUCI(Scanner scan) {
        printTurn();
        System.out.print("Enter UCI (type \"help\" for help): ");
        return scan.nextLine();
    }


    public void renderBoard() {
        for (int i = 0; i < this.board.length; i++) {
            Piece[] row = this.board[i];
            for (int j = 0; j < row.length; j++) {
                if (row[j] == null) {
                    System.out.print("・");
                } else {
                    System.out.print(" " + row[j].render());
                }
            }
            System.out.println(" " + Integer.toString(8 - i));
        }
        renderFooter();
    }

    private void renderFooter() {
        String[] chars = {"a", "b", "c", "d", "e", "f", "g", "h"};
        System.out.println("");
        for (int i = 0; i < chars.length; i++) {
            System.out.print(" " + chars[i]);
        }
        System.out.println("");
    }

    private void init() {
        for (int i = 0; i < this.board.length; i++) {
            Piece[] row = this.board[i];

            if (i == 1 || i == 6) {
               boolean isWhite = i == 6;
               for(int j = 0; j < row.length; j++) {
                   this.board[i][j] = new Pawn(isWhite);
               }
            }

            if (i == 0 || i == 7) {
               boolean isWhite = i == 7;
               this.board[i][0] = new Rook(isWhite);
               this.board[i][1] = new Knight(isWhite);
               this.board[i][2] = new Bishop(isWhite);
               this.board[i][3] = new King(isWhite);
               this.board[i][4] = new Queen(isWhite);
               this.board[i][5] = new Bishop(isWhite);
               this.board[i][6] = new Knight(isWhite);
               this.board[i][7] = new Rook(isWhite);
            }
        }
    }

    private void printTurn() {
        if (this.handCount % 2 == 0) {
          System.out.println("White to Move");
        } else {
          System.out.println("White to Move");
        }
    }

}