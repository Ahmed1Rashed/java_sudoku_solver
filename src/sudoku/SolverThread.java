/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

/**
 *
 * @author hp
 */
public class SolverThread extends Thread{
    private int[][] board;
    private SudokuSolverListener sudokuSolverListener;
    
    public SolverThread(int[][] board, SudokuSolverListener sudokuSolverListener) {
        this.board = board;
        this.sudokuSolverListener = sudokuSolverListener;
    }
    
    @Override
    public void run() {
        SudokuSolver sudokuSolver = new SudokuSolver(board,sudokuSolverListener);
        sudokuSolver.startSolve();
    }
}
