package team1.chess_game;

import java.util.Scanner;
import java.util.List;
import java.util.Objects;
import java.io.*;

public class Game {
    private final static int BOARD_SIZE = 8;
    private Piece[][] board;
    private int handCount = 0;
    private Uci uci;

    public Game() {
        this.board = new Piece[BOARD_SIZE][BOARD_SIZE];
        this.uci = new Uci(BOARD_SIZE);
        this.init();
    }

    public void incrementHandCount() {
        this.handCount++;
    }

    public void start(Scanner scan) {
        renderBoard();

        while (true) {
            String ans = askUCI(scan);

            if (Objects.equals(ans, "help") || Objects.equals(ans, "board") || Objects.equals(ans, "resign")
                    || Objects.equals(ans, "moves")) {
                switch (ans) {

                    case "help":
                        help();
                        break;

                    case "board":
                        renderBoard();
                        break;

                    case "resign":
                        if (this.isWhiteTurn()) {
                            System.out.println("Game over - Black won by resignation");
                        } else {
                            System.out.println("Game over - White won by resignation");
                        }
                        System.exit(0);
                        break;

                    case "moves":
                        moves();
                        break;
                }
            } else if (ans.length() == 4 && uci.validate(ans.substring(0, 2)) && uci.validate(ans.substring(2, 4))) {
                if (makeMove(ans)) {
                    System.out.println("OK");
                    renderBoard();
                    incrementHandCount();
                }

            } else if (ans.length() == 2 && uci.validate(ans)) {
                System.out.println(square(ans));

            } else {
                System.out.println("\nInvalid Input.");
            }
        }
    }

    private void help() {
        System.out.println("+==========================================================================+\n"
                + "| * type 'board' to see the board again                                    |\n"
                + "| * type 'resign' to resign                                                |\n"
                + "| * type 'moves' to list all possible moves                                |\n"
                + "| * type a square (e.g. b1, e2) to list all possible moves for that square |\n"
                + "| * type UIC (e.g. bic3, e7e8q) to make a move                             |\n"
                + "+==========================================================================+");
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
            System.out.println("  " + Integer.toString(this.board.length - i));
        }
        renderFooter();
    }

    public boolean isWhiteTurn() {
        return this.handCount % 2 == 0;
    }

    private void renderFooter() {
        String[] chars = { "a", "b", "c", "d", "e", "f", "g", "h" };
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
                for (int j = 0; j < row.length; j++) {
                    this.board[i][j] = new Pawn(isWhite);
                    this.board[i][j].setPosition(new Position(i, j));
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
                this.board[i][0].setPosition(new Position(i, 0));
                this.board[i][1].setPosition(new Position(i, 1));
                this.board[i][2].setPosition(new Position(i, 2));
                this.board[i][3].setPosition(new Position(i, 3));
                this.board[i][4].setPosition(new Position(i, 4));
                this.board[i][5].setPosition(new Position(i, 5));
                this.board[i][6].setPosition(new Position(i, 6));
                this.board[i][7].setPosition(new Position(i, 7));
            }
        }
    }

    private void printTurn() {
        if (isWhiteTurn()) {
            System.out.println("White to Move");
        } else {
            System.out.println("Black to Move");
        }
    }

    private String square(String square) {
        String moves = "{";
        Position uci = this.uci.resolve(square);
        if (uci == null) {
            return null;
        }
        Piece target = board[uci.getRow()][uci.getCol()];
        int size = board.length;
        for (int j = 0; j < size; j++) {
            for (int i = 0; i < size; i++) {
                Position potential = new Position(i, j);
                if (isValidMove(target, potential)) {
                    moves += potential.toString() + ", ";
                }
            }
        }
        if (moves.length() > 2) {
            String movesFilled = moves.substring(0, moves.length() - 2);
            return movesFilled + "}";
        }

        return moves + "}";
    }

    private boolean isValidMove(Piece target, Position destination) {
        Piece piece = this.board[destination.getRow()][destination.getCol()];
        Boolean isFriend = piece != null && piece.isWhite == target.isWhite;
        Boolean isValid = !isFriend && target.isValidMove(destination);

        if (!isValid) {
            return false;
        }

        if (target.getJump()) {
            return true;
        }

        List<Position> posList = target.position.getPath(destination);
        for (Position pos : posList) {
            Piece candidate = this.board[pos.getRow()][pos.getCol()];
            if (candidate != null && !candidate.position.equals(destination)) {
                return false;
            }
        }
        return true;
    }

    private boolean makeMove(String uci) {
        Position target = this.uci.resolve(uci.substring(0, 2));
        Position destination = this.uci.resolve(uci.substring(2, 4));
        if (target == null || destination == null) {
            System.out.println("Invalid input, please try again");
            return false;
        }

        Piece pieceToMove = board[target.getRow()][target.getCol()];

        if (pieceToMove == null) {
            System.out.println("Piece is missing");
            return false;
        }

        if (isWhiteTurn() != pieceToMove.isWhite) {
            System.out.println("Don't move a opponent piece! Stupid!");
            return false;
        }

        if (isValidMove(pieceToMove, destination)) {
            board[destination.getRow()][destination.getCol()] = pieceToMove;
            board[destination.getRow()][destination.getCol()].setPosition(destination);
            board[target.getRow()][target.getCol()] = null;
            return true;
        }
        return false;
    }

    private void moves() {
        if (isWhiteTurn()) {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    if (board[i][j] != null && board[i][j].isWhite) {
                        int colCol = j + 'a';
                        String col = Character.toString((char) colCol);
                        String row = Integer.toString(i + 1);
                        System.out.println("Possible moves for " + col + row + ":");
                        System.out.println(square(col + row));
                    }
                }
            }
        } else {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    if (board[i][j] != null && !board[i][j].isWhite) {
                        int colCol = j + 'a';
                        String col = Character.toString((char) colCol);
                        String row = Integer.toString(i + 1);
                        System.out.println("Possible moves for " + col + row + ":");
                        System.out.println(square(col + row));
                    }
                }
            }
        }
    }

}
