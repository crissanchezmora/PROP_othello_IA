package edu.upc.epsevg.prop.cristinaroger;

import edu.upc.epsevg.prop.othello.CellType;
import edu.upc.epsevg.prop.othello.GameStatus;
import edu.upc.epsevg.prop.othello.IAuto;
import edu.upc.epsevg.prop.othello.IPlayer;
import edu.upc.epsevg.prop.othello.Move;
import edu.upc.epsevg.prop.othello.SearchType;
import java.awt.Point;
import java.util.ArrayList;
/**
 * Jugador basic
 * @author cristina
 * @author roger
 */
public class Iniesta implements IPlayer, IAuto{
    
    private final String name;
    
    public CellType whoAmI;
    
    private int board = 0;
    
    private boolean time = false;
    
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
    public final static int MAX_VAL = Integer.MAX_VALUE-1;  //  2147483646
    
    /**
     * Min value to use when finding a losing state
     */
    public final static int MIN_VAL = Integer.MIN_VALUE+1;// -2147483646
    
    Point pointLastInTime;
    int heurLastInTime;

    public Iniesta (String name) {
        this.name = name; 
    }
    
    /**
     * @param s Tauler i estat actual del joc.
     * @return el moviment que fa le jugador.
     */
    @Override
    public Move move(GameStatus s) {
        whoAmI = s.getCurrentPlayer();
        board = s.getSize() -1;
        
        ArrayList<Point> moves = s.getMoves();

        if(moves.isEmpty()) s.skipTurn(); 
            Point mov = null;
            int i;
            for (i = 2; i < 100000 && !time; i+=2){
                mov =  IDS(s , whoAmI, i);

            }
           time = false;
           Move move = new Move (mov, _current_node_exploration_count, i, SearchType.MINIMAX);

           return move; 
    }
    
    /**
     * Ens avisa que hem de parar la cerca en curs perquè s'ha exhaurit el temps
     * de joc.
     * TODO: printear hasta dnd ha llegado en este caso.
     */
    @Override
    public void timeout(){
        System.out.println("You are so slow...");
        this.time = true;
    }
    
    /**
     *  Retorna el nom del jugador que s'utlilitza per visualització a la UI
     * 
     * @return Nom del jugador
     */
    @Override
    public String getName(){
        return this.name;
    }
    
