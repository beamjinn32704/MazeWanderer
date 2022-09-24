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
public class Player {
    private Space space;
    
    private ArrayList<Space> visitedSpaces = new ArrayList<>();

    public Player() {
        
    }

    public Player(Space space) {
        //this.space = space;
        setSpace(space);
    }

    public void setSpace(Space space) {
        this.space = space;
        Util.add(visitedSpaces, space);
    }
    
    public boolean onSpace(Space s){
        return space.compareTo(s) == 0;
    }

    public Space getSpace() {
        return space;
    }
    
    public boolean visitedSpace(Space visitedSpace){
        return Util.has(visitedSpaces, visitedSpace);
    }

    public ArrayList<Space> getVisitedSpaces() {
        return visitedSpaces;
    }

    public void setVisitedSpaces(ArrayList<Space> visitedSpaces) {
        this.visitedSpaces = visitedSpaces;
    }
}
