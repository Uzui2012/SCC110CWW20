import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Menu extends JFrame implements ActionListener, ItemListener
{
    private JFrame frame;
    private JPanel panel;
    private JButton start;
    private JComboBox<String> box;
    private int selectedLevel;
    private String[] levels;


    public Menu() {
        levels = new String[40];
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