package team1.chess_game;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import java.util.Objects;

public class Game {
    private Piece[][] board;
    public int handCount = 0;
    private Uci uci;


    public Game() {
        this.board = new Piece[8][8];
        uci = new Uci(this.board.length);
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
                        if (handCount % 2 != 0) {
                            System.out.println("Game over - White won by resignation");
                        } else {
                            System.out.println("Game over - Black won by resignation");
                        }
                        System.exit(0);
                        break;

                    case "moves":
                        moves();
                        break;
                }
            } else if (ans.length() == 4 && uci.validate(ans.substring(0, 2)) && uci.validate(ans.substring(2, 4))) {
                if (makeMove(ans)) {
                    incrementHandCount();
                    if (kingInCheck(true)) {
                        System.out.println("White King is in check!");
                    }
                    if (kingInCheck(false)) {
                        System.out.println("Black King is in check!");
                    }

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
                + "| * type UIC (e.g. b1c3, e7e8q) to make a move                             |\n"
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
                    System.out.print(" ·");
                } else {
                    System.out.print(" " + row[j].render());
                }
            }
            System.out.println("  " + Integer.toString(i + 1));
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
                this.board[i][3] = new Queen(isWhite);
                this.board[i][4] = new King(isWhite);
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
        if (this.handCount % 2 == 0) {
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
        int colInt = uci.getCol();
        int rowInt = uci.getRow();
        try {
            if (board[rowInt][colInt] == null) {
                return "Invalid square!";


            }
            Piece target = board[rowInt][colInt];

            if (target.getValue() == 1) {
                moves += pawnKilling(rowInt, colInt);
            }

            if (target.getValue() == 1 && board[rowInt + 1][colInt] != null && !target.isWhite) {
                if (moves.length() > 2) {
                    String movesFilled = moves.substring(0, moves.length() - 2);
                    return movesFilled + "}";
                } else {
                    return moves + "}";
                }
            }

            if (target.getValue() == 1 && board[rowInt - 1][colInt] != null && target.isWhite) {
                if (moves.length() > 2) {
                    String movesFilled = moves.substring(0, moves.length() - 2);
                    return movesFilled + "}";
                } else {
                    return moves + "}";
                }
            }

            for (int j = 0; j < board.length; j++) {
                for (int i = 0; i < board[0].length; i++) {
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

        } catch (Exception e) {
            return "Invalid input, please try again";
        }

        return moves + "}";
    }

    private String pawnKilling(int rowInt, int colInt) {
        Piece target = board[rowInt][colInt];
        String moves = "";
        if (!target.isWhite) {
            Position potential = new Position(rowInt + 1, colInt + 1);
            Position potential2 = new Position(rowInt + 1, colInt - 1);
            if (colInt != 7 && board[rowInt + 1][colInt + 1] != null && board[rowInt + 1][colInt + 1].isWhite) {
                moves += potential.toString() + ", ";
            }
            if (colInt != 0 && board[rowInt + 1][colInt - 1] != null && board[rowInt + 1][colInt - 1].isWhite) {
                moves += potential2.toString() + ", ";
            }
        }
        if (target.isWhite) {
            Position potential = new Position(rowInt - 1, colInt + 1);
            Position potential2 = new Position(rowInt - 1, colInt - 1);
            if (colInt != 7 && board[rowInt - 1][colInt + 1] != null && !board[rowInt - 1][colInt + 1].isWhite) {
                moves += potential.toString() + ", ";
            }
            if (colInt != 0 && board[rowInt - 1][colInt - 1] != null && !board[rowInt - 1][colInt - 1].isWhite) {

                moves += potential2.toString() + ", ";
            }
        }
        return moves;
    }

    private boolean isValidMove(Piece target, Position destination) {
        Piece piece = this.board[destination.getRow()][destination.getCol()];
        boolean isFriend = piece != null && piece.isWhite == target.isWhite;
        boolean isValid = !isFriend && target.isValidMove(destination);

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

    private Piece pawnPromote(boolean isWhite) {
        System.out.print("To which piece you want to promote your pawn: ");
        Scanner scan = new Scanner(System.in);
        String ans =scan.next();
        if (ans.equals("Queen")) {
            System.out.println("Your paw was promoted to " + ans);
            return new Queen(isWhite);
        }
        if (ans.equals("Rook")) {
            System.out.println("Your paw was promoted to " + ans);
            return new Rook(isWhite);
        }
        if (ans.equals("Bishop")) {
            System.out.println("Your paw was promoted to " + ans);
            return new Bishop(isWhite);
        }
        if (ans.equals("Knight")) {
            System.out.println("Your paw was promoted to " + ans);
            return new Knight(isWhite);
        }
        System.out.println("Invalid piece!");
        return null;
    }

    private boolean kingInCheck(boolean isWhite) {
        for (int a = 0; a < board.length; a++) {
            for (int b = 0; b < board[0].length; b++) {
                Piece piece = board[b][a];
                if (piece != null) {
                    for (int j = 0; j < board.length; j++) {
                        for (int i = 0; i < board[0].length; i++) {
                            Position potential = new Position(i, j);
                            if (board[i][j] != null &&
                                    isValidMove(piece, potential) &&
                                    board[i][j].getValue() == 1000 &&
                                    board[i][j].isWhite != piece.isWhite && piece.isWhite != isWhite) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean makeMove (String uci){
        Position target = this.uci.resolve(uci.substring(0, 2));
        Position destination = this.uci.resolve(uci.substring(2, 4));

        int rowInt = target.getRow();
        int colInt = target.getCol();
        int newRowInt = destination.getRow();
        int newColInt = destination.getCol();

        try {
            Piece pieceToMove = board[rowInt][colInt];

            if (pieceToMove == null || (handCount % 2 == 0 && !pieceToMove.isWhite) || (handCount % 2 != 0 && pieceToMove.isWhite)) {
                System.out.println("Invalid square!");
                return false;
            }

            if (!pieceToMove.isWhite && rowInt == 6 && board[newRowInt][newColInt] == null && colInt == newColInt) {
                Piece promotedPawn = pawnPromote(false);
                board[newRowInt][newColInt] = promotedPawn;
                board[newRowInt][newColInt].setPosition(destination);
                board[rowInt][colInt] = null;
                renderBoard();
                return true;
            }

            if (pieceToMove.isWhite && rowInt == 1 && board[newRowInt][newColInt] == null && colInt == newColInt) {
                Piece promotedPawn = pawnPromote(true);
                board[newRowInt][newColInt] = promotedPawn;
                board[newRowInt][newColInt].setPosition(destination);
                board[rowInt][colInt] = null;
                renderBoard();
                return true;
            }

            if (pieceToMove.getValue() == 1 && pawnKilling(rowInt, colInt).contains(uci.substring(2, 3))) {
                board[newRowInt][newColInt] = pieceToMove;
                board[newRowInt][newColInt].setPosition(destination);
                board[rowInt][colInt] = null;
                System.out.println("OK");
                renderBoard();
                return true;
            }

            if (pieceToMove.getValue() == 1 && ((board[rowInt + 1][colInt] != null && !pieceToMove.isWhite) ||
                    (board[rowInt - 1][colInt] != null && pieceToMove.isWhite))) {
                System.out.println("Invalid move!");
                return false;
            }

            if (pieceToMove.getValue() == 1000 && isValidMove(pieceToMove, destination)) {
                board[newRowInt][newColInt] = pieceToMove;
                board[newRowInt][newColInt].setPosition(destination);
                if (kingInCheck(pieceToMove.isWhite)) {
                    board[newRowInt][newColInt] = null;
                    System.out.println("Check for black King!");
                    return false;
                }
                board[rowInt][colInt] = null;
                renderBoard();
                System.out.println("OK");
                return true;
            }

            if (isValidMove(pieceToMove, destination)) {
                board[newRowInt][newColInt] = pieceToMove;
                board[newRowInt][newColInt].setPosition(destination);
                if (kingInCheck(pieceToMove.isWhite)) {
                    board[newRowInt][newColInt] = null;
                    System.out.println("Check for white King!");
                    return false;
                }
                if (kingInCheck(!pieceToMove.isWhite)) {
                    System.out.println("Check!");
                }
                board[rowInt][colInt] = null;
                renderBoard();
                System.out.println("OK");
                return true;
            }

        } catch (Exception e) {
            System.out.println("Invalid input, please try again");
            return false;
        }
        System.out.println("Invalid input, please try again");
        return false;
    }

    private void moves () {
        if (handCount % 2 == 0) {
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
        }
        if (handCount % 2 == 1) {
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