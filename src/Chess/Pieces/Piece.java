package Chess.Pieces;

import Chess.Game;
import Chess.Coordinates;

import java.util.HashSet;

public abstract class Piece {

    private HashSet<Coordinates> possibles;
    private Coordinates coordinates;
    private boolean turn;
    private String name;

    public Piece(Coordinates coordinates, boolean color, String name) {
        this.coordinates = coordinates;
        this.turn = color;
        this.name = name;
        possibles = new HashSet<>();
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public boolean isTurn() {
        return turn;
    }

    public String getName() {
        return name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public HashSet<Coordinates> getPossibles() {
        return possibles;
    }

    public abstract void calculateAllPossibleMoves(Game game, int i, int j);

    public abstract void play(Game board, Coordinates to);

    public boolean contains(Coordinates to, Game game, int i, int j) {
        calculateAllPossibleMoves(game, i, j);
        return getPossibles().contains(to);
    }

    @Override
    public int hashCode() {
        return (possibles.hashCode() + " " + coordinates.hashCode() + " " + name.hashCode()).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Piece)) {
            return false;
        }
        Piece piece = (Piece) obj;
        return this.possibles.equals(piece.getPossibles()) && this.coordinates.equals(piece.getCoordinates()) && this.turn == piece.isTurn() && this.name.equals(piece.getName());
    }
}
