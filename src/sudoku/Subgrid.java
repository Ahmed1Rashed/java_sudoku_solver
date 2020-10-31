/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

import java.util.ArrayList;

/**
 *
 * @author hp
 */
public class Subgrid{
    int subgridIndex;
    ArrayList<Location> emptyTiles;

    public Subgrid(int subgridIndex, ArrayList<Location> emptyTiles) {
        this.subgridIndex = subgridIndex;
        this.emptyTiles = emptyTiles;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Subgrid) {
            Subgrid s = (Subgrid) obj;
            return(subgridIndex == s.subgridIndex && emptyTiles.equals(s.emptyTiles));
        }
        return false;
    }
}
