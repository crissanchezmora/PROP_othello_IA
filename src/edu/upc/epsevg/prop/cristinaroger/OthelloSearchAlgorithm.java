/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upc.epsevg.prop.cristinaroger;

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
     * @param t El estat actual del tauler de joc
     * @param color El color de la propera fitxa
     * @param maxDepth La profunditat maxima a la que cercar
     * @return El millor moviment utiliztant la heuristica corresponent
     */
    public abstract Point findNextBestMove(TaulerWithHeuristic t, int color, int maxDepth);
}
