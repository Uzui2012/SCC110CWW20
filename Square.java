import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Square extends JPanel
{
    private int x,y;
    private JButton button;
    public Square(int x, int y, JButton button){
        this.x = x;
        this.y = y;
        this.button = button;
    }

    public JButton getButton(){
        return this.button;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

   /* public void setButtonActionListener(ActionListener listener){
        button.addActionListener(listener);
    }*/
}