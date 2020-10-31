/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author hp
 */
public class SudokuSolver {
    /**
     * @param args the command line arguments
     */
    private int[][] board;
    private final SudokuSolverListener sudokuSolverListener;
    //private ArrayList<Integer>[][] boardPossibleMoves; // possible moves for each tile
    private Subgrid[] subgrids; // subgrids sorted in ascending order by number of empty tiles
    
    //list of empty tiles. used in dfs.
    private ArrayList<Location> emptyTiles;
    
    //private Location nextMove = null; // next possible move if exists
    //private int nextMoveNumber;
    
        
    public SudokuSolver(int[][] board, SudokuSolverListener sudokuSolverListener) {
        this.sudokuSolverListener = sudokuSolverListener;
        this.board = board;
        init();
    }
    
    private int getSubgridIndex(Location location) {
        return ((location.row/3)*3+(location.column/3));
    }
    
    private Location[] getSubgridTiles(int subgridIndex) {
        Location[] tiles = new Location[9];
        int index = 0;
        for(int row = (subgridIndex/3)*3; row < (subgridIndex/3)*3 + 3; row++) {
            for(int column = (subgridIndex%3)*3; column < (subgridIndex%3)*3 + 3; column++) {
                tiles[index] = new Location(row,column);
                index++;
            }
        }
        return tiles;
    }
    
    private Location[] getSubgridTiles(Location location) {
        return getSubgridTiles(getSubgridIndex(location));
    }
    
    private Location[] getColumnTiles(Location location) {
        Location[] tiles = new Location[9];
        for(int row = 0; row < 9; row++) {
            tiles[row] = new Location(row,location.column);
        }
        return tiles;
    }
    
    private Location[] getRowTiles(Location location) {
        Location[] tiles = new Location[9];
        for(int column = 0; column < 9; column++) {
            tiles[column] = new Location(location.row,column);
        }
        return tiles;
    }
    
    private ArrayList<Integer> takenSubgridNumbers(Location location) {
        ArrayList<Integer> takenNumbers = new ArrayList();
        Location[] tiles = getSubgridTiles(location);
        for(Location l : tiles) {
            int number = board[l.row][l.column];
            if(number != 0) {
                takenNumbers.add(number);
            }
        }
        return takenNumbers;
    }
    
    private ArrayList<Integer> takenColumnNumbers(Location location) {
        ArrayList<Integer> takenNumbers = new ArrayList();
        Location[] tiles = getColumnTiles(location);
        for(Location l : tiles) {
            int number = board[l.row][l.column];
            if(number != 0) {
                takenNumbers.add(number);
            }
        }
        return takenNumbers;
    }
    
    private ArrayList<Integer> takenRowNumbers(Location location) {
        ArrayList<Integer> takenNumbers = new ArrayList();
        Location[] tiles = getRowTiles(location);
        for(Location l : tiles) {
            int number = board[l.row][l.column];
            if(number != 0) {
                takenNumbers.add(number);
            }
        }
        return takenNumbers;
    }
    
    private ArrayList<Integer> getPossibleMoves(Location location) {
        ArrayList<Integer> possibleMoves = new ArrayList();
        if(board[location.row][location.column] == 0) {
            ArrayList<Integer> takenRowNumbers = takenRowNumbers(location);
            ArrayList<Integer> takenColumnNumbers = takenColumnNumbers(location);
            ArrayList<Integer> takenSubgridNumbers = takenSubgridNumbers(location);


            for(Integer number = 1; number < 10; number++) {
                if(!takenRowNumbers.contains(number) && !takenColumnNumbers.contains(number) && !takenSubgridNumbers.contains(number)) {
                    possibleMoves.add(number);
                }
            }
        }
        return possibleMoves;
    }
    // returns an ArrayList of the moves which can be made in this tile and can't be made in its neighboring tiles
    /*private ArrayList<Integer> getUniqueMoves(Location location) { 
        System.out.println("FINDING UNIQUE MOVES FOR: (" + location.row + "," + location.column + ")");
        ArrayList<Integer> uniqueMoves = boardPossibleMoves[location.row][location.column];
        System.out.print("STARTING UNIQUE MOVES ARE: ");
        for(Integer number : uniqueMoves) {
            System.out.print(number + " ");
        }
        System.out.println("");
        Location[] columnTiles = getColumnTiles(location);
        for(Location tile : columnTiles) {
            if(tile.equals(location)) {
                continue;
            }
            uniqueMoves.removeAll(boardPossibleMoves[tile.column][tile.row]);
        }
        System.out.print("AFTER COLUMN CHECK UNIQUE MOVES ARE: ");
        for(Integer number : uniqueMoves) {
            System.out.print(number + " ");
        }
        System.out.println("");
        Location[] rowTiles = getRowTiles(location);
        for(Location tile : rowTiles) {
            if(tile.equals(location)) {
                continue;
            }
            uniqueMoves.removeAll(boardPossibleMoves[tile.column][tile.row]);
        }
        System.out.print("AFTER ROW CHECK UNIQUE MOVES ARE: ");
        for(Integer number : uniqueMoves) {
            System.out.print(number + " ");
        }
        System.out.println("");
        Location[] subgridTiles = getSubgridTiles(location);
        for(Location tile : subgridTiles) {
            if(tile.equals(location)) {
                continue;
            }
            uniqueMoves.removeAll(boardPossibleMoves[tile.column][tile.row]);
        }
        System.out.print("AFTER SUBGRID CHECK UNIQUE MOVES ARE: ");
        for(Integer number : uniqueMoves) {
            System.out.print(number + " ");
        }
        System.out.println("");
        return uniqueMoves;
    }*/
    
