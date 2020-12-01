package Chess.Pieces;/* Created by oguzkeremyildiz on 24.11.2020 */

import Chess.Game;
import Chess.Coordinates;

public class Knight extends Piece {

    public Knight(Coordinates coordinates, boolean color, String name) {
        super(coordinates, color, name);
    }

    @Override
    public void calculateAllPossibleMoves(Game game, int i, int j) {
        getPossibles().clear();
        if (i - 2 > -1) {
            if (j - 1 > -1) {
                if (game.getPiece(i - 2, j - 1) != null) {
                    if (!(game.getPiece(i - 2, j - 1).isTurn() == isTurn())) {
                        getPossibles().add(new Coordinates(i - 2, j - 1));
                    }
                } else {
                    getPossibles().add(new Coordinates(i - 2, j - 1));
                }
            }
            if (j + 1 < 8) {
                if (game.getPiece(i - 2, j + 1) != null) {
                    if (!(game.getPiece(i - 2, j + 1).isTurn() == isTurn())) {
                        getPossibles().add(new Coordinates(i - 2, j + 1));
                    }
                } else {
                    getPossibles().add(new Coordinates(i - 2, j + 1));
                }
            }
        }
        if (i + 2 < 8) {
            if (j - 1 > -1) {
                if (game.getPiece(i + 2, j - 1) != null) {
                    if (!(game.getPiece(i + 2, j - 1).isTurn() == isTurn())) {
                        getPossibles().add(new Coordinates(i + 2, j - 1));
                    }
                } else {
                    getPossibles().add(new Coordinates(i + 2, j - 1));
                }
            }
            if (j + 1 < 8) {
                if (game.getPiece(i + 2, j + 1) != null) {
                    if (!(game.getPiece(i + 2, j + 1).isTurn() == isTurn())) {
                        getPossibles().add(new Coordinates(i + 2, j + 1));
                    }
                } else {
                    getPossibles().add(new Coordinates(i + 2, j + 1));
                }
            }
        }
        if (j + 2 < 8) {
            if (i + 1 < 8) {
                if (game.getPiece(i + 1, j + 2) != null) {
                    if (!(game.getPiece(i + 1, j + 2).isTurn() == isTurn())) {
                        getPossibles().add(new Coordinates(i + 1, j + 2));
                    }
                } else {
                    getPossibles().add(new Coordinates(i + 1, j + 2));
                }
            }
            if (i - 1 > -1) {
                if (game.getPiece(i - 1, j + 2) != null) {
                    if (!(game.getPiece(i - 1, j + 2).isTurn() == isTurn())) {
                        getPossibles().add(new Coordinates(i - 1, j + 2));
                    }
                } else {
                    getPossibles().add(new Coordinates(i - 1, j + 2));
                }
            }
        }
        if (j - 2 > -1) {
            if (i + 1 < 8) {
                if (game.getPiece(i + 1, j - 2) != null) {
                    if (!(game.getPiece(i + 1, j - 2).isTurn() == isTurn())) {
                        getPossibles().add(new Coordinates(i + 1, j - 2));
                    }
                } else {
                    getPossibles().add(new Coordinates(i + 1, j - 2));
                }
            }
            if (i - 1 > -1) {
                if (game.getPiece(i - 1, j - 2) != null) {
                    if (!(game.getPiece(i - 1, j - 2).isTurn() == isTurn())) {
                        getPossibles().add(new Coordinates(i - 1, j - 2));
                    }
                } else {
                    getPossibles().add(new Coordinates(i - 1, j - 2));
                }
            }
        }
    }

    @Override
    public void play(Game game, Coordinates to) {
        int i = getCoordinates().getX();
        int j = getCoordinates().getY();
        calculateAllPossibleMoves(game, i, j);
        if (getPossibles().contains(to)) {
            Piece current = game.getPiece(i, j);
            game.setPiece(i, j, null);
            game.setPiece(to.getX(), to.getY(), current);
            setCoordinates(to);
        }
    }
}
