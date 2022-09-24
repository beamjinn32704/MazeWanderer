/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindblown.mazewanderer;

import java.util.ArrayList;

/**
 *
 * @author beamj
 */
public class Space implements Comparable<Space>{
    
    Loc loc;
    
    ArrayList<Space> connectedSpaces;
    
    public Space(int x, int y){
        loc = new Loc(x, y);
        connectedSpaces = new ArrayList<>();
    }

    public Space(int x, int y, ArrayList<Space> connectedSpaces) {
        loc = new Loc(x, y);
        this.connectedSpaces = connectedSpaces;
    }
    
    public void connectSpace(Space space){
        Util.add(connectedSpaces, space);
    }
    
    public void disconnectSpace(Space space){
        Util.remove(connectedSpaces, space);
    }
    
    public int numOfConnectedSpaces(){
        return connectedSpaces.size();
    }
    
    public ArrayList<Space> getConnectedSpaces() {
        return connectedSpaces;
    }
    
    public boolean isConnectedTo(Space s){
        return Util.has(connectedSpaces, s);
    }
        
    
    @Override
    public String toString() {
        return loc.toString();
    }
    
    @Override
    public int compareTo(Space o) {
        return loc.compareTo(o.loc);
    }
    
}
