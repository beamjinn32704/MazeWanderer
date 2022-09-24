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
public class Maze extends SpaceMap{
    
    public Maze(Space[] spaces, int width, int height) {
        super(spaces, width, height);
    }
    
    public static Maze generateMaze(){
        int width = Main.MAZE_WIDTH;
        int height = Main.MAZE_HEIGHT;
        Space[] mazeSpaces = new Space[width*height];
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                Space space = new Space(x, y);
                int index = x + y * width;
                mazeSpaces[index] = space;
            }
        }
        Maze maze = new Maze(mazeSpaces, width, height);
        maze.linkSpaces();
        
        return maze;
    }
    
}
