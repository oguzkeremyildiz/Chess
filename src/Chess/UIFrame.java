package Chess;/* Created by oguzkeremyildiz on 2.12.2020 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UIFrame extends JFrame implements ActionListener {

    private BoardPanel screen;

    public UIFrame(Game game, boolean turn) {
        setTitle("Chess");
        screen = new BoardPanel(game, turn);
        add(screen, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setExtendedState(MAXIMIZED_BOTH);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    protected void refresh() {
        screen.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public boolean getTurn(){
        return screen.getTurn();
    }

    public void setTurn() {
        screen.setTurn();
    }
}
