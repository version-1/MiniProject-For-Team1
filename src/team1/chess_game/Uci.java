package team1.chess_game;

public class Uci {
    private int size;

    public Uci(int size) {
        this.size = size;
    }

    public boolean validate(String uci) {
        if (uci.length() != 2) {
            return false;
        }
        for (int i = 0; i < uci.length(); i++) {
            Character c = uci.charAt(i);
            int num = i % 2 == 0 ? c - 97 : Character.getNumericValue(c); // 97 is num converted from 'a'
            if (!(num >= 0 && num <= size)) {
                return false;
            }
        }
        return true;
    }

    public Position resolve(String uci) {
        if (!validate(uci)) {
            return null;
        }

        char colChar = uci.charAt(0);
        char rowChar = uci.charAt(1);
        int col = colChar - 'a';
        int row = rowChar - '1';
        return new Position(row, col);
    }
}
