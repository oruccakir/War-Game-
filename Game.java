import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;


public class Game extends JFrame {

    public final int SCREEN_WIDTH = 500;

    public final int SCREEN_HEIGHT = 500;

    public Game (){

        this.setLayout(new BorderLayout());

        this.setVisible(true);

        this.setTitle("WAR GAME");

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.setSize(SCREEN_WIDTH,SCREEN_HEIGHT);

        



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


        public void run(){
            
        }


    }


    public class Enemy extends Thread {

        public Square enemySquare;

        public Fire leftFire, rightFire;






        public void run(){
            
        }


    }


    public class Friend extends Thread{

        public Square friendSquare;

        public Fire leftFire, rightFire;





        public void run(){
            
        }


    }


    public class AirCraft extends Thread{

        public Square aircraftSquare;

        public Fire leftFire, rightFire;

        public AirCraft(){

            aircraftSquare = new Square(250, 250, 50, 50,Color.RED);
           
        }

        
        public void run(){


        }
        


    }








    }



