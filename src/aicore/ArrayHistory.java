package aicore;

import sprites.Human;

import java.awt.*;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.*;
import java.util.List;

public class ArrayHistory implements SpritesBasic{
    private int[][] arrayHistory;
    private int sizeArray;
    private int[][] environment;
    private int[] pos;
    private int direction;

    private int countBlock;
    private boolean found;
    private Human self;

    HashMap<String,Long> path;
    List<String> pathS;
    HashMap<String,Long> allPath;
    private int[] searchPath;

    public ArrayHistory(Human self, int sizeArray, int direction) {
        this.self = self;
        this.arrayHistory = new int[sizeArray][sizeArray];
        this.sizeArray = sizeArray;
        this.direction = direction;
        countBlock = 1;
    }

    @Override
    public void direction() {

    }

    @Override
    public boolean isCollision(int x, int y) {
        return false;
    }

    private boolean isCollision() {
        switch (direction) {
            case 1:
                return environment[1][0]==0;
            case 2:
                return environment[0][1]==0;
            case 3:
                return environment[1][2]==0;
            case 4:
                return environment[2][1]==0;
        }
        return false;
    }

    @Override
    public void movingDecision() {
        this.lookUpEnv();
        System.out.println("pos ai "+pos[0]+" , "+pos[1]);
        this.printArr();
        if (this.isCollision()) {
            System.out.println(" get wall :: Collision");
            this.gotoOldIntersection();
        }
    }

//    pada saat path dilewati setiap simpang diberinilai dengan value yang selalu increment
    private void gotoOldIntersection() {
        int min=sizeArray*sizeArray;
        int []minIndex=new int[2];
        for (int i = 0; i < sizeArray; i++) {
            for (int j = 0; j < sizeArray; j++) {
                if (arrayHistory[i][j] == 0 || arrayHistory[i][j] == -1) {
                    continue;
                }
                if (min > arrayHistory[i][j]) {
                    min = arrayHistory[i][j];
                    minIndex = new int[]{i, j};
                }
            }
        }
//        System.out.println(" get min pos x: "+minIndex[0]+" y: "+minIndex[1]);
        this.searchPath=minIndex;
        this.searchPath();
    }

    private void searchPath() {
        this.path = new HashMap<>();
        this.pathS= new ArrayList<>();
        this.allPath= new HashMap<>();
        System.out.println("search min "+searchPath[0]+" "+searchPath[1]);
        int max = ((sizeArray * sizeArray) - 1) * 2;
//        System.out.println("max "+max);
        String pathWalk=pathMove(pos[0],pos[1],1);
//        System.out.println(path);
//        System.out.println(pathS);
        System.out.println("walkPath --- :: "+pathWalk);

        this.walkPath(pathWalk);
        this.setPos(this.self.getPos());
        this.found=false;
        pathS=null;
        path=null;
        allPath=null;
    }

    private void walkPath(String path) {
        int counter=1;
        String[] pathWalk = path.trim().split(" ");
        ArrayList<String> pathFix = new ArrayList<>();
        for (String coor : pathWalk) {
            if (!(coor.length() < 3)) {
                pathFix.add(coor);
            }
        }

        for (String coor : pathFix.subList(0,pathFix.size()-1)) {
            if(coor.length()<3){
                continue;
            }

            if (counter++ == 1) {
                continue;
            }
            System.out.println(" walk to " + coor);
            String[] xy = coor.split(",");
            if (xy.length < 2) {
                continue;
            }

            this.self.movingTo(Integer.parseInt(xy[0]), Integer.parseInt(xy[1]));
            this.self.getZPanel().setSpriteLocations(this.self.getPos(),searchPath, Color.magenta);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.setDirection(self.getNav());
        }
        String[] idx = pathFix.get(pathFix.size() - 1).split(",");
        this.self.setFacingTo(Integer.parseInt(idx[0]),Integer.parseInt(idx[1]));
        this.setDirection(self.getNav());
    }

    public int[] getSearchPath() {
        return searchPath;
    }

    public void setMemoryEnv() {
        if (searchPath == null) {
            return;
        }
        System.out.println(" arr "+Arrays.deepToString(environment));
        if (environment[1][0] == 1 && arrayHistory[searchPath[0]][searchPath[1]-1]==0) {
            arrayHistory[searchPath[0]][searchPath[1]-1] = countBlock++;
        }
        if (environment[0][1] == 1 && arrayHistory[searchPath[0]-1][searchPath[1]]==0) {
            arrayHistory[searchPath[0]-1][searchPath[1]] = countBlock++;
        }
        if (environment[1][2] == 1 && arrayHistory[searchPath[0]][searchPath[1]+1]==0) {
            arrayHistory[searchPath[0]][searchPath[1]+1] = countBlock++;
        }
        if (environment[2][1] == 1 && arrayHistory[searchPath[0]+1][searchPath[1]]==0) {
            arrayHistory[searchPath[0]+1][searchPath[1]] = countBlock++;
        }
        searchPath = null;
    }

