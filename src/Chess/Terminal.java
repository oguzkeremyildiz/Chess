package Chess;/* Created by oguzkeremyildiz on 1.12.2020 */

public class Terminal implements PrintBoard {
    @Override
    public void print(Game game) {
        System.out.print(" a b c d e f g h ");
        for (int i = 0; i < 8; i++) {
            System.out.println();
            System.out.print("|");
            for (int j = 0; j < 8; j++) {
                if (game.getPiece(i, j) != null) {
                    System.out.print(game.getPiece(i, j).getName());
                } else {
                    System.out.print(" ");
                }
                System.out.print("|");
            }
            System.out.print(" " + (8 - i));
        }
        System.out.println();
        System.out.print(" a b c d e f g h ");
    }
}
