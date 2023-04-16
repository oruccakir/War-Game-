import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;



public class Game extends JFrame implements MouseListener {

    public final Object controlObject = new Object();

    public final int SCREEN_WIDTH = 500;

    public final int SQUARE_WIDTH = 10;

    public final int SQUARE_HEIGHT = 10;

    public final int SCREEN_HEIGHT = 500;

    public AirCraft airCraft = null;

    public ArrayList <Thread> enemyList = null;

    public ArrayList <Thread> friendList = null;

    public ArrayList<Point> pointsList = null;

    public Random random = null;

    public boolean firstPlacement = true;

    public GamePanel gamePanel = null;

    public String[] directions = null;

    public Game (){

        directions = new String[]{"left","right","up","down"};

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

        this.setSize(SCREEN_WIDTH,SCREEN_HEIGHT);

        
    }


       
    
            
    

    /*public void paint(Graphics g){

        super.paint(g);

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

                g.setColor(tempEnemy.enemySquare.squareColor);
                g.fillRect(tempEnemy.enemySquare.x,tempEnemy.enemySquare.y,tempEnemy.enemySquare.width,tempEnemy.enemySquare.height);

                index++;
                indexEnemy++;

            }

            while(indexFriend < friendList.size()){

                tempFriend = (Friend) friendList.get(indexFriend);

                tempFriend.friendSquare.x = pointArray[index].x;

                tempFriend.friendSquare.y = pointArray[index].y;

                g.setColor(tempFriend.friendSquare.squareColor);
                g.fillRect(tempFriend.friendSquare.x,tempFriend.friendSquare.y,tempFriend.friendSquare.width,tempFriend.friendSquare.height);

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

        

        

    }*/


   

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


    public class GamePanel extends JPanel implements KeyListener{

        public GamePanel (){

            this.addKeyListener(this);
            this.setFocusable(true);
            this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);

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
        
                        g.setColor(tempEnemy.enemySquare.squareColor);
                        g.fillRect(tempEnemy.enemySquare.x,tempEnemy.enemySquare.y,tempEnemy.enemySquare.width,tempEnemy.enemySquare.height);
        
                        index++;
                        indexEnemy++;
        
                    }
        
                    while(indexFriend < friendList.size()){
        
                        tempFriend = (Friend) friendList.get(indexFriend);
        
                        tempFriend.friendSquare.x = pointArray[index].x;
        
                        tempFriend.friendSquare.y = pointArray[index].y;
        
                        g.setColor(tempFriend.friendSquare.squareColor);
                        g.fillRect(tempFriend.friendSquare.x,tempFriend.friendSquare.y,tempFriend.friendSquare.width,tempFriend.friendSquare.height);
        
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
        
                
        
                
        
            


        }


        @Override
        public void keyTyped(KeyEvent e) {}
    
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
        public void keyReleased(KeyEvent e) {}



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

            this.fireType =fireType;

            this.whichDirection = whichDirection;

            int startX = 0, startY = 0;

            if(squareThread instanceof Enemy){

                Enemy tempEnemy = (Enemy) squareThread;

                if(whichDirection.equals("left")) startX = tempEnemy.enemySquare.x - 5;

                else if(whichDirection.equals("right")) startX = tempEnemy.enemySquare.x + tempEnemy.enemySquare.width;

                startY = tempEnemy.enemySquare.y;

            }

            else if(squareThread instanceof Friend){

                Friend tempFriend = (Friend) squareThread;

                if(whichDirection.equals("left")) startX = tempFriend.friendSquare.x- 5;

                else if(whichDirection.equals("right")) startX = tempFriend.friendSquare.x + tempFriend.friendSquare.width;

                startY = tempFriend.friendSquare.y;

            }

            else if(squareThread instanceof AirCraft){
                
                AirCraft tempAirCraft = (AirCraft) squareThread;

                if(whichDirection.equals("left")) startX = tempAirCraft.aircraftSquare.x - 5;

                else if(whichDirection.equals("right")) startX = tempAirCraft.aircraftSquare.x + tempAirCraft.aircraftSquare.width;

                startY = tempAirCraft.aircraftSquare.y;

            }


