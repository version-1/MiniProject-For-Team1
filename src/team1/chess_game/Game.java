package team1.chess_game;

import java.util.Scanner;
import java.util.List;
import java.util.Objects;

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

    public void start(Scanner scan) {
        renderBoard();
        while (true) {
            String ans = askUCI(scan);

            // TODO:
            // if input is help, show help
            // if input is board, show board ( just call renderBoard method)
            // if input is resign, resign and show resign message (if you return false, game
            // will be over)
            // if input is moves, show movable positions
            // if input is UCI, move piece and show board

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
                        break;
                }
            }
            if (ans.length() == 4 && Character.isLetter(ans.charAt(0)) && Character.isDigit(ans.charAt(1))
                    && Character.isLetter(ans.charAt(2)) && Character.isDigit(ans.charAt(3))) {
            }
            if (ans.length() == 2 && Character.isLetter(ans.charAt(0)) && Character.isDigit(ans.charAt(1))) {
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
            System.out.println("  " + Integer.toString(8 - i));
        }
        renderFooter();
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
        if (this.handCount % 2 == 0) {
            System.out.println("White to Move");
        } else {
            System.out.println("Black to Move");
        }
    }

    private String square(String square) {
        String moves = "{";
        char colChar = square.charAt(0);
        char rowChar = square.charAt(1);
        int colInt = colChar - 'a';
        int rowInt = rowChar - '1';
        if (board[rowInt][colInt] == null) {
            return "Invalid square!";
        }

        Piece target = board[rowInt][colInt];
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

    private void move() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].isWhite) {
                    // TODO
                }
            }
        }
    }

    public static void main(String[] args) {
        String square = "a7";
        char colChar = square.charAt(0);
        char rowChar = square.charAt(1);
        int colInt = colChar - 'a';
        int rowInt = rowChar - '1';
        // System.out.println(colInt);
        // System.out.println(rowInt);
        int rowInt1 = 'a';
        char rowChar1 = (char) rowInt1;
        System.out.println(rowChar1);
    }
}
