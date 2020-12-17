package Chess;

import Chess.Pieces.*;

import java.util.Map;

public class Game {

    private Piece[][] board;
    public static final Map<Integer, Integer> INTEGER_MAP = Map.of(8, 0, 7, 1, 6,2, 5, 3, 4, 4, 3, 5, 2, 6, 1, 7, 0, 8);

    public Game() {
        board = new Piece[8][8];
    }

    public boolean finished() {
        boolean bool1 = false;
        boolean bool2 = false;
        for (Piece[] pieces : board) {
            for (int j = 0; j < board[0].length; j++) {
                if (pieces[j] != null && pieces[j].getName().equalsIgnoreCase("K") && pieces[j].isTurn()) {
                    bool1 = true;
                }
                if (pieces[j] != null && pieces[j].getName().equalsIgnoreCase("K") && !pieces[j].isTurn()) {
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

    public Piece getPiece(int i, int j) {
        return board[i][j];
    }

    public void setPiece(int i, int j, Piece piece) {
        board[i][j] = piece;
    }

    public void setEnPassant(boolean turn) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (getPiece(i, j) != null && getPiece(i, j).toString().equals("Pawn") && getPiece(i, j).isTurn() == turn) {
                    Pawn pawn = ((Pawn) getPiece(i, j));
                    if (pawn.getEnPassant()) {
                        pawn.setEnPassant();
                    } else if (!pawn.getStop()) {
                        if (turn) {
                            if (i == 3) {
                                if (j - 1 > -1) {
                                    if (getPiece(i, j - 1) != null && getPiece(i, j - 1).getName().equals("p") && getPiece(i - 1, j - 1) == null) {
                                        pawn.setEnPassant();
                                        pawn.setStop();
                                    }
                                }
                                if (j + 1 < 8) {
                                    if (getPiece(i, j + 1) != null && getPiece(i, j + 1).getName().equals("p") && getPiece(i - 1, j + 1) == null) {
                                        pawn.setEnPassant();
                                        pawn.setStop();
                                    }
                                }
                            }
                        } else {
                            if (i == 4) {
                                if (j - 1 > -1) {
                                    if (getPiece(i, j - 1) != null && getPiece(i, j - 1).getName().equals("P") && getPiece(i + 1, j - 1) == null) {
                                        pawn.setEnPassant();
                                        pawn.setStop();
                                    }
                                }
                                if (j + 1 < 8) {
                                    if (getPiece(i, j + 1) != null && getPiece(i, j + 1).getName().equals("P") && getPiece(i + 1, j + 1) == null) {
                                        pawn.setEnPassant();
                                        pawn.setStop();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void setBoard() {
        /* blacks */
        board[0][0] = new Rook(new Coordinates(0, 0), false, "r");
        board[0][7] = new Rook(new Coordinates(0, 7), false, "r");
        board[0][3] = new Queen(new Coordinates(0, 3), false, "q");
        board[0][4] = new King(new Coordinates(0, 4), false, "k");
        board[0][2] = new Bishop(new Coordinates(0, 2), false, "b");
        board[0][5] = new Bishop(new Coordinates(0, 5), false, "b");
        board[0][1] = new Knight(new Coordinates(0, 1), false, "n");
        board[0][6] = new Knight(new Coordinates(0, 6), false, "n");
        for (int i = 0; i < board[0].length; i++) {
            board[1][i] = new Pawn(new Coordinates(1, i), false, "p");
        }
        /* whites */
        board[7][0] = new Rook(new Coordinates(7, 0), true, "R");
        board[7][7] = new Rook(new Coordinates(7, 7), true, "R");
        board[7][3] = new Queen(new Coordinates(7, 3), true, "Q");
        board[7][4] = new King(new Coordinates(7, 4), true, "K");
        board[7][2] = new Bishop(new Coordinates(7, 2), true, "B");
        board[7][5] = new Bishop(new Coordinates(7, 5), true, "B");
        board[7][1] = new Knight(new Coordinates(7, 1), true, "N");
        board[7][6] = new Knight(new Coordinates(7, 6), true, "N");
        for (int i = 0; i < board[0].length; i++) {
            board[6][i] = new Pawn(new Coordinates(6, i), true, "P");
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
