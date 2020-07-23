package org.example;

import javax.swing.*;

public class App
{

    public static void main( String[] args )
    {
        JFrame window = new JFrame("GoMoku");
        JPanel content = new GameInterface(12);
        window.setContentPane(content);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocation(300, 300);
        window.pack();
        window.setResizable(false);
        window.setVisible(true);
    }
}