    /*private boolean cacheNextMove() {
        for(Subgrid subgrid : subgrids) {
            for(Location emptyTile : subgrid.emptyTiles) {
                ArrayList<Integer> uniqueMoves = getUniqueMoves(emptyTile);
                System.out.println("UNIQUE MOVES SIZES IS: " + uniqueMoves.size());
                if(uniqueMoves.size() == 1) {
                    nextMove = emptyTile;
                    nextMoveNumber = uniqueMoves.get(0);
                    System.out.println("UNIQUE MOVE FOUND AT: (" + nextMove.row + "," + nextMove.column + ")" + " EQUAL TO: " + nextMoveNumber);
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean hasNextMove() {
        if(nextMove != null) {
            return true;
        }
        else {
            return cacheNextMove();
        }
    }*/
    
    /*private void populateBoardPossibleMoves() {
        boardPossibleMoves = new ArrayList[9][9];
        for(int row = 0; row < 9; row++) {
            for(int column = 0; column < 9; column++) {
                boardPossibleMoves[row][column] = getPossibleMoves(new Location(row,column));
                System.out.print("The possible moves for: (" + row + "," + column + ") are: ");
                for(Integer number : boardPossibleMoves[row][column]) {
                    System.out.print(number + " ");
                }
                System.out.println("");
            }
        }
    }*/
    
    private void initializeSubgrids() {
        subgrids = new Subgrid[9];
        for(int subgridIndex = 0; subgridIndex < 9; subgridIndex++) {
            Location[] tiles = getSubgridTiles(subgridIndex);
            ArrayList<Location> emptyTiles = new ArrayList();
            for(Location tile : tiles) {
                if(board[tile.row][tile.column] == 0) {
                    emptyTiles.add(new Location(tile.row,tile.column));
                }
            }
            Subgrid subgrid = new Subgrid(subgridIndex,emptyTiles);
            subgrids[subgridIndex] = subgrid;
        }
        Arrays.sort(subgrids, new SortByEmptyTiles());
        System.out.println("SORTED SUBGRIDS:");
        for(Subgrid s : subgrids) {
            System.out.print(s.subgridIndex + " ");
        }
        System.out.println("");
    }
    
    private void changeTileNumber(Location location, Integer number) {
        board[location.row][location.column] = number;
        sudokuSolverListener.tileChanged(location, number);
        //populateBoardPossibleMoves();
    }
    
    //public void nextMove() {
        //Clean up after move
        //board[nextMove.row][nextMove.column] = nextMoveNumber;
        /*boardPossibleMoves[nextMove.row][nextMove.column].clear();
        Subgrid subgrid = subgrids[getSubgridIndex(nextMove)];
        subgrid.emptyTiles.remove(nextMove);
        for(Location tile : subgrid.emptyTiles) {
            //boardPossibleMoves[tile.row][tile.column].remove(Integer.valueOf(nextMoveNumber));
            boardPossibleMoves[tile.row][tile.column] = getPossibleMoves(tile);
        }
        Location[] columnTiles = getColumnTiles(nextMove);
        for(Location tile : columnTiles) {
            //boardPossibleMoves[tile.row][tile.column].remove(Integer.valueOf(nextMoveNumber));
            boardPossibleMoves[tile.row][tile.column] = getPossibleMoves(tile);
        }
        Location[] rowTiles = getRowTiles(nextMove);
        for(Location tile : rowTiles) {
            //boardPossibleMoves[tile.row][tile.column].remove(Integer.valueOf(nextMoveNumber));
            boardPossibleMoves[tile.row][tile.column] = getPossibleMoves(tile);
        }*/
        //nextMove = null;
        //populateBoardPossibleMoves();
        //cacheNextMove();
    //}
    
    /*public int getNextMoveColumn() {
        return nextMove.column;
    }
    
    public int getNextMoveRow() {
        return nextMove.row;
    }
    
    public int getNextMoveNumber() {
        return nextMoveNumber;
    }*/
    
    private void init() {
        //populateBoardPossibleMoves();
        initializeSubgrids();
    }
    
    public void startSolve() {
        emptyTiles = new ArrayList();
        for(Subgrid subgrid : subgrids) {
            emptyTiles.addAll(subgrid.emptyTiles);
        }
        dfs(0);
    }
    
    private boolean dfs(int tileIndex) {
        if(tileIndex == emptyTiles.size()) {
            return true;
        }
        Location emptyTileLocation = emptyTiles.get(tileIndex);
        ArrayList<Integer> possibleMoves = getPossibleMoves(emptyTileLocation);
        for(Integer possibleMove : possibleMoves) {
            changeTileNumber(emptyTileLocation,possibleMove);
            if(dfs(tileIndex+1)) {
                return true;
            }
        }
        changeTileNumber(emptyTileLocation,0);
        return false;
    }
    
    
}
