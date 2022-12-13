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
    
    private String name;
    private GameStatus s;
    
    public Iniesta(String name){
        this.name = name;
    }
    
    /**
     * 
     * @param s Tauler i estat actual del joc.
     * @return el moviment que fa le jugador.
     */
    public Move move(GameStatus s) {
        
        ArrayList<Point> moves = s.getMoves();
        if(moves.isEmpty())
        {
            // no podem moure, el moviment (de tipus Point) es passa null.
            return new Move(null, 0L,0,  ); 
        } else {
           
        }
        return new Move ();
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
