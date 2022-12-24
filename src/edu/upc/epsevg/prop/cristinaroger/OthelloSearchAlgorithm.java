//package edu.upc.epsevg.prop.cristinaroger;
//
//import edu.upc.epsevg.prop.othello.CellType;
//import edu.upc.epsevg.prop.othello.GameStatus;
//import java.awt.Point;
//import java.util.ArrayList;
//
///**
// *
// * Clase base per algorismes de cerca per a Othello
// * 
// * @author cristina
// * @author roger
// */
//public abstract class OthelloSearchAlgorithm {
//    
//    /**
//     * 
//     * 
//     * 
//     * @param s El estat actual del tauler de joc
//     * @param whoAmI
//     * @param maxDepth La profunditat maxima a la que cercar
//     * @param stabilization
//     * @return El millor moviment utiliztant la heuristica corresponent
//     */
//    public abstract Point findNextBestMove(GameStatus s, CellType whoAmI, int maxDepth, boolean timeout);
//
//    public abstract Point IDS(GameStatus s, CellType whoAmI, int Depth, boolean timeout);
//}
