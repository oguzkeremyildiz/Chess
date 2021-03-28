package Chess;

import Chess.Piece.Piece;
import Chess.Piece.PieceName;

import java.util.HashSet;

public class Search {

    private Game game;

    public Search(Game game) {
        this.game = game;
    }

    private HashSet<Coordinates> searchForKnight(Coordinates current, Piece piece) {
        HashSet<Coordinates> possibles = new HashSet<>();
        if (current.getX() - 2 > -1) {
            if (current.getY() - 1 > -1) {
                if (game.getPiece(current.getX() - 2, current.getY() - 1) != null) {
                    if (!(game.getPiece(current.getX() - 2, current.getY() - 1).color() == piece.color())) {
                        possibles.add(new Coordinates(current.getX() - 2, current.getY() - 1));
                    }
                } else {
                    possibles.add(new Coordinates(current.getX() - 2, current.getY() - 1));
                }
            }
            if (current.getY() + 1 < 8) {
                if (game.getPiece(current.getX() - 2, current.getY() + 1) != null) {
                    if (!(game.getPiece(current.getX() - 2, current.getY() + 1).color() == piece.color())) {
                        possibles.add(new Coordinates(current.getX() - 2, current.getY() + 1));
                    }
                } else {
                    possibles.add(new Coordinates(current.getX() - 2, current.getY() + 1));
                }
            }
        }
        if (current.getX() + 2 < 8) {
            if (current.getY() - 1 > -1) {
                if (game.getPiece(current.getX() + 2, current.getY() - 1) != null) {
                    if (!(game.getPiece(current.getX() + 2, current.getY() - 1).color() == piece.color())) {
                        possibles.add(new Coordinates(current.getX() + 2, current.getY() - 1));
                    }
                } else {
                    possibles.add(new Coordinates(current.getX() + 2, current.getY() - 1));
                }
            }
            if (current.getY() + 1 < 8) {
                if (game.getPiece(current.getX() + 2, current.getY() + 1) != null) {
                    if (!(game.getPiece(current.getX() + 2, current.getY() + 1).color() == piece.color())) {
                        possibles.add(new Coordinates(current.getX() + 2, current.getY() + 1));
                    }
                } else {
                    possibles.add(new Coordinates(current.getX() + 2, current.getY() + 1));
                }
            }
        }
        if (current.getY() + 2 < 8) {
            if (current.getX() + 1 < 8) {
                if (game.getPiece(current.getX() + 1, current.getY() + 2) != null) {
                    if (!(game.getPiece(current.getX() + 1, current.getY() + 2).color() == piece.color())) {
                        possibles.add(new Coordinates(current.getX() + 1, current.getY() + 2));
                    }
                } else {
                    possibles.add(new Coordinates(current.getX() + 1, current.getY() + 2));
                }
            }
            if (current.getX() - 1 > -1) {
                if (game.getPiece(current.getX() - 1, current.getY() + 2) != null) {
                    if (!(game.getPiece(current.getX() - 1, current.getY() + 2).color() == piece.color())) {
                        possibles.add(new Coordinates(current.getX() - 1, current.getY() + 2));
                    }
                } else {
                    possibles.add(new Coordinates(current.getX() - 1, current.getY() + 2));
                }
            }
        }
        if (current.getY() - 2 > -1) {
            if (current.getX() + 1 < 8) {
                if (game.getPiece(current.getX() + 1, current.getY() - 2) != null) {
                    if (!(game.getPiece(current.getX() + 1, current.getY() - 2).color() == piece.color())) {
                        possibles.add(new Coordinates(current.getX() + 1, current.getY() - 2));
                    }
                } else {
                    possibles.add(new Coordinates(current.getX() + 1, current.getY() - 2));
                }
            }
            if (current.getX() - 1 > -1) {
                if (game.getPiece(current.getX() - 1, current.getY() - 2) != null) {
                    if (!(game.getPiece(current.getX() - 1, current.getY() - 2).color() == piece.color())) {
                        possibles.add(new Coordinates(current.getX() - 1, current.getY() - 2));
                    }
                } else {
                    possibles.add(new Coordinates(current.getX() - 1, current.getY() - 2));
                }
            }
        }
        return possibles;
    }

