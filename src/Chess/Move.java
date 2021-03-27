package Chess;/* Created by oguzkeremyildiz on 24.11.2020 */

import Chess.Piece.Piece;

public class Move implements Cloneable {

    private Coordinates toCoordinates;
    private Coordinates fromCoordinates;
    private Piece promoted;

    public Move(Coordinates fromCoordinates, Coordinates toCoordinates, Piece promoted) {
        this.toCoordinates = toCoordinates;
        this.fromCoordinates = fromCoordinates;
        this.promoted = promoted;
    }

    public Coordinates getFromCoordinates() {
        return fromCoordinates;
    }

    public Coordinates getToCoordinates() {
        return toCoordinates;
    }

    public Piece getPromoted() {
        return promoted;
    }

    public void setToCoordinates(Coordinates toCoordinates) {
        this.toCoordinates = toCoordinates;
    }

    public void setFromCoordinates(Coordinates fromCoordinates) {
        this.fromCoordinates = fromCoordinates;
    }

    public void setPromoted(Piece promoted) {
        this.promoted = promoted;
    }

    public String toString() {
        return fromCoordinates.toString() + ": played to " + toCoordinates.toString() + " coordinates.";
    }

    @Override
    public int hashCode() {
        return (this.fromCoordinates + " " + this.toCoordinates).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Move)) {
            return false;
        }
        Move move = (Move) obj;
        return this.promoted.equals(move.promoted) && this.toCoordinates.equals(move.toCoordinates) && this.fromCoordinates.equals(move.fromCoordinates);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
