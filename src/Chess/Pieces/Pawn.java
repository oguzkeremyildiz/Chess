package Chess.Pieces;

import Chess.Game;
import Chess.Coordinates;

import java.util.Scanner;

public class Pawn extends Piece {

   public Pawn(Coordinates coordinates, boolean color, String name) {
       super(coordinates, color, name);
   }

    @Override
    public void calculateAllPossibleMoves(Game game, int i, int j) {
       getPossibles().clear();
       if (isTurn()) {
           if (i - 1 > -1) {
               if (game.getBoard()[i - 1][j] == null) {
                   getPossibles().add(new Coordinates(i - 1, j));
               }
               if (j - 1 > -1 && game.getBoard()[i - 1][j - 1] != null && !(game.getBoard()[i - 1][j - 1].isTurn() == isTurn())) {
                   getPossibles().add(new Coordinates(i - 1, j - 1));
               }
               if (j + 1 < 8 && game.getBoard()[i - 1][j + 1] != null && !(game.getBoard()[i - 1][j + 1].isTurn() == isTurn())) {
                   getPossibles().add(new Coordinates(i - 1, j + 1));
               }
           }
           if (i == 6 && game.getBoard()[4][j] == null && game.getBoard()[5][j] == null) {
               getPossibles().add(new Coordinates(4, j));
           }
       } else {
           if (i + 1 < 8) {
               if (game.getBoard()[i + 1][j] == null) {
                   getPossibles().add(new Coordinates(i + 1, j));
               }
               if (j - 1 > -1 && game.getBoard()[i + 1][j - 1] != null && !(game.getBoard()[i + 1][j - 1].isTurn() == isTurn())) {
                   getPossibles().add(new Coordinates(i + 1, j - 1));
               }
               if (j + 1 < 8 && game.getBoard()[i + 1][j + 1] != null && !(game.getBoard()[i + 1][j + 1].isTurn() == isTurn())) {
                   getPossibles().add(new Coordinates(i + 1, j + 1));
               }
           }
           if (i == 1 && game.getBoard()[3][j] == null && game.getBoard()[2][j] == null) {
               getPossibles().add(new Coordinates(3, j));
           }
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
            if (isTurn()) {
                if (current.getCoordinates().getX() == 0) {
                    Scanner scanner = new Scanner(System.in);
                    System.out.println("Choose a piece. (bishop, queen ext.)");
                    String piece = scanner.next();
                    switch (piece) {
                        case "bishop":
                            game.getBoard()[to.getX()][to.getY()] = new Bishop(to, true, "B");
                            break;
                        case "knight":
                            game.getBoard()[to.getX()][to.getY()] = new Knight(to, true, "N");
                            break;
                        case "queen":
                            game.getBoard()[to.getX()][to.getY()] = new Queen(to, true, "Q");
                            break;
                        case "rook":
                            game.getBoard()[to.getX()][to.getY()] = new Rook(to, true, "R");
                            break;
                        default:
                            break;
                    }
                }
            } else {
                if (current.getCoordinates().getX() == 7) {
                    game.getBoard()[to.getX()][to.getY()] = new Queen(to, false, "q");
                }
            }
        }
        return getPossibles().contains(to);
    }
}
