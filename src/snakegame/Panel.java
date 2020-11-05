package snakegame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author - joydrath roy joy
 */

public class Panel extends JPanel implements ActionListener{

    private static final int Panelwidth = 400;
    private static final int Panelheight =400;
    private static final int Perunit = 15;
    private static final int Gameunit = (Panelwidth*Panelheight)/Perunit;
    private static final int Delay = 200;
    int SnakeX[] = new int [Gameunit];
    int SnakeY[] = new int [Gameunit];
    
    int applefruitX ,applefruitY,bodyparts=3,totalfruits = 0;
    
    char Direction = 'R';
    boolean running = false;
    
    Timer timer;
    Random random;
    
    Panel(){
        this.setPreferredSize(new Dimension(Panelwidth,Panelheight));
        this.setBackground(Color.gray);
        this.setForeground(Color.BLUE);
        this.setFocusable(true);
        this.addKeyListener(new ControlKey());
        startGame();
    }
    
    @Override
    public void paint(Graphics g){
        super.paint(g);
        if(running){
            // for spilts screen in Grid

            for(int i=0;i<Panelwidth/Perunit;i++){
                g.drawLine(0,i*Perunit,Panelwidth,i*Perunit);
                g.drawLine(i*Perunit, 0, i*Perunit,Panelheight);
            }

            // new apple 
            g.setColor(Color.red);
            g.fillOval(applefruitX, applefruitY, Perunit, Perunit);

            // snake draw

            for(int i=0;i<bodyparts;i++){
                if(i==0){
                    g.setColor(Color.black);
                    g.fillRect(SnakeX[i], SnakeY[i], Perunit, Perunit);
                }
                else {
                    g.setColor(Color.black);
                    g.fillRect(SnakeX[i], SnakeY[i], Perunit, Perunit);
                }
            }
        }
        else {
            score(g);
            gameOver(g);
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionevent) {
        if(running){
            snakeMove();
            appleCount();
            chkCollision();
        }
        repaint();
    }
    
    public class ControlKey extends KeyAdapter implements KeyListener {
    
        @Override
        public void keyTyped(KeyEvent arg0) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

            if(e.getKeyCode() == KeyEvent.VK_LEFT){
                if(Direction != 'R')Direction='L';
            }

            else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                if(Direction != 'L')Direction='R';
            }
            else if(e.getKeyCode() == KeyEvent.VK_UP){
                if(Direction != 'D')Direction='U';
            }
            else if(e.getKeyCode() == KeyEvent.VK_DOWN){
                if(Direction != 'U')Direction='D';
            }

        }

        @Override
        public void keyReleased(KeyEvent arg0) {

        }

    }
    
    public void startGame(){
        appearFruit();
        running = true;
        timer = new Timer(Delay,this);
        timer.start();
    }
    
    public void appearFruit(){
        random = new Random();
        applefruitX = random.nextInt((Panelwidth/Perunit))*Perunit;
        applefruitY = random.nextInt((Panelwidth/Perunit))*Perunit;
    }
    
    public void snakeMove(){
        for(int i=bodyparts;i>0;i--){
            SnakeX[i]= SnakeX[i-1];
            SnakeY[i]= SnakeY[i-1];
        }
        
        // for head shifting 
        switch (Direction) {
            case 'R':
                SnakeX[0] = SnakeX[0]+Perunit;
                break;
            case 'L':
                SnakeX[0] = SnakeX[0]-Perunit;
                break;
            case 'U':
                SnakeY[0] = SnakeY[0]-Perunit;
                break;
            case 'D':
                SnakeY[0] = SnakeY[0]+Perunit;
                break;
            default:
                break;
        }
    }
    
    public void chkCollision(){
         // for self collision
        for(int i=bodyparts;i>0;i--){
            if(SnakeX[0]== SnakeX[i] && SnakeY[0]== SnakeY[i]){
                running = false;
            }
        }
        
        // for boder collision 
        
        if(SnakeX[0]<0)running = false;
        if(SnakeX[0]>Panelwidth)running = false;
        if(SnakeY[0]<0)running = false;
        if(SnakeY[0]>Panelheight)running = false;
        
        if(running==false)timer.stop();
    }
    
    public void appleCount(){
        if(SnakeX[0] == applefruitX  && SnakeY[0] == applefruitY){
            totalfruits++;
            bodyparts++;
            appearFruit();
        }
    }
    
    public void gameOver(Graphics g){
        g.setColor(Color.red);
        g.setFont(new Font("Serif",Font.BOLD,30));
        FontMetrics mat = getFontMetrics(g.getFont());
        g.drawString("Game Over",(Panelwidth-mat.stringWidth("Game Over"))/2, (Panelheight- mat.stringWidth("Game Over"))/2);
    }
    
    public void score(Graphics g){
        g.setColor(Color.red);
        g.setFont(new Font("Serif",Font.BOLD,20));
        FontMetrics mat = getFontMetrics(g.getFont());
        g.drawString("Score : "+totalfruits,((Panelwidth-10)-mat.stringWidth("Score : "+totalfruits))/2, ((Panelheight-10)- mat.stringWidth("Score : "+totalfruits)-10)/2);
    }
    
}
