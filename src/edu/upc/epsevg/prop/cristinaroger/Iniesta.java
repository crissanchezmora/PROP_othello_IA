/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upc.epsevg.prop.cristinaroger;

import edu.upc.epsevg.prop.othello.CellType;
import edu.upc.epsevg.prop.othello.GameStatus;
import edu.upc.epsevg.prop.othello.IAuto;
import edu.upc.epsevg.prop.othello.IPlayer;
import edu.upc.epsevg.prop.othello.Move;
import edu.upc.epsevg.prop.othello.SearchType;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
/**
 * Jugador basic
 * @author cristina
 * @author roger
 */
public class Iniesta implements IPlayer, IAuto{
    
    private final String name;
    private GameStatus s;
    
    /**
     * Internal TaulerWithHeuristic to cache calculations from other turns
     */
    private TaulerWithHeuristic _t;
    
    /**
     * The max depth to do the searches
     */
    private int _maxDepth = 0;
    
    /**
     * The search algorithm to use
     */
    private OthelloSearchAlgorithm _searchAlg;
    
    public Iniesta (int maxDepth) {
        this.name = "Iniesta";
        this._maxDepth = maxDepth;
        this._searchAlg = new OthelloSearchAlgorithmMinMaxAlphaBeta();
        _t = new TaulerWithHeuristic(s, 0, 0);
    }
    
    /**
     * @param s Tauler i estat actual del joc.
     * @return el moviment que fa le jugador.
     */
    public Move move(GameStatus s) {
        System.out.println("Jugador actual" + s.getCurrentPlayer());
        System.out.println("Jugador actual2" + _t.getCurrentPlayer());
        ArrayList<Point> moves = s.getMoves();
        
        System.out.println(_t.toString());
        System.out.println(s.toString());
        
        for (int i = 0; i < s.getSize(); i++){
            for (int j = 0; j < s.getSize(); j++){
                CellType aa = s.getPos(j, i);
                if (aa == CellType.EMPTY){
                    System.out.print("0 ");
                } else if (aa == CellType.PLAYER1){
                    System.out.print("1 ");
                } else {
                    System.out.print("2 ");
                }
             
                //System.out.print(aa + " ");
            }
            System.out.println("");
        }
        
        /*for (int j = 0; j < moves.size(); j++){
            System.out.println("MovimientoIniesta: " +  moves.get(j));
        }*/
        
        if(moves.isEmpty())
        {
            // no podem moure, el moviment (de tipus Point) es passa null.
            return new Move(null, 0L,0, SearchType.MINIMAX ); 
        } else {
           
           Point mov = _searchAlg.findNextBestMove(_t, _t.getCurrentPlayer(), _maxDepth);
           Move move = new Move (mov, 5, 6, SearchType.MINIMAX);
           //_t.movePiece(mov);
           return move;
        }
        
    }
    
    /**
     * Ens avisa que hem de parar la cerca en curs perquè s'ha exhaurit el temps
     * de joc.
     * TODO: printear hasta dnd ha llegado en este caso.
     */
    public void timeout(){
        System.out.println("You are so slow...");
    }
    /**
     *  Retorna el nom del jugador que s'utlilitza per visualització a la UI
     * 
     * @return Nom del jugador
     */
    
    @Override
    public String getName(){
        return "Player("+ name + ")";
    }
    
    
}