    private HashSet<Coordinates> searchForBishop(Coordinates current, Piece piece) {
        HashSet<Coordinates> possibles = new HashSet<>();
        int iterate = 1;
        while (current.getY() - iterate > -1 && current.getX() - iterate > -1) {
            if (game.getPiece(current.getX() - iterate, current.getY() - iterate) == null || game.getPiece(current.getX() - iterate, current.getY() - iterate).color() != piece.color()) {
                possibles.add(new Coordinates(current.getX() - iterate, current.getY() - iterate));
                if (game.getPiece(current.getX() - iterate, current.getY() - iterate) != null && game.getPiece(current.getX() - iterate, current.getY() - iterate).color() != piece.color()) {
                    break;
                }
            } else {
                break;
            }
            iterate++;
        }
        iterate = 1;
        while (current.getY() + iterate < 8 && current.getX() - iterate > -1) {
            if (game.getPiece(current.getX() - iterate, current.getY() + iterate) == null || game.getPiece(current.getX() - iterate, current.getY() + iterate).color() != piece.color()) {
                possibles.add(new Coordinates(current.getX() - iterate, current.getY() + iterate));
                if (game.getPiece(current.getX() - iterate, current.getY() + iterate) != null && game.getPiece(current.getX() - iterate, current.getY() + iterate).color() != piece.color()) {
                    break;
                }
            } else {
                break;
            }
            iterate++;
        }
        iterate = 1;
        while (current.getY() + iterate < 8 && current.getX() + iterate < 8) {
            if (game.getPiece(current.getX() + iterate, current.getY() + iterate) == null || game.getPiece(current.getX() + iterate, current.getY() + iterate).color() != piece.color()) {
                possibles.add(new Coordinates(current.getX() + iterate, current.getY() + iterate));
                if (game.getPiece(current.getX() + iterate, current.getY() + iterate) != null && game.getPiece(current.getX() + iterate, current.getY() + iterate).color() != piece.color()) {
                    break;
                }
            } else {
                break;
            }
            iterate++;
        }
        iterate = 1;
        while (current.getY() - iterate > -1 && current.getX() + iterate < 8) {
            if (game.getPiece(current.getX() + iterate, current.getY() - iterate) == null || game.getPiece(current.getX() + iterate, current.getY() - iterate).color() != piece.color()) {
                possibles.add(new Coordinates(current.getX() + iterate, current.getY() - iterate));
                if (game.getPiece(current.getX() + iterate, current.getY() - iterate) != null && game.getPiece(current.getX() + iterate, current.getY() - iterate).color() != piece.color()) {
                    break;
                }
            } else {
                break;
            }
            iterate++;
        }
        return possibles;
    }

    private HashSet<Coordinates> searchForRook(Coordinates current, Piece piece) {
        HashSet<Coordinates> possibles = new HashSet<>();
        int iterate = 1;
        while (current.getX() + iterate < 8) {
            if (game.getPiece(current.getX() + iterate, current.getY()) == null || !(game.getPiece(current.getX() + iterate, current.getY()).color() == piece.color())) {
                possibles.add(new Coordinates(current.getX() + iterate, current.getY()));
            }
            if (game.getPiece(current.getX() + iterate, current.getY()) != null) {
                break;
            }
            iterate++;
        }
        iterate = 1;
        while (current.getX() - iterate > -1) {
            if (game.getPiece(current.getX() - iterate, current.getY()) == null || !(game.getPiece(current.getX() - iterate, current.getY()).color() == piece.color())) {
                possibles.add(new Coordinates(current.getX() - iterate, current.getY()));
            }
            if (game.getPiece(current.getX() - iterate, current.getY()) != null) {
                break;
            }
            iterate++;
        }
        iterate = 1;
        while (current.getY() + iterate < 8) {
            if (game.getPiece(current.getX(), current.getY() + iterate) == null || !(game.getPiece(current.getX(), current.getY() + iterate).color() == piece.color())) {
                possibles.add(new Coordinates(current.getX(), current.getY() + iterate));
            }
            if (game.getPiece(current.getX(), current.getY() + iterate) != null) {
                break;
            }
            iterate++;
        }
        iterate = 1;
        while (current.getY() - iterate > -1) {
            if (game.getPiece(current.getX(), current.getY() - iterate) == null || !(game.getPiece(current.getX(), current.getY() - iterate).color() == piece.color())) {
                possibles.add(new Coordinates(current.getX(), current.getY() - iterate));
            }
            if (game.getPiece(current.getX(), current.getY() - iterate) != null) {
                break;
            }
            iterate++;
        }
        return possibles;
    }

