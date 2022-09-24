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
public class Main {
    
    public static int MAZE_WIDTH = 40;
    public static int MAZE_HEIGHT = 30;
    
    public static int SPACE_SIDE_LENGTH = 9;
    
    public static void main(String[] args) {
        Maze maze = Maze.generateMaze();
        MazePlayer mazePlayer = new MazePlayer(maze);
        mazePlayer.setVisible(true);
//        Util.showNorm(mazePlayer, "Maze").setVisible(true);
    }
}
