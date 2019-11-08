package panel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ArrayPanel extends JPanel {
    int[][] data;
    JLabel[][] grid;

    public ArrayPanel(int row,int col,int[][]data) {
        this.setLayout(new GridLayout(row, col));
        this.data=data;
        grid= new JLabel[row][col];

        for (int i = 0; i < row; i++){
            for (int j = 0; j < col; j++){
                grid[i][j] = new JLabel((i+j)+"",SwingConstants.CENTER);
                grid[i][j].setPreferredSize(new Dimension(26,26));
                grid[i][j].setBorder(new LineBorder(Color.BLACK));
                grid[i][j].setBackground(Color.black);
                grid[i][j].setOpaque(true);
                grid[i][j].setFont(new Font("Arial Bold", Font.PLAIN, 12));
                grid[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
                this.add(grid[i][j]);
            }
        }
        grid[0][0].setBackground(Color.GRAY);
    }

    public void setData(int[][] brain) {
        this.data = brain;
        for (int i = 0; i < brain.length; i++){
            for (int j = 0; j < brain[0].length; j++){
                grid[i][j].setText(brain[i][j]+"");
            }
        }
    }

    public void setDataSprite(int[] indexSprite, Color color) {
        for (int i = 0; i < grid.length; i++){
            for (int j = 0; j < grid[0].length; j++){
                if(Integer.parseInt(this.grid[i][j].getText().trim())>0){
                    this.grid[i][j].setBackground(Color.green);
                }else {
                    this.grid[i][j].setBackground(Color.black);
                }
            }
        }
        this.grid[indexSprite[0]][indexSprite[1]].setBackground(color);
    }
    public void setDataSprite(int[] indexSprite,int[] indexToGo, Color color) {
        setDataSprite(indexSprite,color);
        this.grid[indexToGo[0]][indexToGo[1]].setBackground(Color.yellow);
        this.grid[indexSprite[0]][indexSprite[1]].setBackground(color);

    }
}
