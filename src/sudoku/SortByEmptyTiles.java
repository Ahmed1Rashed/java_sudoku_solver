/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

import java.util.Comparator;

/**
 *
 * @author hp
 */
public class SortByEmptyTiles implements Comparator<Subgrid>{


    @Override
    public int compare(Subgrid o1, Subgrid o2) {
        return Integer.compare(o1.emptyTiles.size(), o2.emptyTiles.size());
    }
    
}
