package Chess;/* Created by oguzkeremyildiz on 26.03.2021 */

import Chess.Piece.Piece;

public class BackMove extends Move {

    private Piece to;
    private Piece from;
    private Pair<Piece, Pair<Coordinates, Coordinates>> rookByCastle;

    public BackMove(Coordinates fromCoordinates, Coordinates toCoordinates, Piece promoted, Piece from, Piece to, Pair<Piece, Pair<Coordinates, Coordinates>> rookByCastle) {
        super(fromCoordinates, toCoordinates, promoted);
        this.to = to;
        this.from = from;
        this.rookByCastle = rookByCastle;
    }

    public Piece getTo() {
        return to;
    }

    public void setTo(Piece to) {
        this.to = to;
    }

    public Piece getFrom() {
        return from;
    }

    public Pair<Piece, Pair<Coordinates, Coordinates>> getRookByCastle() {
        return rookByCastle;
    }

    public void setRookByCastle(Pair<Piece, Pair<Coordinates, Coordinates>> rookByCastle) {
        this.rookByCastle = rookByCastle;
    }

    public void setFrom(Piece from) {
        this.from = from;
    }
}
