package Chess;/* Created by oguzkeremyildiz on 1.12.2020 */

import javax.swing.*;

public class UI extends PrintBoard {

    UIFrame uiFrame;
    private Search search;

    public UI(Game game, Search search, boolean turn) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                uiFrame = new UIFrame(game, search, turn);
            }
        });
    }

    @Override
    public void print(Game game) {
        if (uiFrame != null) {
            uiFrame.refresh();
        }
    }

    @Override
    public void humanMove() throws InterruptedException {
        while (true) {
            Thread.sleep(100);
            if (uiFrame != null && !uiFrame.getTurn()){
                uiFrame.setTurn();
                break;
            }
        }
    }

    @Override
    public void run() {

    }
}
