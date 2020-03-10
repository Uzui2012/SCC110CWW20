import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Board extends JFrame implements ActionListener
{
    /**
     *
     */
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

        System.out.println(coordinates[x][y].getPiece() + " at " + x + ", " + y + " square was clicked. "
                + selectedSquare.getPiece() + " at " + selectedSquare.getX() + " +, " + selectedSquare.getY()
                + " is the previous selected square");
        

        //calculate if a square is selected
        if (selected) {
            //if so, calculate legality of move
            if (calcLegalMove(x, y)) {
                //if legal, make move, remove hopped over frog, deselect
                selectedSquare.moveTo(coordinates[x][y]);
                //RemoveFrog
                removeFrog(x, y);
                //Deselect
                selected = false;
                selectedSquare = new Square(99, 99, new JButton(), Piece.WATER);
            } else {
                //if not legal deselect
                selectedSquare.deselectSquare();
                selected = false;
                selectedSquare = new Square(99, 99, new JButton(), Piece.WATER);
            }
        } else {
            //if nothing is selected
            if (coordinates[x][y].getPiece() == Piece.GREEN || coordinates[x][y].getPiece() == Piece.RED) { //and if a frog was selected, then select it
                selected = true;
                coordinates[x][y].selectSquare();
                selectedSquare = coordinates[x][y];
            }
            //otherwise, make no selection
        }

        System.out.println(selectedSquare.getPiece() + " at " + x + ", " + y + " is selected");
        frame.revalidate();
        frame.repaint();
        if (numLegalMoves() == 0) {
            JOptionPane.showMessageDialog(frame, "NO MORE MOVES", "GAME OVER", JOptionPane.PLAIN_MESSAGE);
            
        }
    }

    public void removeFrog(int x, int y) {
        //Calculates coordinates of the frog to remove
        int q = selectedSquare.getX() +  ( (x - selectedSquare.getX()) / 2);
        int r = selectedSquare.getY() + ((y - selectedSquare.getY()) / 2);
        //Remove Frog
        coordinates[q][r].button.setIcon(new ImageIcon("images/LilyPad.png"));
        coordinates[q][r].setPiece(Piece.PAD);
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
        //Coordinates of the intevening space
        int q = selectedSquare.getX() +  (xDiff / 2);
        int r = selectedSquare.getY() + (yDiff / 2);        
        //System.out.println("q: " + q + ", r: " + r);
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
    
    public int numLegalMoves() {
        Square temp = selectedSquare;
        int count = 0;
        int arrayOfPositions[][] = {
            {0,0}, {0,2}, {0,4},
                {1,1}, {1,3},
            {2,0}, {2,2}, {2,4},
                {3,1}, {3,3},
            {4,0}, {4,2}, {4,4}
        };
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (coordinates[i][j].getPiece() == Piece.GREEN || coordinates[i][j].getPiece() == Piece.RED) {
                    for (int k = 0; k < arrayOfPositions.length; k++) {
                        selectedSquare = coordinates[arrayOfPositions[k][0]][arrayOfPositions[k][1]];
                        if (calcLegalMove(i, j)) {
                            System.out.println(arrayOfPositions[k][0] + ", " + arrayOfPositions[k][1]);
                            count++;
                        }
                    }
                }
            }
        }
        selectedSquare = temp;
        System.out.println(count);
        return count;
    }
}

