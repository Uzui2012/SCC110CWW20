import javax.swing.*;
import java.awt.*;

public class Board
{
    private Square coordinates[][];

    public Board(){
        coordinates = new Square[5][];      

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5,5));
        for(int i = 0; i < 5; i ++){
            coordinates[i] = new Square[5]; //init squares
            for(int  j = 0; j < 5; j++){
                //use level.csv later
                coordinates[i][j] = new Square(i, j, new JButton(new ImageIcon("images/GreenFrog.png")));
                panel.add(coordinates[i][j].getButton());
            }
        }

        
        JFrame frame = new JFrame("Frame");
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(750,750));
        frame.setResizable(false);       
        frame.setVisible(true);
    }

}