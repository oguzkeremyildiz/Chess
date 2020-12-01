import Chess.*;
import Chess.Pieces.Piece;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    private static HashMap<Integer, Integer> setIntegerMap() {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(8, 0);
        map.put(7, 1);
        map.put(6, 2);
        map.put(5, 3);
        map.put(4, 4);
        map.put(3, 5);
        map.put(2, 6);
        map.put(1, 7);
        return map;
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

    private static HashMap<String, Integer> setStringMap() {
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

    private static boolean setTurn(boolean turn) {
        return !turn;
    }

    public static void main(String[]args) throws CloneNotSupportedException, FileNotFoundException {
        Game game = new Game();
        game.setBoard();
        Computer computer = new Computer(game);
        Computer1 computer1 = new Computer1(game);
        boolean turn = true;
        Scanner scanner = new Scanner(System.in);
        HashMap<String, Integer> stringMap = setStringMap();
        HashMap<Integer, String> reverseStringMap = setReverseStringMap();
        HashMap<Integer, Integer> integerMap = setIntegerMap();
        HashMap<Integer, Integer> reverseIntegerMap = setReverseIntegerMap();
        while (game.finished()) {
            game.printBoard();
            if (turn) {
                /*game.printBoard();
                System.out.println();
                System.out.println("Select the piece.");
                int last = stringMap.get(scanner.next());
                int first = integerMap.get(scanner.nextInt());
                while (first > 7 || first < 0 || last > 7 || last < 0 || game.getBoard()[first][last] == null || !game.getBoard()[first][last].isTurn()) {
                    System.out.println("Try again.");
                    last = stringMap.get(scanner.next());
                    first = integerMap.get(scanner.nextInt());
                }
                Piece piece = game.getBoard()[first][last];
                System.out.println(piece.getName() + " was selected.");
                System.out.println("Enter the first position to move.");
                int y = stringMap.get(scanner.next());
                System.out.println("Enter the second position to move.");
                int x = integerMap.get(scanner.nextInt());
                while (!piece.play(game, new Coordinates(x, y))) {
                    System.out.println("Cannot be played to this point!");
                    y = stringMap.get(scanner.next());
                    x = integerMap.get(scanner.nextInt());
                }
                System.out.println(piece.getName() + ": played to " + reverseIntegerMap.get(x) + " and " + reverseStringMap.get(y) + " coordinates.");*/
                Move bestMove = computer1.findMove();
                System.out.println(bestMove.getFrom().getName() + ": played to " + reverseIntegerMap.get(bestMove.getToCoordinates().getX()) + " and " + reverseStringMap.get(bestMove.getToCoordinates().getY()) + " coordinates.");
                bestMove.getFrom().play(game, bestMove.getToCoordinates());
            } else {
                Move bestMove = computer.findMove();
                System.out.println(bestMove.getFrom().getName() + ": played to " + reverseIntegerMap.get(bestMove.getToCoordinates().getX()) + " and " + reverseStringMap.get(bestMove.getToCoordinates().getY()) + " coordinates.");
                bestMove.getFrom().play(game, bestMove.getToCoordinates());
            }
            turn = setTurn(turn);
        }
    }
}