    /**
     * Find the best column to put the next piece of given color of the given
     * game t using MiniMax with alpha-beta pruning
     * 
     * @param s The state of game
     * @param whoAmI
     * @param maxDepth The maximum depth to conduct the search
     * @return The best found movement using C4Heuristic
     */
    public Point findNextBestMove(GameStatus s, CellType whoAmI, int maxDepth){
        
        if(maxDepth < 0){
            return new Point (-1,-1);
        }
        
        // Init search profiling
        _current_node_exploration_count = 0; 
        _current_leaf_exploration_count = 0;
        long ms_begin = System.currentTimeMillis();
        
        // Do search
        int bestHeuristic = Integer.MIN_VALUE;
        int alpha = MIN_VAL;
        int beta = MAX_VAL;
        ArrayList <Point> moves = s.getMoves();
        Point bestMove = new Point (-1, -1);
        
        //Check valid movement
        if (!moves.isEmpty()){
            
            for (int i = 0; i < moves.size(); i++){

                //Create copy and move piece
                GameStatus nextT = new GameStatus(s);
                
                nextT.movePiece(moves.get(i));
                
                int movHeur = minmax(nextT, whoAmI, maxDepth-1, false, alpha, beta);

                if(bestHeuristic < movHeur || bestMove.equals(new Point(-1,-1))) {

                    bestHeuristic = movHeur;
                    bestMove = moves.get(i);
                }   
            }
        }
        
        if (bestMove.equals(new Point (-1, -1))){
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
        System.out.println("//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////");

        heurLastInTime = bestHeuristic;
        pointLastInTime = bestMove;
        if(time) return pointLastInTime;
        
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
    private int minmax(GameStatus s, CellType whoAmI, int maxDepth, boolean isMax, int alpha, int beta) {
        
        if (time){
            
            return heurLastInTime;
        }

        _current_node_exploration_count++;
        if(maxDepth <= 0 || s.isGameOver()) {
            
            _current_leaf_exploration_count++;

            return getHeuristic(s, whoAmI);
        }
        
        int heuristic = isMax ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        ArrayList <Point> moves = s.getMoves();

        if (!moves.isEmpty()){

            for (int i = 0; i < moves.size(); i++) {
                
//                System.out.println("Entered FOR | inside minmax");
//                System.out.println("i = " + i);
                
                // Move

                GameStatus nextT = new GameStatus(s);
                //System.out.println("nextT (copy of s): ");
                //System.out.println(nextT.toString());
                
                try { nextT.movePiece(moves.get(i)); } catch (Exception e) {}
                
                int movHeur;
                    movHeur = minmax(nextT, whoAmI ,maxDepth-1, !isMax, alpha, beta);

                // Evaluate movement

                if(isMax) {
                    
                    // Update max heuristic
                    heuristic = Math.max(heuristic, movHeur);

                    // Prune if we exceeded upper bound
                    if (beta <= heuristic){
                        
                        break;
                    }
                    // Update alpha (lower bound)
                    alpha = Math.max(alpha, heuristic);
                    
//                    System.out.println("Alpha: " + alpha);
                    
                } else {
                    
//                    System.out.println("Entered ELSE: !isMax | inside minmax");
                    
                    // Update min heuristic
                    heuristic = Math.min(heuristic, movHeur);
                    
//                    System.out.println("Heuristic: " + heuristic);

                    // Prune if we exceeded lower bound
                    if (heuristic <= alpha){
                                               
                        break;
                    }

                    // Update beta (upper bound)
                    beta = Math.min(beta, heuristic);
                    
//                    System.out.println("Beta: " + beta);
                }  
            }
        }
        else {
            heuristic = getHeuristic(s, whoAmI);
        }
//        System.out.println("Returning heuristic: " + heuristic +" to minmax");
        return heuristic;
    }

    private int getHeuristic(GameStatus s, CellType whoAmI) {
        
        //Check if the game was won
        if (s.isGameOver()){
                if (s.GetWinner() == whoAmI) {
                    return MAX_VAL;
                }
                
                return MIN_VAL;
            }
        CellType rival  = CellType.opposite(s.getCurrentPlayer());
        CellType player = s.getCurrentPlayer();
        
        int heuristic = 0;
        
        heuristic += getHeuristicCorner(s,rival,player);
        heuristic -= s.getMoves().size()*50;    //num mov rival
        heuristic += getHeuristicWalls(s,rival,player);
        heuristic += s.getScore(player) - s.getScore(rival);
        if (s.getScore(player) + s.getScore(rival) < 25) heuristic += getHeuristicHashTag(s, player);
        //heuristic -= getHeuristicHashTag(s, rival); ??? Lo ponemos o no es necesario ???
        //heuristic += stableChips(s, rival, player);
      
     return heuristic;
    }   
    
    private int getHeuristicCorner(GameStatus s, CellType rival, CellType player){
        int heuristic = 0;
        if (s.getPos(0, 0) == rival){
            heuristic -= 700;
        } else if( s.getPos(0, 0) == player){
            heuristic += 700;
        }
        if (s.getPos(0, board) == rival){
            heuristic -= 700;
        } else if( s.getPos(0, board) == player){
            heuristic += 700;
        }
        if (s.getPos(board, 0) == rival){
            heuristic -= 700;
        } else if( s.getPos(board, 0) == player){
            heuristic += 700;
        }
        if (s.getPos(board, board) == rival){
            heuristic -= 700;
        } else if( s.getPos(board, board) == player){
            heuristic += 700;
        }
        
        return heuristic;
    }

    private int getHeuristicWalls(GameStatus s, CellType rival, CellType player){
        int heuristic = 0;
        
        for (int i = 0; i <= board; i++){
            if(s.getPos(0, i) == rival){
                heuristic -= 200;
            } else if (s.getPos(0, i) == player){
                heuristic += 200;
            }
            if(s.getPos(i, 0) == rival){
                heuristic -= 200;
            } else if (s.getPos(i, 0) == player){
                heuristic += 200;
            }
            if(s.getPos(board, i) == rival){
                heuristic -= 200;
            } else if (s.getPos(board, i) == player){
                heuristic += 200;
            }
            if(s.getPos(i, board) == rival){
                heuristic -= 200;
            } else if (s.getPos(i, board) == player){
                heuristic += 200;
            }
        }
        
        return heuristic;
    }
    
    private int getHeuristicHashTag(GameStatus s, CellType player) {
        int heuristic = 0;
        for (int i = 0; i <= board; ++i) {
            //horitzontal superior
            if (s.getPos(1, i).equals(player)) {
                heuristic -= 300;
            }
            
            //horitzontal inferior
            if (s.getPos(board-1, i).equals(player)) {
                heuristic -= 300;
            }
            
            //vertical esquerra
            if (s.getPos(i, 1).equals(player)) {
                heuristic -= 300;
            }
            
            //vertical dreta
            if (s.getPos(i, board-1).equals(player)) {
                heuristic -= 300;
            }
        }
        
        
        return heuristic;
    }
    public Point IDS (GameStatus s, CellType whoAmI, int Depth){
        
        board = s.getSize() -1;
        
        Point bestMov;
        
        bestMov = findNextBestMove(s,whoAmI, Depth);
        
        return bestMov;
    }
}
