package Chess.Players;/* Created by oguzkeremyildiz on 1.12.2020 */

import Chess.Coordinates;
import Chess.Game;
import Chess.Move;
import Chess.Pieces.Piece;

import java.util.HashMap;
import java.util.Scanner;

public class Human extends Player {

    private HashMap<Integer, Integer> reverseIntegerMap;
    private HashMap<Integer, String> reverseStringMap;

    public Human(Game game) {
        super(game);
        integerMap = setIntegerMap();
        stringMap = setStringMap();
        reverseIntegerMap = setReverseIntegerMap();
        reverseStringMap = setReverseStringMap();
    }

    private static HashMap<Integer, Integer> setReverseIntegerMap() {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, 8);
        map.put(1, 7);
        map.put(2, 6);
        map.put(3, 5);
        map.put(4, 4);
        map.put(5, 3);
        map.put(6, 2);
        map.put(7, 1);
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
    public Move findMove() {
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.println("Select the piece.");
        int last = stringMap.get(scanner.next());
        int first = integerMap.get(scanner.nextInt());
        while (first > 7 || first < 0 || last > 7 || last < 0 || game.getPiece(first, last) == null || !game.getPiece(first, last).isTurn()) {
            System.out.println("Try again.");
            last = stringMap.get(scanner.next());
            first = integerMap.get(scanner.nextInt());
        }
        Piece piece = game.getPiece(first, last);
        System.out.println(piece.getName() + " was selected.");
        System.out.println("Enter the first position to move.");
        int y = stringMap.get(scanner.next());
        System.out.println("Enter the second position to move.");
        int x = integerMap.get(scanner.nextInt());
        while (!piece.contains(new Coordinates(x, y), game, first, last)) {
            System.out.println("Cannot be played to this point!");
            y = stringMap.get(scanner.next());
            x = integerMap.get(scanner.nextInt());
        }
        System.out.println(piece.getName() + ": played to " + reverseStringMap.get(y) + reverseIntegerMap.get(x) + " coordinates.");
        return new Move(piece, game.getPiece(x, y), new Coordinates(x, y));
    }
}
