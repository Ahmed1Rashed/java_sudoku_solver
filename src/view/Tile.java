/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author hp
 */
public class Tile extends JButton{
    private final int row;
    private final int column;
    private JLabel label;
    private int number = 0;
    
    public Tile(int row, int column) {
        this.row = row;
        this.column = column;
        setBackground(Color.white);
        label = new JLabel();
        add(label);
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);

    }
    
    public int getRow() {
        return row;
    }
    
    public int getColumn() {
        return column;
    }
    
    public int getNumber() {
        return number;
    }
    
    public void setNumber(int i) {
        if(i > -1 && i < 10) {
            number = i;
            if(number == 0) {
                label.setText(null);
            }
            else {
                label.setText(String.valueOf(number));
            }
        }
    }

}
