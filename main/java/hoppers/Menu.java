package main.java.hoppers;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.WindowEvent;

/**
 * Menu class to select the level of Hoppers to play. 
 */
public class Menu extends JFrame implements ActionListener, ItemListener
{
    private JFrame frame;
    private JPanel panel;
    private JButton start;
    private JComboBox<String> box;
    private int selectedLevel;
    private String[] levels;

    /**
     * Create a Menu object that starts a JFrame with needed components to start a game of Hoppers.
     */
    public Menu() {
        levels = new String[40];
        selectedLevel = 1;
        for (int i = 0; i < 40; i++) {
            levels[i] = Integer.toString(i + 1);
        }

        panel = new JPanel();
        start = new JButton("Start");
        box = new JComboBox<>(levels);

        panel.add(new JLabel("Select Level:"));
        panel.add(box);        
        panel.add(start);        

        start.addActionListener(this);
        box.addItemListener(this);

        frame = new JFrame("Hoppers");
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(250, 150));
        frame.setResizable(false);
        frame.setVisible(true);
    }
    
    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            selectedLevel = Integer.parseInt(e.getItem().toString());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        Board board = new Board(selectedLevel);
    }
}