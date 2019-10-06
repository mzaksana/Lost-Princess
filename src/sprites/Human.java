package sprites;

import muzzle.Muzzle;

import java.awt.*;

public class Human extends Sprite {

    private Rectangle body;
    private Rectangle head;
    //    color object
    private Color color;

    //    position face object
    //    1 lf, 2 up, 3 rg, 4 do
    private int nav;

    //    position
    int x;
    int y;

    private Muzzle map;

    public Human(Color color, int nav) {
        this.color = color;
        this.nav = nav;
    }

    public void setMuzzle(Muzzle muzzle){
        this.map=muzzle;
    }

    //    init head and body
    public void spawn() {
        this.setBody(new Rectangle(52 + this.getX() * 25, 52 + this.getY() * 25, 21, 21));
        this.setHead();
    }

    public Rectangle getBody() {
        return body;
    }

    public void setBody(Rectangle body) {
        this.body = body;
    }

    public Rectangle getHead() {
        return head;
    }

    private void setHead() {
        switch (nav){
            case 1:
                this.head = new Rectangle(52 + this.getX() * 25, 52 + this.getY() * 25, 5, 21);
                break;

            case 2:
                this.head = new Rectangle(52 + this.getX() * 25, 52 + this.getY() * 25, 21, 5);
                break;
            case 3:
                this.head = new Rectangle(68 + this.getX() * 25, 52 + this.getY() * 25, 5, 21);
                break;
            case 4:
                this.head = new Rectangle(52 + this.getX() * 25, 68 + this.getY() * 25, 21, 5);
                break;
        }
    }

    public void setPosition(int x,int y){
        this.x=x;
        this.y=y;
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

    @Override
    public void moveLf() {
        setNav(1);
        if(isNextNotWall(getX()-1,getY())){
            setX(getX()-1);
        }
    }

    @Override
    public void moveRg() {
        setNav(3);
        if(isNextNotWall(getX() + 1,getY())){
            setX(getX() + 1);
        }
    }

    @Override
    public void moveUp() {
        setNav(2);
        if(isNextNotWall(getX(),getY() - 1)){
            setY(getY() - 1);
        }
    }

    @Override
    public void moveDw() {
        setNav(4);
        if(isNextNotWall(getX(),getY() + 1)){
            setY(getY() + 1);
        }
    }

    private boolean isNextNotWall(int x, int y){
        if ((x>=0 && x <= this.map.getStyle().length )&& (y >=0 && y <= this.map.getStyle()[0].length)) {
            return this.map.getStyle()[x][y] == 1;
        }
        return false;
    }
    public int getNav() {
        return nav;
    }

    public void setNav(int nav) {
        this.nav = nav;
    }

}
