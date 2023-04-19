
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;





public class Game extends JFrame{

    public final int SCREEN_WIDTH = 500;

    public final int SQUARE_WIDTH = 10;

    public final int SQUARE_HEIGHT = 10;

    public final int SCREEN_HEIGHT = 500;

    public AirCraft airCraft = null;
    
    public ArrayList<Thread> airCraftFireList = null;

    public ArrayList <Thread> enemyList = null;

    public ArrayList<Thread> enemyFireList = null;

    public ArrayList <Thread> friendList = null;

    public ArrayList<Thread> friendFireList = null;

    public ArrayList<Point> pointsList = null;

    public Random random = null;

    public boolean firstPlacement = true;

    public GamePanel gamePanel = null;

    public ReentrantLock generalLock = null;

    public boolean  isFired = false;

    public boolean isGameWinned = false;

    public Game (){

        generalLock = new ReentrantLock();

        airCraftFireList = new ArrayList<>();

        enemyFireList = new ArrayList<>();

        friendFireList = new ArrayList<>();

        pointsList = new ArrayList<>();

        enemyList = new ArrayList<>(); 

        friendList = new ArrayList<>();

        random = new Random();

        this.setLayout(new BorderLayout());

        gamePanel = new GamePanel();

        gamePanel.setBounds(0, 0,SCREEN_WIDTH,SCREEN_HEIGHT);

        this.add(gamePanel,BorderLayout.CENTER);

        this.setVisible(true);

        this.setTitle("WAR GAME");

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.setLocationRelativeTo(null);

        this.setSize(SCREEN_WIDTH,SCREEN_HEIGHT);

        //this.setResizable(false);

    }


    public void setUpThePoints(){

        int minX = 10, maxX = gamePanel.getWidth()-20-2;   // eskiden screenWiddt vardı
        int minY = 30, maxY = gamePanel.getHeight()-20-2;  // eskiden screenHeight vardı

        int randomX = 0;
        int randomY = 0;

        Point tempPoint = null;

        Square tempSquare = null;

        while(pointsList.size()!= ( enemyList.size() + friendList.size() )){

            randomX = random.nextInt(maxX - minX +1) + minX;
            randomY = random.nextInt(maxY - minY +1) + minY;

            tempSquare = new Square(randomX,randomY,10,10,Color.BLACK);

            tempPoint = new Point(randomX, randomY);

            if(pointsList.contains(tempPoint) == false && tempSquare.intersects(airCraft.aircraftSquare) == false) pointsList.add(tempPoint);

        }

    }


    public class GamePanel extends JPanel implements KeyListener ,MouseListener{

