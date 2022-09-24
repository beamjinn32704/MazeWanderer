/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindblown.mazewanderer;

/**
 *
 * @author beamj
 */
public class Loc implements Comparable<Loc>{
    public int x;
    public int y;

    public Loc(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    @Override
    public int compareTo(Loc o) {
        if(x == o.x && y == o.y){
            return 0;
        }
        if(x > o.x){
            return 1;
        } else if(x < o.x){
            return -1;
        }
        
        if(y > o.y){
            return 1;
        } else {
            return -1;
        }
    }
    
    public Loc left(){
        return new Loc(x - 1, y);
    }
    
    public Loc right(){
        return new Loc(x + 1, y);
    }
    
    public Loc up(){
        return new Loc(x, y + 1);
    }
    
    public Loc down(){
        return new Loc(x, y - 1);
    }
    
    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
