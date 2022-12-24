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
 * Jugador
 * @author cristina
 * @author roger
 */
public class Iniesta implements IPlayer, IAuto{
    
    private final String name;
    
    public CellType whoAmI;
    
    private int board = 0;
    
    private boolean time = false;
    
    /**
     * Contador per els nodes a l'exploració actual
     */
    int _current_node_exploration_count = 0;
    
    /**
     * Contador per les fulles a l'exploració actual
     */
    int _current_leaf_exploration_count = 0;
    
    /**
     * Valor màxim per a un estat de victoria
     */
    public final static int MAX_VAL = Integer.MAX_VALUE-1;  //  2147483646
    
    /**
     * Valor mínim per a un estat de victoria
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

        //Implementació IDS
        for (i = 2; i < 100000 && !time; i+=2){
            mov =  findNextBestMove(s , whoAmI, i);
            if (time) i-=2; //No contem la última, perque no ha acabat

        }
        time = false;

        //Per a executar sense timeout, comentar el for i utilitzar la linia d'abaix
        //mov = findNextBestMove(s, whoAmI, 8);
        Move move = new Move (mov, _current_node_exploration_count, i-2, SearchType.MINIMAX);

//           System.out.println("Selected movement " + move + ' ' +
//                           "with heuristic " + heurLastInTime + ' ' +
//                           "for " + s.getCurrentPlayer() + ' ' +
//                           "having explored " + _current_leaf_exploration_count + " leaves " +
//                           "and " + _current_node_exploration_count + " nodes ");

        return move; 
    }
    
    /**
     * Ens avisa que hem de parar la cerca en curs perquè s'ha exhaurit el temps
     * de joc.
     */
    @Override
    public void timeout(){
        this.time = true;
    }
    
    /**
     * 
     * @return Nom del jugador
     */
    @Override
    public String getName(){
        return this.name;
    }
    
    /**
     * Troba les millors coordenades per a moure la seguent peça utilitzant
     * un minimax amb poda alpha beta, i IDS
     * 
     * @param s Estat de la partida
     * @param whoAmI
     * @param maxDepth Profunditat màxima a la que volem arribr
     * @return Millor moviment trobat amb la nostra heurística
     */
    public Point findNextBestMove(GameStatus s, CellType whoAmI, int maxDepth){
        
        if(maxDepth < 0){
            return new Point (-1,-1);
        }
        
        //Inicialitza els perfils de la cerca
        _current_node_exploration_count = 0; 
        _current_leaf_exploration_count = 0;
        long ms_begin = System.currentTimeMillis();
        
        //Fa la cerca
        int bestHeuristic = Integer.MIN_VALUE;
        int alpha = MIN_VAL;
        int beta = MAX_VAL;
        ArrayList <Point> moves = s.getMoves();
        Point bestMove = new Point (-1, -1);
        
        //Comprova si el moviment es vàlid
        if (!moves.isEmpty()){
            
            for (int i = 0; i < moves.size(); i++){

                //Crea una copia de s i mou la peça
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
        // Acaba la cerca i imprimeix informació del resultat
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
        if(time) return pointLastInTime;
        pointLastInTime = bestMove;
        
        return bestMove;
    }
    
    /**
     * Troba la pitjor (if !isMax) o millor (if isMax) seguent heurística per a 
     * jugador actual de s a la profunditat maxDepth dintre dels límits alpha beta
     * 
     * @param s Estat del tauler
     * @param maxDepth Profunditat màxima de la cerca actual
     * @param isMax Boleà per saber si estem en un max o en un min
     * @param alpha Límit inferior de la cerca
     * @param beta Límit superior de la cerca
     * @return La millor herística trobada
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

                GameStatus nextT = new GameStatus(s);
                
                try { nextT.movePiece(moves.get(i)); } catch (Exception e) {}
                
                int movHeur;
                    movHeur = minmax(nextT, whoAmI ,maxDepth-1, !isMax, alpha, beta);

                // Evalua el moviment
                if(isMax) {
                    
                    // Actualitza la heurística més alta
                    heuristic = Math.max(heuristic, movHeur);

                    // Poda si excedim el límit superior
                    if (beta <= heuristic){
                        
                        break;
                    }
                    
                    // Actualitza alpha
                    alpha = Math.max(alpha, heuristic);
                    
                } else {
                    
                    // Actualitza la heurística més baixa
                    heuristic = Math.min(heuristic, movHeur);
                    
                    // Poda si excedim el límit inferior
                    if (heuristic <= alpha){
                                               
                        break;
                    }

                    // Actualitza beta 
                    beta = Math.min(beta, heuristic);
                }  
            }
        }
        else {
            heuristic = getHeuristic(s, whoAmI);
        }
        
        return heuristic;
    }

    /**
     * Calcula la heurística
     * 
     * @param s Estat del tauler
     * @param whoAmI Guarda si iniesta es P1 o P2
     * @return Millor heurística trobada
     */
    private int getHeuristic(GameStatus s, CellType whoAmI) {
        
        //Comprova si ha acabat el joc
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
        
        if (time) return heurLastInTime;
        
        return heuristic;
    }   
    
    /**
     * Suma o resta heurística depenent de quantes cantonades hi ha amb una fitxa
     * i depenent de quin jugador es la fitxa
     * 
     * @param s Estat del tauler
     * @param rival jugador rival actual
     * @param player jugador actual
     * @return Heurística respecte a les cantonades
     */
    private int getHeuristicCorner(GameStatus s, CellType rival, CellType player){
        int heuristic = 0;
        if (s.getPos(0, 0) == rival){
            heuristic -= 800;
        } else if( s.getPos(0, 0) == player){
            heuristic += 800;
        }
        if (s.getPos(0, board) == rival){
            heuristic -= 800;
        } else if( s.getPos(0, board) == player){
            heuristic += 800;
        }
        if (s.getPos(board, 0) == rival){
            heuristic -= 800;
        } else if( s.getPos(board, 0) == player){
            heuristic += 800;
        }
        if (s.getPos(board, board) == rival){
            heuristic -= 800;
        } else if( s.getPos(board, board) == player){
            heuristic += 800;
        }
        
        return heuristic;
    }

      /**
     * Suma o resta heurística depenent de quantes fitxes hi ha a les parets
     * i depenent de quin jugador són les fitxes
     * 
     * @param s Estat del tauler
     * @param rival jugador rival actual
     * @param player jugador actual
     * @return Heurística respecte a les parets
     */
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
    
      /**
     * Hi ha certes posicions que no són recomanables tenir en un early game,
     * resta heurística segons si tenim fitxes en alguna d'aquestes posicions.
     * El dibuix que fan les posicions no recomanables recorden a un Hashtag,
     * d'aquí el nom de la funció.
     * 
     * @param s Estat del tauler
     * @param player jugador actual
     * @return Heurística respecte al diseny HashTag
     */
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

}
