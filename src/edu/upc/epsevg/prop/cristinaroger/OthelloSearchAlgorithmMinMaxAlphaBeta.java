/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upc.epsevg.prop.cristinaroger;
import edu.upc.epsevg.prop.othello.CellType;
import edu.upc.epsevg.prop.othello.GameStatus;
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
     * Max value to use when finding a winning state
     */
    public final static int MAX_VAL = Integer.MAX_VALUE-1;  //  2147483647
    
    /**
     * Min value to use when finding a losing state
     */
    public final static int MIN_VAL = Integer.MIN_VALUE+1;// -2147483647
    
    
    /**
     * Find the best column to put the next piece of given color of the given
     * game t using MiniMax with alpha-beta pruning
     * 
     * @param s The state of game
     * @param whoAmI
     * @param maxDepth The maximum depth to conduct the search
     * @return The best found movement using C4Heuristic
     */
    @Override
    public Point findNextBestMove(GameStatus s, CellType whoAmI, int maxDepth){
        if(maxDepth < 0){
            System.out.println("ccc");
            return new Point (-1,-1);
        }
        // Init search profiling
        _current_node_exploration_count = 0;
        _current_leaf_exploration_count = 0;
        long ms_begin = System.currentTimeMillis();
        
        // Do search
        int bestHeuristic = Integer.MIN_VALUE;
        Point bestMove = new Point (-1,-1);
        
        int alpha = TaulerWithHeuristic.MIN_VAL;
        int beta = TaulerWithHeuristic.MAX_VAL;

        ArrayList <Point> moves = s.getMoves();
        //for (int j = 0; j < moves.size(); j++){
        //    System.out.println("Movimientos s 1: " +  moves.get(j));
        //}
        //Check valid movement
        if (!moves.isEmpty()){
            for (int i = 0; i < moves.size(); i++){
                if(!s.canMove(moves.get(i), s.getCurrentPlayer())){
                    continue;
                }
                //Create copy and move piece
                GameStatus nextT = new GameStatus(s);
                //System.out.println("Antes de mov en el next");
                //System.out.println("Tablero s antes del mov");
                //System.out.println(s.toString());
                //System.out.println("Player antes:" + s.getCurrentPlayer());
                nextT.movePiece(moves.get(i));
                //System.out.println("Player dsp:" + s.getCurrentPlayer());
                //System.out.println("despues move");
                //Evaluate movement
                
                int movHeur = minmax(nextT, maxDepth-1, false, alpha, beta);
                
                
                if(bestHeuristic < movHeur || bestMove == new Point (-1,-1)) {
                    System.out.println("1 movHeur: " + movHeur);
                    System.out.println("1 BestMove: " + bestMove);
                    System.out.println("1 BestHeur: " + bestHeuristic);
                    bestHeuristic = movHeur;
                    bestMove = moves.get(i);
                    System.out.println("2 movHeur: " + movHeur);
                    System.out.println("2 BestMove: " + bestMove);
                    System.out.println("2 BestHeur: " + bestHeuristic);
                }   
            }
       }
        if (bestMove == new Point (-1,-1)){
            System.out.println("No moves available.");
            s.skipTurn();
        }
        // End search profiling and output results
        long ms_end = System.currentTimeMillis();
        long incr_ms = (ms_end - ms_begin);
        double incr_s = (double)incr_ms/1000.0;
        System.out.println("Selected movement " + bestMove + ' ' +
                           "with heuristic " + bestHeuristic + ' ' +
                           "for " + s.getCurrentPlayer() + ' ' +
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
    private int minmax(GameStatus s, int maxDepth, boolean isMax, int alpha, int beta) {
        System.out.println("VALORES MINIMAX "
                            + " maxDepth: " + maxDepth 
                            + " isMax: " + isMax
                            + " jugador actual: " + s.getCurrentPlayer());
        _current_node_exploration_count++;
        if(maxDepth <= 0 || s.isGameOver()) {
            _current_leaf_exploration_count++;
            return getHeuristic(s);
        }
        
        System.out.println("Player actual:" + s.getCurrentPlayer() + " Is max: " + isMax);
        int heuristic = isMax ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        ArrayList <Point> moves = s.getMoves();
        
        //System.out.println("PLayer minmax " + s.getCurrentPlayer());
        //for (int j = 0; j < moves.size(); j++){
        //    System.out.println("Movimientos s 2: " +  moves.get(j));
        //} 
        if (!moves.isEmpty()){
            System.out.println("aaaa");
            for (int i = 0; i < moves.size(); i++) {
                // Move

                GameStatus nextT = new GameStatus(s);
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
        }
        System.out.println("bbb");
        System.out.println("Movimientos posibles? " + moves.isEmpty());
        return heuristic;
    }

    private int getHeuristic(GameStatus s) {
        //Check if the game was won
        if (s.isGameOver()){
            System.out.println("jugador actual juego terminado: " + s.getCurrentPlayer());
            System.out.println("jugador actual juego ganado:    " + s.GetWinner());
            if (s.GetWinner() == s.getCurrentPlayer()) return MAX_VAL;
            return MIN_VAL;
        }
        
        CellType rival  = CellType.opposite(s.getCurrentPlayer());
        CellType player = s.getCurrentPlayer();
        
        //System.out.println("Rival: " + rival + s.getScore(rival) + " Player: " + player + s.getScore(player));
        //System.out.println("Tablero de heur");
        //System.out.println(s.toString());
        //System.out.println( "Heuristica actual: " + (s.getScore(rival) - s.getScore(player)));
     return s.getScore(player) - s.getScore(rival);
    }   
    
    @Override
    public Point IDS (GameStatus s, CellType whoAmI){
        
        Point bestMov = new Point(-1, -1);
        
        for (int i = 1; i < 5; i++){
            bestMov = findNextBestMove(s,whoAmI, i);
        }
        return bestMov;
    }
}
