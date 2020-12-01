package Chess.Pieces;

import Chess.Game;
import Chess.Coordinates;

public class King extends Piece {

    public King(Coordinates coordinates, boolean color, String name) {
        super(coordinates, color, name);
    }

    public void calculateAllPossibleMoves(Game game, int i, int j) {
        getPossibles().clear();
        if (i + 1 < 8) {
            if (game.getBoard()[i + 1][j] == null || !(game.getBoard()[i + 1][j].isTurn() == isTurn())) {
                getPossibles().add(new Coordinates(i + 1, j));
            }
            if (j + 1 < 8 && (game.getBoard()[i + 1][j + 1] == null || !(game.getBoard()[i + 1][j + 1].isTurn() == isTurn()))) {
                getPossibles().add(new Coordinates(i + 1, j + 1));
            }
            if (j - 1 > -1 && (game.getBoard()[i + 1][j - 1] == null || !(game.getBoard()[i + 1][j - 1].isTurn() == isTurn()))) {
                getPossibles().add(new Coordinates(i + 1, j - 1));
            }
        }
        if (i - 1 > -1) {
            if (game.getBoard()[i - 1][j] == null || !(game.getBoard()[i - 1][j].isTurn() == isTurn())) {
                getPossibles().add(new Coordinates(i - 1, j));
            }
            if (j + 1 < 8 && (game.getBoard()[i - 1][j + 1] == null || !(game.getBoard()[i - 1][j + 1].isTurn() == isTurn()))) {
                getPossibles().add(new Coordinates(i - 1, j + 1));
            }
            if (j - 1 > -1 && (game.getBoard()[i - 1][j - 1] == null || !(game.getBoard()[i - 1][j - 1].isTurn() == isTurn()))) {
                getPossibles().add(new Coordinates(i - 1, j - 1));
            }
        }
        if (j + 1 < 8 && (game.getBoard()[i][j + 1] == null || !(game.getBoard()[i][j + 1].isTurn() == isTurn()))) {
            getPossibles().add(new Coordinates(i, j + 1));
        }
        if (j - 1 > -1 && (game.getBoard()[i][j - 1] == null || !(game.getBoard()[i][j - 1].isTurn() == isTurn()))) {
            getPossibles().add(new Coordinates(i, j - 1));
        }
        if (isTurn()) {
            if (i == 7) {
                if (j == 4 && game.getBoard()[7][7] != null && game.getBoard()[7][7].getName().equals("R") && game.getBoard()[7][5] == null && game.getBoard()[7][6] == null) {
                    getPossibles().add(new Coordinates(7, 6));
                }
                if (j == 4 && game.getBoard()[7][0] != null && game.getBoard()[7][0].getName().equals("R") && game.getBoard()[7][1] == null && game.getBoard()[7][2] == null && game.getBoard()[7][3] == null) {
                    getPossibles().add(new Coordinates(7, 2));
                }
                if (j == 3 && game.getBoard()[7][7] != null && game.getBoard()[7][7].getName().equals("R") && game.getBoard()[7][4] == null && game.getBoard()[7][5] == null && game.getBoard()[7][6] == null) {
                    getPossibles().add(new Coordinates(7, 5));
                }
                if (j == 3 && game.getBoard()[7][0] != null && game.getBoard()[7][0].getName().equals("R") && game.getBoard()[7][1] == null && game.getBoard()[7][2] == null) {
                    getPossibles().add(new Coordinates(7, 1));
                }
            }
        } else {
            if (i == 0) {
                if (j == 4 && game.getBoard()[0][7] != null && game.getBoard()[0][7].getName().equals("R") && game.getBoard()[0][5] == null && game.getBoard()[0][6] == null) {
                    getPossibles().add(new Coordinates(0, 6));
                }
                if (j == 4 && game.getBoard()[0][0] != null && game.getBoard()[0][0].getName().equals("R") && game.getBoard()[0][1] == null && game.getBoard()[0][2] == null && game.getBoard()[0][3] == null) {
                    getPossibles().add(new Coordinates(0, 2));
                }
                if (j == 3 && game.getBoard()[0][7] != null && game.getBoard()[0][7].getName().equals("R") && game.getBoard()[0][4] == null && game.getBoard()[0][5] == null && game.getBoard()[0][6] == null) {
                    getPossibles().add(new Coordinates(0, 5));
                }
                if (j == 3 && game.getBoard()[0][0] != null && game.getBoard()[0][0].getName().equals("R") && game.getBoard()[0][1] == null && game.getBoard()[0][2] == null) {
                    getPossibles().add(new Coordinates(0, 1));
                }
            }
        }
    }

