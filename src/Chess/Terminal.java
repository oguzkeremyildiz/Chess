package Chess;/* Created by oguzkeremyildiz on 1.12.2020 */

import Chess.Piece.Piece;
import Chess.Piece.PieceName;

import java.util.HashMap;
import java.util.Scanner;

public class Terminal extends PrintBoard {

    private Game game;
    private HashMap<Integer, String> reverseStringMap;
    private HashMap<String, Integer> stringMap;

    public Terminal(Game game) {
        this.game = game;
        stringMap = setStringMap();
        reverseStringMap = setReverseStringMap();
    }

    protected static HashMap<String, Integer> setStringMap() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("a", 0);
        map.put("b", 1);
        map.put("c", 2);
        map.put("d", 3);
        map.put("e", 4);
        map.put("f", 5);
        map.put("g", 6);
        map.put("h", 7);
        return map;
    }

    private static HashMap<Integer, String> setReverseStringMap() {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(0, "a");
        map.put(1, "b");
        map.put(2, "c");
        map.put(3, "d");
        map.put(4, "e");
        map.put(5, "f");
        map.put(6, "g");
        map.put(7, "h");
        return map;
    }
    @Override
    public void print(Game game) {
        System.out.print(" a b c d e f g h ");
        for (int i = 0; i < 8; i++) {
            System.out.println();
            System.out.print("|");
            for (int j = 0; j < 8; j++) {
                if (game.getPiece(i, j) != null) {
                    String name = game.getPiece(i, j).getName().toString();
                    if (!game.getPiece(i, j).color()) {
                        name = name.toLowerCase();
                    }
                    System.out.print(name);
                } else {
                    System.out.print(" ");
                }
                System.out.print("|");
            }
            System.out.print(" " + (8 - i));
        }
        System.out.println();
        System.out.print(" a b c d e f g h ");
    }

    @Override
    public void humanMove() throws CloneNotSupportedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.println("Select the piece.");
        int last = stringMap.get(scanner.next());
        int first = Game.INTEGER_MAP.get(scanner.nextInt());
        while (first > 7 || first < 0 || last > 7 || last < 0 || game.getPiece(first, last) == null || !game.getPiece(first, last).color()) {
            System.out.println("Try again.");
            last = stringMap.get(scanner.next());
            first = Game.INTEGER_MAP.get(scanner.nextInt());
        }
        Piece piece = game.getPiece(first, last);
        System.out.println(piece.getName() + " was selected.");
        System.out.println("Enter the first position to move.");
        int y = stringMap.get(scanner.next());
        System.out.println("Enter the second position to move.");
        int x = Game.INTEGER_MAP.get(scanner.nextInt());
        while (!Search.search(new Coordinates(first, last), game).contains(new Coordinates(x, y))) {
            System.out.println("Cannot be played to this point!");
            y = stringMap.get(scanner.next());
            x = Game.INTEGER_MAP.get(scanner.nextInt());
        }
        System.out.println(piece.getName() + ": played to " + reverseStringMap.get(y) + Game.INTEGER_MAP.get(x) + " coordinates.");
        if (piece.getName().equals(PieceName.P) && x == 0) {
            System.out.println("Choose a piece. (bishop, queen etc.)");
            String str = scanner.next();
            switch (str) {
                case "bishop":
                    Search.play(new Coordinates(first, last), new Coordinates(x, y), new Piece(true, PieceName.B, null), game);
                    break;
                case "knight":
                    Search.play(new Coordinates(first, last), new Coordinates(x, y), new Piece(true, PieceName.N, null), game);
                    break;
                case "queen":
                    Search.play(new Coordinates(first, last), new Coordinates(x, y), new Piece(true, PieceName.Q, null), game);
                    break;
                case "rook":
                    Search.play(new Coordinates(first, last), new Coordinates(x, y), new Piece(true, PieceName.R, null), game);
                    break;
                default:
                    break;
            }
        } else {
            Search.play(new Coordinates(first, last), new Coordinates(x, y), null, game);
        }
    }


    @Override
    public void run() {

    }
}
