package panel;

import panel.ArrayPanel;
import sprites.ZText;

import javax.swing.*;
import java.awt.*;

public class ZPanel extends JPanel  implements Runnable{
    ZText message;
    ArrayPanel arrayPanel;
    StringBuilder dot;
    private volatile boolean cancelled;

    public ZPanel(){
        message = new ZText(14, "LEFT");
        message.setText("Message : ");
        message.setPreferredSize(new Dimension(600, 30));
        message.setVisible(true);
        arrayPanel = new ArrayPanel(24, 24, new int[][]{{}});
        arrayPanel.setBounds(0,0,600,500);
        dot = new StringBuilder();
        this.add(message);
        this.add(arrayPanel);
//        this.setBackground(Color.MAGENTA);
    }

    public void setMessage(String s) {
        this.message.setText(s);
    }

    public void setData(int[][] brain) {
        this.arrayPanel.setData(brain);
    }
    public void setSpriteLocations(int[]index,Color color){
        this.arrayPanel.setDataSprite(index, color);
    }
    public void setSpriteLocations(int[]index,int[] gotoIndex,Color color){
        this.arrayPanel.setDataSprite(index, gotoIndex,color);
    }

    @Override
    public void run() {
        while (!isCancelled()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            dot.append(" . ");
            if (dot.length() % 13 == 0) {
                dot.delete(0, dot.length());
                dot.append(" . ");
            }
            this.setMessage("Message : looking forward " + dot);
        }
    }
    public void cancel(String s)
    {
        cancelled = true;
        this.setMessage(s);
    }

    public boolean isCancelled() {
        return cancelled;
    }
}
