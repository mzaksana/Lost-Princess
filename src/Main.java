import muzzle.Muzzle;
import sprites.Human;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Main extends JFrame implements KeyListener{
    Muzzle muzzle;
    Human knight;
    Human princess;


    public Main(){
        this.muzzle = new Muzzle("/home/mza/Documents/Pro/LostPrincess/muzzle_style/style1.txt");
        initSprites();

        this.muzzle.setKnight(this.knight);
        this.muzzle.setPrincess(this.princess);
        this.knight.setMuzzle(this.muzzle);
        this.princess.setMuzzle(this.muzzle);

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
        knight.setPosition(knight.getX()+1, knight.getY()+1);
        knight.setNav(nav);
        repaint();
    }

    private void initSprites(){
        Random rand = new Random();
        knight = new Human(Color.RED, 4);
        princess = new Human(Color.BLUE, 4);
        int x,y;
        do {
            x = rand.nextInt(26);
            y = rand.nextInt(26);
        } while (muzzle.getStyle()[x][y] != 1);
        System.out.println("knight " +x+" "+y);
        knight.setPosition(x,y);

        do {
            x = rand.nextInt(26);
            y = rand.nextInt(26);
        } while (muzzle.getStyle()[x][y] != 1 && (x!=knight.getX() && y!=knight.getY()));
        System.out.println("princess " +x+" "+y);
        princess.setPosition(x,y);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case 37:
                knight.moveLf();
                break;
            case 38:
                knight.moveUp();
                break;
            case 39:
                knight.moveRg();
                break;
            case 40:
                knight.moveDw();
                break;
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
