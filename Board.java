import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@SuppressWarnings("serial")

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

    public Board(int selectedLevel) {
        coordinates = new Square[5][];

        panel = new JPanel();
        panel.setLayout(new GridLayout(5, 5));

        String fileName= "levels.csv";
        File file= new File(fileName);
        Scanner inputStream;

        int count = 0;
        String[] level = new String[25];

        try{
            inputStream = new Scanner(file);
            String line= inputStream.next();
            level = line.split(","); 
            while(count != selectedLevel){
                line = inputStream.next();
                level = line.split(",");                
                count++;
            }
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        int k = 0;
        for (int i = 0; i < 5; i++) {
            coordinates[i] = new Square[5]; //init squares
            for (int j = 0; j < 5; j++) {
                
                if (level[j+k] .equals("w")) {
                    coordinates[i][j] = new Square(i, j, new JButton(new ImageIcon("images/Water.png")), Piece.WATER);
                } else if (level[j+k].equals("p")) {
                    coordinates[i][j] = new Square(i, j, new JButton(new ImageIcon("images/LilyPad.png")), Piece.PAD);
                } else if (level[j+k].equals("g")) {
                    coordinates[i][j] = new Square(i, j, new JButton(new ImageIcon("images/GreenFrog.png")),
                            Piece.GREEN);
                } else if (level[j+k].equals("r")) {
                    coordinates[i][j] = new Square(i, j, new JButton(new ImageIcon("images/RedFrog.png")), Piece.RED);
                }
                panel.add(coordinates[i][j].button);
                coordinates[i][j].button.addActionListener(this);
            }
            k = k + 5; 
        }

        frame = new JFrame("Hoppers level "); //add level number
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(750, 750));
        frame.setResizable(false);
        frame.setVisible(true);        
    }
    
    @Override
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
        
        //Calculate if a square is selected
        if (selected) {
            //If so, calculate legality of move
            if (calcLegalMove(x, y)) {
                //If legal, make move, remove hopped over frog, deselect
                selectedSquare.moveTo(coordinates[x][y]);
                //RemoveFrog
                removeFrog(x, y);
                //Deselect
                selected = false;
                selectedSquare = new Square(99, 99, new JButton(), Piece.WATER);
                if (numLegalMoves() == 0) {
                    endGame();
                }
            } else {
                //If not legal deselect
                selectedSquare.deselectSquare();
                selected = false;
                selectedSquare = new Square(99, 99, new JButton(), Piece.WATER);
            }
        } else {
            //If nothing is selected & clicked square is a green or red frog, select it
            if (coordinates[x][y].getPiece() == Piece.GREEN || coordinates[x][y].getPiece() == Piece.RED) { 
                selected = true;
                coordinates[x][y].selectSquare();
                selectedSquare = coordinates[x][y];
            }
            //otherwise, make no selection
        }
        frame.revalidate();
        frame.repaint();        
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
        if (checkFrog(x, y) && checkPad(x, y)) {
            return true;
        }
        return false;
    }
    
    public boolean checkPad(int x, int y) {
        if (coordinates[x][y].getPiece() == Piece.PAD) {
            return true;
        }
        return false;
    }

    public boolean checkFrog(int x, int y) {
        int xDiff = x - selectedSquare.getX();
        int yDiff = y - selectedSquare.getY();
        //Coordinates of the intevening space
        int q = selectedSquare.getX() + (xDiff / 2);
        int r = selectedSquare.getY() + (yDiff / 2);

        if (coordinates[q][r].getPiece() == Piece.GREEN || coordinates[q][r].getPiece() == Piece.RED) {
            return true;
        }
        return false;
    }
        
    public int numLegalMoves() {
        Square temp = selectedSquare;
        int count = 0;
        int a[][] = { { -2, -2 }, { -4, 0 }, { -2, 2 }, { 0, 4 }, { 2, 2 }, { 4, 0 }, { 2, -2 }, { 0, -4 }, };
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (coordinates[i][j].getPiece() == Piece.GREEN || coordinates[i][j].getPiece() == Piece.RED) {
                    selectedSquare = coordinates[i][j];
                    for (int r = 0; r < 8; r++) {
                        if (a[r][0] + i >= 0 && a[r][0] + i <= 4 && a[r][1] + j >= 0 && a[r][1] + j <= 4) {
                            if (calcLegalMove(a[r][0] + i, a[r][1] + j)) {
                                count++;
                            }

                        }
                    }
                }
            }
        }
        selectedSquare = temp;
        return count;
    }

    public void endGame() {
        JOptionPane.showMessageDialog(frame, "NO MORE MOVES", "GAME OVER", JOptionPane.PLAIN_MESSAGE);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        Menu newMenu = new Menu();
    }
}

