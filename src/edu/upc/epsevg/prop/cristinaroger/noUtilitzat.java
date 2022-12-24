package edu.upc.epsevg.prop.cristinaroger;

import edu.upc.epsevg.prop.othello.CellType;
import edu.upc.epsevg.prop.othello.GameStatus;

/**
 *
 * @author cristina
 * @author roger
 */
public class noUtilitzat {
    
    int board = 0;
    private int getStables(GameStatus s, CellType player) {
        int stables = 0;
        
        //Cantonada superior esquerra
        int x = 0;
        int y = 0;
        boolean stable;
        if (s.getPos(x, y).equals(player)) {
            stable = true;
            for (y = 0; y <= board && stable; y++) { 
                int j = 0; 
                for (int i = y; i >= 0 && stable; i--) {
                    if (s.getPos(j, i).equals(player)) {
                        ++stables;
                        ++j;
                    } else 
                        stable = false;   
                }
                if (!stable) {
                    stable = true;
                    j = 0;
                    for (int i = y; i >= 0 && stable; i--) {
                        if (s.getPos(i, j).equals(player)) {
                            ++stables;
                            ++j;
                        } else 
                            stable = false;
                    }
                }
            }
        }
        
        //Cantonada inferior esquerra
        x = 0;
        y = board;
        if (s.getPos(x, y).equals(player)) {
            stable = true;
            for (x = 0; x <= board && stable; x++) { // derecha derecha
                int j = board;
                for (int i = x; i >= 0 && stable; i--) {
                    if (s.getPos(i, j).equals(player)) {
                        ++stables;
                        --j;
                    } else 
                        stable = false;            
                }
                if (!stable) {
                    stable = true;
                    j = 0;
                    for (int i = board-x; i <= board && stable; i++) {
                        if (s.getPos(j, i).equals(player)) {
                            ++stables;
                            ++j;
                        } else 
                            stable = false;                  
                    }
                }
            }
        }
        
        //Cantonada superior dreata
        x = board;
        y = 0;
        if (s.getPos(x, y).equals(player)) {
            stable = true;
            for (x = board; x >= 0 && stable; x--) { // derecha derecha
                int j = 0;
                for (int i = x; i <= board && stable; i++) {
                    if (s.getPos(i, j).equals(player)) {
                        ++stables;
                        ++j;
                    } else 
                        stable = false;
                }
                if (!stable) {
                    stable = true;
                    j = board;
                    for (int i = board-x; i >= 0 && stable; i--) {
                        if (s.getPos(j, i).equals(player)) {
                            ++stables;
                            --j;
                        } else 
                            stable = false;                          
                    }
                }
            }
        }
        
        x = board;
        y = board;
        if (s.getPos(x, y).equals(player)) {
            stable = true;
            for (y = board; y >= 0 && stable; y--) { //derecha arriba
                int j = board;  //desde la derecha de la esquina
                for (int i = y; i <= board && stable; i++) { //retrocede desde y hasta el borde a la izquierda de la esquina
                    if (s.getPos(j, i).equals(player)) {
                        ++stables;
                        --j;
                    } else 
                        stable = false;
                }
                if (!stable) {
                    stable = true;
                    j = board;
                    for (int i = y; i <= board && stable; i++) {
                        if (s.getPos(i, j).equals(player)) {
                            ++stables;
                            --j;
                        } else 
                            stable = false;
                    }
                }
            }
        }
        
        return stables*200;
    }
}
