import Chess.*;
import Chess.Players.Computer;
import Chess.Players.Human;
import Chess.Players.Player;

import java.io.FileNotFoundException;

public class Main {

    private static boolean setTurn(boolean turn) {
        return !turn;
    }

    public static void main(String[]args) throws CloneNotSupportedException, FileNotFoundException {
        Game game = new Game();
        game.setBoard();
        Player computer = new Computer(game);
        Player human = new Human(game);
        PrintBoard printBoard = new Terminal();
        boolean turn = true;
        while (game.finished()) {
            Move bestMove;
            if (turn) {
                printBoard.print(game);
                bestMove = human.findMove();
            } else {
                bestMove = computer.findMove();
                System.out.println(bestMove.getFrom().getName() + ": played to " + bestMove.toString().substring(4, 6) + " coordinates.");
            }
            bestMove.getFrom().play(game, bestMove.getToCoordinates());
            turn = setTurn(turn);
        }
    }
}
