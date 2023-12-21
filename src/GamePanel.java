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
    static final int DELAY=95;
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

    //Adding Obstackles
    int[] obstacleX;
    int[] obstacleY;
    int numObstacles = 3;

    //adding color for background
    Color darkGreen = new Color(0xccd5ae);
    Color lightGreen = new Color(0xe9edc9);
    Color appleColor = new Color(0xef233c);
    Color obstacklesColor = new Color(0x30343f);
    Color headColor = new Color(0x344e41);
    Color bodyColor = new Color(0x588157);
    //Color gameOverbgColor = new Color(0x4a5759);
    //Color gameOverbgColor = new Color(0x212529);
    Color gameOverbgColor = new Color(0x30343f);
    //Color gameOverbgColor = new Color(0x4a5759);


    GamePanel(){

        random= new Random();
        setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HIEGHT));
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(new MyKeyAdapter());

        //adding obstackels
        obstacleX = new int[numObstacles];
        obstacleY = new int[numObstacles];
        placeObstacles();


        startGame();
    }

    public void startGame(){
        newApple();
        running=true;
        timer= new Timer(DELAY,this);
        timer.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){

        if(running){

// Draw the chessboard pattern
            for (int i = 0; i < SCREEN_WIDTH / UNIT_SIZE; i++) {
                for (int j = 0; j < SCREEN_HIEGHT / UNIT_SIZE; j++) {
                    if ((i + j) % 2 == 0) {
                        g.setColor(darkGreen);
                    } else {
                        g.setColor(lightGreen);
                    }
                    g.fillRect(i * UNIT_SIZE, j * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
                }
            }

// This is for making grid line;
//        for (int i=0;i<SCREEN_HIEGHT/UNIT_SIZE;i++ ){
//            g.drawLine(i*UNIT_SIZE,0,i*UNIT_SIZE,SCREEN_HIEGHT);
//            g.drawLine(0,i*UNIT_SIZE,SCREEN_WIDTH,i*UNIT_SIZE);
//        }
        g.setColor(appleColor);
        g.fillOval(appleX,appleY,UNIT_SIZE,UNIT_SIZE);

        //drawing body parts
        for(int i=0;i<bodyParts;i++){
            if(i==0){
                g.setColor(headColor);
                //g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
//                g.fillArc(x[i],y[i],UNIT_SIZE,UNIT_SIZE,100,20);
                g.fill3DRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE,true);
            }else{
                g.setColor(bodyColor);
                g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);

            }
        }

            g.setColor(Color.red);
            g.setFont(new Font("Ink Free",Font.BOLD,40));
            FontMetrics metrics= getFontMetrics(g.getFont());
            g.drawString("Score: "+applesEaten,(SCREEN_WIDTH-metrics.stringWidth("Score: "+applesEaten))/2,g.getFont().getSize());


            //for adding obstackles
            drawObstacles(g);

        }else{
            gameOver(g);
        }


    }
    public void newApple(){
        appleX=random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY=random.nextInt((int)(SCREEN_HIEGHT/UNIT_SIZE))*UNIT_SIZE;
    }
    public void move(){
        for(int i= bodyParts;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        switch (direction){
            case 'U':
                y[0]=y[0]-UNIT_SIZE;
            break;
            case 'D':
                y[0]=y[0]+UNIT_SIZE;
                break;
            case 'L':
                x[0]=x[0]-UNIT_SIZE;
                break;
            case 'R':
                x[0]=x[0]+UNIT_SIZE;
                break;

        }
    }

    //two obstacles method

    public void placeObstacles() {
        for (int i = 0; i < numObstacles; i++) {
            obstacleX[i] = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
            obstacleY[i] = random.nextInt((int) (SCREEN_HIEGHT / UNIT_SIZE)) * UNIT_SIZE;
        }
    }

    public void drawObstacles(Graphics g) {
        g.setColor(obstacklesColor); // Set obstacle color
        for (int i = 0; i < numObstacles; i++) {
            g.fillRect(obstacleX[i], obstacleY[i], UNIT_SIZE, UNIT_SIZE);
        }
    }
    public void checkApple(){
        if((x[0]==appleX)&&(y[0]==appleY)){
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }
    public void  checkCollisions(){
        //checks if head collide  with body
        for(int i=bodyParts;i>0;i--){
            if((x[0] == x[i])&&(y[0]==y[i])){
                running=false;
            }
        }
        //check if head touches left  border
        if(x[0]<0){
            running=false;
        }
        //check if head touches right  border
        if(x[0]>SCREEN_WIDTH){
            running=false;
        }
        //check if head touches top  border
        if(y[0]<0){
            running=false;
        }
        //check if head touches bottom  border
        if(y[0]>SCREEN_HIEGHT){
            running=false;
        }


        // Check for collisions with obstacles
        for (int i = 0; i < numObstacles; i++) {
            if (x[0] == obstacleX[i] && y[0] == obstacleY[i]) {
                running = false;
            }
        }


        if (!running){
            timer.stop();
        }
    }
    public void gameOver(Graphics g){
        setBackground(gameOverbgColor);
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,40));
        FontMetrics metrics1= getFontMetrics(g.getFont());
        g.drawString("Score: "+applesEaten,(SCREEN_WIDTH-metrics1.stringWidth("Score: "+applesEaten))/2,g.getFont().getSize());
        //Game over Text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,75));
        FontMetrics metrics= getFontMetrics(g.getFont());
        g.drawString("Game Over",(SCREEN_WIDTH-metrics.stringWidth("Game Over"))/2,SCREEN_HIEGHT/2);


        //powered by
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial",Font.BOLD,20));
        FontMetrics metrics3= getFontMetrics(g.getFont());
        g.drawString("   Developed by: Asadullah Imran",(SCREEN_WIDTH-metrics.stringWidth("Developed by: Asadullah Imran"))/50,SCREEN_HIEGHT-50);


        // Draw the author's name at the middle bottom
//        g.setColor(Color.black);
//        g.setFont(new Font("Arial", Font.PLAIN, 20));
//        String authorName = "Author: Your Name";
//        int xAuthor = (SCREEN_WIDTH - metrics.stringWidth(authorName)) / 50;
//        int yAuthor = SCREEN_HIEGHT - 50;
//        g.drawString(authorName, xAuthor, yAuthor);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkApple();
            checkCollisions();
        }
        repaint();

    }
    public class MyKeyAdapter extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction !='R'){
                        direction='L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction !='L'){
                        direction='R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction !='D'){
                        direction='U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction !='U'){
                        direction='D';
                    }
                    break;

            }
        }
    }
}
