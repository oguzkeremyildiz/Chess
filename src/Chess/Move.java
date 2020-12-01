package Chess;/* Created by oguzkeremyildiz on 24.11.2020 */

import Chess.Pieces.Piece;

public class Move implements Cloneable {

    private Coordinates toCoordinates;
    private Piece from;
    private Piece to;

    public Move(Piece from, Piece to, Coordinates toCoordinates) {
        this.toCoordinates = toCoordinates;
        this.from = from;
        this.to = to;
    }

    public Coordinates getToCoordinates() {
        return toCoordinates;
    }

    public Piece getFrom() {
        return from;
    }

    public String toString(){
        if (to == null){
            return from.getName() + from.getCoordinates().toString() + "-" + toCoordinates.toString();
        } else {
            return from.getName() + from.getCoordinates().toString() + "x" + toCoordinates.toString();
        }
    }

    public Piece getTo() {
        return to;
    }

    @Override
    public int hashCode() {
        return (this.toCoordinates + " " + this.from + " " + this.to).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Move)) {
            return false;
        }
        Move move = (Move) obj;
        return this.toCoordinates.equals(move.getToCoordinates()) && this.from == move.getFrom() && this.to == move.getTo();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
