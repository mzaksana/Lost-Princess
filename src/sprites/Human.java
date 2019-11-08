package sprites;

import aicore.ArrayHistory;
import muzzle.Muzzle;
import panel.ZPanel;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.Arrays;

public class Human implements Sprite {
//    private Collision brain;
    private ArrayHistory brain;

    private Rectangle body;
    private Rectangle head;

    //    color object
    private Color color;

    //    position face object
    //    1 lf, 2 up, 3 rg, 4 do
    private int nav;
    boolean find;
    //    position
    int x;
    int y;

    private Muzzle map;
    private ZPanel zPanel;
    int[][] environment;

    public Human(Color color, int nav) {
        this.color = color;
        this.nav = nav;
        // environment 3 x 3 karena implementasinya nanti
        // sebuah sprite hanya bisa "melihat" sejauh satu block darinya kesegala arah
        // dimana index 1,1 adalah posisi sprite

        this.environment = new int[3][3];
        this.find=false;
    }

    public void setMuzzle(Muzzle muzzle){
//        System.out.println("hm kn");
//        muzzle.printArr();
        this.map=muzzle;
    }

    //    init head and body
    public void spawn() {
        this.setBody(new Rectangle(52 + this.getY() * 25, 52 + this.getX() * 25, 21, 21));
//        System.out.println("span pos "+getX()+" , "+getY());
        this.setHead();
        if(this.brain==null){
//            this.brain = new Collision(this,new int[]{getX(),getY()},1,new int[]{1,2,3,4});
            this.brain = new ArrayHistory(this,24,nav);
        }
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
                this.head = new Rectangle(52 + this.getY() * 25, 52 + this.getX() * 25, 5, 21);
                break;
            case 2:
                this.head = new Rectangle(52 + this.getY() * 25, 52 + this.getX() * 25, 21, 5);
                break;
            case 3:
                this.head = new Rectangle(68 + this.getY() * 25, 52 + this.getX() * 25, 5, 21);
                break;
            case 4:
                this.head = new Rectangle(52 + this.getY() * 25, 68 + this.getX() * 25, 21, 5);
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

    public void moving(){
        this.environment();
        System.out.println("pos hm "+this.getX()+" , "+this.getY());
        brain.setPos(this.getPos());
        brain.movingDecision();
        this.moving(brain.getDirection());
        this.brain.setPos(this.getPos());
        System.out.println("brain.getDirection() " +brain.getDirection());
//        this.brain.setMemoryEnv();
        this.environment();
    }
    @Override
    public boolean moveLf() {
        setNav(1);
        if(isNextNotWall(getX(),getY()-1)){
//            System.out.println(getX()+" "+(getY()-1));
            setY(getY()-1);
            this.spawn();
            return true;
        }
        return false;
    }

    @Override
    public boolean moveRg() {
        setNav(3);
        if(isNextNotWall(getX(),getY()+1)){
//            System.out.println(getX()+" "+(getY()+1));
            setY(getY() + 1);
            this.spawn();
            return true;
        }
        return false;
    }

    @Override
    public boolean moveUp() {
        setNav(2);
        if(isNextNotWall(getX() - 1,getY())){
//            System.out.println((getX()-1)+" "+(getY()));
            setX(getX() - 1);
            this.spawn();
            return true;
        }
        return false;
    }

    @Override
    public boolean moveDw() {
        setNav(4);
        if(isNextNotWall(getX()+1,getY())){
//            System.out.println((getX()+1)+" "+getY());
            setX(getX() + 1);
            this.spawn();
            return true;
        }
        return false;
    }

    public void environment() {
        System.out.println();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                this.environment[i + 1][j + 1] = this.map.getMapEnv(this.getX() + i, this.getY() + j);
                if(this.map.getMapEnv(this.getX() + i, this.getY() + j)==-100){
                    find = true;
                }
                System.out.print(this.environment[i + 1][j + 1]+" ");
            }
            System.out.println();
        }
        brain.setEnvironment(this.environment);
    }


    private boolean isNextNotWall(int x, int y){
//        System.out.println(" map for wall x:y "+x+ " : "+y);
        if ((x >= 0 && x <= this.map.getStyle().length) && (y >= 0 && y <= this.map.getStyle()[0].length)) {
//            System.out.println(" map for wall x,y "+this.map.getStyle()[x][y]);
            return this.map.getStyle()[x][y] == 1;
        }
        return false;
    }
    public int getNav() {
        return nav;
    }

    public void setNav(int nav) {
        System.out.println(" set nav "+nav);
        this.nav = nav;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int[] getPos(){
        return new int[]{getX(),getY()};
    }

    public void moving(int move){
        this.environment();
        System.out.println("Pos "+ Arrays.toString(getPos()));
        System.out.println("Pos "+ Arrays.deepToString(environment));
        switch (move) {
            case 1:
                this.moveLf();
                zPanel.setData(this.getBrain());
                zPanel.setSpriteLocations(this.getPos(),Color.magenta);
                return;
            case 2:
                this.moveUp();
                zPanel.setData(this.getBrain());
                zPanel.setSpriteLocations(this.getPos(),Color.magenta);
                return;
            case 3:
                this.moveRg();
                zPanel.setData(this.getBrain());
                zPanel.setSpriteLocations(this.getPos(),Color.magenta);
                return;
            case 4:
                this.moveDw();
                zPanel.setData(this.getBrain());
                zPanel.setSpriteLocations(this.getPos(),Color.magenta);
        }
    }

    public void movingTo(int x, int y) {
        map.repaint();

        if(this.x>x){
//            up
            moving(2);
        }else if(this.y<y){
//            right
            moving(3);
        }else if(this.x<x){
//            down
            moving(4);
        }else {
            moving(1);
//            left
        }
        map.repaint();
    }

    @Override
    public String toString() {
        return "x: "+getX()+" y: "+getY();
    }

    public boolean getFind() {
        return this.find;
    }

    public int[][] getBrain() {
        return this.brain.getMemory();
    }

    public void setZPanel(ZPanel zPanel){
        this.zPanel = zPanel;
    }

    public ZPanel getZPanel() {
        return zPanel;
    }
    public void setFacingTo(int x,int y){

        if(this.x>x){
//            up
            setNav(2);
        }else if(this.y<y){
//            right
            setNav(3);
        }else if(this.x<x){
//            down
            setNav(4);
        }else {
            setNav(1);
//            left
        }
    }
    public void setEnvironment(int[][] environment) {
        this.environment = environment;
    }
}
