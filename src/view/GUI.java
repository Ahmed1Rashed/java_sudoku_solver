/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import sudoku.Location;
import sudoku.SolverThread;
import sudoku.SudokuSolver;
import sudoku.SudokuSolverListener;

/**
 *
 * @author hp
 */
public class GUI implements MouseListener, WindowListener, KeyListener, SudokuSolverListener{
    private Tile highlightedTile = null;
    private JFrame gui = new JFrame();;
    private Tile[][] board = new Tile[9][9];
    private SudokuSolver sudokuSolver = null;
            
    public GUI() {
        gui.addWindowListener(this);
        gui.setLayout(new GridLayout(3,3));
        for(int subgridNumber = 0; subgridNumber < 9; subgridNumber++) {
            JPanel subgrid = new JPanel();
            subgrid.setLayout(new GridLayout(3,3));
            subgrid.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            for(int i = 0; i < 9; i++) {
                Tile tile = new Tile(((subgridNumber/3)*3+i/3),(subgridNumber%3)*3+i%3);
                board[tile.getRow()][tile.getColumn()] = tile;
                tile.addMouseListener(this);
                tile.addKeyListener(this);
                subgrid.add(tile);
            }
            gui.add(subgrid);
        }
        System.out.println("TILE COORDINATES ARE: ");
        for(int row = 0; row < 9; row++) {
            for(int column = 0; column < 9; column++) {
                System.out.println(board[row][column].getRow() + " " + board[row][column].getColumn());
            }
        }
        gui.setBounds(0, 0, 640, 480);
        gui.setVisible(true);
    }
    
    private void highlightTile(Tile tile) {
        removeHighlight();
        tile.setBackground(Color.cyan);
        tile.updateUI();
        highlightedTile = tile;
    }
    private void removeHighlight() {
        if(highlightedTile != null) {
            highlightedTile.setBackground(Color.WHITE);
            highlightedTile.updateUI();
            highlightedTile = null;
        }
    }
    
    private void changeHighlightedNumber(int number) {
        if(highlightedTile != null) {
        highlightedTile.setNumber(number);
        removeHighlight();
        }
    }

    private void solve() {
        int[][] boardInt = new int[9][9];
        for(int row = 0; row < 9; row++) {
            for(int column = 0; column < 9; column++) {
                boardInt[row][column] = board[row][column].getNumber();
            }
        }
        SolverThread sudokuSolverThread = new SolverThread(boardInt,this);
        sudokuSolverThread.start();
        //else {
        //}
        /*System.out.println("Solving done.");
        for(int row = 0; row < 9; row++) {
            for(int column = 0; column < 9; column++) {
                board[row][column].setNumber(solvedBoard[row][column]);
            }
        }*/
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        char clickedChar = e.getKeyChar();
        System.out.println((int) clickedChar);
        if(Character.isDigit(clickedChar)) {
            int number = (Character.digit(clickedChar, 10));
            //System.out.println(number);
            changeHighlightedNumber(number);
        }
        else if((int) clickedChar == 10) {
            // Enter key
            solve();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Component clickedComponent = e.getComponent();
        if(clickedComponent instanceof Tile) {
            Tile clickedTile = (Tile) clickedComponent;
            highlightTile(clickedTile);
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
    public void tileChanged(Location tileLocation, int newValue) {
        board[tileLocation.row][tileLocation.column].setNumber(newValue);
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

}