    private HashSet<Coordinates> searchForQueen(Coordinates current, Piece piece) {
        HashSet<Coordinates> possibles = new HashSet<>();
        int iterate = 1;
        while (current.getX() + iterate < 8) {
            if (game.getPiece(current.getX() + iterate, current.getY()) == null || !(game.getPiece(current.getX() + iterate, current.getY()).color() == piece.color())) {
                possibles.add(new Coordinates(current.getX() + iterate, current.getY()));
            }
            if (game.getPiece(current.getX() + iterate, current.getY()) != null) {
                break;
            }
            iterate++;
        }
        iterate = 1;
        while (current.getX() - iterate > -1) {
            if (game.getPiece(current.getX() - iterate, current.getY()) == null || !(game.getPiece(current.getX() - iterate, current.getY()).color() == piece.color())) {
                possibles.add(new Coordinates(current.getX() - iterate, current.getY()));
            }
            if (game.getPiece(current.getX() - iterate, current.getY()) != null) {
                break;
            }
            iterate++;
        }
        iterate = 1;
        while (current.getY() + iterate < 8) {
            if (game.getPiece(current.getX(), current.getY() + iterate) == null || !(game.getPiece(current.getX(), current.getY() + iterate).color() == piece.color())) {
                possibles.add(new Coordinates(current.getX(), current.getY() + iterate));
            }
            if (game.getPiece(current.getX(), current.getY() + iterate) != null) {
                break;
            }
            iterate++;
        }
        iterate = 1;
        while (current.getY() - iterate > -1) {
            if (game.getPiece(current.getX(), current.getY() - iterate) == null || !(game.getPiece(current.getX(), current.getY() - iterate).color() == piece.color())) {
                possibles.add(new Coordinates(current.getX(), current.getY() - iterate));
            }
            if (game.getPiece(current.getX(), current.getY() - iterate) != null) {
                break;
            }
            iterate++;
        }
        iterate = 1;
        while (current.getY() - iterate > -1 && current.getX() - iterate > -1) {
            if (game.getPiece(current.getX() - iterate, current.getY() - iterate) == null || game.getPiece(current.getX() - iterate, current.getY() - iterate).color() != piece.color()) {
                possibles.add(new Coordinates(current.getX() - iterate, current.getY() - iterate));
                if (game.getPiece(current.getX() - iterate, current.getY() - iterate) != null && game.getPiece(current.getX() - iterate, current.getY() - iterate).color() != piece.color()) {
                    break;
                }
            } else {
                break;
            }
            iterate++;
        }
        iterate = 1;
        while (current.getY() + iterate < 8 && current.getX() - iterate > -1) {
            if (game.getPiece(current.getX() - iterate, current.getY() + iterate) == null || game.getPiece(current.getX() - iterate, current.getY() + iterate).color() != piece.color()) {
                possibles.add(new Coordinates(current.getX() - iterate, current.getY() + iterate));
                if (game.getPiece(current.getX() - iterate, current.getY() + iterate) != null && game.getPiece(current.getX() - iterate, current.getY() + iterate).color() != piece.color()) {
                    break;
                }
            } else {
                break;
            }
            iterate++;
        }
        iterate = 1;
        while (current.getY() + iterate < 8 && current.getX() + iterate < 8) {
            if (game.getPiece(current.getX() + iterate, current.getY() + iterate) == null || game.getPiece(current.getX() + iterate, current.getY() + iterate).color() != piece.color()) {
                possibles.add(new Coordinates(current.getX() + iterate, current.getY() + iterate));
                if (game.getPiece(current.getX() + iterate, current.getY() + iterate) != null && game.getPiece(current.getX() + iterate, current.getY() + iterate).color() != piece.color()) {
                    break;
                }
            } else {
                break;
            }
            iterate++;
        }
        iterate = 1;
        while (current.getY() - iterate > -1 && current.getX() + iterate < 8) {
            if (game.getPiece(current.getX() + iterate, current.getY() - iterate) == null || game.getPiece(current.getX() + iterate, current.getY() - iterate).color() != piece.color()) {
                possibles.add(new Coordinates(current.getX() + iterate, current.getY() - iterate));
                if (game.getPiece(current.getX() + iterate, current.getY() - iterate) != null && game.getPiece(current.getX() + iterate, current.getY() - iterate).color() != piece.color()) {
                    break;
                }
            } else {
                break;
            }
            iterate++;
        }
        return possibles;
    }

