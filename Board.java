import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Board extends JFrame implements ActionListener
{
    private Square coordinates[][];
    private JFrame frame;
    private JPanel panel;
    private boolean selected;
    private Square selectedSquare = new Square(99, 99, new JButton(), Piece.WATER);

    public Board() {
        coordinates = new Square[5][];

        panel = new JPanel();
        panel.setLayout(new GridLayout(5, 5));
        
        int array[][] = {
            {1,0,1,0,1},
            {0,2,0,2,0},
            {1,0,2,0,1},
            {0,1,0,1,0},
            {2,0,3,0,2}
        };
        for (int i = 0; i < 5; i++) {
            coordinates[i] = new Square[5]; //init squares
            for (int j = 0; j < 5; j++) {
                //use level.csv later
                if (array[i][j] == 0) {
                    coordinates[i][j] = new Square(i, j, new JButton(new ImageIcon("images/Water.png")), Piece.WATER);
                }else if (array[i][j] == 1) {
                    coordinates[i][j] = new Square(i, j, new JButton(new ImageIcon("images/LilyPad.png")), Piece.PAD);
                }else if (array[i][j] == 2) {
                    coordinates[i][j] = new Square(i, j, new JButton(new ImageIcon("images/GreenFrog.png")), Piece.GREEN);
                }else {
                    coordinates[i][j] = new Square(i, j, new JButton(new ImageIcon("images/RedFrog.png")), Piece.RED);
                }

                panel.add(coordinates[i][j].button);
                coordinates[i][j].button.addActionListener(this);
            }
        }

        frame = new JFrame("Frame");
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(750, 750));
        frame.setResizable(false);
        frame.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e) {
        int x = 0, y = 0;
        for (int i = 0; i < coordinates.length; i++) {
            for (int j = 0; j < coordinates[i].length; j++) {
                if (e.getSource() == coordinates[i][j].button) {
                    x = i;
                    y = j;
                }
            }
        }
        System.out.println(coordinates[x][y].getPiece() + " at " + x + ", " + y + " square was clicked");
        if (selected) {
            if (calcLegalMove(x, y)) {
                selectedSquare.moveTo(coordinates[x][y]);
            } else if (coordinates[x][y] == selectedSquare) {
                selected = false;
                selectedSquare = new Square(99, 99, new JButton(), Piece.WATER);
                if (coordinates[x][y].getPiece() == Piece.GREEN_SELECT) {
                    selectedSquare = coordinates[x][y];
                    coordinates[x][y].button.setIcon(new ImageIcon("images/GreenFrog.png"));
                    coordinates[x][y].setPiece(Piece.GREEN);
                } else if (coordinates[x][y].getPiece() == Piece.RED_SELECT) {
                    selectedSquare = coordinates[x][y];
                    coordinates[x][y].button.setIcon(new ImageIcon("images/RedFrog.png"));
                    coordinates[x][y].setPiece(Piece.RED);
                }
            }
        } else if (!selected) {
            if (coordinates[x][y].getPiece() == Piece.GREEN) {
                selectedSquare = coordinates[x][y];
                coordinates[x][y].button.setIcon(new ImageIcon("images/GreenFrog2.png"));
                coordinates[x][y].setPiece(Piece.GREEN_SELECT);
            } else if (coordinates[x][y].getPiece() == Piece.RED) {
                selectedSquare = coordinates[x][y];
                coordinates[x][y].button.setIcon(new ImageIcon("images/RedFrog2.png"));
                coordinates[x][y].setPiece(Piece.RED_SELECT);
            }
            selected = true;

        }
        System.out.println(selectedSquare.getPiece() + " at " + x + ", " + y + " is now selected");
        frame.revalidate();
        frame.repaint();
    }

    public boolean calcLegalMove(int x, int y) {
        if (checkSpace(x, y)) {
            if (checkFrog(x, y)) {
                return true;
            }
            return false;
        }
        return false;        
    }

    public boolean checkFrog(int x, int y) {
        int xDiff = x - selectedSquare.getX();
        int yDiff = y - selectedSquare.getY();
        int q = xDiff / 2;
        int r = yDiff / 2;        
        System.out.println("q: " + q + ", r: " + r);
        if (coordinates[q][r].getPiece() == Piece.GREEN || coordinates[q][r].getPiece() == Piece.RED) {
            return true;
        }
        return false;
    }
    
    public boolean checkSpace(int x, int y) {
        int xDiff = x - selectedSquare.getX();
        int yDiff = y - selectedSquare.getY();
        if (xDiff == -2 || xDiff == 2) {
            if (yDiff == -2 || yDiff == 2) {
                return true;
            }
        } else if (xDiff == 4 || xDiff == -4) {
            if (yDiff == 0) {
                return true;
            }
        } else if (xDiff == 0) {
            if (yDiff == -4 || yDiff == 4) {
                return true;
            }
        } else {
            return false;
        }        
        return false;
    }
}

