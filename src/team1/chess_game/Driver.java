package team1.chess_game;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        Game game = new Game();
        Scanner scan = new Scanner(System.in);
        game.start(scan);
        scan.close();
    }
}