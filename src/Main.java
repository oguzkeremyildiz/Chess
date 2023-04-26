import Chess.*;
import Chess.Piece.Piece;
import Chess.Players.Computer;
import Chess.Players.Interface.*;

import java.io.FileNotFoundException;

public class Main {

    private static boolean setTurn(boolean turn) {
        return !turn;
    }

    public static void main(String[]args) throws CloneNotSupportedException, FileNotFoundException, InterruptedException {
        Game game = new Game();
        game.setBoard();
        Computer computer = new Computer(game, Point.NORMAL);
        PrintBoard printBoard = new UI(game, true);
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
                    int size = UIFrame.model.size();
                    if (to == null) {
                        UIFrame.model.addElement(size / 2 + 1 + "-) " + game.getPiece(bestMove.getFromCoordinates().getX(), bestMove.getFromCoordinates().getY()).getName().toString().toLowerCase() + ": played from " + oldCoordinates + " to " + bestMove.getToCoordinates());
                    } else if (toName != null) {
                        UIFrame.model.addElement(size / 2 + 1 + "-) " + fromName + ": played from " + oldCoordinates + " to " + bestMove.getToCoordinates() + " (" + toName + " out of the board)");
                    }
                    printBoard.print(game);
                }
                Search.play(bestMove.getFromCoordinates(), bestMove.getToCoordinates(), bestMove.getPromoted(), game);
            }
            turn = setTurn(turn);
        }
    }
}