            this.fireSquare = new Square(startX, startY, 5,5, fireColor);  
            
        }


        public void run(){
            
            
        }


    }


    public class Enemy extends Thread {

        public Square enemySquare;

        public Fire leftFire, rightFire;

        public Enemy (){

            this.enemySquare = new Square(0,0,SQUARE_WIDTH,SQUARE_HEIGHT,Color.BLACK);




            enemyList.add(this);

        }






        public void run(){

            try{
                Thread.sleep(500);
            }
            catch(Exception e){
                e.getStackTrace();
            }

            int randomDirection = 0;

            while(true){

                randomDirection = random.nextInt(4);

                //synchronized(controlObject){

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

                    
                    

                    /*Friend tempFriend = (Friend) isIntersectWithFriend(enemySquare);

                    if(tempFriend != null){

                        friendList.remove(tempFriend);
    
                        enemyList.remove(this);
    
                    }*/

                    

                    

                    

                    

                    gamePanel.repaint();

                    try{
                        Thread.sleep(500);
                    }
                    catch(Exception e){
                        e.getStackTrace();
                    }

                //}


            }

            
        }


    }


    public class Friend extends Thread{

        public Square friendSquare;

        public Fire leftFire, rightFire;

        public Friend (){

            this.friendSquare = new Square(0,0,SQUARE_WIDTH, SQUARE_HEIGHT ,Color.GREEN);



            friendList.add(this);

        }





        public void run(){

            try{
                Thread.sleep(500);
            }
            catch(Exception e){
                e.getStackTrace();
            }

            int randomDirection = 0;

            while(true){

                randomDirection = random.nextInt(4);

                //synchronized(controlObject){

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

                    /*Enemy tempEnemy = (Enemy) isIntersectWithEnemy(friendSquare);

                    if(tempEnemy != null){

                        enemyList.remove(tempEnemy);

                        friendList.remove(this);

                    }*/

                    gamePanel.repaint();

                    try{
                        Thread.sleep(500);
                    }
                    catch(Exception e){
                        e.getStackTrace();
                    }

                //}


            }

            
            
        }


    }


    public class AirCraft extends Thread{

        public Square aircraftSquare;

        public Fire leftFire, rightFire;

        public AirCraft(){

            this.aircraftSquare = new Square(250, 250, SQUARE_WIDTH, SQUARE_HEIGHT,Color.RED);

            this.leftFire = new Fire("aircraftFire", "left", Color.ORANGE, this);

            this.rightFire= new Fire("aircraftFire", "right", Color.ORANGE, this);


            airCraft = this;

        }

        
        public void run(){

            System.out.println("aircraft");

            

            

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


    
    public synchronized boolean  borderControl(Square square){

        if(gamePanel.contains(square.x, square.y) && gamePanel.contains(square.x+square.width, square.y+square.height)) return true;

        return false;
    }

    public synchronized Thread isIntersectWithEnemy(Square square){

        Enemy tempEnemy = null;

        for(int i=0; i<enemyList.size(); i++){

            tempEnemy = (Enemy) enemyList.get(i);

            if(square.intersects(tempEnemy.enemySquare)) return tempEnemy;

        }

        return null;

    }

    public synchronized Thread isIntersectWithFriend(Square square){

        Friend tempFriend = null;

        for(int i=0; i<friendList.size(); i++){

            tempFriend= (Friend) friendList.get(i);

            if(square.intersects(tempFriend.friendSquare)) return tempFriend;
            
        }

        return null;

    }

    public boolean locationandIntersectionControl(Square tempSquare){

        if(airCraft.aircraftSquare.intersects(tempSquare)) return false;

        Enemy tempEnemy = null;

        Friend tempFriend = null;

        for(int i=0; i<enemyList.size(); i++){

            tempEnemy = (Enemy) enemyList.get(i);

            if(tempEnemy.enemySquare.intersects(tempSquare)) return false;

        }

        for(int i=0; i<friendList.size(); i++){

            tempFriend = (Friend) friendList.get(i);

            if(tempFriend.friendSquare.intersects(tempSquare)) return false;

        }


        return true;

    }



    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

  

    

    







}



