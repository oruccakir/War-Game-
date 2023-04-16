import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;



public class Game extends JFrame implements KeyListener , MouseListener {

    public final int SCREEN_WIDTH = 500;

    public final int SCREEN_HEIGHT = 500;

    public AirCraft airCraft = null;

    public ArrayList <Thread> enemyList = null;

    public ArrayList <Thread> friendList = null;

    public HashSet <Point> pointsSet = null;

    public Random random = null;

    public boolean firstPlacement = true;

    public Game (){

        pointsSet = new HashSet<>();

        enemyList = new ArrayList<>();

        friendList = new ArrayList<>();

        random = new Random();

        this.addKeyListener(this);

        airCraft = new AirCraft();

        this.setLayout(new BorderLayout());

        this.setVisible(true);

        this.setTitle("WAR GAME");

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.setSize(SCREEN_WIDTH,SCREEN_HEIGHT);

        

    }

    public void paint(Graphics g){

        super.paint(g);

        Enemy tempEnemy = null;

        Friend tempFriend = null;

        if(firstPlacement){

            setUpThePoints();

            int index = 0, indexEnemy =0, indexFriend = 0;

            Point pointArray[] = new Point[enemyList.size()+friendList.size()];

            pointArray = pointsSet.toArray(pointArray);

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

            if(control == false) airCraft.aircraftSquare.y -=10;
            
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


    public void setUpThePoints(){

        int minX = 10, maxX = SCREEN_WIDTH-20;
        int minY = 30, maxY = SCREEN_HEIGHT-20;

        int randomX = 0;
        int randomY = 0;

        Point tempPoint = null;

        //Square tempSquare = null;

        while(pointsSet.size() != ( enemyList.size() + friendList.size() )){

            randomX = random.nextInt(maxX - minX +1) + minX;
            randomY = random.nextInt(maxY - minY +1) + minY;

            tempPoint = new Point(randomX, randomY);

           // tempSquare = new Square(minY, maxY, randomX, randomY, getForeground())

            if(pointsSet.contains(tempPoint) == false) pointsSet.add(tempPoint);

        }

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

            this.enemySquare = new Square(0,0,10,10,Color.BLACK);




            enemyList.add(this);

        }






        public void run(){
            
        }


    }


    public class Friend extends Thread{

        public Square friendSquare;

        public Fire leftFire, rightFire;

        public Friend (){

            this.friendSquare = new Square(0,0,10,10, Color.GREEN);



            friendList.add(this);

        }





        public void run(){
            
        }


    }


    public class AirCraft extends Thread{

        public Square aircraftSquare;

        public Fire leftFire, rightFire;

        public AirCraft(){

            this.aircraftSquare = new Square(250, 250, 10, 10,Color.RED);

            this.leftFire = new Fire("aircraftFire", "left", Color.ORANGE, this);

            this.rightFire= new Fire("aircraftFire", "right", Color.ORANGE, this);

        }

        
        public void run(){

            

        }
        
    }


    public class Point {

        public int x, y;

        public Point (int x, int y){

            this.x = x;

            this.y = y;

        }

        public boolean equals(Object obj){

            if(obj instanceof Point == false)  return false;

            Point tempPoint = (Point) obj;

            return this.x == tempPoint.x && this.y == tempPoint.y;
        }

    }


    
    public boolean borderControl(Square square){

        if(square.x >= 10 && square.x <= SCREEN_WIDTH-20 && square.y<=SCREEN_HEIGHT-20 && square.y >=30) return true;

        return false;
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



