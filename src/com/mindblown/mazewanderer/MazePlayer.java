/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.mindblown.mazewanderer;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

/**
 *
 * @author beamj
 */
public class MazePlayer extends javax.swing.JFrame {
    private Maze maze;
    private Player player;
    
    /**
     *
     * @param maze
     */
    public MazePlayer(Maze maze) {
        initComponents();
        this.maze = maze;
        Space startSpace = maze.getSpace(0);
        player = new Player(startSpace);
        setVisible(true);
    }
    
    public Maze getMaze() {
        return maze;
    }
    
    public Player getPlayer() {
        return player;
    }
    
    
//    public void play(){
//        Space playerSpace = player.getSpace();
//        Loc playerLoc = playerSpace.loc;
//        boolean left = maze.left(playerSpace);
//        boolean right = maze.right(playerSpace);
//        boolean down = maze.down(playerSpace);
//        boolean up = maze.up(playerSpace);
//        boolean end = maze.isEnd(playerSpace);
//        System.out.println(status(playerLoc, left, right, up, down, end));
//        String response = Util.strip(in.nextLine()).toLowerCase();
//        if(response.equals("l") || response.equals("left")){
//            if(left){
//                //LEFT
//                move(0);
//            } else {
//                System.out.println("Sorry! You can't go left!");
//            }
//        } else if(response.equals("r") || response.equals("right")){
//            if(right){
//                //RIGHT
//                move(1);
//            } else {
//                System.out.println("Sorry! You can't go right!");
//            }
//        } else if(response.equals("u") || response.equals("up")){
//            if(up){
//                //UP
//                move(2);
//            } else {
//                System.out.println("Sorry! You can't go up!");
//            }
//        } else if(response.equals("d") || response.equals("down")){
//            if(down){
//                //DOWN
//                move(3);
//            } else {
//                System.out.println("Sorry! You can't go down!");
//            }
//        } else if(response.equals("f") || response.equals("finish")){
//            if(end){
//                //END
//                endGame();
//            } else {
//                System.out.println("Sorry! You can't finish!");
//            }
//        } else {
//
//        }
//    }
    
    private void endGame(){
        System.out.println("Congratulations! You finished the game!");
    }
    
    /**
     *
     * @param mode This tells what action to do.
     * 0 is left.
     * 1 is right.
     * 2 is up.
     * 3 is down.
     * 7 is end.
     * @return whether to continue playing;
     */
    private void move(int mode){
        Space playerSpace = player.getSpace();
        Loc playerLoc = playerSpace.loc;
        switch (mode) {
            case 0:
                if(maze.left(playerSpace)){
                    player.setSpace(maze.findSpace(playerLoc.left()));
                    repaint();
                }
                break;
            case 1:
                if(maze.right(playerSpace)){
                    player.setSpace(maze.findSpace(playerLoc.right()));
                    repaint();
                }
                break;
            case 2:
                if(maze.up(playerSpace)){
                    player.setSpace(maze.findSpace(playerLoc.up()));
                    repaint();
                }
                break;
            case 3:
                if(maze.down(playerSpace)){
                    player.setSpace(maze.findSpace(playerLoc.down()));
                    repaint();
                }
                break;
            case 7:
                if(maze.isEnd(playerSpace)){
                    endGame();
                }
                break;
            default:
                break;
        }
    }
    
    private String status(Loc loc, boolean left, boolean right, boolean up, boolean down, boolean end){
        String message = "You are at " + loc.toString() + ".";
        if(end){
            message += "\nYou can finish the game! (f)";
        }
        
        if(left){
            message += "\nYou can go left to " + loc.left() + " (l)";
        }
        
        if(right){
            message += "\nYou can go right to " + loc.right() + " (r)";
        }
        
        if(up){
            message += "\nYou can go up to " + loc.up() + " (u)";
        }
        
        if(down){
            message += "\nYou can go down to " + loc.down() + " (d)";
        }
        return message;
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g); //To change body of generated methods, choose Tools | Templates.
        
        int startX = 10;
        int startY = 15;
        
        Space[] spaces = maze.getSpaces();
        Graphics2D g2d = (Graphics2D)g;
        int width = maze.getWidth();
        int height = maze.getHeight();
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                int ind = x + y * width;
                Space space = spaces[ind];
                if(player.visitedSpace(space)){
                    Space[] possibleNeigbors = maze.possibleNeighbors(space);
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
                    
                    int picX = x + startX;
                    int picY = height - y + startY;
                    
                    int leftX = picX * Main.SPACE_SIDE_LENGTH;
                    int rightX = (picX + 1) * Main.SPACE_SIDE_LENGTH;
                    
                    int topY = picY * Main.SPACE_SIDE_LENGTH;
                    int bottomY = (picY + 1) * Main.SPACE_SIDE_LENGTH;
                    
                    Loc topLeft = new Loc(leftX, topY);
                    Loc topRight = new Loc(rightX, topY);
                    Loc bottomLeft = new Loc(leftX, bottomY);
                    Loc bottomRight = new Loc(rightX, bottomY);
                    
                    if(player.onSpace(space)){
                        g2d.drawOval(topLeft.x, topLeft.y, Main.SPACE_SIDE_LENGTH, Main.SPACE_SIDE_LENGTH);
                    }
                    
                    if(upConnected){
                        
                    } else {
                        g2d.drawLine(topLeft.x, topLeft.y, topRight.x, topRight.y);
                    }
                    
                    if(downConnected){
                        
                    } else {
                        g2d.drawLine(bottomLeft.x, bottomLeft.y, bottomRight.x, bottomRight.y);
                    }
                    
                    if(leftConnected){
                        
                    } else {
                        g2d.drawLine(topLeft.x, topLeft.y, bottomLeft.x, bottomLeft.y);
                    }
                    
                    if(rightConnected){
                        
                    } else {
                        g2d.drawLine(topRight.x, topRight.y, bottomRight.x, bottomRight.y);
                    }
                    
                    if(maze.isEnd(space)){
                        g2d.drawLine(topLeft.x, topLeft.y, bottomRight.x, bottomRight.y);
                        g2d.drawLine(topRight.x, topRight.y, bottomLeft.x, bottomLeft.y);
                    }
                }
            }
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 486, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 395, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        int keyCode = evt.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                move(0);
                break;
            case KeyEvent.VK_RIGHT:
                move(1);
                break;
            case KeyEvent.VK_UP:
                move(2);
                break;
            case KeyEvent.VK_DOWN:
                move(3);
                break;
            case KeyEvent.VK_ENTER:
                move(7);
            default:
                break;
        }
    }//GEN-LAST:event_formKeyPressed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
