package snakegame;
import javax.swing.*;
public class snake extends JFrame{
    snake(){
        super("snakegame");
        add(new board());
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }
    public static void main(String[] args) {
        new snake().setVisible(true);
    }
}