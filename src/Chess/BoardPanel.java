package Chess;/* Created by oguzkeremyildiz on 2.12.2020 */

import Chess.Piece.Piece;
import Chess.Piece.PieceName;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashSet;

public class BoardPanel extends JPanel implements MouseListener, MouseMotionListener {

    private Game game;
    private final HashSet<String> set;
    private boolean turn, dragged;
    private Piece currentPiece;
    private int mouseIndex1;
    private int mouseIndex2;
    private int fromIndex1;
    private int fromIndex2;

    public BoardPanel(Game game, boolean turn) {
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        setLayout(null);
        this.game = game;
        this.turn = turn;
        dragged = false;
        setFocusable(true);
        this.set = set();
    }

    private HashSet<String> set() {
        HashSet<String> set = new HashSet<>();
        set.add("q");
        set.add("Q");
        set.add("queen");
        set.add("Queen");
        set.add("r");
        set.add("rook");
        set.add("R");
        set.add("Rook");
        set.add("Bishop");
        set.add("b");
        set.add("B");
        set.add("bishop");
        set.add("Knight");
        set.add("knight");
        set.add("n");
        set.add("N");
        return set;
    }

    private Image convert(Piece piece) {
        switch (piece.getName()) {
            case R:
                if (piece.color()) {
                    return new ImageIcon("white_rook.png").getImage();
                }
                return new ImageIcon("black_rook.png").getImage();
            case N:
                if (piece.color()) {
                    return new ImageIcon("white_knight.png").getImage();
                }
                return new ImageIcon("black_knight.png").getImage();
            case B:
                if (piece.color()) {
                    return new ImageIcon("white_bishop.png").getImage();
                }
                return new ImageIcon("black_bishop.png").getImage();
            case Q:
                if (piece.color()) {
                    return new ImageIcon("white_queen.png").getImage();
                }
                return new ImageIcon("black_queen.png").getImage();
            case K:
                if (piece.color()) {
                    return new ImageIcon("white_king.png").getImage();
                }
                return new ImageIcon("black_king.png").getImage();
            case P:
                if (piece.color()) {
                    return new ImageIcon("white_pawn.png").getImage();
                }
                return new ImageIcon("black_pawn.png").getImage();
            default:
                break;
        }
        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image board = new ImageIcon("board.png").getImage();
        g.drawImage(board, 400, 80, null);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (game.getPiece(i, j) != null && game.getPiece(i, j) != currentPiece) {
                    g.drawImage(convert(game.getPiece(i, j)), 425 + 72 * j, 90 + 72 * i, null);
                }
            }
        }
        if (dragged && currentPiece != null) {
            g.drawImage(convert(currentPiece), mouseIndex1, mouseIndex2, null);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!dragged){
            return;
        }
        if (currentPiece == null) {
            return;
        }
        int index1 = 0;
        int index2 = 0;
        for (int j = 0; j < 8; j++) {
            if (425 + 72 * j < e.getX() && 425 + 72 * (j + 1) > e.getX()) {
                index1 = j;
                break;
            }
        }
        for (int i = 0; i < 8; i++) {
            if (90 + 72 * i < e.getY() && 90 + 72 * (i + 1) > e.getY()) {
                index2 = i;
                break;
            }
        }
        Coordinates from = new Coordinates(fromIndex1, fromIndex2);
        Coordinates to = new Coordinates(index2, index1);
        String oldCoordinates = from.toString();
        if (Search.search(from, game).contains(to)) {
            if (currentPiece.getName().equals(PieceName.P) && index2 == 0) {
                String piece = "";
                while (!set.contains(piece)) {
                    piece = JOptionPane.showInputDialog("Choose a piece. (bishop, queen etc.)", "queen");
                }
                switch (piece) {
                    case "bishop":
                    case "b":
                    case "B":
                    case "Bishop":
                        try {
                            Search.play(from, to, new Piece(true, PieceName.B, null), game);
                        } catch (CloneNotSupportedException cloneNotSupportedException) {
                            cloneNotSupportedException.printStackTrace();
                        }
                        break;
                    case "knight":
                    case "Knight":
                    case "N":
                    case "n":
                        try {
                            Search.play(from, to, new Piece(true, PieceName.N, null), game);
                        } catch (CloneNotSupportedException cloneNotSupportedException) {
                            cloneNotSupportedException.printStackTrace();
                        }
                        break;
                    case "queen":
                    case "Queen":
                    case "q":
                    case "Q":
                        try {
                            Search.play(from, to, new Piece(true, PieceName.Q, null), game);
                        } catch (CloneNotSupportedException cloneNotSupportedException) {
                            cloneNotSupportedException.printStackTrace();
                        }
                        break;
                    case "rook":
                    case "r":
                    case "Rook":
                    case "R":
                        try {
                            Search.play(from, to, new Piece(true, PieceName.R, null), game);
                        } catch (CloneNotSupportedException cloneNotSupportedException) {
                            cloneNotSupportedException.printStackTrace();
                        }
                        break;
                    default:
                        break;
                }
            } else {
                try {
                    Search.play(from, to, null, game);
                } catch (CloneNotSupportedException cloneNotSupportedException) {
                    cloneNotSupportedException.printStackTrace();
                }
            }
            UIFrame.model.addElement(currentPiece.getName() + ": played from " + oldCoordinates + " to " + to);
            turn = false;
        }
        currentPiece = null;
        dragged = false;
        repaint();
    }

    public boolean getTurn(){
        return turn;
    }

    public void setTurn() {
        turn = !turn;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (turn && game.finished()) {
            if (!dragged) {
                fromIndex2 = 0;
                fromIndex1 = 0;
                for (int j = 0; j < 8; j++) {
                    if (425 + 72 * j < e.getX() && 425 + 72 * (j + 1) > e.getX()) {
                        fromIndex2 = j;
                        break;
                    }
                }
                for (int i = 0; i < 8; i++) {
                    if (90 + 72 * i < e.getY() && 90 + 72 * (i + 1) > e.getY()) {
                        fromIndex1 = i;
                        break;
                    }
                }
                if (game.getPiece(fromIndex1, fromIndex2) != null && game.getPiece(fromIndex1, fromIndex2).color()) {
                    currentPiece = game.getPiece(fromIndex1, fromIndex2);
                }
                if (currentPiece != null) {
                    dragged = true;
                }
            } else {
                mouseIndex1 = e.getX();
                mouseIndex2 = e.getY();
                repaint();
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }
}
