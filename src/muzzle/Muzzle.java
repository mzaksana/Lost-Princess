package muzzle;
import sprites.Human;

import java.awt.*;
import javax.swing.JPanel;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

public class Muzzle extends JPanel{
    private int[][] style;
    private int sizeArr;

    public Human human;

    public Muzzle(int dimen){
        Random rand = new Random();
        sizeArr =dimen-100;
        int d=(sizeArr +25)/25;
        this.style = new int[d+1][d+1];

        for (int i = 0; i < d; i++) {
            for (int j=0;j<d;j++){
                this.style[i][j]=rand.nextInt(2);
                System.out.print(this.style[i][j] + " ");
            }
            System.out.println();
        }
    }

    public Muzzle(String path){
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
    }

    public void paint(Graphics g){
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;
        int r=0,c;
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
        human.spawn();
        fillRectangle(g2d, human.getBody());
        g2d.setColor(Color.BLACK);
        fillRectangle(g2d, human.getHead());
    }

    private void fillRectangle(Graphics2D g2d,Rectangle rectangle){
        g2d.fillRect(rectangle.x,rectangle.y,rectangle.width,rectangle.height);
    }

    public int[][] getStyle() {
        return style;
    }

    public void setStyle(int[][] style) {
        this.style = style;
    }

    public int getSizeArr() {
        return sizeArr;
    }

    public void setSizeArr(int sizeArr) {
        this.sizeArr = sizeArr;
    }

    public Human getHuman() {
        return human;
    }

    public void setHuman(Human human) {
        this.human = human;
    }
}