    private String pathMove(int row, int col,long counter) {
        if(found){
            return "";
        }

        if (!isInBound(row, col) || path.containsKey(row+" "+col))
            return "";
        path.put(row+" "+col,counter);

        if (row==searchPath[0] && col==searchPath[1]) {
            found=true;
            System.out.println("Found at " + row + " " + col);
            return row+","+col+" ";
        } else if (arrayHistory[row][col] == -1) {
            String val="";
            Vector<String> step=new Vector<>();
            HashMap<String,Integer[]> stepc=new HashMap<>();

            int[][] lookUp=new int[][]{{0,-1},{-1,0},{0,1},{1,0}};
            for (int[] is : lookUp) {
                Integer[] index=new Integer[]{Math.abs(searchPath[0]-(row+is[0])),Math.abs(searchPath[1]-(col+is[1]))};
                if(stepc.containsKey(index[0]+","+index[1])){

                    Integer row1=stepc.get(index[0]+","+index[1])[0];
                    Integer row2=row;

                    if(Math.abs(searchPath[0]-row1)>Math.abs(searchPath[0]-row2)){
                        stepc.put(index[0]+","+(index[1]-0.5), new Integer[]{row+is[0],col+is[1]});
                        step.add(index[0]+","+(index[1]-0.5));

                    }else{
                        stepc.put(index[0]+","+(index[1]+0.5), new Integer[]{row+is[0],col+is[1]});
                        step.add(index[0]+","+(index[1]+0.5));
                    }

                }else{
                    stepc.put(index[0]+","+index[1], new Integer[]{row+is[0],col+is[1]});
                    step.add(index[0]+","+index[1]);
                }
            }

            // System.out.println(shift+" step "+Arrays.deepToString(step.toArray()));
            Collections.sort(step);
            // System.out.println(shift+" step ord "+Arrays.deepToString(step.toArray()));
            // System.out.println(shift+" stepc "+Arrays.deepToString(stepc.values().toArray()));

            for (String string : step) {
                // System.out.println(shift+" string "+string);
                Integer[]index=stepc.get(string);
                // System.out.println(shift+" index "+Arrays.toString(index));
                val=pathMove(index[0], index[1],counter+1);
                if (!val.equals("")){
                    return row+","+col+" "+val;
                }
            }

        }
        return "";
    }

    private boolean isInBound(int row, int col) {
        boolean bol = false;
        if (row < arrayHistory.length && col < arrayHistory[0].length && col >= 0 && row >= 0) {
            bol = true;
        }
        if(bol){
            if(arrayHistory[row][col]==0){
                bol=false;
            }
        }
        return bol;
    }


    private boolean checkPos(int x, int y) {
        if(x<sizeArray && y<sizeArray){
            return arrayHistory[x][y] == -1;
        }
        return false;
    }
    private void makePath(int []searchingIndex) {
        System.out.println(pos[0]+" , "+pos[1]);
        System.out.println(searchingIndex[0]+" , "+searchingIndex[1]);
        
    }

    private boolean seeEnv(int x,int y){
        return environment[x][y]==1;
    }
    private void lookUpEnv() {
        if (environment[1][0] == 1 && arrayHistory[pos[0]][pos[1]-1]!=-1) {
            arrayHistory[pos[0]][pos[1]-1] = countBlock++;
        }
        if (environment[0][1] == 1 && arrayHistory[pos[0]-1][pos[1]]!=-1) {
            arrayHistory[pos[0]-1][pos[1]] = countBlock++;
        }
        if (environment[1][2] == 1 && arrayHistory[pos[0]][pos[1]+1]!=-1) {
            arrayHistory[pos[0]][pos[1]+1] = countBlock++;
        }
        if (environment[2][1] == 1 && arrayHistory[pos[0]+1][pos[1]]!=-1) {
            arrayHistory[pos[0]+1][pos[1]] = countBlock++;
        }
    }

    public void printArr(){
        System.out.println("brain memory");
        for (int i = 0; i < this.sizeArray; i++) {
            for (int j = 0; j < this.sizeArray; j++) {
                System.out.print(this.arrayHistory[i][j]+"\t");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();

    }



    public int[][] getEnvironment() {
        return environment;
    }

    public void setEnvironment(int[][] environment) {
        this.environment = environment;
    }

    public int[] getPos() {
        return pos;
    }

    public void setPos(int[] pos) {
        this.pos = pos;
        System.out.println(" set val pos "+Arrays.toString(pos));
        System.out.println(" set val "+arrayHistory[pos[0]][pos[1]] + " to >> -1");
        arrayHistory[pos[0]][pos[1]]=-1;
    }

    public int getDirection() {
        return direction;
    }

    private void setDirection(int direction) {
        this.direction = direction;
    }

    public int[][] getMemory() {
        return arrayHistory;
    }
}
