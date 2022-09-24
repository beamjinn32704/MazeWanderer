/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindblown.mazewanderer;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author beamj
 */
public class SpaceMap {
    private Space[] spaces;
    private int width;
    private int height;
    
    private Space endSpace;

    public SpaceMap(Space[] spaces, int width, int height) {
        this.spaces = spaces;
        this.width = width;
        this.height = height;
        endSpace = spaces[(width - 1) + (height - 1) * width];
    }
    
    public boolean isEnd(Space s){
        return endSpace.compareTo(s) == 0;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Space[] getSpaces() {
        return spaces;
    }
    
    private boolean validLoc(Loc loc){
        return loc.x >= 0 && loc.x < width && loc.y >= 0 && loc.y < height;
    }
    
    public Space findSpace(Loc loc){
        if(!validLoc(loc)){
            return null;
        }
        
        int x = loc.x;
        int y = loc.y;
        
        int index = x + y * width;
        return spaces[index];
    }
    
    public void linkSpaces(){
        connectSpaces();
        Random gen = new Random();
        for(int i = 0; i < spaces.length; i++){
            Space space = spaces[i];
            float chance = gen.nextFloat();
            if(chance <= 0.45){
                disconnectSpaceFromOthers(space, 2);
            } else {
                disconnectSpaceFromOthers(space, 3);
            }
        }
    }
    
    private void disconnectSpaceFromOthers(Space space, int maxConnected){
        ArrayList<Space> connectedSpaces = space.getConnectedSpaces();
        int numOfConnectedSpaces = connectedSpaces.size();
        
        if(numOfConnectedSpaces <= maxConnected){
            return;
        }
        Random gen = new Random();
        int spacesToDisconnect = numOfConnectedSpaces - maxConnected;
        ArrayList<Space> spacesThatCanDisconnect = new ArrayList<>();
        spacesThatCanDisconnect.addAll(connectedSpaces);
        for(int i = 0; i < spacesToDisconnect; i++){
            boolean go = true;
            Space spaceToDisconnect;
            while(go){
                if(spacesThatCanDisconnect.isEmpty()){
                    System.out.println("No other spaces can be disconnected! "
                            + "SpaceMap disconnectSpaceFromOthers method " + (numOfConnectedSpaces - i) + " instead of " + maxConnected);
                    return;
                }
                int indToDisconnect = gen.nextInt(spacesThatCanDisconnect.size());
                //SEE IF THE SPACE TO BE REMOVED CAN BE DISCONNECTED. (AFTER
                //DISCONNECTION STILL HAS AT LEAST ONE CONNECTED SPACE)
                //IF SO, DO A TRIAL OF DISCONNECTING THEM AND SEE IF ALL SPACES
                //ARE CONNECTED
                spaceToDisconnect = spacesThatCanDisconnect.get(indToDisconnect);
                int spaceToDisconnectConnectedNum = spaceToDisconnect.numOfConnectedSpaces();
                if(spaceToDisconnectConnectedNum == 1){
                    
                } else {
                    //TRIAL
                    space.disconnectSpace(spaceToDisconnect);
                    spaceToDisconnect.disconnectSpace(space);
                    go = !allSpacesConnected();
                    if(go){
                        space.connectSpace(spaceToDisconnect);
                        spaceToDisconnect.connectSpace(space);
                    }
                }
                Util.remove(spacesThatCanDisconnect, spaceToDisconnect);
            }
        }
    }
    
    private boolean allSpacesConnected(){
        ArrayList<Space> countedSpaces = new ArrayList<>();
        ArrayList<Space> spacesToCheck = new ArrayList<>();
        ArrayList<Space> spacesChecked = new ArrayList<>();
        if(spaces.length == 0){
            return true;
        }
        Space firstSpace = spaces[0];
        Util.add(spacesToCheck, firstSpace);
        countedSpaces.add(firstSpace);
        while(!spacesToCheck.isEmpty()){
            Space spaceToCheck = spacesToCheck.remove(0);
            Util.add(spacesChecked, spaceToCheck);
            ArrayList<Space> connectedSpaces = spaceToCheck.getConnectedSpaces();
            Util.addAll(countedSpaces, connectedSpaces);
            for(Space s : connectedSpaces){
                if(!Util.has(spacesChecked, s)){
                    Util.add(spacesToCheck, s);
                }
            }
        }
        return countedSpaces.size() == spaces.length;
    }
    
    private void connectSpaces(){
        ArrayList<Space> spacesToLink = new ArrayList<>();
        for(int i = 0; i < spaces.length; i++){
            Space space = spaces[i];
            if(space != null){
                Util.add(spacesToLink, space);
            } else {
                System.out.println("Index " + i + " was null!! (Space Map Method Connect Spaces)");
            }
        }
        
        ArrayList<Space> linkedSpacesStorer = new ArrayList<>();
        
        while(!spacesToLink.isEmpty()){
            Space spaceToLink = spacesToLink.remove(0);
            Space[] linkedSpaces = linkSpace(spaceToLink);
            Util.add(linkedSpacesStorer, spaceToLink);
            for(int i = 0; i < linkedSpaces.length; i++){
                Space linkedSpace = linkedSpaces[i];
                if(linkedSpace != null && !Util.has(linkedSpacesStorer, linkedSpace)){
                    Util.add(spacesToLink, linkedSpace);
                }
            }
        }
    }
    
    /**
     *
     * @param space
     * @return an array of spaces of possible neighbors
     * to the space param. The array of spaces is in the 
     * order of left, down, up, and right.
     */
    public Space[] possibleNeighbors(Space space){
        Space[] spaceNeighbors = new Space[4];
        int x = space.loc.x;
        int y = space.loc.y;
        
        Loc[] neighbors = new Loc[]{
            new Loc(x - 1, y), new Loc(x, y - 1),
            new Loc(x, y + 1), new Loc(x + 1, y)
        };
        
        for(int i = 0; i < neighbors.length; i++){
            Loc loc = neighbors[i];
            Space spaceNext = findSpace(loc);
            spaceNeighbors[i] = spaceNext;
        }
        return spaceNeighbors;
    }
    
    private Space[] linkSpace(Space space){
        Space[] spacesLinked = possibleNeighbors(space);
        
        for(int i = 0; i < spacesLinked.length; i++){
            Space s = spacesLinked[i];
            if(s != null){
                space.connectSpace(s);
                s.connectSpace(space);
            }
        }
        return spacesLinked;
    }
    
    public boolean left(Space space){
        Loc left = space.loc.left();
        return validLoc(left) && space.isConnectedTo(findSpace(left));
    }
    
    public boolean right(Space space){
        Loc right = space.loc.right();
        return validLoc(right) && space.isConnectedTo(findSpace(right));
    }
    
    public boolean up(Space space){
        Loc up = space.loc.up();
        return validLoc(up) && space.isConnectedTo(findSpace(up));
    }
    
    public boolean down(Space space){
        Loc down = space.loc.down();
        return validLoc(down) && space.isConnectedTo(findSpace(down));
    }
    
    public Space getSpace(int ind){
        return getSpaces()[ind];
    }

    @Override
    public String toString() {
        String print = "";
        for(int y = height - 1; y >= 0; y--){
            String upString = "";
            String normString = "";
            String downString = "";
            for(int x = 0; x < width; x++){
                int ind = x + y * width;
                Space space = spaces[ind];
                Space[] possibleNeigbors = possibleNeighbors(space);
                //0 = left
                //1 = down
                //2 = up
                //3 = right
                Space left = possibleNeigbors[0];
                Space down = possibleNeigbors[1];
                Space up = possibleNeigbors[2];
                Space right = possibleNeigbors[3];
                
                boolean leftConnected = left != null && space.isConnectedTo(left);
                boolean downConnected = down != null && space.isConnectedTo(down);
                boolean upConnected = up != null && space.isConnectedTo(up);
                boolean rightConnected = right != null && space.isConnectedTo(right);
                
                if(upConnected){
                    upString += "    ";
                } else {
                    upString += "----";
                }
                
                if(downConnected){
                    downString += "    ";
                } else {
                    downString += "----";
                }
                
                if(leftConnected){
                    normString += "   ";
                } else {
                    normString += "|  ";
                }
                
                if(rightConnected){
                    normString += " ";
                } else {
                    normString += "|";
                }
            }
            print += upString + "\n" + normString + "\n" + downString + "\n";
        }
//        System.out.println(print);
        return print;
    }
}