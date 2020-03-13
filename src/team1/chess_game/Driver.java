package team1.chess_game;

import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        Game game = new Game();
        Scanner scan = new Scanner(System.in);
        while(game.start(scan)) {
            game.incrementHandCount();
        }
        scan.close();
    }
}