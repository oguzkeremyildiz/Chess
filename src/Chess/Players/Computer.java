package Chess.Players;/* Created by oguzkeremyildiz on 24.11.2020 */

import Chess.Coordinates;
import Chess.Game;
import Chess.Move;
import Chess.Pair;
import Chess.Pieces.*;
import Chess.Players.Interface.NormalCalculate;
import Chess.Players.Interface.PointsInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class Computer extends Player {

    private HashMap<String[], String[]> openings;
    private static int MAX_DEPTH = 6;

    public Computer(Game game) throws FileNotFoundException {
        super(game);
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

    private String findType(Piece piece, Coordinates coordinates) {
        if (piece.getName().equalsIgnoreCase("p")) {
            return "IO";
        }
        if (piece.getName().equals(piece.getName().toLowerCase()) && piece.getCoordinates().getX() < coordinates.getX() && !piece.getName().equals("k")) {
            return "IO";
        }
        if (!piece.getName().equals(piece.getName().toLowerCase()) && piece.getCoordinates().getX() > coordinates.getX() && !piece.getName().equals("K")) {
            return "IO";
        }
        if (game.getPiece(coordinates.getX(), coordinates.getY()) != null) {
            return "TY";
        }
        if (coordinates.getX() == piece.getCoordinates().getX()) {
            return "N";
        }
        return "GO";
    }

    private LinkedHashMap<String, HashSet<Move>> setLinkedHashMap() {
        LinkedHashMap<String, HashSet<Move>> map = new LinkedHashMap<>();
        map.put("IO", new HashSet<>());
        map.put("TY", new HashSet<>());
        map.put("N", new HashSet<>());
        map.put("GO", new HashSet<>());
        return map;
    }

    private LinkedHashMap<String, HashSet<Move>> constructCandidates(boolean turn) {
        LinkedHashMap<String, HashSet<Move>> map = setLinkedHashMap();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (game.getPiece(i, j) != null && game.getPiece(i, j).isTurn() == turn) {
                    game.getPiece(i, j).calculateAllPossibleMoves(game, i, j);
                    HashSet<Coordinates> possibles = game.getPiece(i, j).getPossibles();
                    for (Coordinates coordinates : possibles) {
                        String type = findType(game.getPiece(i, j), coordinates);
                        map.get(type).add(new Move(game.getPiece(i, j), game.getPiece(coordinates.getX(), coordinates.getY()), coordinates));
                    }
                }
            }
        }
        return map;
    }

    private int checkSize(Move move) {
        if (move.getFrom().getName().equals("P") && move.getToCoordinates().getX() == 0) {
            return 4;
        }
        return 1;
    }

    private boolean setMove(int size, int index, Move move, boolean isQueen) {
        if (size == 4) {
            switch (index) {
                case 0:
                    if (move.getFrom().getName().equals("P")) {
                        move.setFrom(new Bishop(move.getFrom().getCoordinates(), true, "B"));
                    } else {
                        move.setFrom(new Pawn(move.getFrom().getCoordinates(), true, "P"));
                    }
                    break;
                case 1:
                    if (move.getFrom().getName().equals("P")) {
                        move.setFrom(new Queen(move.getFrom().getCoordinates(), true, "Q"));
                    } else {
                        move.setFrom(new Pawn(move.getFrom().getCoordinates(), true, "P"));
                    }
                    break;
                case 2:
                    if (move.getFrom().getName().equals("P")) {
                        move.setFrom(new Rook(move.getFrom().getCoordinates(), true, "R"));
                    } else {
                        move.setFrom(new Pawn(move.getFrom().getCoordinates(), true, "P"));
                    }
                    break;
                case 3:
                    if (move.getFrom().getName().equals("P")) {
                        move.setFrom(new Knight(move.getFrom().getCoordinates(), true, "N"));
                    } else {
                        move.setFrom(new Pawn(move.getFrom().getCoordinates(), true, "P"));
                    }
                    break;
            }
        } else if (size == 1) {
            if (move.getFrom().getName().equals("p") && move.getToCoordinates().getX() == 7) {
                move.setFrom(new Queen(move.getFrom().getCoordinates(), false, "q"));
                return true;
            } else if (isQueen) {
                move.setFrom(new Pawn(move.getFrom().getCoordinates(), false, "p"));
            }
        }
        return false;
    }

    private boolean isCheckMate() {
        Piece king = null;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (game.getPiece(i, j) != null && game.getPiece(i, j).getName().equals("k")) {
                    king = game.getPiece(i, j);
                    break;
                }
            }
            if (king != null) {
                break;
            }
        }
        assert king != null;
        int i = 1;
        while (i + king.getCoordinates().getX() < 8) {
            Piece current = game.getPiece(i + king.getCoordinates().getX(), king.getCoordinates().getY());
            if (current != null) {
                if (current.getName().equals("R") || current.getName().equals("Q")) {
                    return true;
                }
                if (current.getName().equals(current.getName().toLowerCase())) {
                    break;
                }
            }
            i++;
        }
        i = 1;
        while (king.getCoordinates().getX() - i > -1) {
            Piece current = game.getPiece(king.getCoordinates().getX() - i, king.getCoordinates().getY());
            if (current != null) {
                if (current.getName().equals("R") || current.getName().equals("Q")) {
                    return true;
                }
                if (current.getName().equals(current.getName().toLowerCase())) {
                    break;
                }
            }
            i++;
        }
        i = 1;
        while (i + king.getCoordinates().getY() < 8) {
            Piece current = game.getPiece(king.getCoordinates().getX(), i + king.getCoordinates().getY());
            if (current != null) {
                if (current.getName().equals("R") || current.getName().equals("Q")) {
                    return true;
                }
                if (current.getName().equals(current.getName().toLowerCase())) {
                    break;
                }
            }
            i++;
        }
        i = 1;
        while (king.getCoordinates().getY() - i > -1) {
            Piece current = game.getPiece(king.getCoordinates().getX(), king.getCoordinates().getY() - i);
            if (current != null) {
                if (current.getName().equals("R") || current.getName().equals("Q")) {
                    return true;
                }
                if (current.getName().equals(current.getName().toLowerCase())) {
                    break;
                }
            }
            i++;
        }
        i = 1;
        while (king.getCoordinates().getX() + i < 8 && king.getCoordinates().getY() + i < 8) {
            Piece current = game.getPiece(king.getCoordinates().getX() + i, king.getCoordinates().getY() + i);
            if (current != null) {
                if (current.getName().equals("B") || current.getName().equals("Q")) {
                    return true;
                }
                if (current.getName().equals(current.getName().toLowerCase())) {
                    break;
                }
            }
            i++;
        }
        i = 1;
        while (king.getCoordinates().getX() + i < 8 && king.getCoordinates().getY() - i > -1) {
            Piece current = game.getPiece(king.getCoordinates().getX() + i, king.getCoordinates().getY() - i);
            if (current != null) {
                if (current.getName().equals("B") || current.getName().equals("Q")) {
                    return true;
                }
                if (current.getName().equals(current.getName().toLowerCase())) {
                    break;
                }
            }
            i++;
        }
        i = 1;
        while (king.getCoordinates().getX() - i > -1 && king.getCoordinates().getY() + i < 8) {
            Piece current = game.getPiece(king.getCoordinates().getX() - i, king.getCoordinates().getY() + i);
            if (current != null) {
                if (current.getName().equals("B") || current.getName().equals("Q")) {
                    return true;
                }
                if (current.getName().equals(current.getName().toLowerCase())) {
                    break;
                }
            }
            i++;
        }
        while (king.getCoordinates().getX() - i > -1 && king.getCoordinates().getY() - i > -1) {
            Piece current = game.getPiece(king.getCoordinates().getX() - i, king.getCoordinates().getY() - i);
            if (current != null) {
                if (current.getName().equals("B") || current.getName().equals("Q")) {
                    return true;
                }
                if (current.getName().equals(current.getName().toLowerCase())) {
                    break;
                }
            }
            i++;
        }
        i = king.getCoordinates().getX();
        int j = king.getCoordinates().getY();
        if (i - 2 > -1) {
            if (j - 1 > -1) {
                if (game.getPiece(i - 2, j - 1) != null) {
                    if (game.getPiece(i - 2, j - 1).getName().equals("N")) {
                        return true;
                    }
                }
            }
            if (j + 1 < 8) {
                if (game.getPiece(i - 2, j + 1) != null) {
                    if (game.getPiece(i - 2, j + 1).getName().equals("N")) {
                        return true;
                    }
                }
            }
        }
        if (i + 2 < 8) {
            if (j - 1 > -1) {
                if (game.getPiece(i + 2, j - 1) != null) {
                    if (game.getPiece(i + 2, j - 1).getName().equals("N")) {
                        return true;
                    }
                }
            }
            if (j + 1 < 8) {
                if (game.getPiece(i + 2, j + 1) != null) {
                    if (game.getPiece(i + 2, j + 1).getName().equals("N")) {
                        return true;
                    }
                }
            }
        }
        if (j + 2 < 8) {
            if (i + 1 < 8) {
                if (game.getPiece(i + 1, j + 2) != null) {
                    if (game.getPiece(i + 1, j + 2).getName().equals("N")) {
                        return true;
                    }
                }
            }
            if (i - 1 > -1) {
                if (game.getPiece(i - 1, j + 2) != null) {
                    if (game.getPiece(i - 1, j + 2).getName().equals("N")) {
                        return true;
                    }
                }
            }
        }
        if (j - 2 > -1) {
            if (i + 1 < 8) {
                if (game.getPiece(i + 1, j - 2) != null) {
                    if (game.getPiece(i + 1, j - 2).getName().equals("N")) {
                        return true;
                    }
                }
            }
            if (i - 1 > -1) {
                if (game.getPiece(i - 1, j - 2) != null) {
                    return game.getPiece(i - 1, j - 2).getName().equals("N");
                }
            }
        }
        return false;
    }

    private PointsInterface findPointInterface() {
        return new NormalCalculate();
    }

    private Move miniMaxDecision(boolean turn, int depth, int alpha, int beta) throws CloneNotSupportedException {
        LinkedHashMap<String, HashSet<Move>> subset = constructCandidates(turn);
        Move best = null;
        Move necessaryMove = null;
        int bestValue = Integer.MIN_VALUE;
        for (String key : subset.keySet()) {
            for (Move move1 : subset.get(key)) {
                int size = checkSize(move1);
                for (int i = 0; i < size; i++) {
                    Coordinates oldCoordinates = (Coordinates) move1.getFrom().getCoordinates().clone();
                    boolean bool = setMove(size, i, move1, false);
                    Piece enPassant = move(move1);
                    Pair<Move, Integer> current = minValue(!turn, depth - 1, alpha, beta);
                    if (current.getValue() > bestValue){
                        if (!(current.getValue() < -900 && Math.abs(stringMap.get(move1.getToCoordinates().toString().charAt(0) + "") - stringMap.get(oldCoordinates.toString().charAt(0) + "")) > 1 && (move1.toString().charAt(0) + "").equals("k"))) {
                            if (!isCheckMate()) {
                                best = move1;
                                bestValue = current.getValue();
                            } else {
                                necessaryMove = current.getKey();
                            }
                        }
                    } else if (current.getValue() == bestValue && current.getValue() == 0) {
                        if (Math.abs(stringMap.get(move1.getToCoordinates().toString().charAt(0) + "") - stringMap.get(oldCoordinates.toString().charAt(0) + "")) > 1 && (move1.toString().charAt(0) + "").equals("k")) {
                            best = move1;
                        }
                    }
                    move1.getFrom().setCoordinates(oldCoordinates);
                    setMove(size, i, move1, bool);
                    undo(move1, enPassant);
                    alpha = Math.max(alpha, current.getValue());
                    if (alpha >= beta) {
                        return best;
                    }
                }
            }
        }
        if (best == null) {
            return necessaryMove;
        }
        return best;
    }

    private Pair<Move, Integer> minValue(boolean turn, int depth, int alpha, int beta) throws CloneNotSupportedException {
        Pair<Move, Integer> best;
        PointsInterface pointsInterface = findPointInterface();
        int point = pointsInterface.calculatePoints(game);
        if (depth == 0 || point > 900 || point < -900) {
            return new Pair<>(null, point);
        } else {
            best = new Pair<>(null, Integer.MAX_VALUE);
            LinkedHashMap<String, HashSet<Move>> subset = constructCandidates(turn);
            for (String key : subset.keySet()) {
                for (Move move1 : subset.get(key)) {
                    int size = checkSize(move1);
                    for (int i = 0; i < size; i++) {
                        Coordinates oldCoordinates = (Coordinates) move1.getFrom().getCoordinates().clone();
                        boolean bool = setMove(size, i, move1, false);
                        Piece enPassant = move(move1);
                        Pair<Move, Integer> current = maxValue(!turn, depth - 1, alpha, beta);
                        if (current.getValue() < best.getValue()){
                            best = current;
                        }
                        move1.getFrom().setCoordinates(oldCoordinates);
                        setMove(size, i, move1, bool);
                        undo(move1, enPassant);
                        beta = Math.min(beta, current.getValue());
                        if (alpha >= beta) {
                            return best;
                        }
                    }
                }
            }
        }
        return best;
    }

    private Pair<Move, Integer> maxValue(boolean turn, int depth, int alpha, int beta) throws CloneNotSupportedException {
        Pair<Move, Integer> best;
        PointsInterface pointsInterface = findPointInterface();
        int point = pointsInterface.calculatePoints(game);
        if (depth == 0 || point > 900 || point < -900) {
            return new Pair<>(null, point);
        } else {
            best = new Pair<>(null, Integer.MIN_VALUE);
            LinkedHashMap<String, HashSet<Move>> subset = constructCandidates(turn);
            for (String key : subset.keySet()) {
                for (Move move1 : subset.get(key)) {
                    int size = checkSize(move1);
                    for (int i = 0; i < size; i++) {
                        Coordinates oldCoordinates = (Coordinates) move1.getFrom().getCoordinates().clone();
                        boolean bool = setMove(size, i, move1, false);
                        Piece enPassant = move(move1);
                        Pair<Move, Integer> current = minValue(!turn, depth - 1, alpha, beta);
                        if (current.getValue() > best.getValue()){
                            best = current;
                        }
                        move1.getFrom().setCoordinates(oldCoordinates);
                        setMove(size, i, move1, bool);
                        undo(move1, enPassant);
                        alpha = Math.max(alpha, current.getValue());
                        if (alpha >= beta) {
                            return best;
                        }
                    }
                }
            }
        }
        return best;
    }

    private void undo(Move move, Piece enPassant) {
        int from1 = move.getToCoordinates().getX();
        int from2 = move.getToCoordinates().getY();
        int to1 = move.getFrom().getCoordinates().getX();
        int to2 = move.getFrom().getCoordinates().getY();
        if (move.getTo() != null) {
            Piece piece = move.getFrom();
            game.setPiece(from1, from2, null);
            game.setPiece(to1, to2, piece);
            game.setPiece(from1, from2, move.getTo());
        } else {
            Piece piece = move.getFrom();
            game.setPiece(from1, from2, null);
            game.setPiece(to1, to2, piece);
        }
        if (enPassant != null) {
            game.setPiece(enPassant.getCoordinates().getX(), enPassant.getCoordinates().getY(), enPassant);
        }
    }

    private Piece move(Move move) {
        int currentI = move.getFrom().getCoordinates().getX();
        int currentJ = move.getFrom().getCoordinates().getY();
        Piece piece = move.getFrom();
        piece.setCoordinates(new Coordinates(move.getToCoordinates().getX(), move.getToCoordinates().getY()));
        game.setPiece(currentI, currentJ, null);
        game.setPiece(move.getToCoordinates().getX(), move.getToCoordinates().getY(), piece);
        if (currentI == 3 && move.getFrom().getName().equals("P") && Math.abs(currentJ - move.getToCoordinates().getY()) > 0 && move.getTo() == null) {
            if (currentJ - 1 > -1) {
                Piece temporary = game.getPiece(currentI, currentJ - 1);
                if (temporary != null && temporary.getName().equals("p")) {
                    game.setPiece(currentI, currentJ - 1, null);
                    return temporary;
                }
            }
            if (currentJ + 1 < 8) {
                Piece temporary = game.getPiece(currentI, currentJ + 1);
                if (temporary != null && temporary.getName().equals("p")) {
                    game.setPiece(currentI, currentJ + 1, null);
                    return temporary;
                }
            }
        }
        if (currentI == 4 && move.getFrom().getName().equals("p") && Math.abs(currentJ - move.getToCoordinates().getY()) > 0 && move.getTo() == null) {
            if (currentJ - 1 > -1) {
                Piece temporary = game.getPiece(currentI, currentJ - 1);
                if (temporary != null && temporary.getName().equals("P")) {
                    game.setPiece(currentI, currentJ - 1, null);
                    return temporary;
                }
            }
            if (currentJ + 1 < 8) {
                Piece temporary = game.getPiece(currentI, currentJ + 1);
                if (temporary != null && temporary.getName().equals("P")) {
                    game.setPiece(currentI, currentJ + 1, null);
                    return temporary;
                }
            }
        }
        return null;
    }

    private Pair<Boolean, String[]> isOpeningsContainBoardOrder() {
        boolean check;
        for (String[] key : openings.keySet()) {
            check = true;
            for (String current : key) {
                if (game.getPiece(Game.INTEGER_MAP.get(Integer.parseInt(current.charAt(2) + "")), stringMap.get(current.charAt(1) + "")) != null) {
                    if (!game.getPiece(Game.INTEGER_MAP.get(Integer.parseInt(current.charAt(2) + "")), stringMap.get(current.charAt(1) + "")).getName().equals(current.charAt(0) + "")) {
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
            return new Move(game.getPiece(Game.INTEGER_MAP.get(Integer.parseInt(pair.getValue()[0].charAt(1) + "")), stringMap.get(pair.getValue()[0].charAt(0) + "")), game.getPiece(Game.INTEGER_MAP.get(Integer.parseInt(pair.getValue()[1].charAt(1) + "")), stringMap.get(pair.getValue()[1].charAt(0) + "")), new Coordinates(Game.INTEGER_MAP.get(Integer.parseInt(pair.getValue()[1].charAt(1) + "")), stringMap.get(pair.getValue()[1].charAt(0) + "")));
        }
        return miniMaxDecision(false, MAX_DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
}