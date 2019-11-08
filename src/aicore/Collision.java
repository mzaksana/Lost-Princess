package aicore;

/*  Collision learning
 *  Object movement by collision detection
 *  move to other random direction exclude collision direction
 *
 * exclude direction have level history
 * level history use to record all direction when object cant move by collision
 * so at random value to new direction we can take
 * level 1 just record 1 value collision
 * level 2 can record 2 value collision
 * level n can record n value collision
 * */

import sprites.Human;

public class Collision implements SpritesBasic{
    private int[] layer;
    private int[] pos;
    private int direction;
    private int[] orderPriority;
    private int[][] environment;
    private boolean swicth;
    private Human self;

    public Collision(Human human, int[] pos, int layer, int[] orderPriority) {
        this.self = human;
        this.pos = pos;
        this.layer = new int[layer];
        this.orderPriority = orderPriority;
        this.direction=1;
    }

    @Override
    public void direction() {

    }

    @Override
    public boolean isCollision(int x, int y) {
        System.out.println("switch "+direction);
        switch (direction){
            case 1:
                if(environment[1][0]!=1){
                    orderPriority();
                    System.out.println("update "+direction);
                }
                break;
            case 2:
                if(environment[0][1]!=1){
                    orderPriority();
                    System.out.println("update "+direction);
                }
                break;
            case 3:
                if(environment[1][2]!=1){
                    orderPriority();
                    System.out.println("update "+direction);
                }
                break;
            case 4:
                System.out.println("case "+environment[2][2]);

                if(environment[2][1]!=1){
                    orderPriority();
                    System.out.println("update "+direction);
                }
                break;
        }
        return true;
    }

    @Override
    public void movingDecision() {
        isCollision(pos[0], pos[1]);
        System.out.println("pos " +pos[0]+","+pos[1]);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(environment[i][j]+" ");
            }
            System.out.println();
            System.out.println();
        }
    }

    public int[] getLayer() {
        return layer;
    }

    public void setLayer(int[] layer) {
        this.layer = layer;
    }

    public int[] getOrderPriority() {
        return orderPriority;
    }

    public void setOrderPriority(int[] orderPriority) {
        this.orderPriority = orderPriority;
    }

    public int[][] getEnvironment() {
        return environment;
    }

    public void setEnvironment(int[][] environment) {
        this.environment = environment;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int[] getPos() {
        return pos;
    }

    public void setPos(int[] pos) {
        this.pos=pos;
    }

    private void orderPriority() {
        swicth = !swicth;
        int [] temp= new int[4];

        System.arraycopy(orderPriority, 1, temp, 0, 3);
        temp[3] = orderPriority[0];
        orderPriority = temp;
        int tmp = orderPriority[2];
        direction = orderPriority[0];
        for (int i = 0; i < 4; i++) {
            if (swicth) {
                break;
            }
            if (orderPriority[i] == 2) {
                orderPriority[i]=4;
            }else if(orderPriority[i]==4){
                orderPriority[i]=2;
            }

        }
        System.out.println("direct "+direction);
    }
}
