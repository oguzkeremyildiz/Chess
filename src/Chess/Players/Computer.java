package Chess.Players;/* Created by oguzkeremyildiz on 24.11.2020 */

import Chess.Coordinates;
import Chess.Game;
import Chess.Move;
import Chess.Pair;
import Chess.Pieces.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Computer extends Player {

    private HashMap<String, Integer> pointMap;
    private static int MAX_DEPTH = 5;

    public Computer(Game game) {
        super(game);
        pointMap = setMap();
        stringMap = setStringMap();
    }

    private HashSet<Move> constructCandidates(boolean turn) {
        HashSet<Move> set = new HashSet<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (game.getPiece(i, j) != null && game.getPiece(i, j).isTurn() == turn) {
                    game.getPiece(i, j).calculateAllPossibleMoves(game, i, j);
                    HashSet<Coordinates> possibles = game.getPiece(i, j).getPossibles();
                    for (Coordinates coordinates : possibles) {
                        set.add(new Move(game.getPiece(i, j), game.getPiece(coordinates.getX(), coordinates.getY()), coordinates));
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

    private int findPointsInBoard(Game game) {
        int sum = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (game.getPiece(i, j) != null) {
                    sum += pointMap.get(game.getPiece(i, j).getName());
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
            Piece piece = game.getPiece(from1, from2);
            game.setPiece(from1, from2, null);
            game.setPiece(to1, to2, piece);
            game.setPiece(from1, from2, move.getTo());
        } else {
            Piece piece = game.getPiece(from1, from2);
            game.setPiece(from1, from2, null);
            game.setPiece(to1, to2, piece);
        }
    }

    private void move(Move move) {
        int currentI = move.getFrom().getCoordinates().getX();
        int currentJ = move.getFrom().getCoordinates().getY();
        Piece piece = game.getPiece(currentI, currentJ);
        piece.setCoordinates(new Coordinates(move.getToCoordinates().getX(), move.getToCoordinates().getY()));
        game.setPiece(currentI, currentJ, null);
        game.setPiece(move.getToCoordinates().getX(), move.getToCoordinates().getY(), piece);
    }

    private boolean isOpeningsContainBoardOrder(String[] board) {
        for (String current : board) {
            if (game.getPiece(Game.INTEGER_MAP.get(Integer.parseInt(current.charAt(2) + "")), stringMap.get(current.charAt(1) + "")) != null) {
                if (!game.getPiece(Game.INTEGER_MAP.get(Integer.parseInt(current.charAt(2) + "")), stringMap.get(current.charAt(1) + "")).getName().equals(current.charAt(0) + "")) {
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
                return new Move(game.getPiece(Game.INTEGER_MAP.get(Integer.parseInt(to[0].charAt(1) + "")), stringMap.get(to[0].charAt(0) + "")), game.getPiece(Game.INTEGER_MAP.get(Integer.parseInt(to[1].charAt(1) + "")), stringMap.get(to[1].charAt(0) + "")), new Coordinates(Game.INTEGER_MAP.get(Integer.parseInt(to[1].charAt(1) + "")), stringMap.get(to[1].charAt(0) + "")));
            }
        }
        return miniMaxDecision(false, MAX_DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
}
