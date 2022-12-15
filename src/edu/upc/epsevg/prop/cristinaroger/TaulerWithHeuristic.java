/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upc.epsevg.prop.cristinaroger;
import edu.upc.epsevg.prop.othello.Board;
import edu.upc.epsevg.prop.othello.CellType;
import edu.upc.epsevg.prop.othello.GameStatus;
import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
/**
 *
 * @author cristina
 * @author roger
 */
public class TaulerWithHeuristic extends GameStatus{
    
    /**
     * Max value to use when finding a winning state
     */
    public final static int MAX_VAL = Integer.MAX_VALUE;  //  2147483647
    
    /**
     * Min value to use when finding a losing state
     */
    public final static int MIN_VAL = Integer.MIN_VALUE+1;// -2147483647

    /**
     * Internal wrapped GameStatus
     */
    private GameStatus _t;
    
    /**
     * Last applied movement
     */
    private Point _lastMovement;
    
    /**
     * Color of the last applied movement
     */
    private int _lastColor;
    
    /**
     * Number of movements applied
     */
    private int _numMovements;
       
    public TaulerWithHeuristic(GameStatus _t, Point _lastMovement, int _lastColor, int _numMovements) {
        this._t = _t;
        this._lastMovement = _lastMovement;
        this._lastColor = _lastColor;
        this._numMovements = _numMovements;
    }
    
    public int getHeuristic(CellType color){
        //Check if the game was won
        if (this.isGameOver()){
            if (this.GetWinner() == this.getCurrentPlayer()) return MAX_VAL;
        }
        
        CellType rival  = CellType.opposite(this.getCurrentPlayer());
        CellType player = this.getCurrentPlayer();
        
     return this.getScore(player) - this.getScore(rival);
    }
}
