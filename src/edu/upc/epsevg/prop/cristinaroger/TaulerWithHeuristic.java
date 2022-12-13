/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upc.epsevg.prop.cristinaroger;

/**
 *
 * @author cristina
 * @author roger
 */
public class TaulerWithHeuristic {
    
    /**
     * Max value to use when finding a winning state
     */
    public final static int MAX_VAL = Integer.MAX_VALUE;  //  2147483647
    
    /**
     * Min value to use when finding a losing state
     */
    public final static int MIN_VAL = Integer.MIN_VALUE+1;// -2147483647
    
    /**
     * Auxiliary enum to link a player with its color
     */
    private static enum Player {
        NONE(0),
        P1(1),
        P2(-1);

        /**
         * Color of the player
         */
        private final int _color;

        private Player(int color) {
            _color = color;
        }

        /**
         * Get the player associated with color
         * 
         * @param color The color
         * @return The player associated with color
         */
        public static Player get(int color) {
            if(color ==  1) return P1;
            if(color == -1) return P2;
            return NONE;
        }

        /**
         * Get the color associated with the player
         * 
         * @return The color associated with the player
         */
        public int getColor() {
            return _color;
        }
         @Override
        public String toString() {
            return "Player{" + this.name() + '}';
        }

        /**
         * Get rival of the player
         * 
         * @return The rival of the player
         */
        private Player rival() {
            if(_color ==  1) return P2;
            if(_color == -1) return P1;
            return NONE;
        }
    }
    
    /**
     * Internal wrapped Tauler
     */
    private final Tauler _t;
    
}
