package sprites;

import java.awt.*;

public class Mouse extends Sprite {
    //    color object
    private Color color;

    //    position face object
    //    1 lf, 2 up, 3 rg, 4 do
    private int nav;

    //    position
    int x;
    int y;

    public Mouse(Color color,int nav) {
        this.color = color;
        this.nav = nav;
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

    }

    @Override
    public void moveRg() {

    }

    @Override
    public void moveUp() {

    }

    @Override
    public void moveDw() {

    }
}
