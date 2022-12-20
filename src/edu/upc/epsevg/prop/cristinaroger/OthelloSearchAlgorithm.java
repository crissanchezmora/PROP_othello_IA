/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upc.epsevg.prop.cristinaroger;

import edu.upc.epsevg.prop.othello.CellType;
import edu.upc.epsevg.prop.othello.GameStatus;
import java.awt.Point;

/**
 *
 * Clase base per algorismes de cerca per a Othello
 * 
 * @author cristina
 * @author roger
 */
public abstract class OthelloSearchAlgorithm {
    
    /**
     * 
     * 
     * 
     * @param s El estat actual del tauler de joc
     * @param maxDepth La profunditat maxima a la que cercar
     * @return El millor moviment utiliztant la heuristica corresponent
     */
    public abstract Point findNextBestMove(GameStatus s, CellType whoAmI, int maxDepth);

    public abstract Point IDS(GameStatus s, CellType whoAmI);
}
