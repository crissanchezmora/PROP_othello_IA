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