        public GamePanel (){

            this.addKeyListener(this);
            this.addMouseListener(this);
            this.setFocusable(true);
            this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
            this.setMinimumSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
            this.setMaximumSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));

        }


        public void paintComponent(Graphics g ){

                super.paintComponent(g);
        
                Enemy tempEnemy = null;
        
                Friend tempFriend = null;
        
                if(firstPlacement){
        
                    setUpThePoints();
        
                    int index = 0, indexEnemy =0, indexFriend = 0;
        
                    Point pointArray[] = new Point[enemyList.size()+friendList.size()];
        
                    pointArray = pointsList.toArray(pointArray);
        
                    while(indexEnemy < enemyList.size()){
        
                        tempEnemy = (Enemy) enemyList.get(indexEnemy);
        
                        tempEnemy.enemySquare.x = pointArray[index].x;
        
                        tempEnemy.enemySquare.y = pointArray[index].y;
        
                        index++;
                        indexEnemy++;
        
                    }
        
                    while(indexFriend < friendList.size()){
        
                        tempFriend = (Friend) friendList.get(indexFriend);
        
                        tempFriend.friendSquare.x = pointArray[index].x;
        
                        tempFriend.friendSquare.y = pointArray[index].y;
        
                        index++;
                        indexFriend++;
        
                    }
        
                    firstPlacement = false;
        
                }


                for(int i=0; i<enemyList.size(); i++){

                    tempEnemy = (Enemy) enemyList.get(i);
        
                    g.setColor(tempEnemy.enemySquare.squareColor);
                    g.fillRect(tempEnemy.enemySquare.x,tempEnemy.enemySquare.y,tempEnemy.enemySquare.width,tempEnemy.enemySquare.height);
        
                }
        
                for(int i=0; i<friendList.size(); i++){
        
                    tempFriend = (Friend) friendList.get(i);
                    
                    g.setColor(tempFriend.friendSquare.squareColor);
                    g.fillRect(tempFriend.friendSquare.x,tempFriend.friendSquare.y,tempFriend.friendSquare.width,tempFriend.friendSquare.height);
        
                }
        
                g.setColor(airCraft.aircraftSquare.squareColor);
                g.fillRect(airCraft.aircraftSquare.x, airCraft.aircraftSquare.y,airCraft.aircraftSquare.width,airCraft.aircraftSquare.height);

                for(int i=0; i<airCraftFireList.size(); i++){

                    Fire fire = (Fire)airCraftFireList.get(i);

                    g.setColor(fire.fireSquare.squareColor);
                    g.fillRect(fire.fireSquare.x,fire.fireSquare.y,fire.fireSquare.width,fire.fireSquare.height);

                }

                for(int i=0; i<enemyFireList.size(); i++){

                    Fire fire = (Fire) enemyFireList.get(i);

                    g.setColor(fire.fireSquare.squareColor);
                    g.fillRect(fire.fireSquare.x,fire.fireSquare.y,fire.fireSquare.width,fire.fireSquare.height);

                }

                for(int i=0; i<friendFireList.size(); i++){

                    Fire fire = (Fire) friendFireList.get(i);

                    g.setColor(fire.fireSquare.squareColor);
                    g.fillRect(fire.fireSquare.x,fire.fireSquare.y,fire.fireSquare.width,fire.fireSquare.height);

                }

            
        }


        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyReleased(KeyEvent e) {}
    
        @Override
        public void keyPressed(KeyEvent e) {
    
            int keyCode = e.getKeyCode();
    
            if(keyCode == KeyEvent.VK_W){

                airCraft.aircraftSquare.y -= 10;
    
                boolean control = borderControl(airCraft.aircraftSquare);
    
                if(control == false) airCraft.aircraftSquare.y += 10;

                repaint();
            }
    
            else if(keyCode == KeyEvent.VK_A){
    
                airCraft.aircraftSquare.x -= 10;
    
                boolean control = borderControl(airCraft.aircraftSquare);
    
                if(control == false) airCraft.aircraftSquare.x += 10;

                repaint();
            }
    
            else if(keyCode == KeyEvent.VK_S){
    
                airCraft.aircraftSquare.y += 10;
    
                boolean control = borderControl(airCraft.aircraftSquare);
    
                if(control == false) airCraft.aircraftSquare.y -= 10;

                repaint();
            }
    
            else if(keyCode == KeyEvent.VK_D){
    
                airCraft.aircraftSquare.x += 10;
    
                boolean control = borderControl(airCraft.aircraftSquare);

                if(control == false) airCraft.aircraftSquare.x -= 10;

                repaint();
            }

         
        }

        @Override
        public void mouseClicked(MouseEvent e) { isFired = true; }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}


    }


    public class Square extends Rectangle{

        public Color squareColor;

        public Square (int startX, int  startY, int width, int height,Color squareColor){

            super(startX,startY,width,height);
            this.squareColor = squareColor;

        }

    }


    public class Fire extends Thread{

        public Square fireSquare;

        public String fireType = null;

        public String whichDirection = null;

        public Fire (String fireType , String whichDirection, Color fireColor,Thread squareThread){

            this.setName("FIRE THREAD");

            this.fireType =fireType;

            this.whichDirection = whichDirection;

            int startX = 0, startY = 0;

            if(squareThread instanceof Enemy){

                Enemy tempEnemy = (Enemy) squareThread;

                if(whichDirection.equals("left")) {startX = tempEnemy.enemySquare.x - 6; startY = tempEnemy.enemySquare.y+3;}

                else if(whichDirection.equals("right")) {startX = tempEnemy.enemySquare.x + tempEnemy.enemySquare.width; startY = tempEnemy.enemySquare.y+3;}

            }

            else if(squareThread instanceof Friend){

                Friend tempFriend = (Friend) squareThread;

                if(whichDirection.equals("left")) {startX = tempFriend.friendSquare.x- 6; startY = tempFriend.friendSquare.y+3;}      

                else if(whichDirection.equals("right")) {startX = tempFriend.friendSquare.x + tempFriend.friendSquare.width; startY = tempFriend.friendSquare.y+3;}

                

            }

            else if(squareThread instanceof AirCraft){
                
                AirCraft tempAirCraft = (AirCraft) squareThread;

                if(whichDirection.equals("left")) {startX = tempAirCraft.aircraftSquare.x - 6; startY = tempAirCraft.aircraftSquare.y+3;}

                else if(whichDirection.equals("right")) {startX = tempAirCraft.aircraftSquare.x + tempAirCraft.aircraftSquare.width; startY = tempAirCraft.aircraftSquare.y+3;}

            }

            this.fireSquare = new Square(startX, startY, 5,5, fireColor);

        }


        public void run(){

            try{
                Thread.sleep(50);
            }
            catch(Exception e){
                e.getStackTrace();
            }

            while(true){

                generalLock.lock();

                if(this.fireType.equals("aircraft")){

                    if(this.whichDirection.equals("left")){

                        this.fireSquare.x -=10;

                        if(borderControl(fireSquare) == false){

                            airCraftFireList.remove(this);

                            gamePanel.repaint();

                            generalLock.unlock();

                            return;

                        }    

                    }

                    else if(this.whichDirection.equals("right")){

                        this.fireSquare.x +=10;

                        if(borderControl(fireSquare) == false){

                            airCraftFireList.remove(this);

                            gamePanel.repaint();

                            generalLock.unlock();

                            return;

                        }


                    }

                    for(int i=0; i<enemyList.size(); i++){
    
                        Enemy tempEnemy = (Enemy) enemyList.get(i);
    
                        if(this.fireSquare.intersects(tempEnemy.enemySquare)){
    
                            airCraftFireList.remove(this);
    
                            enemyList.remove(i);
    
                            gamePanel.repaint();

                            generalLock.unlock();
                
                            return;
    
                        }
    
                    }
    
                }
    
                else if(this.fireType.equals("enemy")){

                    if(this.whichDirection.equals("left")){

                        this.fireSquare.x -=10;

                        if(borderControl(fireSquare) == false){

                            enemyFireList.remove(this);

                            gamePanel.repaint();

                            generalLock.unlock();

                            return;

                        }

                    }

                    else if(this.whichDirection.equals("right")){

                        this.fireSquare.x += 10;

                        if(borderControl(fireSquare) == false){

                            enemyFireList.remove(this);

                            gamePanel.repaint();

                            generalLock.unlock();

                            return;

                        }


                    }

                    for(int i=0; i<friendList.size(); i++){

                            Friend tempFriend = (Friend) friendList.get(i);

                            if(this.fireSquare.intersects(tempFriend.friendSquare)){

                                enemyFireList.remove(this);

                                friendList.remove(i);

                                gamePanel.repaint();

                                generalLock.unlock();

                                return;

                            }

                    }

                }
    
                else if(this.fireType.equals("friend")){

                    if(this.whichDirection.equals("left")){

                        this.fireSquare.x -= 10;

                        if(borderControl(fireSquare) == false){

                            friendFireList.remove(this);

                            gamePanel.repaint();

                            generalLock.unlock();

                            return;

                        }

                    }

                    else if(this.whichDirection.equals("right")){

                        this.fireSquare.x +=10;

                        if(borderControl(fireSquare) == false){

                            friendFireList.remove(this);

                            gamePanel.repaint();

                            generalLock.unlock();

                            return;

                        }

                    }


                    for(int i=0; i<enemyList.size(); i++){

                        Enemy tempEnemy = (Enemy)enemyList.get(i);

                        if(this.fireSquare.intersects(tempEnemy.enemySquare)){

                            friendFireList.remove(this);

                            enemyList.remove(i);

                            gamePanel.repaint();

                            generalLock.unlock();

                            return;

                        }

                    }

                }


                generalLock.unlock();

                gamePanel.repaint();

                try{
                    Thread.sleep(100);
                }
                catch(Exception e){

                }
           }
        }


    }


    public class Enemy extends Thread {

        public Square enemySquare;

        public Enemy (){

            this.setName("ENEMY THREAD");

            this.enemySquare = new Square(0,0,SQUARE_WIDTH,SQUARE_HEIGHT,Color.BLACK);

            enemyList.add(this);

        }


        public void run(){

            try{
                Thread.sleep(50);
            }
            catch(Exception e){
                e.getStackTrace();
            }

            int randomDirection = 0;

            long timeControl = 0;

            while(enemyList.contains(this)){

                generalLock.lock();

                randomDirection = random.nextInt(4);

                    if(randomDirection == 0){

                        this.enemySquare.x -= 10;
                        if(borderControl(enemySquare) == false) this.enemySquare.x +=10;

                    }
                    else if(randomDirection == 1){

                        this.enemySquare.x += 10;
                        if(borderControl(enemySquare) == false) this.enemySquare.x -=10;

                    }
                    else if(randomDirection == 2){

                        this.enemySquare.y -=10;
                        if(borderControl(enemySquare) == false) this.enemySquare.y +=10;

                    }
                    else if(randomDirection == 3){

                        this.enemySquare.y +=10;
                        if(borderControl(enemySquare) == false) this.enemySquare.y -=10;

                    }

                    Friend tempFriend = null;

                    for(int i=0; i<friendList.size(); i++){

                        tempFriend = (Friend)friendList.get(i);

                        if(this.enemySquare.intersects(tempFriend.friendSquare)){

                            friendList.remove(i);

                            enemyList.remove(this);

                            generalLock.unlock();

                            gamePanel.repaint();

                            return;

                        }

                    }

                    
                    if(timeControl % 2 == 1){

                        Fire leftFire = new Fire("enemy","left",Color.BLUE,this);

                            enemyFireList.add(leftFire);
                            leftFire.start();
                        
                        Fire rightFire = new Fire("enemy","right",Color.BLUE,this);
                        
                            enemyFireList.add(rightFire);
                            rightFire.start();

                    }


                    generalLock.unlock();


                    gamePanel.repaint();

                    try{
                        Thread.sleep(500);
                    }
                    catch(Exception e){
                        e.getStackTrace();
                    }

                    timeControl++;


            }

            
        }


    }


    public class Friend extends Thread{

        public Square friendSquare;

        public Friend (){

            this.setName("FRIEND THREAD");

            this.friendSquare = new Square(0,0,SQUARE_WIDTH, SQUARE_HEIGHT ,Color.GREEN);

            friendList.add(this);

        }


        public void run(){

            try{
                Thread.sleep(50);
            }
            catch(Exception e){
                e.getStackTrace();
            }

            int randomDirection = 0;

            long timeControl = 0;

            while(friendList.contains(this)){

                randomDirection = random.nextInt(4);

                    generalLock.lock();

                    if(randomDirection == 0){

                        this.friendSquare.x -= 10;
                        if(borderControl(friendSquare) == false) this.friendSquare.x +=10;

                    }
                    else if(randomDirection == 1){

                        this.friendSquare.x += 10;
                        if(borderControl(friendSquare) == false) this.friendSquare.x -=10;


                    }
                    else if(randomDirection == 2){

                        this.friendSquare.y -=10;
                        if(borderControl(friendSquare) == false) this.friendSquare.y +=10;

                    }
                    else if(randomDirection == 3){

                        this.friendSquare.y +=10;
                        if(borderControl(friendSquare) == false) this.friendSquare.y -=10;

                    }

                    if(timeControl % 2 == 1){

                        Fire leftFire = new Fire("friend","left",Color.MAGENTA,this);

                            friendFireList.add(leftFire);
                            leftFire.start();

                        Fire rightFire = new Fire("friend","right",Color.MAGENTA,this);

                           friendFireList.add(rightFire);
                           rightFire.start();

                    }

                
                    generalLock.unlock();
                    
                    gamePanel.repaint();

                    try{
                        Thread.sleep(500);
                    }
                    catch(Exception e){
                        e.getStackTrace();
                    }

                    timeControl++;


            }

        }


    }


    public class AirCraft extends Thread{

        public Square aircraftSquare;

        public AirCraft(){

            this.setName("AIRCRAFT THREAD");

            this.aircraftSquare = new Square(250, 250, SQUARE_WIDTH, SQUARE_HEIGHT,Color.RED);

            airCraft = this;

        }

        
        public void run(){


            try{
                Thread.sleep(5);
            }
            catch(Exception e){
                e.getStackTrace();
            }

            while(true){

                generalLock.lock();

                if(enemyList.size() == 0){

                    isGameWinned = true;

                    stopTheGame();

                }

                
                for(int i=0; i<enemyList.size(); i++){

                    Enemy tempEnemy = (Enemy) enemyList.get(i);

                    if(this.aircraftSquare.intersects(tempEnemy.enemySquare)){

                        stopTheGame();

                    }

                }

                for(int i=0; i<enemyFireList.size(); i++){

                    Fire enemyFire = (Fire) enemyFireList.get(i);

                    if(this.aircraftSquare.intersects(enemyFire.fireSquare)){

                        stopTheGame();

                    }

                }

        
               if(isFired){

                   Fire leftFire = new Fire("aircraft","left",Color.ORANGE,this);

                   airCraftFireList.add(leftFire);

                   leftFire.start();

                   Fire rightFire = new Fire("aircraft","right",Color.ORANGE,this);

                   airCraftFireList.add(rightFire);

                   rightFire.start();
       
                   isFired = false;
                
                }


                generalLock.unlock();

            }

        }
        
    }


    public class Point {

        public Square pointSquare = null;

        public int x, y;

        public Point (int x, int y){

            this.x = x;

            this.y = y;

            pointSquare = new Square(x, y, SQUARE_WIDTH+1,SQUARE_HEIGHT+1,Color.BLACK);

        }

        public boolean equals(Object obj){

            if(obj instanceof Point == false)  return false;

            Point tempPoint = (Point) obj;

            if(Math.abs(this.x - tempPoint.x) <= 0.1 && Math.abs(this.y - tempPoint.y) <= 0.1) return true;

            if(this.pointSquare.intersects(tempPoint.pointSquare)) return true;

            return false;


        }

    }


    public boolean borderControl(Square square){

        if(square.x >= 0 && square.x <= 470 && square.y >=0 && square.y<= 450) return true;

        return false;
    }

    public void stopTheGame(){

        gamePanel.removeKeyListener(gamePanel);

        gamePanel.repaint();

        if(isGameWinned)
           JOptionPane.showMessageDialog(Game.this,"Game Finished You WIN.....!!!!!");
        else
           JOptionPane.showMessageDialog(Game.this,"Game Finished You LOST.....!!!!!");

        System.exit(0);

    }


}



