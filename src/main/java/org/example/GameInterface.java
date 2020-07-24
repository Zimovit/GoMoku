package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class GameInterface extends JPanel implements MouseListener, ActionListener {
    /*Field is a main game field, south is just a place to show who is win,
    east to control
     */
    JPanel field, south, east, playerColor;
    //control buttons
    JButton newGame, reset;
    //cells array
    ArrayList<ArrayList<Cell>> cellArray;
    //current player
    boolean currentPlayerIsRed;
    //when game is over, cells won't react on clicks
    boolean gameIsOver;
    //board size
    int boardSize;
    //if game is running or some player won
    JLabel gameStatus;
    //cell indexes to draw a line
    int startRow, startCol, endRow, endCol;

    public GameInterface(int size){
        gameIsOver = false;
        boardSize = size;
        setLayout(new BorderLayout());
        setBackground(Color.CYAN);
        currentPlayerIsRed = true;  //red starts
        //initialising array
        cellArray = new ArrayList<>();
        for (int i = 0; i < boardSize; i++){
            cellArray.add(new ArrayList<Cell>());
            for (int j = 0; j < boardSize; j++){
                cellArray.get(i).add(new Cell(i, j));
            }
        }

        //creating the field
        field = new JPanel();
        field.setBackground(Color.RED);
        field.setLayout(new GridLayout(size, size));    //square form
        field.setPreferredSize(new Dimension(size*20, size*20));    //20 p each cell
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                Cell cell = cellArray.get(i).get(j);
                cell.addMouseListener(this);
                field.add(cell);
            }
        }

        //creating control panel
        east = new JPanel();
        east.setLayout(new GridLayout(3,1));

        playerColor = new JPanel();  //to show color of the current player
        playerColor.setBackground(Color.RED);

        newGame = new JButton("New game");
        newGame.addActionListener(this);
        newGame.setEnabled(false);

        reset = new JButton("Quit");
        reset.addActionListener(this);

        east.add(playerColor);
        east.add(newGame);
        east.add(reset);

        //creating south panel/ it's easy
        south = new JPanel();
        south.setBackground(Color.GRAY);
        south.setLayout(new BorderLayout());
        gameStatus = new JLabel("Ready to start");
        gameStatus.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        gameStatus.setHorizontalAlignment(JLabel.CENTER);
        south.add(gameStatus, BorderLayout.CENTER);

        //packing
        add(field, BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);
        add(east, BorderLayout.EAST);
    }

    private boolean gameFinished(int x, int y){
        Cell cell;

        char currentPlayer = (currentPlayerIsRed ? Cell.RED : Cell.BLACK);

        int count = 1;  //how many tokens in row. One is already placed
        int row, col;
        startRow = x;
        endRow = x;
        startCol = y;
        endCol = y;
        int[] dirX = {0, 1, 1, 1}, dirY = {1, 1, 0, -1}; //directions of checking
        for (int i = 0; i < dirX.length; i++){
            row = x + dirX[i];
            col = y + dirY[i];


            while (row >= 0 && row < boardSize && col >= 0 && col < boardSize){
                cell = cellArray.get(row).get(col);
                if (cell.getOwner() == currentPlayer) {
                    count++;
                    startRow = row;
                    startCol = col;
                } else break;
                if (count >= 5) return true;
                row += dirX[i];
                col += dirY[i];
            }
            //backward
            row = x - dirX[i];
            col = y - dirY[i];
            while (row >= 0 && row < boardSize && col >= 0 && col < boardSize){
                cell = cellArray.get(row).get(col);
                if (cell.getOwner() == currentPlayer) {
                    count++;
                    endRow = row;
                    endCol = col;
                } else break;
                if (count >= 5) return true;
                row -= dirX[i];
                col -= dirY[i];
            }
            count = 1;  //not 5 in a row, change row direction
        }

        return false;

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (gameIsOver) return;
        //calculating the clicked cell
        Cell cell = (Cell) e.getSource();
        if (cell.isOccupied()) return;

        if (currentPlayerIsRed){
            cell.setOwner('r');
            playerColor.setBackground(Color.BLACK);
            gameStatus.setText("Current player: Black");

        } else {
            cell.setOwner('b');
            playerColor.setBackground(Color.RED);
            gameStatus.setText("Current player: Red");
        }

        cell.setOccupied(true);
        repaint();

        if (gameFinished(cell.getxOnBoard(), cell.getyOnBoard())){
            gameIsOver = true;
            newGame.setEnabled(true);
            String winner = currentPlayerIsRed ? "Red" : "Black";
            gameStatus.setText(winner + " wins!");
            repaint();
            return;
        }
        currentPlayerIsRed = !currentPlayerIsRed;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if (gameIsOver){
            Color color = g.getColor();
            g.setColor(Color.CYAN);
            g.drawLine(startRow*20+10, startCol*20+10,
                    endRow*20+10, endCol*20+10);
            g.setColor(color);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();  //what button was pressed

        switch (command){
            case "Quit":
                System.exit(0);
                break;  //just let it be XD
            case "New game": {
                Cell cell;
                for (int i = 0; i < boardSize; i++){
                    for (int j = 0; j < boardSize; j++){
                        cell = cellArray.get(i).get(j);
                        cell.setOwner(Cell.NONE);
                        cell.setOccupied(false);
                    }
                }

                newGame.setEnabled(false);
                currentPlayerIsRed = true;
                gameIsOver = false;
                gameStatus.setText("Ready to start");
                repaint();
            }
        }

    }
}
