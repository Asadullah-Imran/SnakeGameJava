import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH=600;
    static final int SCREEN_HIEGHT=600;
    static final int UNIT_SIZE=25;
    static final int GAME_UNITS=(SCREEN_HIEGHT*SCREEN_WIDTH)/UNIT_SIZE;
    static final int UNIT_DELAY=75;
    final int x[]=new int[GAME_UNITS];
    final int y[]=new int[GAME_UNITS];
    int bodyParts=6;
    int applesEaten=0;
    int appleX;
    int appleY;
    char direction='R';
    boolean running=false;
    Timer timer;
    Random random;


    GamePanel(){
        random= new Random();
        setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HIEGHT));
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame(){

    }
    public void paintComponent(Graphics g){

    }
    public void draw(Graphics g){

    }
    public void newApple(){

    }
    public void move(){

    }
    public void cheackApple(){

    }
    public void  checkCollisions(){

    }
    public void gameOver(Graphics g){

    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
    public class MyKeyAdapter extends KeyAdapter{
        public void keyPressed(KeyEvent e){

        }
    }
}
