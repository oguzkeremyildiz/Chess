import Chess.*;
import Chess.Players.Computer;

import java.io.FileNotFoundException;

public class Main {

    private static boolean setTurn(boolean turn) {
        return !turn;
    }

    public static void main(String[]args) throws CloneNotSupportedException, FileNotFoundException, InterruptedException {
        Game game = new Game();
        game.setBoard();
        Computer computer = new Computer(game);
        PrintBoard printBoard = new UI(game, true);
        boolean turn = true;
        while (game.finished()) {
            Move bestMove;
            if (turn) {
                printBoard.print(game);
                printBoard.humanMove();
            } else {
                bestMove = computer.findMove();
                System.out.println(bestMove.getFrom().getName() + ": played to " + bestMove.toString().substring(4, 6) + " coordinates.");
                bestMove.getFrom().play(game, bestMove.getToCoordinates());
                printBoard.print(game);
            }
            turn = setTurn(turn);
        }
    }
}
