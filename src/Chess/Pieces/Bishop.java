package Chess.Pieces;/* Created by oguzkeremyildiz on 24.11.2020 */

import Chess.Game;
import Chess.Coordinates;

public class Bishop extends Piece {

    public Bishop(Coordinates coordinates, boolean color, String name) {
        super(coordinates, color, name);
    }

    @Override
    public void calculateAllPossibleMoves(Game game, int i, int j) {
        getPossibles().clear();
        int iterate = 1;
        while (j - iterate > -1 && i - iterate > -1) {
            if (game.getBoard()[i - iterate][j - iterate] == null || game.getBoard()[i - iterate][j - iterate].isTurn() != isTurn()) {
                getPossibles().add(new Coordinates(i - iterate, j - iterate));
                if (game.getBoard()[i - iterate][j - iterate] != null && game.getBoard()[i - iterate][j - iterate].isTurn() != isTurn()) {
                    break;
                }
            } else {
                break;
            }
            iterate++;
        }
        iterate = 1;
        while (j + iterate < 8 && i - iterate > -1) {
            if (game.getBoard()[i - iterate][j + iterate] == null || game.getBoard()[i - iterate][j + iterate].isTurn() != isTurn()) {
                getPossibles().add(new Coordinates(i - iterate, j + iterate));
                if (game.getBoard()[i - iterate][j + iterate] != null && game.getBoard()[i - iterate][j + iterate].isTurn() != isTurn()) {
                    break;
                }
            } else {
                break;
            }
            iterate++;
        }
        iterate = 1;
        while (j + iterate < 8 && i + iterate < 8) {
            if (game.getBoard()[i + iterate][j + iterate] == null || game.getBoard()[i + iterate][j + iterate].isTurn() != isTurn()) {
                getPossibles().add(new Coordinates(i + iterate, j + iterate));
                if (game.getBoard()[i + iterate][j + iterate] != null && game.getBoard()[i + iterate][j + iterate].isTurn() != isTurn()) {
                    break;
                }
            } else {
                break;
            }
            iterate++;
        }
        iterate = 1;
        while (j - iterate > -1 && i + iterate < 8) {
            if (game.getBoard()[i + iterate][j - iterate] == null || game.getBoard()[i + iterate][j - iterate].isTurn() != isTurn()) {
                getPossibles().add(new Coordinates(i + iterate, j - iterate));
                if (game.getBoard()[i + iterate][j - iterate] != null && game.getBoard()[i + iterate][j - iterate].isTurn() != isTurn()) {
                    break;
                }
            } else {
                break;
            }
            iterate++;
        }
    }

    @Override
    public boolean play(Game game, Coordinates to) {
        int i = getCoordinates().getX();
        int j = getCoordinates().getY();
        calculateAllPossibleMoves(game, i, j);
        if (getPossibles().contains(to)) {
            Piece current = game.getBoard()[i][j];
            game.getBoard()[i][j] = null;
            game.getBoard()[to.getX()][to.getY()] = current;
            setCoordinates(to);
        }
        return getPossibles().contains(to);
    }
}
