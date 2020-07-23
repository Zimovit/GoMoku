package org.example;

import javax.swing.*;
import java.awt.*;

public class Cell extends JPanel {
    private  char owner; //red, black or non
    private boolean occupied;
    private int xOnBoard, yOnBoard;   //cell's position on the board

    //this section is for owners constants
    public static final char NONE = 'n';
    public static final char RED = 'r';
    public static final char BLACK = 'b';

    //constructor
    public Cell(int x, int y){
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        xOnBoard = x;
        yOnBoard = y;
        owner = NONE;
        occupied = false;

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if (occupied){
            if (owner == RED){
                g.setColor(Color.RED);
            } else {
                g.setColor(Color.BLACK);
            }

            g.fillOval(3,3, getWidth()-7, getHeight()-7);
        }
    }


    //accessor methods

    public char getOwner() {
        return owner;
    }

    public void setOwner(char owner) {
        this.owner = owner;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public int getxOnBoard() {
        return xOnBoard;
    }

    public int getyOnBoard() {
        return yOnBoard;
    }
}