    private HashSet<Coordinates> searchForKing(Coordinates current, Piece piece) {
        HashSet<Coordinates> possibles = new HashSet<>();
        if (current.getX() + 1 < 8) {
            if (game.getPiece(current.getX() + 1, current.getY()) == null || !(game.getPiece(current.getX() + 1, current.getY()).color() == piece.color())) {
                possibles.add(new Coordinates(current.getX() + 1, current.getY()));
            }
            if (current.getY() + 1 < 8 && (game.getPiece(current.getX() + 1, current.getY() + 1) == null || !(game.getPiece(current.getX() + 1, current.getY() + 1).color() == piece.color()))) {
                possibles.add(new Coordinates(current.getX() + 1, current.getY() + 1));
            }
            if (current.getY() - 1 > -1 && (game.getPiece(current.getX() + 1, current.getY() - 1) == null || !(game.getPiece(current.getX() + 1, current.getY() - 1).color() == piece.color()))) {
                possibles.add(new Coordinates(current.getX() + 1, current.getY() - 1));
            }
        }
        if (current.getX() - 1 > -1) {
            if (game.getPiece(current.getX() - 1, current.getY()) == null || !(game.getPiece(current.getX() - 1, current.getY()).color() == piece.color())) {
                possibles.add(new Coordinates(current.getX() - 1, current.getY()));
            }
            if (current.getY() + 1 < 8 && (game.getPiece(current.getX() - 1, current.getY() + 1) == null || !(game.getPiece(current.getX() - 1, current.getY() + 1).color() == piece.color()))) {
                possibles.add(new Coordinates(current.getX() - 1, current.getY() + 1));
            }
            if (current.getY() - 1 > -1 && (game.getPiece(current.getX() - 1, current.getY() - 1) == null || !(game.getPiece(current.getX() - 1, current.getY() - 1).color() == piece.color()))) {
                possibles.add(new Coordinates(current.getX() - 1, current.getY() - 1));
            }
        }
        if (current.getY() + 1 < 8 && (game.getPiece(current.getX(), current.getY() + 1) == null || !(game.getPiece(current.getX(), current.getY() + 1).color() == piece.color()))) {
            possibles.add(new Coordinates(current.getX(), current.getY() + 1));
        }
        if (current.getY() - 1 > -1 && (game.getPiece(current.getX(), current.getY() - 1) == null || !(game.getPiece(current.getX(), current.getY() - 1).color() == piece.color()))) {
            possibles.add(new Coordinates(current.getX(), current.getY() - 1));
        }
        if (piece.color() && piece.getOldMove() == null) {
            if (current.getX() == 7) {
                if (current.getY() == 4 && game.getPiece(7, 7) != null && game.getPiece(7, 7).getOldMove() == null && game.getPiece(7, 7).color() && game.getPiece(7, 7).getName().equals(PieceName.R) && game.getPiece(7, 5) == null && game.getPiece(7, 6) == null) {
                    possibles.add(new Coordinates(7, 6));
                }
                if (current.getY() == 4 && game.getPiece(7, 0) != null && game.getPiece(7, 0).getOldMove() == null && game.getPiece(7, 0).color() && game.getPiece(7, 0).getName().equals(PieceName.R) && game.getPiece(7, 1) == null && game.getPiece(7, 2) == null && game.getPiece(7, 3) == null) {
                    possibles.add(new Coordinates(7, 2));
                }
            }
        } else if (piece.getOldMove() == null) {
            if (current.getX() == 0) {
                if (current.getY() == 4 && game.getPiece(0, 7) != null && game.getPiece(0, 7).getOldMove() == null && !game.getPiece(0, 7).color() && game.getPiece(0, 7).getName().equals(PieceName.R) && game.getPiece(0, 5) == null && game.getPiece(0, 6) == null) {
                    possibles.add(new Coordinates(0, 6));
                }
                if (current.getY() == 4 && game.getPiece(0, 0) != null && game.getPiece(0, 0).getOldMove() == null && !game.getPiece(0, 0).color() && game.getPiece(0, 0).getName().equals(PieceName.R) && game.getPiece(0, 1) == null && game.getPiece(0, 2) == null && game.getPiece(0, 3) == null) {
                    possibles.add(new Coordinates(0, 2));
                }
            }
        }
        return possibles;
    }

