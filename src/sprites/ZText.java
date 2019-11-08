package sprites;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
    Ztext extends JTextField dimaksudkan untuk membuat custom JTextField
    dalam kasus ini ZText memiliki setingan Font dan Border yang berbeda dengan Super classnya
    ZText memiliki setingan default setVisible(true) dan setEditable(false)
 */
public class ZText extends JTextField {

    /**
        Method constructor ini digunakan untuk membuat text field
        dengan Text , ukuran font yang ditentukan dan warna default rgb(235,235,235);
        @param ukuranFont Ini adalah Variable untuk menentukan ukuran font pada text field
        @param posisi Ini adalah variable untuk menentukan posisi text pada text field
     */
    public ZText(int ukuranFont, String posisi){
        setFont(ukuranFont,posisi);
        this.setBackground(new Color(235,235,235));
        this.setVisible(true);
    }

    /**
        Method constructor ini digunakan untuk membuat text field
        dengan Text , ukuran font yang ditentukan dan warna dinamis;
        @param ukuranFont Ini adalah Variable untuk menentukan ukuran font pada text field
        @param posisi Ini adalah variable untuk menentukan posisi text pada text field
        @param r Ini adalah variable untuk menentukan warna background pada text field
        @param g Ini adalah variable untuk menentukan warna background pada text field
        @param b Ini adalah variable untuk menentukan warna background pada text field
     */
    public ZText(int ukuranFont, String posisi, int r, int g, int b){
        setFont(ukuranFont,posisi);
        this.setBackground(new Color(r,g,b));
        this.setVisible(true);
    }

    /**
        Method Ini dibuat untuk meng-Override setBorder pada super class
        untuk menghilangkan border pada text field
     */
    @Override
    public void setBorder(Border border) {
    // Kosong karena dalam kasus ini border ingin didisable
    }

    /**
        Method Ini untuk Mengubah font Style
        Font yang ditambahkan berasal dari folder lokal
        @param ukuranFont Ini adalah Variable untuk menentukan ukuran font text pada button
        @param posisi Ini adalah variable untuk menentukan posisi text pada text field
     */
    private void setFont(int ukuranFont,String posisi){
        Font font = new Font("SansSerif", Font.BOLD, ukuranFont);
        this.setFont(font);
        this.setEditable(false);
        // Mengatur posisi text
        if(posisi.compareToIgnoreCase("RIGHT")==0){
            this.setHorizontalAlignment(JTextField.RIGHT);
        }
        else{
            this.setHorizontalAlignment(JTextField.LEFT);
        }

    }
}
