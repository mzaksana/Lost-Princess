package muzzle;
import sprites.Human;

import java.awt.*;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

public class Muzzle extends JPanel{
    private int[][] style;
    private int sizeArr;

    private Human knight;
    private Human princess;

    public Muzzle(int dimen){

        Random rand = new Random();
        sizeArr =dimen-100;
        int d=(sizeArr +25)/25;
        this.style = new int[d+1][d+1];

        for (int i = 0; i < d; i++) {
            for (int j=0;j<d;j++){
                this.style[i][j]=rand.nextInt(2);
            }
        }
    }

    public Muzzle(String path){
        System.out.println("file "+path);
        try (BufferedReader br = Files.newBufferedReader(Paths.get(path))) {
            // read line by line
            String line;
            int row=0;
            boolean flag=true;
            while ((line = br.readLine()) != null) {
                System.out.println(" dimen "+line);
                if(flag){
                    String[] dimen=line.split("x");
                    System.out.println(" dimen x: "+dimen[0]+" y: "+dimen[1]);
                    this.style= new int[Integer.parseInt(dimen[0])][Integer.parseInt(dimen[1])];
                    flag = false;
                    continue;
                }
                int col = 0;
                for (char ch : line.toCharArray()) {
                    System.out.println(" ch "+ch);
                    System.out.println(" gen indx :>> row : "+row+"  col : "+col);
                    style[row][col] = ch == '1' ? 1 : 0;
                    col++;
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
                    if (this.style[r][c++] == 1) {
                        g2d.setColor(Color.WHITE);
                    } else {
                        g2d.setColor(Color.GRAY);
                    }
                }
                g2d.fillRect(j,i, 25, 25);
            }r++;
        }

        g2d.setColor(knight.getColor());
        fillRectangle(g2d, knight.getBody());
        g2d.setColor(Color.BLACK);
        fillRectangle(g2d, knight.getHead());

        g2d.setColor(princess.getColor());
        fillRectangle(g2d, princess.getBody());
        g2d.setColor(Color.BLACK);
        fillRectangle(g2d, princess.getHead());

    }

    private void fillRectangle(Graphics2D g2d,Rectangle rectangle){
        g2d.fillRect(rectangle.x,rectangle.y,rectangle.width,rectangle.height);
    }

    public int[][] getStyle() {
        return style;
    }

    public int getMapEnv(int x, int y) {
        if ((x >= 0 && x <= this.getStyle().length) && (y >= 0 && y <= this.getStyle()[0].length)) {
            return this.style[x][y];
        }
        return 0;
    }
    public void setStyle(int[][] style) {
        this.style = style;
    }

    public int getSizeArr() {
        return sizeArr;
    }
    public void printArr(){
        for (int i = 0; i < this.style[0].length; i++) {
            for (int j = 0; j < this.style[0].length; j++) {
                System.out.print(this.style[i][j]+"\t");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();

    }
    public void setSizeArr(int sizeArr) {
        this.sizeArr = sizeArr;
    }

    public Human getKnight() {
        return knight;
    }

    public void setKnight(Human knight) {
        this.knight = knight;
    }

    public Human getPrincess() {
        return princess;
    }

    public void setPrincess(Human princess) {
        this.princess = princess;
    }
}
