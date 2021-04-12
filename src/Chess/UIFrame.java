package Chess;/* Created by oguzkeremyildiz on 2.12.2020 */

import Chess.Piece.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.Scanner;

public class UIFrame extends JFrame implements ActionListener {

    private BoardPanel screen;
    private JList<String> list;
    public static DefaultListModel<String> model;
    private JFileChooser jFileChooser = new JFileChooser(new File("."));
    private JLabel status = new JLabel();
    private Game game;

    public UIFrame(Game game, Search search, boolean turn) {
        setTitle("Chess");
        this.game = game;
        screen = new BoardPanel(game, search, turn);
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        menuBar.add(file);
        JMenuItem save = new JMenuItem("Save");
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        JMenuItem open = new JMenuItem("Open");
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        file.add(open);
        file.add(save);
        getContentPane().add(BorderLayout.NORTH, menuBar);
        model = new DefaultListModel<>();
        list = new JList<>(model);
        JScrollPane pane = new JScrollPane(list);
        add(screen, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setExtendedState(MAXIMIZED_BOTH);
        add(pane, BorderLayout.WEST);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                open();
            }
        });
    }

    private void open() {
        if (jFileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            open(jFileChooser.getSelectedFile());
        }
    }

    private void open(File file) {
        try {
            Scanner source = new Scanner(file);
            game.setBoard(source);
            model.clear();
            refresh();
        } catch (Exception e) {
            status.setText("Error Opening " + file.getName());
        }
    }

    private void save(File file) {
        try {
            PrintWriter out = new PrintWriter(new FileOutputStream(file));
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    Piece piece = game.getPiece(i, j);
                    if (piece != null) {
                        if (piece.getOldMove() != null) {
                            out.print(i + " " + j + " " + piece.getName() + " " + piece.color() + " " + piece.getOldMove().getX() + piece.getOldMove().getY());
                        } else {
                            out.print(i + " " + j + " " + piece.getName() + " " + piece.color() + " " + piece.getOldMove());
                        }
                        out.println();
                    }
                }
            }
            out.close();
            status.setText(file.getName() + " Saved ");
        } catch (IOException e) {
            status.setText("Error saving " + file.getName());
        }
    }

    private void save() {
        if (jFileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            save(jFileChooser.getSelectedFile());
        }
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
