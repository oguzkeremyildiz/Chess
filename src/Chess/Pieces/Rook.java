package Chess.Pieces;/* Created by oguzkeremyildiz on 24.11.2020 */

import Chess.Game;
import Chess.Coordinates;

public class Rook extends Piece {

    public Rook(Coordinates coordinates, boolean color, String name) {
        super(coordinates, color, name);
    }

    @Override
    public void calculateAllPossibleMoves(Game game, int i, int j) {
        getPossibles().clear();
        int iterate = 1;
        while (i + iterate < 8) {
            if (game.getBoard()[i + iterate][j] == null || !(game.getBoard()[i + iterate][j].isTurn() == isTurn())) {
                getPossibles().add(new Coordinates(i + iterate, j));
            }
            if (game.getBoard()[i + iterate][j] != null) {
                break;
            }
            iterate++;
        }
        iterate = 1;
        while (i - iterate > -1) {
            if (game.getBoard()[i - iterate][j] == null || !(game.getBoard()[i - iterate][j].isTurn() == isTurn())) {
                getPossibles().add(new Coordinates(i - iterate, j));
            }
            if (game.getBoard()[i - iterate][j] != null) {
                break;
            }
            iterate++;
        }
        iterate = 1;
        while (j + iterate < 8) {
            if (game.getBoard()[i][j + iterate] == null || !(game.getBoard()[i][j + iterate].isTurn() == isTurn())) {
                getPossibles().add(new Coordinates(i, j + iterate));
            }
            if (game.getBoard()[i][j + iterate] != null) {
                break;
            }
            iterate++;
        }
        iterate = 1;
        while (j - iterate > -1) {
            if (game.getBoard()[i][j - iterate] == null || !(game.getBoard()[i][j - iterate].isTurn() == isTurn())) {
                getPossibles().add(new Coordinates(i, j - iterate));
            }
            if (game.getBoard()[i][j - iterate] != null) {
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
