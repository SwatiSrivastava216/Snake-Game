package snakegame;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class board extends JPanel implements ActionListener{

    private Image apple;
    private Image dot;
    private Image head;
    private final int DOT_SIZE = 10;
    private final int ALL_DOTS = 900;
    private final int RANDOM_POSITION = 25;
    private int apple_x;
    private int apple_y;
    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];
    private boolean leftDirection = false;
    private boolean rightDirection =  true;
    private boolean upDirection =  false;
    private boolean downDirection =  false;
    private boolean inGame = true;
    private int dots;
    private Timer timer;
    board(){   
        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(500, 500));
        setFocusable(true);
        loadImages();
        initGame();
    }
    public void loadImages(){
        ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("snakegame/Icons/apple.png"));
        apple=i1.getImage();   
        ImageIcon i2=new ImageIcon(ClassLoader.getSystemResource("snakegame/Icons/dot.png"));
        dot=i2.getImage();
        ImageIcon i3=new ImageIcon(ClassLoader.getSystemResource("snakegame/Icons/head.png"));
        head=i3.getImage();
    }
    public void initGame(){   
        dots=3;
        for(int z=0;z<dots;z++){
            x[z]=50-z*DOT_SIZE;
            y[z]=50;
        }
        locateApple();
        timer = new Timer(200, this);
        timer.start();
    }   
    public void locateApple(){
        int r=(int)(Math.random()*RANDOM_POSITION);
        apple_x=(r * DOT_SIZE); 
        r=(int)(Math.random()*RANDOM_POSITION);
        apple_y=(r*DOT_SIZE); 
    }    
    public void checkApple(){
        if((x[0]==apple_x) && (y[0]==apple_y)){
            dots++;
            locateApple();
        }  
    }    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        if(inGame){
            g.drawImage(apple,apple_x,apple_y,this);
            for(int z=0;z<dots;z++){
                if(z == 0)
                    g.drawImage(head,x[z],y[z],this);
                else
                    g.drawImage(dot,x[z],y[z],this);
            }
            
            Toolkit.getDefaultToolkit().sync();
        }
        else
             gameOver(g);
    }        
    public void gameOver(Graphics g){
        String msg = "GAME OVER!!";
        Font font = new Font("TIMES NEW ROMAN", Font.BOLD, 45);
        FontMetrics metrices=getFontMetrics(font);
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(msg,(500-metrices.stringWidth(msg))/2,500/2);
    }
    public void checkCollision(){
        for(int z=dots;z>0;z--){
            if((z>4) && (x[0]==x[z]) && (y[0]==y[z]))
                inGame=false;
        } 
        if(y[0]>=500)
            inGame = false;
        if(x[0]>=500)
            inGame = false;
        if(x[0]<0)
            inGame = false;
        if(y[0] < 0 )
            inGame = false;
        if(!inGame)
            timer.stop();
    }
    public void move(){
        for(int z=dots;z>0;z--){
            x[z]=x[z-1]; 
            y[z]=y[z-1];
        }
        if(leftDirection)
            x[0]=x[0]-DOT_SIZE;
        if(rightDirection)
            x[0]+=DOT_SIZE;
        if(upDirection)
            y[0]=y[0]-DOT_SIZE;
        if(downDirection)
            y[0]+=DOT_SIZE;
    }    
    public void actionPerformed(ActionEvent ae){
        if(inGame){
            checkApple();
            checkCollision();
            move();
        }       
        repaint();
    }
    private class TAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
           int key=e.getKeyCode();
           if(key == KeyEvent.VK_LEFT && (!rightDirection)){
               leftDirection=true;
               upDirection=false;
               downDirection=false;
           }
           if(key == KeyEvent.VK_RIGHT && (!leftDirection)){
               rightDirection=true;
               upDirection=false;
               downDirection=false;
           }
           if(key==KeyEvent.VK_UP && (!downDirection)){
               leftDirection=false;
               upDirection=true;
               rightDirection=false;
           }
           if(key==KeyEvent.VK_DOWN && (!upDirection)){
               downDirection=true;
               rightDirection=false;
               leftDirection=false;
           }
        }
    }
}
