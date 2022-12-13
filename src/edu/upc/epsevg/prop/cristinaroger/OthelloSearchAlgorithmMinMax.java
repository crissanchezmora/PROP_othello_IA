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
    public int findNextBestMove(TaulerWithHeuristic t, int color, int maxDepth) {
        if(maxDepth < 0)
            return -1;
        
        // Init search profiling
        _current_node_exploration_count = 0;
        _current_leaf_exploration_count = 0;
        _current_root_color = color;
        long ms_begin = System.currentTimeMillis();
        
        // Do search
        int bestHeuristic = Integer.MIN_VALUE;
        Point bestMove;
        bestMove.setLocation(-1, -1);
        
        int alpha = TaulerWithHeuristic.MIN_VAL;
        int beta = TaulerWithHeuristic.MAX_VAL;
        
        
        
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
    
}
