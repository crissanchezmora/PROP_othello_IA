/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upc.epsevg.prop.cristinaroger;

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
    
    private String name;
    
    /**
     * The max depth to do the searches
     */
    private int _maxDepth = 4;
    /**
     * The search algorithm to use
     */
    private OthelloSearchAlgorithm _searchAlg;
    

    
    public Iniesta (String name) {
        this.name = name;
        this._searchAlg = new OthelloSearchAlgorithmMinMaxAlphaBeta();  
    }
    
    /**
     * @param s Tauler i estat actual del joc.
     * @return el moviment que fa le jugador.
     */
    @Override
    public Move move(GameStatus s) {
        
        System.out.println("Jugador actual" + s.getCurrentPlayer());
        
        ArrayList<Point> moves = s.getMoves();

        System.out.println("Tablero s antes de turno");
        System.out.println(s.toString());
        
        
        /*for (int j = 0; j < moves.size(); j++){
            System.out.println("MovimientoIniesta: " +  moves.get(j));
        }*/
        
        if(moves.isEmpty())
        {
            // no podem moure, el moviment (de tipus Point) es passa null.
            return new Move(null, 0L,0, SearchType.MINIMAX ); 
        } else {
           
           Point mov = _searchAlg.findNextBestMove(s ,s.getCurrentPlayer(), _maxDepth);
           System.out.println("Celda en s: " + s.getPos(mov));
           Move move = new Move (mov, 5, 6, SearchType.MINIMAX);

        System.out.println("Tablero s depsues de turno");
        System.out.println(s.toString());
        
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
        return this.name;
    }
    
    
}
