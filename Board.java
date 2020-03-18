import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@SuppressWarnings("serial")
/**
 * Represents and implements the Board of the game Hoppers.
 * Only one Board should be created at a time.
 */
public class Board extends JFrame implements ActionListener
{
    private Square coordinates[][];
    private Square selectedSquare;
    private JFrame frame;
    private JPanel panel;
    private boolean selected;
   
    /**
     * Creates a new Board representing the passed selected level, used to be played Hoppers on. 
     * @param selectedLevel integer passed indicating which level has been selected and must be loaded to the board
     */
    public Board(int selectedLevel) {
        coordinates = new Square[5][];
        selectedSquare = new Square(99, 99, new JButton(), Piece.WATER);
        panel = new JPanel();

        panel.setLayout(new GridLayout(5, 5));

        loadLevel(selectedLevel);      

        frame = new JFrame("Hoppers, level " + selectedLevel); //add level number
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
                if (e.getSource() == coordinates[i][j].getButton()) {
                    x = i;
                    y = j;
                }
            }
        }

        // Calculate if a square is selected
        if (selected) {
            // If so, calculate legality of move
            if (calcLegalMove(x, y)) {
                // If legal, make move, remove hopped over frog, deselect
                selectedSquare.moveTo(coordinates[x][y]);
                // RemoveFrog
                removeFrog(x, y);
                // Deselect
                selected = false;
                selectedSquare = new Square(99, 99, new JButton(), Piece.WATER);
                if (numLegalMoves() == 0) {
                    endGame();
                }
            } else {
                // If not legal deselect
                selectedSquare.deselectSquare();
                selected = false;
                selectedSquare = new Square(99, 99, new JButton(), Piece.WATER);
            }
        } else {
            // If nothing is selected & clicked square is a green or red frog, select it
            if (coordinates[x][y].getPiece() == Piece.GREEN || coordinates[x][y].getPiece() == Piece.RED) {
                selected = true;
                coordinates[x][y].selectSquare();
                selectedSquare = coordinates[x][y];
            }
            // Otherwise, make no selection
        }

        frame.revalidate();
        frame.repaint();
    }

    /**
     * This method takes the passed selected level and loads the level off the levels.csv file. 
     * Then uses the encoded data of the level to constuct this level by initialising the coordinates 2D String array with the appropriate data.
     * @param selectedLevel The selected level represented as an integer
     */
    public void loadLevel(int selectedLevel){
        String fileName= "levels.csv";
        File file= new File(fileName);
        Scanner inputStream;
        int count = 0;
        String[] level = new String[25];
        try{
            inputStream = new Scanner(file);
            String line= inputStream.next();
            level = line.split(","); 
            while(count != selectedLevel && inputStream.hasNext()){
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
            coordinates[i] = new Square[5];
            for (int j = 0; j < 5; j++) {                
                if (level[j+k] .equals("w")) {
                    coordinates[i][j] = new Square(i, j, new JButton(new ImageIcon("images/Water.png")), Piece.WATER);
                } else if (level[j+k].equals("p")) {
                    coordinates[i][j] = new Square(i, j, new JButton(new ImageIcon("images/LilyPad.png")), Piece.PAD);
                } else if (level[j+k].equals("g")) {
                    coordinates[i][j] = new Square(i, j, new JButton(new ImageIcon("images/GreenFrog.png")), Piece.GREEN);
                } else if (level[j+k].equals("r")) {
                    coordinates[i][j] = new Square(i, j, new JButton(new ImageIcon("images/RedFrog.png")), Piece.RED);
                }
                panel.add(coordinates[i][j].getButton());
                coordinates[i][j].getButton().addActionListener(this);
            }
            k = k + 5; 
        }
    }

    /**
     * Calculates coordinates of the frog to remove, then removes it
     * @param x x coordinate of frog to remove
     * @param y y coordinate of frog to remove
     */
    public void removeFrog(int x, int y) {
        int q = selectedSquare.getX() + ((x - selectedSquare.getX()) / 2);
        int r = selectedSquare.getY() + ((y - selectedSquare.getY()) / 2);
        coordinates[q][r].getButton().setIcon(new ImageIcon("images/LilyPad.png"));
        coordinates[q][r].setPiece(Piece.PAD);
    }

    /**
     * This method calculates whether or not the attempted move is within the game's rules
     * @param x x coordinate of move to test
     * @param y y coordinate of move to test
     * @return True if the move was legal, false if not
     */
    public boolean calcLegalMove(int x, int y) {
        if (checkFrog(x, y) && checkPad(x, y)) {
            return true;
        }
        return false;
    }
    
    /**
     * This method calculates whether or not the passed coordinates holds a LilyPad piece
     * @param x x coordinate of pad
     * @param y y coordinate of pad
     * @return True if there is a LilyPad, false if there is not
     */
    public boolean checkPad(int x, int y) {
        if (coordinates[x][y].getPiece() == Piece.PAD) {
            return true;
        }
        return false;
    }

    /**
     * This method checks that there is a frog in the intervening space between the currently selected square, and the passed square.
     * @param x x coordinate of square being attempted to be moved to
     * @param y y coordinate of square being attempted to be moved to
     * @return True if there exists a frog between squares, false if not
     */
    public boolean checkFrog(int x, int y) {
        int xDiff = x - selectedSquare.getX();
        int yDiff = y - selectedSquare.getY();
        //Coordinates of the intervening space
        int q = selectedSquare.getX() + (xDiff / 2);
        int r = selectedSquare.getY() + (yDiff / 2);
        if (coordinates[q][r].getPiece() == Piece.GREEN || coordinates[q][r].getPiece() == Piece.RED) {
            return true;
        }
        return false;
    }
    
    /**
     * This method calculates the number of legal moves possible currently on the board 
     * @return The number of legal moves possible
     */
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

    /**
     * This method is called at the ending of the game, communicates this to the player, and takes the user back to the menu
     */
    public void endGame() {
        JOptionPane.showMessageDialog(frame, "NO MORE MOVES", "GAME OVER", JOptionPane.PLAIN_MESSAGE);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        Menu newMenu = new Menu();
    }
}

