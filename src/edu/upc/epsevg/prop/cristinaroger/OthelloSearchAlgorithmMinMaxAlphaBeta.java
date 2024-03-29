//package edu.upc.epsevg.prop.cristinaroger;
//import edu.upc.epsevg.prop.othello.CellType;
//import edu.upc.epsevg.prop.othello.GameStatus;
//import java.awt.Point;
//import java.util.ArrayList;
//
///**
// *
// *  Minimax amb poda alpha beta
// * @author cristina
// * @author roger
// */
//public class OthelloSearchAlgorithmMinMaxAlphaBeta extends OthelloSearchAlgorithm{
//    /**
//     * Counter for the nodes explored in the current exploration
//     */
//    int _current_node_exploration_count = 0;
//    
//    /**
//     * Counter for the leaves explored in the current exploration 
//     */
//    int _current_leaf_exploration_count = 0;
//    
//    /**
//     * Max value to use when finding a winning state
//     */
//    public final static int MAX_VAL = Integer.MAX_VALUE-1;  //  2147483647
//    
//    /**
//     * Min value to use when finding a losing state
//     */
//    public final static int MIN_VAL = Integer.MIN_VALUE+1;// -2147483647
//    
//    public static int board = 0;
//    
//    
//    /**
//     * Find the best column to put the next piece of given color of the given
//     * game t using MiniMax with alpha-beta pruning
//     * 
//     * @param s The state of game
//     * @param whoAmI
//     * @param maxDepth The maximum depth to conduct the search
//     * @return The best found movement using C4Heuristic
//     */
//    @Override
//    public Point findNextBestMove(GameStatus s, CellType whoAmI, int maxDepth, boolean timeout){
//        
//        if(maxDepth < 0){
//            
////            System.out.println("Entered IF: maxDepth < 0 | inside findNextBestMove");
////            System.out.println("returning Point -1 -1 to findNextBestMove");
//            
//            return new Point (-1,-1);
//        }
//        // Init search profiling
//        _current_node_exploration_count = 0; 
//        _current_leaf_exploration_count = 0;
//        long ms_begin = System.currentTimeMillis();
//        
//        // Do search
//        int bestHeuristic = Integer.MIN_VALUE;
//        int alpha = MIN_VAL;
//        int beta = MAX_VAL;
//        ArrayList <Point> moves = s.getMoves();
//        Point bestMove = new Point (-1, -1);
//        
////        System.out.println("bestHeuristic: " + bestHeuristic + ' ' +
////                            "bestMove: " + bestMove + ' ' +
////                            "alpha: " + alpha + ' ' +
////                            "beta: " + beta );
////        for (int j = 0; j < moves.size(); j++){
////            System.out.println("Moves: " +  moves.get(j));
////        }
//        
//        //Check valid movement
//        if (!moves.isEmpty()){
//            
////            System.out.println("Entered IF: moves not empty | inside findNextBestMove");
////            System.out.println("moves size: " + moves.size());
//            
//            for (int i = 0; i < moves.size(); i++){
//
//                //Create copy and move piece
//                GameStatus nextT = new GameStatus(s);
//                
//                //System.out.println("nextT(copy of s): ");
//                //System.out.println(nextT.toString());
////                System.out.println("movement to apply to nextT: " + moves.get(i));
//                
//                nextT.movePiece(moves.get(i));
//                
////                System.out.println("nextT (with movement): ");
////                System.out.println(nextT.toString());
//                
//                    //System.out.println("Now we will enter minmax with depth -1 and isMax false");
//
//                    int movHeur = minmax(nextT, whoAmI, maxDepth-1, false, alpha, beta, timeout);
//
//                    //System.out.println("Returned from minmax depth -1 and isMax false");
//
//                    if(bestHeuristic < movHeur || bestMove.equals(new Point(-1,-1))) {
//
////                        System.out.println("Entered IF: bestHeuristic<movHeur or bestMove = -1 -1 | inside findNextBestMove");
////                        System.out.println("1 movHeur: " + movHeur);
////                        System.out.println("1 BestMove: " + bestMove);
////                        System.out.println("1 BestHeur: " + bestHeuristic);
//
//                        bestHeuristic = movHeur;
//                        bestMove = moves.get(i);
//
////                        System.out.println("2 movHeur: " + movHeur);
////                        System.out.println("2 BestMove: " + bestMove);
////                        System.out.println("2 BestHeur: " + bestHeuristic);
//                    }   
//            }
//        }
//        
////        System.out.println("BestMove in findNextBestMove before IF: " + bestMove);
//        
//        if (bestMove.equals(new Point (-1, -1))){
//            
////            System.out.println("Entered IF: bestMove = -1 -1 | inside findNextBestMove");
////            System.out.println("No moves available. Skip Turn");
//            
//            s.skipTurn();
//        }
//        // End search profiling and output results
//        long ms_end = System.currentTimeMillis();
//        long incr_ms = (ms_end - ms_begin);
//        double incr_s = (double)incr_ms/1000.0;
//        
//        System.out.println("Selected movement " + bestMove + ' ' +
//                           "with heuristic " + bestHeuristic + ' ' +
//                           "for " + s.getCurrentPlayer() + ' ' +
//                           "having explored " + _current_leaf_exploration_count + " leaves " +
//                           "and " + _current_node_exploration_count + " nodes " +
//                           "in " + incr_ms  + "ms (" + incr_s + "s)");
//        System.out.println("//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////");
////       System.out.println("Returning bestMove: " + bestMove +" to findNextBestMovement depth: " + maxDepth);
//        
//        return bestMove;
//    }
//    
//    /**
//     * Find the worst (if !isMax) or best (if isMax) next heuristic given the 
//     * color of the given game t and depth maxDepth within the bounds [alpha, 
//     * beta]
//     * 
//     * @param t The state of game
//     * @param maxDepth The maximum depth to conduct the search
//     * @param isMax Flag to decide if the heuristic has to be maximized or 
//     * minimized
//     * @param alpha Lower bound to search
//     * @param beta Upper bound to search
//     * @return The worst found movement using C4Heuristic
//     */
//    private int minmax(GameStatus s, CellType whoAmI, int maxDepth, boolean isMax, int alpha, int beta, boolean timeout) {
//        
//        if (timeout){
//            System.out.println("Ha cambiado el timeout dentro");
//            return MIN_VAL;
//        }
//        
//        
//        _current_node_exploration_count++;
//        if(maxDepth <= 0 || s.isGameOver()) {
//            
////            System.out.println("Entered IF: maxDepth<=0 or gameOver | Inside minmax");
//            
//            _current_leaf_exploration_count++;
//            
////            System.out.println("returning getHeuristic(s) to minmax");
////            if (s.isGameOver()) return getHeuristic(s, whoAmI, true);
//
//            return getHeuristic(s, whoAmI);
//        }
//        
//        int heuristic = isMax ? Integer.MIN_VALUE : Integer.MAX_VALUE;
//        ArrayList <Point> moves = s.getMoves();
//        
////        System.out.println("heuristic:" + heuristic);
////        System.out.println("moves: ");
////        for (int i = 0; i < moves.size(); i++){
////            System.out.println(moves.get(i));
////        }
// 
//        if (!moves.isEmpty()){
//            
////            System.out.println("Entered IF: moves not empty | inside minmax");
////            System.out.println("moves size: " + moves.size());
//            
//            for (int i = 0; i < moves.size(); i++) {
//                
////                System.out.println("Entered FOR | inside minmax");
////                System.out.println("i = " + i);
//                
//                // Move
//
//                GameStatus nextT = new GameStatus(s);
//                //System.out.println("nextT (copy of s): ");
//                //System.out.println(nextT.toString());
//                
//                try { nextT.movePiece(moves.get(i)); } catch (Exception e) {}
//                
//                int movHeur;
////                if (nextT.isGameOver()){
////                    movHeur = getHeuristic(nextT, whoAmI, true);
////                }
////                else{
//                    movHeur = minmax(nextT, whoAmI ,maxDepth-1, !isMax, alpha, beta, timeout);
////                }
//                
////                System.out.println("NextT(movement applied): " + moves.get(i));
////                System.out.println(nextT.toString());
//
//                // Evaluate movement
//                
////                System.out.println("About to call minmax with -1 depht and !isMax");                
//                
////                System.out.println("returned minmax depht -1 and !isMax");
////                System.out.println("MovHeur: " + movHeur);
//
//                if(isMax) {
//                    
////                    System.out.println("Entered IF: isMax | inside minmax");
//                    
//                    // Update max heuristic
//                    heuristic = Math.max(heuristic, movHeur);
//                    
////                    System.out.println("Heuristic: " + heuristic);
//
//                    // Prune if we exceeded upper bound
//                    if (beta <= heuristic){
//                        
////                        System.out.println("Entered IF: beta <= heuristic | inside minmax");
////                        System.out.println("Breaking FOR. i = " + i);
//                        
//                        break;
//                    }
//                    // Update alpha (lower bound)
//                    alpha = Math.max(alpha, heuristic);
//                    
////                    System.out.println("Alpha: " + alpha);
//                    
//                } else {
//                    
////                    System.out.println("Entered ELSE: !isMax | inside minmax");
//                    
//                    // Update min heuristic
//                    heuristic = Math.min(heuristic, movHeur);
//                    
////                    System.out.println("Heuristic: " + heuristic);
//
//                    // Prune if we exceeded lower bound
//                    if (heuristic <= alpha){
//                        
////                        System.out.println("Entered IF: heuristic <= alpha | inside minmax");
////                        System.out.println("Breaking FOR. i = " + i);
//                        
//                        break;
//                    }
//
//                    // Update beta (upper bound)
//                    beta = Math.min(beta, heuristic);
//                    
////                    System.out.println("Beta: " + beta);
//                }  
//            }
//        }
//        else {
//            heuristic = getHeuristic(s, whoAmI);
//        }
////        System.out.println("Returning heuristic: " + heuristic +" to minmax");
//        return heuristic;
//    }
//
//    private int getHeuristic(GameStatus s, CellType whoAmI) {
//        
//        //Check if the game was won
//        if (s.isGameOver()){
//                if (s.GetWinner() == whoAmI) {
//                    return MAX_VAL;
//                }
//                
//                return MIN_VAL;
//            }
//        CellType rival  = CellType.opposite(s.getCurrentPlayer());
//        CellType player = s.getCurrentPlayer();
//        
//        int heuristic = 0;
//        
//        heuristic += getHeuristicCorner(s,rival,player);
//        heuristic -= s.getMoves().size()*50;    //num mov rival
//        heuristic += getHeuristicWalls(s,rival,player);
//        heuristic += s.getScore(player) - s.getScore(rival);
//        if (s.getScore(player) + s.getScore(rival) < 25) heuristic += getHeuristicHashTag(s, player);
//        //heuristic -= getHeuristicHashTag(s, rival); ??? Lo ponemos o no es necesario ???
//        //heuristic += stableChips(s, rival, player);
//      
//     return heuristic;
//    }   
//    
////    private int stableChips(GameStatus s, CellType rival, CellType player){
////        int heuristic = 0;
////        
////        for (int i = 0; i <= board; i++){
////            for (int j = 0; j <= board; j++){
////                
////            }
////        }
////        
////        return heuristic;
////    }
//    
//    private int getHeuristicCorner(GameStatus s, CellType rival, CellType player){
//        int heuristic = 0;
//        if (s.getPos(0, 0) == rival){
//            heuristic -= 700;
//        } else if( s.getPos(0, 0) == player){
//            heuristic += 700;
//        }
//        if (s.getPos(0, board) == rival){
//            heuristic -= 700;
//        } else if( s.getPos(0, board) == player){
//            heuristic += 700;
//        }
//        if (s.getPos(board, 0) == rival){
//            heuristic -= 700;
//        } else if( s.getPos(board, 0) == player){
//            heuristic += 700;
//        }
//        if (s.getPos(board, board) == rival){
//            heuristic -= 700;
//        } else if( s.getPos(board, board) == player){
//            heuristic += 700;
//        }
//        
//        return heuristic;
//    }
//
//    private int getHeuristicWalls(GameStatus s, CellType rival, CellType player){
//        int heuristic = 0;
//        
//        for (int i = 0; i <= board; i++){
//            if(s.getPos(0, i) == rival){
//                heuristic -= 200;
//            } else if (s.getPos(0, i) == player){
//                heuristic += 200;
//            }
//            if(s.getPos(i, 0) == rival){
//                heuristic -= 200;
//            } else if (s.getPos(i, 0) == player){
//                heuristic += 200;
//            }
//            if(s.getPos(board, i) == rival){
//                heuristic -= 200;
//            } else if (s.getPos(board, i) == player){
//                heuristic += 200;
//            }
//            if(s.getPos(i, board) == rival){
//                heuristic -= 200;
//            } else if (s.getPos(i, board) == player){
//                heuristic += 200;
//            }
//        }
//        
//        return heuristic;
//    }
//    
//    private int getHeuristicHashTag(GameStatus s, CellType player) {
//        int heuristic = 0;
//        for (int i = 0; i <= board; ++i) {
//            //horitzontal superior
//            if (s.getPos(1, i).equals(player)) {
//                heuristic -= 300;
//            }
//            
//            //horitzontal inferior
//            if (s.getPos(board-1, i).equals(player)) {
//                heuristic -= 300;
//            }
//            
//            //vertical esquerra
//            if (s.getPos(i, 1).equals(player)) {
//                heuristic -= 300;
//            }
//            
//            //vertical dreta
//            if (s.getPos(i, board-1).equals(player)) {
//                heuristic -= 300;
//            }
//        }
//        
//        
//        return heuristic;
//    }
// 
//    @Override
//    public Point IDS (GameStatus s, CellType whoAmI, int Depth, boolean timeout){
//        
//        board = s.getSize() -1;
//        
//        Point bestMov;
//        
//        bestMov = findNextBestMove(s,whoAmI, Depth, timeout);
//        
//        return bestMov;
//    }
//}
