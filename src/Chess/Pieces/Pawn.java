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
               if (game.getPiece(i - 1, j) == null) {
                   getPossibles().add(new Coordinates(i - 1, j));
               }
               if (j - 1 > -1 && game.getPiece(i - 1, j - 1) != null && !(game.getPiece(i - 1, j - 1).isTurn() == isTurn())) {
                   getPossibles().add(new Coordinates(i - 1, j - 1));
               }
               if (j + 1 < 8 && game.getPiece(i - 1, j + 1) != null && !(game.getPiece(i - 1, j + 1).isTurn() == isTurn())) {
                   getPossibles().add(new Coordinates(i - 1, j + 1));
               }
           }
           if (i == 6 && game.getPiece(4, j) == null && game.getPiece(5, j) == null) {
               getPossibles().add(new Coordinates(4, j));
           }
           if (i == 3) {
               if (j - 1 > -1) {
                   if (game.getPiece(i, j - 1) != null && game.getPiece(i, j - 1).getName().equals("p") && game.getPiece(i - 1, j - 1) == null) {
                       getPossibles().add(new Coordinates(i - 1, j - 1));
                   }
               }
               if (j + 1 < 8) {
                   if (game.getPiece(i, j + 1) != null && game.getPiece(i, j + 1).getName().equals("p") && game.getPiece(i - 1, j + 1) == null) {
                       getPossibles().add(new Coordinates(i - 1, j + 1));
                   }
               }
           }
       } else {
           if (i + 1 < 8) {
               if (game.getPiece(i + 1, j) == null) {
                   getPossibles().add(new Coordinates(i + 1, j));
               }
               if (j - 1 > -1 && game.getPiece(i + 1, j - 1) != null && !(game.getPiece(i + 1, j - 1).isTurn() == isTurn())) {
                   getPossibles().add(new Coordinates(i + 1, j - 1));
               }
               if (j + 1 < 8 && game.getPiece(i + 1, j + 1) != null && !(game.getPiece(i + 1, j + 1).isTurn() == isTurn())) {
                   getPossibles().add(new Coordinates(i + 1, j + 1));
               }
           }
           if (i == 1 && game.getPiece(3, j) == null && game.getPiece(2, j) == null) {
               getPossibles().add(new Coordinates(3, j));
           }
           if (i == 4) {
               if (j - 1 > -1) {
                   if (game.getPiece(i, j - 1) != null && game.getPiece(i, j - 1).getName().equals("P") && game.getPiece(i + 1, j - 1) == null) {
                       getPossibles().add(new Coordinates(i + 1, j - 1));
                   }
               }
               if (j + 1 < 8) {
                   if (game.getPiece(i, j + 1) != null && game.getPiece(i, j + 1).getName().equals("P") && game.getPiece(i + 1, j + 1) == null) {
                       getPossibles().add(new Coordinates(i + 1, j + 1));
                   }
               }
           }
       }
    }

    @Override
    public void play(Game game, Coordinates to) {
       Piece oldTo = game.getPiece(to.getX(), to.getY());
        int i = getCoordinates().getX();
        int j = getCoordinates().getY();
        calculateAllPossibleMoves(game, i, j);
        if (getPossibles().contains(to)) {
            Piece current = game.getPiece(i, j);
            game.setPiece(i, j, null);
            game.setPiece(to.getX(), to.getY(), current);
            setCoordinates(to);
            if (isTurn()) {
                if (i == 3 && Math.abs(j - to.getY()) > 0 && oldTo == null) {
                    if (j - 1 > -1) {
                        Piece temporary = game.getPiece(i, j - 1);
                        if (temporary != null && temporary.getName().equals("p")) {
                            game.setPiece(i, j - 1, null);
                        }
                    }
                    if (j + 1 < 8) {
                        Piece temporary = game.getPiece(i, j + 1);
                        if (temporary != null && temporary.getName().equals("p")) {
                            game.setPiece(i, j + 1, null);
                        }
                    }
                }
                if (current.getCoordinates().getX() == 0) {
                    Scanner scanner = new Scanner(System.in);
                    System.out.println("Choose a piece. (bishop, queen ext.)");
                    String piece = scanner.next();
                    switch (piece) {
                        case "bishop":
                            game.setPiece(to.getX(), to.getY(), new Bishop(to, true, "B"));
                            break;
                        case "knight":
                            game.setPiece(to.getX(), to.getY(), new Knight(to, true, "N"));
                            break;
                        case "queen":
                            game.setPiece(to.getX(), to.getY(), new Queen(to, true, "Q"));
                            break;
                        case "rook":
                            game.setPiece(to.getX(), to.getY(), new Rook(to, true, "R"));
                            break;
                        default:
                            break;
                    }
                }
            } else {
                if (i == 4 && Math.abs(j - to.getY()) > 0 && oldTo == null) {
                    if (j - 1 > -1) {
                        Piece temporary = game.getPiece(i, j - 1);
                        if (temporary != null && temporary.getName().equals("P")) {
                            game.setPiece(i, j - 1, null);
                        }
                    }
                    if (j + 1 < 8) {
                        Piece temporary = game.getPiece(i, j + 1);
                        if (temporary != null && temporary.getName().equals("P")) {
                            game.setPiece(i, j + 1, null);
                        }
                    }
                }
                if (to.getX() == 7) {
                    game.setPiece(to.getX(), to.getY(), new Queen(to, false, "q"));
                }
            }
        }
    }
}
