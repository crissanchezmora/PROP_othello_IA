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
    private final int _maxDepth = 4;
    /**
     * The search algorithm to use
     */
    private OthelloSearchAlgorithm _searchAlg;
    
    public CellType whoAmI;
    
    private boolean time = false;
    
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
        whoAmI = s.getCurrentPlayer();
        //System.out.println("Jugador actual" + s.getCurrentPlayer());
        
        ArrayList<Point> moves = s.getMoves();
        
        ArrayList<ArrayList<Point>> Stabilization = getStables(s);
        ArrayList<Point> IniestaStable = Stabilization.get(0);
        ArrayList<Point> RivalStable = Stabilization.get(1);
        
        if(moves.isEmpty()) s.skipTurn(); 
            Point mov = null;
            int i = 0;
            for (i = 1; i < 100000 && !time; i++){
                mov =  _searchAlg.IDS(s , whoAmI, i);
            }
            time = false;    
           System.out.println("/////////////////////////////////////////////////////////////////////////");
           System.out.println("MOVIMIENTO ELEGIDO: " + mov);
           System.out.println("/////////////////////////////////////////////////////////////////////////");
           Move move = new Move (mov, 5, i, SearchType.MINIMAX);

        //System.out.println("Tablero s despues de turno");
        //System.out.println(s.toString());
        
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

    private ArrayList<ArrayList<Point>> getStables(GameStatus s) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
