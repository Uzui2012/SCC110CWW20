package main.java.hoppers;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;

/**
 * Pieces the square can be.
 */
enum Piece {
    /**
     * Water
     */
    WATER,
    /**
     * Lily pad
     */
    PAD,
    /**
     * Green frog
     */
    GREEN,
    /**
     * Selected green frog
     */
    GREEN_SELECT,
    /**
     * Red frog
     */
    RED,
    /**
     * Selected red frog
     */
    RED_SELECT
}

/**
 * Square class to represent an individual square upon a board in the game Hoppers. Many Sqaures can be created, however should not be of duplicate parameters.
 */
public class Square extends JPanel
{
    /**
     * The x and y coordinates as integers.
     */
    private int x, y;
    /**
     * The piece this square is currently representing.
     */
    private Piece piece;
    /**
     * The JButton this square resides.
     */
    private JButton button;    
    
    /**
     * Creates a new Square with x and y coordinates, a JButton, and a Piece.
     * @param x x coordinate that this square is to resides.
     * @param y y coordinate that this square is to resides.
     * @param button JButton that this square operates on.
     * @param piece Which Hoppers' specific piece resides upon this square.
     */
    public Square(int x, int y, JButton button, Piece piece) {
        this.x = x;
        this.y = y;
        this.button = button;
        this.piece = piece;
    }

    /**
     * @return This square's x coordinate.
     */
    public int getX(){
        return this.x;
    }

    /**
     * @return This square's y coordinate.
     */
    public int getY() {
        return this.y;
    }

    /**
     * @return This square's JButton reference.
     */
    public JButton getButton() {
        return this.button;
    }

    /**
     * @return This square's piece.
     */
    public Piece getPiece() {
        return this.piece;
    }

    /**
     * This method sets the piece of the square.
     * @param piece New piece for which this square is to become.
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
    }
    
    /**
     * Sets the new square's graphic and piece, replaces this square to a LilyPad.
     * Must only be used after game logic dictates the move is legal.
     * @param newSquare The square upon which this square's graphic and piece is to become.
     */
    public void moveTo(Square newSquare) {
        switch (this.piece) {
            case GREEN_SELECT:
                newSquare.setPiece(Piece.GREEN);
                newSquare.getButton().setIcon(new ImageIcon("target/resources/images/GreenFrog.png"));
                break;
            case RED_SELECT:
                newSquare.setPiece(Piece.RED);
                newSquare.getButton().setIcon(new ImageIcon("target/resources/images/RedFrog.png"));
                break;
            default:
                break;
        }
        this.piece = Piece.PAD;
        this.button.setIcon(new ImageIcon("target/resources/images/LilyPad.png"));
    }
    
    /**
     * Changes the graphics of the square this method is called from to a selected state, of either the red or green frogs.
     */
    public void selectSquare() {
        switch (this.piece) {
            case GREEN:
                this.piece = Piece.GREEN_SELECT;
                this.button.setIcon(new ImageIcon("target/resources/images/GreenFrog2.png"));
                break;
            case RED:
                this.piece = Piece.RED_SELECT;
                this.button.setIcon(new ImageIcon("target/resources/images/RedFrog2.png"));
                break;
            default:
                break;
        }
    }

    /**
     * Changes the graphics of the square this method is called from to a deselected state, of either the red or green frogs.
     */
    public void deselectSquare() {
        switch (this.piece) {
            case GREEN_SELECT:
                this.piece = Piece.GREEN;
                this.button.setIcon(new ImageIcon("target/resources/images/GreenFrog.png"));
                break;
            case RED_SELECT:
                this.piece = Piece.RED;
                this.button.setIcon(new ImageIcon("target/resources/images/RedFrog.png"));
                break;
            default:
                break;
        }
    }
}