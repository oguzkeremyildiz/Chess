package Chess.Players;/* Created by oguzkeremyildiz on 24.11.2020 */

import Chess.*;
import Chess.Piece.*;
import Chess.Players.Interface.CoordinateCalculate;
import Chess.Players.Interface.NormalCalculate;
import Chess.Players.Interface.Point;
import Chess.Players.Interface.PointsInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class Computer extends Player {

    private HashMap<String[], String[]> openings;
    private static int MAX_DEPTH = 5;
    private final PointsInterface pointsInterface;

    public Computer(Game game, Point point) throws FileNotFoundException {
        super(game);
        if (point.equals(Point.NORMAL)) {
            this.pointsInterface = new NormalCalculate();
        } else {
            this.pointsInterface = new CoordinateCalculate();
        }
        openings = new HashMap<>();
        stringMap = setStringMap();
        Scanner source = new Scanner(new File("Openings.txt"));
        String line;
        String[] board;
        String[] to;
        while (source.hasNext()) {
            line = source.nextLine();
            board = line.split(" ");
            to = source.nextLine().split(" ");
            openings.put(board, to);
        }
    }

    private String findType(Coordinates from, Coordinates coordinates) {
        Piece piece = game.getPiece(from.getX(), from.getY());
        if (piece.getName().equals(PieceName.P) && !piece.color()) {
            if (game.getPiece(coordinates.getX(), coordinates.getY()) != null) {
                return "TY";
            }
            return "IO";
        }
        if (!piece.color() && from.getX() < coordinates.getX() && !piece.getName().equals(PieceName.K)) {
            return "IO";
        }
        if (piece.color() && from.getX() > coordinates.getX() && !piece.getName().equals(PieceName.K)) {
            return "IO";
        }
        if (game.getPiece(coordinates.getX(), coordinates.getY()) != null) {
            return "TY";
        }
        if (coordinates.getX() == from.getX()) {
            return "N";
        }
        return "GO";
    }

    private LinkedHashMap<String, HashSet<Move>> setLinkedHashMap(boolean turn) {
        PointsInterface pointsInterface = new NormalCalculate();
        int point = (int) pointsInterface.calculatePoints(game);
        LinkedHashMap<String, HashSet<Move>> map = new LinkedHashMap<>();
        if (turn || point < 0) {
            map.put("TY", new HashSet<>());
            map.put("IO", new HashSet<>());
            map.put("N", new HashSet<>());
            map.put("GO", new HashSet<>());
        } else {
            map.put("IO", new HashSet<>());
            map.put("TY", new HashSet<>());
            map.put("N", new HashSet<>());
            map.put("GO", new HashSet<>());
        }
        return map;
    }

    private void setMapForPromotedPiece(LinkedHashMap<String, HashSet<Move>> map, String type, boolean turn, Coordinates from, Coordinates to) {
        map.get(type).add(new Move(from, to, new Piece(turn, PieceName.P, null)));
        map.get(type).add(new Move(from, to, new Piece(turn, PieceName.Q, null)));
        map.get(type).add(new Move(from, to, new Piece(turn, PieceName.R, null)));
        map.get(type).add(new Move(from, to, new Piece(turn, PieceName.B, null)));
        map.get(type).add(new Move(from, to, new Piece(turn, PieceName.N, null)));
    }

    private LinkedHashMap<String, HashSet<Move>> constructCandidates(boolean turn) {
        LinkedHashMap<String, HashSet<Move>> map = setLinkedHashMap(turn);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (game.getPiece(i, j) != null && game.getPiece(i, j).color() == turn) {
                    HashSet<Coordinates> possibles = Search.search(new Coordinates(i, j), game);
                    for (Coordinates coordinates : possibles) {
                        String type = findType(new Coordinates(i, j), coordinates);
                        if ((turn && game.getPiece(i, j).getName().equals(PieceName.P) && coordinates.getX() == 0) || (!turn && game.getPiece(i, j).getName().equals(PieceName.P) && coordinates.getX() == 7)) {
                            setMapForPromotedPiece(map, type, turn, new Coordinates(i, j), coordinates);
                        } else {
                            map.get(type).add(new Move(new Coordinates(i, j), coordinates, null));
                        }
                    }
                }
            }
        }
        return map;
    }

    private int setMaxDepth() {
        int pieceCount = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (game.getPiece(i, j) != null) {
                    pieceCount++;
                }
            }
        }
        if (pieceCount < 6) {
            return 9;
        }
        return pieceCount < 20 ? 7 : 5;
    }

    private void undo(Game clone) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece current = clone.getPiece(i, j);
                game.setPiece(i, j, current);
            }
        }
        game.clear();
        for (int i = 0; i < clone.size(); i++) {
            game.addMove(clone.getMove(i));
        }
    }

    private Move miniMaxDecision(boolean turn, int depth, double alpha, double beta) throws CloneNotSupportedException {
        MAX_DEPTH = setMaxDepth();
        LinkedHashMap<String, HashSet<Move>> subset = constructCandidates(turn);
        Move best = null;
        double bestValue = Integer.MIN_VALUE;
        for (String key : subset.keySet()) {
            for (Move move : subset.get(key)) {
                Game clone = game.clone();
                Search.play(move, game);
                Coordinates oldCoordinates = (Coordinates) move.getFromCoordinates().clone();
                Pair<Move, Double> current = minValue(!turn, depth - 1, alpha, beta);
                if (current.getValue() > bestValue) {
                    if (!(current.getValue() < -900 && Math.abs(stringMap.get(move.getToCoordinates().toString().charAt(0) + "") - stringMap.get(oldCoordinates.toString().charAt(0) + "")) > 1 && (move.toString().charAt(0) + "").equals("k"))) {
                        best = move;
                        bestValue = current.getValue();
                    }
                } else if (current.getValue() == bestValue && current.getValue() == 0) {
                    if (Math.abs(stringMap.get(move.getToCoordinates().toString().charAt(0) + "") - stringMap.get(oldCoordinates.toString().charAt(0) + "")) > 1 && (move.toString().charAt(0) + "").equals("k")) {
                        best = move;
                    }
                }
                undo(clone);
                alpha = Math.max(alpha, current.getValue());
                if (alpha >= beta) {
                    return best;
                }
            }
        }
        return best;
    }

    private Pair<Move, Double> minValue(boolean turn, int depth, double alpha, double beta) throws CloneNotSupportedException {
        Pair<Move, Double> best;
        double point = pointsInterface.calculatePoints(game);
        if (depth == 0 || point > 900 || point < -900) {
            return new Pair<>(null, point);
        } else {
            best = new Pair<>(null, (double) Integer.MAX_VALUE);
            LinkedHashMap<String, HashSet<Move>> subset = constructCandidates(turn);
            for (String key : subset.keySet()) {
                for (Move move : subset.get(key)) {
                    Game clone = game.clone();
                    Search.play(move, game);
                    Pair<Move, Double> current = maxValue(!turn, depth - 1, alpha, beta);
                    if (current.getValue() < best.getValue()) {
                        best = current;
                    }
                    undo(clone);
                    beta = Math.min(beta, current.getValue());
                    if (alpha >= beta) {
                        return best;
                    }
                }
            }
        }
        return best;
    }

    private Pair<Move, Double> maxValue(boolean turn, int depth, double alpha, double beta) throws CloneNotSupportedException {
        Pair<Move, Double> best;
        double point = pointsInterface.calculatePoints(game);
        if (depth == 0 || point > 900 || point < -900) {
            return new Pair<>(null, point);
        } else {
            best = new Pair<>(null, (double) Integer.MIN_VALUE);
            LinkedHashMap<String, HashSet<Move>> subset = constructCandidates(turn);
            for (String key : subset.keySet()) {
                for (Move move : subset.get(key)) {
                    Game clone = game.clone();
                    Search.play(move, game);
                    Pair<Move, Double> current = minValue(!turn, depth - 1, alpha, beta);
                    if (current.getValue() > best.getValue()) {
                        best = current;
                    }
                    undo(clone);
                    alpha = Math.max(alpha, current.getValue());
                    if (alpha >= beta) {
                        return best;
                    }
                }
            }
        }
        return best;
    }

    private String pieceNameToString(Piece piece) {
        String pieceName = piece.getName().toString();
        if (!piece.color()) {
            return pieceName.toLowerCase();
        }
        return pieceName;
    }

    private Pair<Boolean, String[]> isOpeningsContainBoardOrder() {
        boolean check;
        for (String[] key : openings.keySet()) {
            check = true;
            for (String current : key) {
                if (game.getPiece(Game.INTEGER_MAP.get(Integer.parseInt(current.charAt(2) + "")), stringMap.get(current.charAt(1) + "")) != null) {
                    if (!pieceNameToString(game.getPiece(Game.INTEGER_MAP.get(Integer.parseInt(current.charAt(2) + "")), stringMap.get(current.charAt(1) + ""))).equals(current.charAt(0) + "")) {
                        check = false;
                        break;
                    }
                } else {
                    check = false;
                    break;
                }
            }
            if (check) {
                return new Pair<>(true, openings.get(key));
            }
        }
        return new Pair<>(false, null);
    }

    public Move findMove() throws CloneNotSupportedException {
        Pair<Boolean, String[]> pair = isOpeningsContainBoardOrder();
        if (pair.getKey()) {
            game.getPiece(Game.INTEGER_MAP.get(Integer.parseInt(pair.getValue()[0].charAt(1) + "")), stringMap.get(pair.getValue()[0].charAt(0) + "")).setOldMove(new Coordinates(Game.INTEGER_MAP.get(Integer.parseInt(pair.getValue()[0].charAt(1) + "")), stringMap.get(pair.getValue()[0].charAt(0) + "")));
            return new Move(new Coordinates(Game.INTEGER_MAP.get(Integer.parseInt(pair.getValue()[0].charAt(1) + "")), stringMap.get(pair.getValue()[0].charAt(0) + "")), new Coordinates(Game.INTEGER_MAP.get(Integer.parseInt(pair.getValue()[1].charAt(1) + "")), stringMap.get(pair.getValue()[1].charAt(0) + "")), null);
        }
        return miniMaxDecision(false, MAX_DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
}