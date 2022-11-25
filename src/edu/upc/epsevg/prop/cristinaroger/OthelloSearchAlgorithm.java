/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upc.epsevg.prop.cristinaroger;

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
     * @param color El color de la propera fitxa
     * @param maxDepth La profunditat maxima a la que cercar
     * @return El millor moviment utiliztant la heuristica corresponent
     */
    public abstract int findNextBestMove(int color, int maxDepth);
}