    private HashSet<Coordinates> searchForBlackPawn(Coordinates current, Piece piece) {
        HashSet<Coordinates> possibles = new HashSet<>();
        if (current.getX() + 1 < 8) {
            if (game.getPiece(current.getX() + 1, current.getY()) == null) {
                possibles.add(new Coordinates(current.getX() + 1, current.getY()));
            }
            if (current.getY() - 1 > -1 && game.getPiece(current.getX() + 1, current.getY() - 1) != null && !(game.getPiece(current.getX() + 1, current.getY() - 1).color() == piece.color())) {
                possibles.add(new Coordinates(current.getX() + 1, current.getY() - 1));
            }
            if (current.getY() + 1 < 8 && game.getPiece(current.getX() + 1, current.getY() + 1) != null && !(game.getPiece(current.getX() + 1, current.getY() + 1).color() == piece.color())) {
                possibles.add(new Coordinates(current.getX() + 1, current.getY() + 1));
            }
        }
        if (current.getX() == 1 && game.getPiece(3, current.getY()) == null && game.getPiece(2, current.getY()) == null) {
            possibles.add(new Coordinates(3, current.getY()));
        }
        // en passant
        if (current.getX() == 4) {
            if (current.getY() - 1 > -1) {
                if (game.getPiece(current.getX(), current.getY() - 1) != null && game.getPiece(current.getX(), current.getY() - 1).color() && game.getPiece(current.getX(), current.getY() - 1).getName().equals(PieceName.P) && game.getPiece(current.getX() + 1, current.getY() - 1) == null) {
                    if (game.getLastMove().substring(0, 1).equalsIgnoreCase("p") && Math.abs(Integer.parseInt(game.getLastMove().substring(2, 3)) - Integer.parseInt(game.getLastMove().substring(5, 6))) == 2 && current.getX() == Integer.parseInt(game.getLastMove().substring(5, 6))) {
                        possibles.add(new Coordinates(current.getX() + 1, current.getY() - 1));
                    }
                }
            }
            if (current.getY() + 1 < 8) {
                if (game.getPiece(current.getX(), current.getY() + 1) != null && game.getPiece(current.getX(), current.getY() + 1).color() && game.getPiece(current.getX(), current.getY() + 1).getName().equals(PieceName.P) && game.getPiece(current.getX() + 1, current.getY() + 1) == null) {
                    if (game.getLastMove().substring(0, 1).equalsIgnoreCase("p") && Math.abs(Integer.parseInt(game.getLastMove().substring(2, 3)) - Integer.parseInt(game.getLastMove().substring(5, 6))) == 2 && current.getX() == Integer.parseInt(game.getLastMove().substring(5, 6))) {
                        possibles.add(new Coordinates(current.getX() + 1, current.getY() + 1));
                    }
                }
            }
        }
        return possibles;
    }

