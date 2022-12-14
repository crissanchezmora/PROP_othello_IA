/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upc.epsevg.prop.cristinaroger;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 *  Minimax amb poda alpha beta
 * @author cristina
 * @author roger
 */
public class OthelloSearchAlgorithmMinMaxAlphaBeta extends OthelloSearchAlgorithm {
    /**
     * Counter for the nodes explored in the current exploration
     */
    int _current_node_exploration_count = 0;
    
    /**
     * Counter for the leaves explored in the current exploration 
     */
    int _current_leaf_exploration_count = 0;
    
    /**
     * Variable to hold the current color of the root node
     */
    int _current_root_color;
    
    /**
     * Find the best column to put the next piece of given color of the given
     * game t using MiniMax with alpha-beta pruning
     * 
     * @param t The state of game
     * @param color The color of the next piece
     * @param maxDepth The maximum depth to conduct the search
     * @return The best found movement using C4Heuristic
     */
    @Override
    public Point findNextBestMove(TaulerWithHeuristic t, int color, int maxDepth) {
        if(maxDepth < 0)
            return new Point (-1,-1);
        
        // Init search profiling
        _current_node_exploration_count = 0;
        _current_leaf_exploration_count = 0;
        _current_root_color = color;
        long ms_begin = System.currentTimeMillis();
        
        // Do search
        int bestHeuristic = Integer.MIN_VALUE;
        Point bestMove = new Point (-1,-1);
        
        int alpha = TaulerWithHeuristic.MIN_VAL;
        int beta = TaulerWithHeuristic.MAX_VAL;
        
        ArrayList <Point> moves = t.getMoves();
        //Check valid movement
        if (!moves.isEmpty()){
            for (int i = 0; i < moves.size(); i++){
                //Create copy and move piece
                TaulerWithHeuristic nextT = t;
                nextT.movePiece(moves.get(i));
                
                //Evaluate movement
                int movHeur = minmax(nextT, maxDepth-1, false, alpha, beta);
                if(bestHeuristic < movHeur || bestMove == new Point (-1,-1)) {
                bestHeuristic = movHeur;
                bestMove = moves.get(i);
                }   
            }
        }
        
        // End search profiling and output results
        long ms_end = System.currentTimeMillis();
        long incr_ms = (ms_end - ms_begin);
        double incr_s = (double)incr_ms/1000.0;
        System.out.println("Selected movement " + bestMove + ' ' +
                           "with heuristic " + bestHeuristic + ' ' +
                           "having explored " + _current_leaf_exploration_count + " leaves " +
                           "and " + _current_node_exploration_count + " nodes " +
                           "in " + incr_ms  + "ms (" + incr_s + "s)");
        
        return bestMove;
    }
    
    /**
     * Find the worst (if !isMax) or best (if isMax) next heuristic given the 
     * color of the given game t and depth maxDepth within the bounds [alpha, 
     * beta]
     * 
     * @param t The state of game
     * @param maxDepth The maximum depth to conduct the search
     * @param isMax Flag to decide if the heuristic has to be maximized or 
     * minimized
     * @param alpha Lower bound to search
     * @param beta Upper bound to search
     * @return The worst found movement using C4Heuristic
     */
    private int minmax(TaulerWithHeuristic t, int maxDepth, boolean isMax, int alpha, int beta) {
        _current_node_exploration_count++;
        if(maxDepth <= 0 || t.isGameOver()) {
            _current_leaf_exploration_count++;
            return t.getHeuristic(_current_root_color);
        }
        
        int heuristic = isMax ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        ArrayList <Point> moves = t.getMoves();
        for (int i = 0; i < moves.size(); i++) {
            // Move
            TaulerWithHeuristic nextT = t;
            try { nextT.movePiece(moves.get(i)); } catch (Exception e) {}
            
            // Evaluate movement
            int movHeur = minmax(nextT, maxDepth-1, !isMax, alpha, beta);
            
            if(isMax) {
                // Update max heuristic
                heuristic = Math.max(heuristic, movHeur);
                
                // Prune if we exceeded upper bound
                if (beta <= heuristic)
                    break;
                
                // Update alpha (lower bound)
                alpha = Math.max(alpha, heuristic);
            } else {
                // Update min heuristic
                heuristic = Math.min(heuristic, movHeur);
                
                // Prune if we exceeded lower bound
                if (heuristic <= alpha)
                    break;
                
                // Update beta (upper bound)
                beta = Math.min(beta, heuristic);
            }
            
        }
        
        return heuristic;
    }
}
