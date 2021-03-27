package Chess;/* Created by oguzkeremyildiz on 27.03.2021 */

public class FromPieceNullException extends Exception {

    private final BackMove backMove;

    public FromPieceNullException(BackMove backMove) {
        this.backMove = backMove;
    }

    public String toString() {
        return "FromPieceNullException: " + backMove;
    }
}
