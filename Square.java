import javax.swing.*;

enum Piece {
    WATER,
    PAD,
    GREEN,
    GREEN_SELECT,
    RED,
    RED_SELECT
}

public class Square extends JPanel
{
    private int x, y;
    private Piece piece;
    public JButton button;    
    
    public Square(int x, int y, JButton button, Piece piece) {
        this.x = x;
        this.y = y;
        this.button = button;
        this.piece = piece;

    }

    public int getX(){
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public Piece getPiece() {
        return this.piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
    
    public void moveTo(Square newSquare) {
        switch (this.piece) {
            case GREEN_SELECT:
                newSquare.piece = Piece.GREEN;
                newSquare.button.setIcon(new ImageIcon("images/GreenFrog.png"));
                break;
            case RED_SELECT:
                newSquare.piece = Piece.RED;
                newSquare.button.setIcon(new ImageIcon("images/RedFrog.png"));
                break;              
            default:
                break;
        }       
        this.piece = Piece.PAD;
        this.button.setIcon(new ImageIcon("images/LilyPad.png"));
    }

}