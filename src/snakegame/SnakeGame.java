package snakegame;

import javax.swing.JFrame;

/**
 * @author - joydrath roy joy
 **/
public class SnakeGame {
    public static void main(String[] args) {
         
        JFrame frame =  new JFrame();
        frame.add(new Panel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setTitle("Demo Game");
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.pack();
    }
    
}