    private HashSet<Coordinates> searchForWhitePawn(Coordinates current, Piece piece) {
        HashSet<Coordinates> possibles = new HashSet<>();
        if (current.getX() - 1 > -1) {
            if (game.getPiece(current.getX() - 1, current.getY()) == null) {
                possibles.add(new Coordinates(current.getX() - 1, current.getY()));
            }
            if (current.getY() - 1 > -1 && game.getPiece(current.getX() - 1, current.getY() - 1) != null && !(game.getPiece(current.getX() - 1, current.getY() - 1).color() == piece.color())) {
                possibles.add(new Coordinates(current.getX() - 1, current.getY() - 1));
            }
            if (current.getY() + 1 < 8 && game.getPiece(current.getX() - 1, current.getY() + 1) != null && !(game.getPiece(current.getX() - 1, current.getY() + 1).color() == piece.color())) {
                possibles.add(new Coordinates(current.getX() - 1, current.getY() + 1));
            }
        }
        if (current.getX() == 6 && game.getPiece(4, current.getY()) == null && game.getPiece(5, current.getY()) == null) {
            possibles.add(new Coordinates(4, current.getY()));
        }
        // en passant
        if (current.getX() == 3) {
            if (current.getY() - 1 > -1) {
                if (game.getPiece(current.getX(), current.getY() - 1) != null && !game.getPiece(current.getX(), current.getY() - 1).color() && game.getPiece(current.getX(), current.getY() - 1).getName().equals(PieceName.P) && game.getPiece(current.getX() - 1, current.getY() - 1) == null) {
                    if (game.getLastMove().substring(0, 1).equalsIgnoreCase("p") && Math.abs(Integer.parseInt(game.getLastMove().substring(2, 3)) - Integer.parseInt(game.getLastMove().substring(5, 6))) == 2 && current.getX() == Integer.parseInt(game.getLastMove().substring(5, 6))) {
                        possibles.add(new Coordinates(current.getX() - 1, current.getY() - 1));
                    }
                }
            }
            if (current.getY() + 1 < 8) {
                if (game.getPiece(current.getX(), current.getY() + 1) != null && !game.getPiece(current.getX(), current.getY() + 1).color() && game.getPiece(current.getX(), current.getY() + 1).getName().equals(PieceName.P) && game.getPiece(current.getX() - 1, current.getY() + 1) == null) {
                    if (game.getLastMove().substring(0, 1).equalsIgnoreCase("p") && Math.abs(Integer.parseInt(game.getLastMove().substring(2, 3)) - Integer.parseInt(game.getLastMove().substring(5, 6))) == 2 && current.getX() == Integer.parseInt(game.getLastMove().substring(5, 6))) {
                        possibles.add(new Coordinates(current.getX() - 1, current.getY() + 1));
                    }
                }
            }
        }
        return possibles;
    }

    public HashSet<Coordinates> search(Coordinates current) {
        Piece piece = game.getPiece(current.getX(), current.getY());
        if (piece.getName().equals(PieceName.P)) {
            if (piece.color()) {
                return searchForWhitePawn(current, piece);
            }
            return searchForBlackPawn(current, piece);
        }
        if (piece.getName().equals(PieceName.K)) {
            return searchForKing(current, piece);
        }
        if (piece.getName().equals(PieceName.Q)) {
            return searchForQueen(current, piece);
        }
        if (piece.getName().equals(PieceName.R)) {
            return searchForRook(current, piece);
        }
        if (piece.getName().equals(PieceName.B)) {
            return searchForBishop(current, piece);
        }
        if (piece.getName().equals(PieceName.N)) {
            return searchForKnight(current, piece);
        }
        return null;
    }

    private void normalMove(Coordinates current, Coordinates to) {
        Piece piece = game.getPiece(current.getX(), current.getY());
        game.setPiece(current.getX(), current.getY(), null);
        game.setPiece(to.getX(), to.getY(), piece);
    }

    private boolean isEnPassant(Coordinates current, Coordinates to) {
        Piece fromPiece = game.getPiece(current.getX(), current.getY());
        Piece toPiece = game.getPiece(to.getX(), to.getY());
        return fromPiece.getName().equals(PieceName.P) && current.getX() != to.getX() && current.getY() != to.getY() && toPiece == null;
    }

    private void enPassantMove(Coordinates current, Coordinates to) {
        Piece piece = game.getPiece(current.getX(), current.getY());
        if (piece.color()) {
            game.setPiece(to.getX() + 1, to.getY(), null);
        } else {
            game.setPiece(to.getX() - 1, to.getY(), null);
        }
    }

    private boolean isCastling(Coordinates current, Coordinates to) {
        Piece piece = game.getPiece(current.getX(), current.getY());
        return piece.getName().equals(PieceName.K) && Math.abs(current.getY() - to.getY()) > 1;
    }

