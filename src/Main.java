import muzzle.Muzzle;

import javax.swing.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame implements ActionListener {
    JButton a = new JButton("Move that");
    public static void main(String[] args) {
        JFrame frame = new JFrame();
//      just for now
        final int size =700;
        frame.setSize(size,size);
//        frame.getContentPane().add(new Muzzle(size));
        frame.getContentPane().add(new Muzzle("/home/mza/Documents/Pro/VMicroMouse/muzzle_style/style1.txt"));
        frame.setLocationRelativeTo(null);
        frame.setBackground(Color.LIGHT_GRAY);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        repaint();
    }
}
