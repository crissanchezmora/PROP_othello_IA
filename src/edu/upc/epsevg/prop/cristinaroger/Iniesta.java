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
    
    private int board = 0;
    
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
        board = s.getSize() -1;
        //System.out.println("Jugador actual" + s.getCurrentPlayer());
//        
//        ArrayList<ArrayList<Point>> Stabilization = getStables(s);
//        ArrayList<Point> IniestaStable = Stabilization.get(0);
//        ArrayList<Point> RivalStable = Stabilization.get(1);
        
        ArrayList<Point> moves = s.getMoves();

        if(moves.isEmpty()) s.skipTurn(); 
            Point mov = null;
            int i = 6;
            for (i = 2; i < 100000 && !time; i+=2){
                mov =  _searchAlg.IDS(s , whoAmI, i);
            }
           time = false;
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
       
        CellType rival  = CellType.opposite(s.getCurrentPlayer());
        CellType player = s.getCurrentPlayer();
        
        ArrayList<Point> playerStable = getStable(player, s);
        ArrayList<Point> rivalStable = getStable(rival, s);
        
        ArrayList<ArrayList<Point>> stables = new ArrayList<>();
        stables.add(playerStable);
        stables.add(rivalStable);
        return stables;
    }
    
    private ArrayList<Point> getStable(CellType player, GameStatus s) {
        ArrayList<Point> playerStable = new ArrayList<>();
        playerStable.add(new Point(0 ,0));
        int cont = 0;
        
        //Cantonada superior esquerra
        int x = 0;
        int y = 0;
        boolean stable;
        if (s.getPos(x, y).equals(player)) {
            stable = true;
            for (y = 0; y <= board && stable; y++) {
                int j = 0;
                for (int i = y; i >= 0 && stable; i--) {
                    if (s.getPos(j, y).equals(player)) {
                        ++cont;
                        ++j;
                    } else {
                        stable = false;
                        playerStable.add(new Point(j, i));
                    }
                }
                if (!stable) {
                    stable = true;
                    j = 0;
                    for (int i = y; i >= 0 && stable; i--) {
                        if (s.getPos(i, j).equals(player)) {
                            ++cont;
                            ++j;
                        } else {
                            stable = false;
                            playerStable.add(new Point(i, j));
                        }
                    }
                }
            }
        }
        
        x = 0;
        y = board;
        if (s.getPos(x, y).equals(player)) {
            stable = true;
            while (stable) {
                
            }
        }
        
        x = board;
        y = 0;
        if (s.getPos(x, y).equals(player)) {
            stable = true;
            while (stable) {
                
            }
        }
        
        x = board;
        y = board;
        if (s.getPos(x, y).equals(player)) {
            stable = true;
            while (stable) {
                
            }
        }
        
        playerStable.get(0).move(0, cont);
        
        return playerStable;
    }
    
    
}
