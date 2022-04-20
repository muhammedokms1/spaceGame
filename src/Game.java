import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class Fire {
    private int x;
    private int y;

    public Fire(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


}

public class Game extends JPanel implements KeyListener, ActionListener {
    Timer timer = new Timer(5,this);
    private int passingTime = 0;
    private int usedFire = 0;
    private BufferedImage image;
    private ArrayList<Fire> fires = new ArrayList<Fire>();
    private int firedirY = 1;
    private int ballX = 0;
    private int balldirX = 2;
    private int spaceSheepX = 0;
    private int dirSpaceX = 20;
    public boolean check(){
        for ( Fire fire : fires){
            if (new Rectangle(fire.getX(),fire.getY(),10,20).intersects(new Rectangle(ballX,0,20,20))){
                return true;
            }
        }
        return false;
    }


    public Game() {
        try {
            image = ImageIO.read(new FileImageInputStream(new File("xd.jpg")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setBackground(Color.BLACK);

        timer.start();
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        passingTime +=5;
        graphics.setColor(Color.RED);
        graphics.fillOval(ballX,0,20,20);

        graphics.drawImage(image,spaceSheepX,490,image.getWidth()/23,image.getHeight()/23,this);
        for (Fire fire : fires){
            if(fire.getY() < 0){
                fires.remove(fire);
            }
        }
        graphics.setColor(Color.GREEN);
        for (Fire fire : fires){
            graphics.fillRect(fire.getX(),fire.getY(),10,20);
        }
        if(check()){
            timer.stop();
            String message = "You Win !! \n"+
                                "Used Fire :  " + usedFire +
                    "\nPassing Time : " + passingTime/1000.0 + " second";
            JOptionPane.showMessageDialog(this,message);
            System.exit(0);
        }
    }

    @Override
    public void repaint() {
        super.repaint();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {


    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int c = keyEvent.getKeyCode();
        if ( c == KeyEvent.VK_LEFT){
            if(spaceSheepX <= 0){
                spaceSheepX = 0;
            }
            else {
                spaceSheepX -= dirSpaceX;
            }
        }
        else if (c == KeyEvent.VK_RIGHT){
            if (spaceSheepX >= 740){
                spaceSheepX = 740;
            }
            else {
                spaceSheepX += dirSpaceX;
            }
        }
        else if (c == KeyEvent.VK_CONTROL) {
            fires.add(new Fire(spaceSheepX+15,470));
            usedFire++;
        }

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        for(Fire fire : fires){
            fire.setY(fire.getY() - firedirY);
        }
        ballX += balldirX;
        if (ballX >= 750){
            balldirX = -balldirX;
        }
        if (ballX <= 0){
            balldirX = -balldirX;
        }
        repaint();

    }
}