    private boolean castling(Game game, Coordinates to, int i, int j) {
        if (isTurn()) {
            if (i == 7) {
                if (to.getX() == 7 && to.getY() == 6 && j == 4 && game.getBoard()[7][7] != null && game.getBoard()[7][7].getName().equals("R") && game.getBoard()[7][5] == null && game.getBoard()[7][6] == null) {
                    Piece king = game.getBoard()[i][j];
                    Piece rook = game.getBoard()[7][7];
                    game.getBoard()[i][j] = null;
                    game.getBoard()[7][7] = null;
                    rook.setCoordinates(new Coordinates(7, 5));
                    king.setCoordinates(new Coordinates(7, 6));
                    game.getBoard()[7][5] = rook;
                    game.getBoard()[7][6] = king;
                    return true;
                }
                if (to.getX() == 7 && to.getY() == 2 && j == 4 && game.getBoard()[7][0] != null && game.getBoard()[7][0].getName().equals("R") && game.getBoard()[7][1] == null && game.getBoard()[7][2] == null && game.getBoard()[7][3] == null) {
                    Piece king = game.getBoard()[i][j];
                    Piece rook = game.getBoard()[7][0];
                    game.getBoard()[i][j] = null;
                    game.getBoard()[7][0] = null;
                    rook.setCoordinates(new Coordinates(7, 3));
                    king.setCoordinates(new Coordinates(7, 2));
                    game.getBoard()[7][3] = rook;
                    game.getBoard()[7][2] = king;
                    return true;
                }
                if (to.getX() == 7 && to.getY() == 5 && j == 3 && game.getBoard()[7][7] != null && game.getBoard()[7][7].getName().equals("R") && game.getBoard()[7][4] == null && game.getBoard()[7][5] == null && game.getBoard()[7][6] == null) {
                    Piece king = game.getBoard()[i][j];
                    Piece rook = game.getBoard()[7][7];
                    game.getBoard()[i][j] = null;
                    game.getBoard()[7][7] = null;
                    king.setCoordinates(new Coordinates(7, 5));
                    rook.setCoordinates(new Coordinates(7, 4));
                    game.getBoard()[7][4] = rook;
                    game.getBoard()[7][5] = king;
                    return true;
                }
                if (to.getX() == 7 && to.getY() == 1 && j == 3 && game.getBoard()[7][0] != null && game.getBoard()[7][0].getName().equals("R") && game.getBoard()[7][1] == null && game.getBoard()[7][2] == null) {
                    Piece king = game.getBoard()[i][j];
                    Piece rook = game.getBoard()[7][0];
                    game.getBoard()[i][j] = null;
                    game.getBoard()[7][0] = null;
                    rook.setCoordinates(new Coordinates(7, 2));
                    king.setCoordinates(new Coordinates(7, 1));
                    game.getBoard()[7][2] = rook;
                    game.getBoard()[7][1] = king;
                    return true;
                }
            }
        } else {
            if (i == 0) {
                if (to.getX() == 0 && to.getY() == 6 && j == 4 && game.getBoard()[0][7] != null && game.getBoard()[0][7].getName().equals("r") && game.getBoard()[0][5] == null && game.getBoard()[0][6] == null) {
                    Piece king = game.getBoard()[i][j];
                    Piece rook = game.getBoard()[0][7];
                    game.getBoard()[i][j] = null;
                    game.getBoard()[0][7] = null;
                    rook.setCoordinates(new Coordinates(0, 5));
                    king.setCoordinates(new Coordinates(0, 6));
                    game.getBoard()[0][5] = rook;
                    game.getBoard()[0][6] = king;
                    return true;
                }
                if (to.getX() == 0 && to.getY() == 2 && j == 4 && game.getBoard()[0][0] != null && game.getBoard()[0][0].getName().equals("r") && game.getBoard()[0][1] == null && game.getBoard()[0][2] == null && game.getBoard()[0][3] == null) {
                    Piece king = game.getBoard()[i][j];
                    Piece rook = game.getBoard()[0][0];
                    game.getBoard()[i][j] = null;
                    game.getBoard()[0][0] = null;
                    rook.setCoordinates(new Coordinates(0, 3));
                    king.setCoordinates(new Coordinates(0, 2));
                    game.getBoard()[0][3] = rook;
                    game.getBoard()[0][2] = king;
                    return true;
                }
                if (to.getX() == 0 && to.getY() == 5 && j == 3 && game.getBoard()[0][7] != null && game.getBoard()[0][7].getName().equals("r") && game.getBoard()[0][4] == null && game.getBoard()[0][5] == null && game.getBoard()[0][6] == null) {
                    Piece king = game.getBoard()[i][j];
                    Piece rook = game.getBoard()[0][7];
                    game.getBoard()[i][j] = null;
                    game.getBoard()[0][7] = null;
                    king.setCoordinates(new Coordinates(0, 5));
                    rook.setCoordinates(new Coordinates(0, 4));
                    game.getBoard()[0][4] = rook;
                    game.getBoard()[0][5] = king;
                    return true;
                }
                if (to.getX() == 0 && to.getY() == 1 && j == 3 && game.getBoard()[0][0] != null && game.getBoard()[0][0].getName().equals("r") && game.getBoard()[0][1] == null && game.getBoard()[0][2] == null) {
                    Piece king = game.getBoard()[i][j];
                    Piece rook = game.getBoard()[0][0];
                    game.getBoard()[i][j] = null;
                    game.getBoard()[0][0] = null;
                    rook.setCoordinates(new Coordinates(0, 2));
                    king.setCoordinates(new Coordinates(0, 1));
                    game.getBoard()[0][2] = rook;
                    game.getBoard()[0][1] = king;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean check(Coordinates to, Game game, int j) {
        return to.getX() == 7 && to.getY() == 6 && j == 4 && game.getBoard()[7][7] != null && game.getBoard()[7][7].getName().equals("R") && game.getBoard()[7][5] == null && game.getBoard()[7][6] == null || to.getX() == 7 && to.getY() == 2 && j == 4 && game.getBoard()[7][0] != null && game.getBoard()[7][0].getName().equals("R") && game.getBoard()[7][1] == null && game.getBoard()[7][2] == null && game.getBoard()[7][3] == null || to.getX() == 7 && to.getY() == 5 && j == 3 && game.getBoard()[7][7] != null && game.getBoard()[7][7].getName().equals("R") && game.getBoard()[7][4] == null && game.getBoard()[7][5] == null && game.getBoard()[7][6] == null || to.getX() == 7 && to.getY() == 1 && j == 3 && game.getBoard()[7][0] != null && game.getBoard()[7][0].getName().equals("R") && game.getBoard()[7][1] == null && game.getBoard()[7][2] == null || to.getX() == 0 && to.getY() == 6 && j == 4 && game.getBoard()[0][7] != null && game.getBoard()[0][7].getName().equals("r") && game.getBoard()[0][5] == null && game.getBoard()[0][6] == null || to.getX() == 0 && to.getY() == 2 && j == 4 && game.getBoard()[0][0] != null && game.getBoard()[0][0].getName().equals("r") && game.getBoard()[0][1] == null && game.getBoard()[0][2] == null && game.getBoard()[0][3] == null || to.getX() == 0 && to.getY() == 5 && j == 3 && game.getBoard()[0][7] != null && game.getBoard()[0][7].getName().equals("r") && game.getBoard()[0][4] == null && game.getBoard()[0][5] == null && game.getBoard()[0][6] == null || to.getX() == 0 && to.getY() == 1 && j == 3 && game.getBoard()[0][0] != null && game.getBoard()[0][0].getName().equals("r") && game.getBoard()[0][1] == null && game.getBoard()[0][2] == null;
    }

    @Override
    public boolean play(Game game, Coordinates to) {
        int i = getCoordinates().getX();
        int j = getCoordinates().getY();
        calculateAllPossibleMoves(game, i, j);
        if (getPossibles().contains(to) && !check(to, game, j)) {
            Piece current = game.getBoard()[i][j];
            game.getBoard()[i][j] = null;
            game.getBoard()[to.getX()][to.getY()] = current;
            setCoordinates(to);
        } else {
            if (castling(game, to, i, j)) {
                return true;
            }
        }
        return getPossibles().contains(to);
    }
}
