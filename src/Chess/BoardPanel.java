package Chess;/* Created by oguzkeremyildiz on 2.12.2020 */

import Chess.Pieces.Piece;

import javax.swing.*;
import javax.swing.plaf.PanelUI;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class BoardPanel extends JPanel implements MouseListener, MouseMotionListener {

    private Game game;
    private boolean turn, dragged;
    private Piece currentPiece;
    private int mouseIndex1;
    private int mouseIndex2;

    public BoardPanel(Game game, boolean turn) {
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        setLayout(null);
        this.game = game;
        this.turn = turn;
        dragged = false;
        setFocusable(true);
    }

    private Image convert(String piece) {
        switch (piece) {
            case "r":
                return new ImageIcon("black_rook.png").getImage();
            case "n":
                return new ImageIcon("black_knight.png").getImage();
            case "b":
                return new ImageIcon("black_bishop.png").getImage();
            case "q":
                return new ImageIcon("black_queen.png").getImage();
            case "k":
                return new ImageIcon("black_king.png").getImage();
            case "p":
                return new ImageIcon("black_pawn.png").getImage();
            case "R":
                return new ImageIcon("white_rook.png").getImage();
            case "N":
                return new ImageIcon("white_knight.png").getImage();
            case "B":
                return new ImageIcon("white_bishop.png").getImage();
            case "Q":
                return new ImageIcon("white_queen.png").getImage();
            case "K":
                return new ImageIcon("white_king.png").getImage();
            case "P":
                return new ImageIcon("white_pawn.png").getImage();
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
                    g.drawImage(convert(game.getPiece(i, j).getName()), 425 + 72 * j, 90 + 72 * i, null);
                }
            }
        }
        if (dragged && currentPiece != null){
            g.drawImage(convert(currentPiece.getName()), mouseIndex1, mouseIndex2, null);
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
        currentPiece.calculateAllPossibleMoves(game, currentPiece.getCoordinates().getX(), currentPiece.getCoordinates().getY());
        if (currentPiece.getPossibles().contains(new Coordinates(index2, index1))) {
            currentPiece.play(game, new Coordinates(index2, index1));
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
        if (turn) {
            if (!dragged){
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
                if (game.getPiece(index2, index1) != null && game.getPiece(index2, index1).getName().toUpperCase().equals(game.getPiece(index2, index1).getName())) {
                    currentPiece = game.getPiece(index2, index1);
                }
                dragged = true;
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
