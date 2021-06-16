package Chess.Players.Interface;

import Chess.Coordinates;
import Chess.Game;
import Chess.Piece.Piece;
import Chess.Search;

import java.util.HashMap;

public class CoordinateCalculate implements PointsInterface {

    private final HashMap<String, Integer> pointMap = setMap();

    public CoordinateCalculate() {
    }

    private String pieceNameToString(Piece piece) {
        String pieceName = piece.getName().toString();
        if (!piece.color()) {
            return pieceName.toLowerCase();
        }
        return pieceName;
    }

    @Override
    public double calculatePoints(Game game) {
        double sum = 0.0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = game.getPiece(i, j);
                if (piece != null) {
                    int subsets = Search.search(new Coordinates(i, j), game).size();
                    sum += pointMap.get(pieceNameToString(piece));
                    if (piece.color()) {
                        sum -= ((subsets + 0.0) / possibilities(piece.toString()));
                    } else {
                        sum += ((subsets + 0.0) / possibilities(piece.toString()));
                    }
                }
            }
        }
        return sum;
    }

    private int possibilities(String name) {
        switch (name) {
            case "P":
                return 3;
            case "R:":
                return 14;
            case "Q":
                return 27;
            case "N":
            case "K":
                return 8;
            case "B":
                return 13;
            default:
                return Integer.MAX_VALUE;
        }
    }

    @Override
    public HashMap<String, Integer> setMap() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("P", -1);
        map.put("B", -3);
        map.put("N", -3);
        map.put("R", -5);
        map.put("Q", -9);
        map.put("K", -1000);
        map.put("p", 1);
        map.put("b", 3);
        map.put("n", 3);
        map.put("r", 5);
        map.put("q", 9);
        map.put("k", 1000);
        return map;
    }
}
