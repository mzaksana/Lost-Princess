package muzzle;
import sprites.Mouse;

import java.awt.*;
import javax.swing.JPanel;
import java.awt.geom.AffineTransform;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Muzzle extends JPanel{
    private int style[][];
    private int size;

    Mouse mouse;

    public Muzzle(int dimen){
        Random rand = new Random();
        size=dimen-100;
        int d=(size+25)/25;
        this.style = new int[d+1][d+1];

        for (int i = 0; i < d; i++) {
            for (int j=0;j<d;j++){
                this.style[i][j]=rand.nextInt(2);
                System.out.print(this.style[i][j] + " ");
            }
            System.out.println();
        }
        makeSprites();
    }

    public Muzzle(String path){
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(path))) {
            // read line by line
            String line;
            int row=0;
            boolean flag=true;
            while ((line = br.readLine()) != null) {
                if(flag){
                    String[] dimen=line.split("x");
                    this.style= new int[Integer.parseInt(dimen[0])+1][Integer.parseInt(dimen[1])+1];
                    flag = false;
                    continue;
                }
                int col = 0;
                for (char ch : line.toCharArray()) {
                    style[row][col++] = Character.getNumericValue(ch);
                }
                row++;
            }
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
        makeSprites();
    }

    public void paint(Graphics g){
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;
        int r=0,c=0;
        for(int i = 50; i <= 625; i+=25){
            c=0;
            for(int j = 50; j <= 625; j+=25){
                if(r<style.length && c<style.length){
                    if(this.style[r][c++]==1){
                        g2d.setColor(Color.WHITE);
                    }else {
                        g2d.setColor(Color.GRAY);
                    }
                }
                g2d.fillRect(i, j, 25, 25);
            }r++;
        }
        g2d.setColor(Color.RED);


        Rectangle a = new Rectangle(52 + this.mouse.getX() * 25, 52 + this.mouse.getY() * 25, 21, 21);
        fillRectangle(g2d,a);
        mouse.setX(100);
        mouse.setY(100);
        repaint();

//        fillRectangle(g2d, a);
//        g2d.fillRect(52 + this.mouse.getX() * 25, 52+ this.mouse.getY() * 25, 21, 21);
    }

    private void fillRectangle(Graphics2D g2d,Rectangle rectangle){
        g2d.fillRect(rectangle.x,rectangle.y,rectangle.width,rectangle.height);
    }
    private void makeSprites(){
        Random rand = new Random();

        mouse = new Mouse(Color.RED, 1);
        int x,y;
        while (true) {
            x = rand.nextInt(26);
            y = rand.nextInt(26);
            if(this.style[x][y]==1){
                System.out.println(this.style[x][y]);
                break;
            }
        }
        mouse.setPosition(0,0);
    }
}
