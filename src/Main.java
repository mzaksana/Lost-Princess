import muzzle.Muzzle;
import panel.ZPanel;
import sprites.Human;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Main extends JFrame implements KeyListener {

    Muzzle muzzle;
    ZPanel info;
    Human knight;
    Human princess;
    boolean play;
    public Main() {
        this.muzzle = new Muzzle("/home/mza/Rains/Pro/LostPrincess/muzzle_style/style0.txt");
        this.info = new ZPanel();
        initSprites();

        this.muzzle.setKnight(this.knight);
        this.muzzle.setPrincess(this.princess);
        this.knight.setMuzzle(this.muzzle);
        this.princess.setMuzzle(this.muzzle);
        this.play=true;
        addKeyListener(this);
    }

    public static void main(String[] args) {
        Main main = new Main();
//      just for now
        final int size = 700;

        main.setSize(size + 650, size+20);
        main.setLayout(null);
        main.setTitle("Lost Princess");
        // Mengatur frame agar tidak dapat di resize
        main.setResizable(false);
        main.setLocationRelativeTo(null);
        main.setBackground(Color.LIGHT_GRAY);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setVisible(true);

        main.getContentPane().add(main.muzzle);
        main.muzzle.setBounds(0, 0, 650, 700);
//
        main.getContentPane().add(main.info);
        main.info.setBounds(650,7,700,700);
        main.info.setData(main.knight.getBrain());
        main.knight.setZPanel(main.info);
        Random rand = new Random();
        Thread inf = new Thread(main.info);
        inf.start();
        do {
            try {
                Thread.sleep(750);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            main.moving();

        } while (main.play);
    }

    private void initSprites() {
        Random rand = new Random();
        knight = new Human(Color.RED, 1);
        princess = new Human(Color.PINK, 4);
        int x, y;
        do {
            x = rand.nextInt(24);
            y = rand.nextInt(24);
        } while (muzzle.getStyle()[x][y] != 1);
        System.out.println("knight " + x + " " + y);
//        System.out.println(muzzle.getStyle()[x][y]);
//        knight.setPosition(0, 2);
        knight.setPosition(x, y);
        int u, i;
        do {
            u = rand.nextInt(24);
            i = rand.nextInt(24);
        } while (muzzle.getStyle()[u][i] != 1 || (u == knight.getX() && i == knight.getY()));
        System.out.println("princess " + u + " " + i);
        System.out.println(muzzle.getStyle()[u][i]);
//        princess.setPosition(0, 20);
        princess.setPosition(u,i);
        knight.spawn();
        princess.spawn();
        System.out.println("map");
        muzzle.printArr();
    }

    private void moving() {

        if (isFindedPrincess()) {
            System.out.println("ended");
            gameEnd();
        }
        knight.moving();
        muzzle.repaint();
        if (isFindedPrincess()) {
            System.out.println("ended");
            gameEnd();
        }
    }

    public boolean isFindedPrincess() {
        System.out.println("knight :"+knight);
        System.out.println("princess :"+princess);
        return knight.getX() == princess.getX() && knight.getY() == princess.getY();
    }

    public void gameEnd() {
        this.play=false;
        this.info.cancel("Message : Happy Ending ..");
        System.out.println("Happy Ending ..");
    }

    public ZPanel getInfo() {
        return info;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        System.out.println("key "+keyEvent.getKeyCode());
        System.out.println("pos "+knight.getX()+" , "+knight.getY());
        switch (keyEvent.getKeyCode()) {
            case 37:
                knight.moving(1);
                break;
            case 38:
                knight.moving(2);
                break;
            case 39:
                knight.moving(3);
                break;
            case 40:
                knight.moving(4);
                break;
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
