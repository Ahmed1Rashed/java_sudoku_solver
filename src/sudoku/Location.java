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
public class Location {
    public int row = -1;
    public int column = -1;
    public Location(int row, int column) {
        this.row = row;
        this.column = column;
    }
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Location) {
            Location location = (Location) obj;
            if(location.row == row && location.column == column) {
                return true;
            }
        }
        return true;
    }
}
