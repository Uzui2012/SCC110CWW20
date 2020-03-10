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
    
    public void selectSquare() {
        switch (this.piece) {
            case GREEN:
                this.piece = Piece.GREEN_SELECT;
                this.button.setIcon(new ImageIcon("images/GreenFrog2.png"));
                break;
            case RED:
                this.piece = Piece.RED_SELECT;
                this.button.setIcon(new ImageIcon("images/RedFrog2.png"));
                break;
            default:
                break;
        }
    }

    public void deselectSquare() {
        switch (this.piece) {
            case GREEN_SELECT:
                this.piece = Piece.GREEN;
                this.button.setIcon(new ImageIcon("images/GreenFrog.png"));
                break;
            case RED_SELECT:
                this.piece = Piece.RED;
                this.button.setIcon(new ImageIcon("images/RedFrog.png"));
                break;
            default:
                break;
        }
    }

}