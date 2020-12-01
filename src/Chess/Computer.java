package Chess;/* Created by oguzkeremyildiz on 24.11.2020 */

import Chess.Pieces.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Computer {

    private Game game;
    private HashMap<String, Integer> pointMap;
    private static int MAX_DEPTH = 5;
    private HashMap<Integer, Integer> integerMap;
    private HashMap<String, Integer> stringMap;

    public Computer(Game game) {
        this.game = game;
        pointMap = setMap();
        integerMap = setIntegerMap();
        stringMap = setStringMap();
    }

    private HashSet<Move> constructCandidates(boolean turn) {
        HashSet<Move> set = new HashSet<>();
        for (int i = 0; i < game.getBoard().length; i++) {
            for (int j = 0; j < game.getBoard()[0].length; j++) {
                if (game.getBoard()[i][j] != null && game.getBoard()[i][j].isTurn() == turn) {
                    game.getBoard()[i][j].calculateAllPossibleMoves(game, i, j);
                    HashSet<Coordinates> possibles = game.getBoard()[i][j].getPossibles();
                    for (Coordinates coordinates : possibles) {
                        set.add(new Move(game.getBoard()[i][j], game.getBoard()[coordinates.getX()][coordinates.getY()], coordinates));
                    }
                }
            }
        }
        return set;
    }

    private HashMap<String, Integer> setMap() {
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

    private int findPointsInBoard(Game board) {
        int sum = 0;
        for (int i = 0; i < board.getBoard().length; i++) {
            for (int j = 0; j < board.getBoard()[0].length; j++) {
                if (board.getBoard()[i][j] != null) {
                    sum += pointMap.get(board.getBoard()[i][j].getName());
                }
            }
        }
        return sum;
    }

    private Move miniMaxDecision(boolean turn, int depth, int alpha, int beta) throws CloneNotSupportedException {
        HashSet<Move> subset = constructCandidates(turn);
        Move best = null;
        int bestValue = Integer.MIN_VALUE;
        for (Move move1 : subset) {
            Coordinates oldCoordinates = (Coordinates) move1.getFrom().getCoordinates().clone();
            move(move1);
            Pair<Move, Integer> current = minValue(!turn, depth - 1, alpha, beta);
            if (current.getValue() > bestValue){
                best = move1;
                bestValue = current.getValue();
            } else if (current.getValue() == bestValue && current.getValue() < -900) {
                if (Math.abs(stringMap.get(move1.getToCoordinates().toString().charAt(0) + "") - stringMap.get(oldCoordinates.toString().charAt(0) + "")) > 1 && (move1.toString().charAt(0) + "").equals("k")) {
                    best = move1;
                }
            }
            move1.getFrom().setCoordinates(oldCoordinates);
            undo(move1);
            alpha = Math.max(alpha, current.getValue());
            if (alpha >= beta) {
                break;
            }
        }
        return best;
    }

    private Pair<Move, Integer> minValue(boolean turn, int depth, int alpha, int beta) throws CloneNotSupportedException {
        Pair<Move, Integer> best;
        int point = findPointsInBoard(game);
        if (depth == 0 || point > 900 || point < -900) {
            return new Pair<>(null, point);
        } else {
            best = new Pair<>(null, Integer.MAX_VALUE);
            HashSet<Move> subset = constructCandidates(turn);
            for (Move move1 : subset) {
                Coordinates oldCoordinates = (Coordinates) move1.getFrom().getCoordinates().clone();
                move(move1);
                Pair<Move, Integer> current = maxValue(!turn, depth - 1, alpha, beta);
                if (current.getValue() < best.getValue()){
                    best = current;
                }
                move1.getFrom().setCoordinates(oldCoordinates);
                undo(move1);
                beta = Math.min(beta, current.getValue());
                if (alpha >= beta) {
                    break;
                }
            }
        }
        return best;
    }

    private Pair<Move, Integer> maxValue(boolean turn, int depth, int alpha, int beta) throws CloneNotSupportedException {
        Pair<Move, Integer> best;
        int point = findPointsInBoard(game);
        if (depth == 0 || point > 900 || point < -900) {
            return new Pair<>(null, point);
        } else {
            best = new Pair<>(null, Integer.MIN_VALUE);
            HashSet<Move> subset = constructCandidates(turn);
            for (Move move1 : subset) {
                Coordinates oldCoordinates = (Coordinates) move1.getFrom().getCoordinates().clone();
                move(move1);
                Pair<Move, Integer> current = minValue(!turn, depth - 1, alpha, beta);
                if (current.getValue() > best.getValue()){
                    best = current;
                }
                move1.getFrom().setCoordinates(oldCoordinates);
                undo(move1);
                alpha = Math.max(alpha, current.getValue());
                if (alpha >= beta) {
                    break;
                }
            }
        }
        return best;
    }

    private void undo(Move move) {
        int from1 = move.getToCoordinates().getX();
        int from2 = move.getToCoordinates().getY();
        int to1 = move.getFrom().getCoordinates().getX();
        int to2 = move.getFrom().getCoordinates().getY();
        if (move.getTo() != null) {
            Piece piece = game.getBoard()[from1][from2];
            game.getBoard()[from1][from2] = null;
            game.getBoard()[to1][to2] = piece;
            game.getBoard()[from1][from2] = move.getTo();
        } else {
            Piece piece = game.getBoard()[from1][from2];
            game.getBoard()[from1][from2] = null;
            game.getBoard()[to1][to2] = piece;
        }
    }

    private void move(Move move) {
        int currentI = move.getFrom().getCoordinates().getX();
        int currentJ = move.getFrom().getCoordinates().getY();
        Piece piece = game.getBoard()[currentI][currentJ];
        piece.setCoordinates(new Coordinates(move.getToCoordinates().getX(), move.getToCoordinates().getY()));
        game.getBoard()[currentI][currentJ] = null;
        game.getBoard()[move.getToCoordinates().getX()][move.getToCoordinates().getY()] = piece;
    }

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

    private boolean isOpeningsContainBoardOrder(String[] board) {
        for (String current : board) {
            if (game.getBoard()[integerMap.get(Integer.parseInt(current.charAt(2) + ""))][stringMap.get(current.charAt(1) + "")] != null) {
                if (!game.getBoard()[integerMap.get(Integer.parseInt(current.charAt(2) + ""))][stringMap.get(current.charAt(1) + "")].getName().equals(current.charAt(0) + "")) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    public Move findMove() throws CloneNotSupportedException, FileNotFoundException {
        Scanner source = new Scanner(new File("Openings.txt"));
        String line;
        String[] board;
        String[] to;
        while (source.hasNext()) {
            line = source.nextLine();
            board = line.split(" ");
            to = source.nextLine().split(" ");
            if (isOpeningsContainBoardOrder(board)) {
                return new Move(game.getBoard()[integerMap.get(Integer.parseInt(to[0].charAt(1) + ""))][stringMap.get(to[0].charAt(0) + "")], game.getBoard()[integerMap.get(Integer.parseInt(to[1].charAt(1) + ""))][stringMap.get(to[1].charAt(0) + "")], new Coordinates(integerMap.get(Integer.parseInt(to[1].charAt(1) + "")), stringMap.get(to[1].charAt(0) + "")));
            }
        }
        return miniMaxDecision(false, MAX_DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
}
