import Chess.*;
import Chess.Piece.Piece;
import Chess.Players.Computer;
import Chess.Players.Interface.*;

import java.io.FileNotFoundException;

public class Main {

    private static boolean setTurn(boolean turn) {
        return !turn;
    }

    public static void main(String[]args) throws CloneNotSupportedException, FileNotFoundException, InterruptedException, FromPieceNullException {
        Game game = new Game();
        game.setBoard();
        Search search = new Search(game);
        Computer computer = new Computer(game, search, new NormalCalculate());
        PrintBoard printBoard = new UI(game, search, true);
        boolean turn = true;
        while (game.finished()) {
            Move bestMove;
            if (turn) {
                printBoard.print(game);
                printBoard.humanMove();
            } else {
                bestMove = computer.findMove();
                String oldCoordinates = bestMove.getFromCoordinates().toString();
                String fromName = game.getPiece(bestMove.getFromCoordinates().getX(), bestMove.getFromCoordinates().getY()).getName().toString().toLowerCase();
                String toName = null;
                if (game.getPiece(bestMove.getToCoordinates().getX(), bestMove.getToCoordinates().getY()) != null) {
                    toName = game.getPiece(bestMove.getToCoordinates().getX(), bestMove.getToCoordinates().getY()).getName().toString();
                }
                Piece to = game.getPiece(bestMove.getToCoordinates().getX(), bestMove.getToCoordinates().getY());
                if (UIFrame.model == null) {
                    System.out.println(game.getPiece(bestMove.getFromCoordinates().getX(), bestMove.getFromCoordinates().getY()).getName().toString() + bestMove);
                }
                if (UIFrame.model != null) {
                    if (to == null) {
                        UIFrame.model.addElement(game.getPiece(bestMove.getFromCoordinates().getX(), bestMove.getFromCoordinates().getY()).getName().toString().toLowerCase() + ": played from " + oldCoordinates + " to " + bestMove.getToCoordinates());
                    } else if (toName != null) {
                        UIFrame.model.addElement(fromName + ": played from " + oldCoordinates + " to " + bestMove.getToCoordinates() + " (" + toName + " out of the board)");
                    }
                    printBoard.print(game);
                }
                search.play(bestMove.getFromCoordinates(), bestMove.getToCoordinates(), bestMove.getPromoted());
            }
            turn = setTurn(turn);
        }
    }
}
