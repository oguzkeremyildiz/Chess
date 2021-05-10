package Chess;

import Chess.Piece.*;

import javax.swing.*;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;

public class Game {

    private Piece[][] board;
    private LinkedList<String> moves;
    public static final Map<Integer, Integer> INTEGER_MAP = Map.of(8, 0, 7, 1, 6,2, 5, 3, 4, 4, 3, 5, 2, 6, 1, 7, 0, 8);

    public Game() {
        moves = new LinkedList<>();
        board = new Piece[8][8];
    }

    public boolean finished() {
        boolean bool1 = false;
        boolean bool2 = false;
        for (Piece[] pieces : board) {
            for (int j = 0; j < board[0].length; j++) {
                if (pieces[j] != null && pieces[j].getName().equals(PieceName.K) && pieces[j].color()) {
                    bool1 = true;
                }
                if (pieces[j] != null && pieces[j].getName().equals(PieceName.K) && !pieces[j].color()) {
                    bool2 = true;
                }
            }
        }
        if (!bool1) {
            if (UIFrame.model != null) {
                if (!UIFrame.model.contains("black wins.")) {
                    UIFrame.model.addElement("black wins.");
                }
            } else {
                System.out.println("black wins.");
            }
        }
        if (!bool2) {
            if (UIFrame.model != null) {
                if (!UIFrame.model.contains("white wins.")) {
                    UIFrame.model.addElement("white wins.");
                }
            } else {
                System.out.println("white wins.");
            }
        }
        return bool1 && bool2;
    }

    public String getLastMove() {
        if (!moves.isEmpty()) {
            return moves.getLast();
        }
        return null;
    }

    public void addMove(String move) {
        moves.add(move);
    }

    public Piece getPiece(int i, int j) {
        return board[i][j];
    }

    public void setPiece(int i, int j, Piece piece) {
        board[i][j] = piece;
    }

    private boolean findBoolean(String str) {
        if (str.equals("false")) {
            return false;
        }
        return true;
    }

    private PieceName findPieceName(String str) {
        switch (str) {
            case "P":
                return PieceName.P;
            case "N":
                return PieceName.N;
            case "B":
                return PieceName.B;
            case "R":
                return PieceName.R;
            case "Q":
                return PieceName.Q;
            case "K":
                return PieceName.K;
        }
        return null;
    }

    private Coordinates findCoordinates(String str) {
        if (str.equals("null")) {
            return null;
        }
        return new Coordinates(str.charAt(0), str.charAt(1));
    }

    public void setBoard(Scanner source, DefaultListModel<String> model) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                setPiece(i, j, null);
            }
        }
        while (source.hasNext()) {
            String line = source.nextLine();
            if (line.equals("")) {
                break;
            }
            String[] array = line.split(" ");
            board[Integer.parseInt(array[0])][Integer.parseInt(array[1])] = new Piece(findBoolean(array[3]), findPieceName(array[2]), findCoordinates(array[4]));
        }
        while (source.hasNext()) {
            String line = source.nextLine();
            model.addElement(line);
        }
    }

    public void setBoard() {
        /* blacks */
        board[0][0] = new Piece(false, PieceName.R, null);
        board[0][7] = new Piece(false, PieceName.R, null);
        board[0][3] = new Piece(false, PieceName.Q, null);
        board[0][4] = new Piece(false, PieceName.K, null);
        board[0][2] = new Piece(false, PieceName.B, null);
        board[0][5] = new Piece(false, PieceName.B, null);
        board[0][1] = new Piece(false, PieceName.N, null);
        board[0][6] = new Piece(false, PieceName.N, null);
        for (int i = 0; i < board[0].length; i++) {
            board[1][i] = new Piece(false, PieceName.P, null);
        }
        /* whites */
        board[7][0] = new Piece(true, PieceName.R, null);
        board[7][7] = new Piece(true, PieceName.R, null);
        board[7][3] = new Piece(true, PieceName.Q, null);
        board[7][4] = new Piece(true, PieceName.K, null);
        board[7][2] = new Piece(true, PieceName.B, null);
        board[7][5] = new Piece(true, PieceName.B, null);
        board[7][1] = new Piece(true, PieceName.N, null);
        board[7][6] = new Piece(true, PieceName.N, null);
        for (int i = 0; i < board[0].length; i++) {
            board[6][i] = new Piece(true, PieceName.P, null);
        }
    }

    @Override
    public int hashCode() {
        StringBuilder str = new StringBuilder();
        for (Piece[] pieces : this.board) {
            for (int j = 0; j < this.board[0].length; j++) {
                str.append(" ").append(pieces[j].hashCode());
            }
        }
        return str.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Game)) {
            return false;
        }
        Game game = (Game) obj;
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[0].length; j++) {
                if (!this.board[i][j].equals(game.getPiece(i, j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