    private void castle(Coordinates current, Coordinates to, BackMove backMove) throws CloneNotSupportedException {
        Piece piece = game.getPiece(current.getX(), current.getY());
        if (piece.color()) {
            if (to.getY() == 6) {
                Piece rook = game.getPiece(7, 7);
                backMove.setRookByCastle(new Pair<>(rook.clone(), new Pair<>(new Coordinates(7, 7), new Coordinates(7, 5))));
                rook.setOldMove(7, 7);
                game.setPiece(7, 7, null);
                game.setPiece(7, 5, rook);
            } else {
                Piece rook = game.getPiece(7, 0);
                backMove.setRookByCastle(new Pair<>(rook.clone(), new Pair<>(new Coordinates(7, 0), new Coordinates(7, 3))));
                rook.setOldMove(7, 0);
                game.setPiece(7, 0, null);
                game.setPiece(7, 3, rook);
            }
        } else {
            if (to.getY() == 6) {
                Piece rook = game.getPiece(0, 7);
                backMove.setRookByCastle(new Pair<>(rook.clone(), new Pair<>(new Coordinates(0, 7), new Coordinates(0, 5))));
                rook.setOldMove(0, 7);
                game.setPiece(0, 7, null);
                game.setPiece(0, 5, rook);
            } else {
                Piece rook = game.getPiece(0, 0);
                backMove.setRookByCastle(new Pair<>(rook.clone(), new Pair<>(new Coordinates(0, 0), new Coordinates(0, 3))));
                rook.setOldMove(0, 0);
                game.setPiece(0, 0, null);
                game.setPiece(0, 3, rook);
            }
        }
    }

    public BackMove play(Coordinates current, Coordinates to, Piece promoted) throws CloneNotSupportedException, FromPieceNullException {
        Piece toPiece = game.getPiece(to.getX(), to.getY());
        BackMove backMove;
        if (game.getPiece(current.getX(), current.getY()) == null) {
            throw new FromPieceNullException(new BackMove(current, to, promoted, null, null, null));
        }
        if (toPiece != null) {
            backMove = new BackMove(current, to, promoted, game.getPiece(current.getX(), current.getY()).clone(), toPiece.clone(), null);
        } else {
            backMove = new BackMove(current, to, promoted, game.getPiece(current.getX(), current.getY()).clone(), null, null);
        }
        if (promoted == null) {
            game.getPiece(current.getX(), current.getY()).setOldMove(new Coordinates(current.getX(), current.getY()));
            if (isEnPassant(current, to)) {
                enPassantMove(current, to);
            } else if (isCastling(current, to)) {
                castle(current, to, backMove);
            }
            normalMove(current, to);
        } else {
            game.setPiece(current.getX(), current.getY(), null);
            game.setPiece(to.getX(), to.getY(), promoted);
        }
        return backMove;
    }

    public BackMove play(Move move) throws CloneNotSupportedException, FromPieceNullException {
        return play(move.getFromCoordinates(), move.getToCoordinates(), move.getPromoted());
    }

    public void undo(BackMove backMove) {
        if (backMove.getRookByCastle() != null) {
            Pair<Coordinates, Coordinates> coordinates = backMove.getRookByCastle().getValue();
            backMove.getRookByCastle().getKey().setOldMove(null);
            game.setPiece(coordinates.getKey().getX(), coordinates.getKey().getY(), backMove.getRookByCastle().getKey());
            game.setPiece(coordinates.getValue().getX(), coordinates.getValue().getY(), null);
        } else if (backMove.getTo() == null && backMove.getFrom().getName().equals(PieceName.P) && backMove.getFromCoordinates().getX() != backMove.getToCoordinates().getX() && backMove.getFromCoordinates().getY() != backMove.getToCoordinates().getY()) {
            if (backMove.getFrom().color()) {
                game.setPiece(backMove.getToCoordinates().getX() + 1, backMove.getToCoordinates().getY(), new Piece(false, PieceName.P, null));
            } else {
                game.setPiece(backMove.getToCoordinates().getX() - 1, backMove.getToCoordinates().getY(), new Piece(true, PieceName.P, null));
            }
        }
        game.setPiece(backMove.getFromCoordinates().getX(), backMove.getFromCoordinates().getY(), backMove.getFrom());
        game.setPiece(backMove.getToCoordinates().getX(), backMove.getToCoordinates().getY(), backMove.getTo());
    }
}