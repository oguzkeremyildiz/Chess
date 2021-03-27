package Chess.Piece;

import Chess.Coordinates;

public class Piece {

    private final boolean color;
    private final PieceName name;
    private Coordinates oldMove;

    public Piece(boolean color, PieceName name, Coordinates oldMove) {
        this.color = color;
        this.name = name;
        this.oldMove = oldMove;
    }

    public boolean color() {
        return color;
    }

    public PieceName getName() {
        return name;
    }

    public Coordinates getOldMove() {
        return oldMove;
    }

    public void setOldMove(Coordinates oldMove) {
        this.oldMove = oldMove;
    }

    public void setOldMove(int first, int second) {
        this.oldMove = new Coordinates(first, second);
    }

    @Override
    public int hashCode() {
        return (oldMove.hashCode() + " " + name.hashCode()).hashCode();
    }

    @Override
    public String toString() {
        return name.toString();
    }

    private boolean handle(Coordinates first, Coordinates last) {
        if (first == null && last == null) {
            return true;
        } else if (first == null) {
            return false;
        } else if (last == null) {
            return false;
        }
        return first.equals(last);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Piece)) {
            return false;
        }
        Piece piece = (Piece) obj;
        return handle(this.oldMove, piece.oldMove) && this.color == piece.color() && this.name.equals(piece.getName());
    }

    @Override
    public Piece clone() throws CloneNotSupportedException {
        if (this.oldMove == null) {
            return new Piece(this.color, this.name, null);
        }
        return new Piece(this.color, this.name, new Coordinates(this.oldMove.getX(), this.oldMove.getY()));
    }
}
