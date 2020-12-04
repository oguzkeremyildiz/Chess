package Chess;/* Created by oguzkeremyildiz on 2.12.2020 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UIFrame extends JFrame implements ActionListener {

    private BoardPanel screen;
    private JList<String> list;
    public static DefaultListModel<String> model;

    public UIFrame(Game game, boolean turn) {
        setTitle("Chess");
        screen = new BoardPanel(game, turn);
        model = new DefaultListModel<>();
        list = new JList<>(model);
        JScrollPane pane = new JScrollPane(list);
        add(screen, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setExtendedState(MAXIMIZED_BOTH);
        add(pane, BorderLayout.WEST);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
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
