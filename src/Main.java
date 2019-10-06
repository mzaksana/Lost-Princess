import muzzle.Muzzle;
import sprites.Human;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Main extends JFrame implements KeyListener{
    Muzzle muzzle;
    Human human;
    public Main(){
        this.muzzle = new Muzzle("/home/mza/Documents/Pro/LostPrincess/muzzle_style/style1.txt");
        initSprites();

        this.muzzle.setHuman(this.human);
        this.human.setMuzzle(this.muzzle);
        addKeyListener(this);
    }
    public static void main(String[] args) throws InterruptedException {
        Main main = new Main();
//      just for now
        final int size =700;
        main.setSize(size,size);
        main.getContentPane().add(main.muzzle);
        main.setLocationRelativeTo(null);
        main.setBackground(Color.LIGHT_GRAY);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setVisible(true);
    }


    public void move(int nav) {
        human.setPosition(human.getX()+1, human.getY()+1);
        human.setNav(nav);
        repaint();
    }

    private void initSprites(){
        Random rand = new Random();
        human = new Human(Color.RED, 4);
        int x,y;
        do {
            x = rand.nextInt(26);
            y = rand.nextInt(26);
        } while (muzzle.getStyle()[x][y] != 1);
        System.out.println("post " +x+" "+y);
        human.setPosition(x,y);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case 37:
                human.moveLf();
                break;
            case 38:
                human.moveUp();
                break;
            case 39:
                human.moveRg();
                break;
            case 40:
                human.moveDw();
                break;
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
