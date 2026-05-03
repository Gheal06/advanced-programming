package com.gheal.java;

import javax.swing.*;
import java.util.Collections;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

public class Grid extends JPanel{
    private int h, w, cellSize;
    private Cell[][] grid;
    private ArrayList<Integer> cellPath;
    public Grid(int h, int w, int newCellSize){
        super();
        this.h=h;
        this.w=w;
        this.cellSize=newCellSize;
        //super.setMinimumSize(new Dimension(w*cellSize, h*cellSize));
        super.setPreferredSize(new Dimension(w*cellSize, h*cellSize));
        grid = new Cell[h][w];
        cellPath = null;
        for(int i=0;i<h;i++) for(int j=0;j<w;j++) grid[i][j]=new Cell();
    }
    public Cell[][] getGrid(){
        return grid;
    }
    public ArrayList<Integer> getCellPath(){
        return cellPath;
    }
    public int getCellSize(){
        return cellSize;
    }
    public int getGridHeight(){
        return h;
    }
    public int getGridWidth(){
        return w;
    }
    boolean validatePath(){
        cellPath = new ArrayList<Integer>();
        Queue<Integer> q = new ArrayDeque<Integer>();
        boolean[][] visited = new boolean[h][w];
        Integer[] prv = new Integer[h*w];
        visited[0][0] = true;
        q.add(0);
        while(!q.isEmpty()){
            int u=q.poll();
            for(int d=0;d<4;d++){
                if(grid[u/w][u%w].getWallState(d)==WallState.NONE){
                    int nx=u/w+Directions.dh[d], ny=u%w+Directions.dw[d];
                    if(nx>=0 && nx<h && ny>=0 && ny<w && !visited[nx][ny]){
                        visited[nx][ny]=true;
                        prv[nx*w+ny]=u;
                        q.add(nx*w+ny);
                    }
                }
            }
        }
        if(!visited[h-1][w-1]) return false;
        int u=h*w-1;
        while(u!=0){
            cellPath.add(u);
            u=prv[u];
        }
        cellPath.add(0);
        Collections.reverse(cellPath);
        return true;
    }
    public void reinit(int cellSize, Cell[][] grid, ArrayList<Integer> cellPath){
        this.cellSize=cellSize;
        h=grid.length;
        w=grid[0].length;
        this.cellPath=cellPath;
        this.grid=grid;
        repaint();
    }
    public void resize(int h, int w, int newCellSize){
        this.h=h;
        this.w=w;
        this.cellSize=newCellSize;
        grid = new Cell[h][w];
        for(int i=0;i<h;i++) for(int j=0;j<w;j++) grid[i][j]=new Cell();
        //super.setMinimumSize(new Dimension(w*cellSize, h*cellSize));
        super.setPreferredSize(new Dimension(w*cellSize, h*cellSize));
        cellPath = null;
        super.repaint();
    }
    public void setCellWallState(int i, int j, int wallPos, WallState state){
        int ni = i + Directions.dh[wallPos];
        int nj = j + Directions.dw[wallPos];
        if(ni<0 || ni>=h || nj<0 || nj>=w) return;
        System.out.println(String.format("%d %d %d %d", i, j, ni, nj));
        grid[i][j].setWallState(wallPos, state);
        grid[ni][nj].setWallState(wallPos^2, state);
        cellPath = null;
        super.repaint();
    }
    public void toggleWall(int i1, int j1, int i2, int j2){
        if(i1<0 || i1>=h || j1<0 || j1>=w) return;
        if(i2<0 || i2>=h || j2<0 || j2>=w) return;
        for(int d=0;d<4;d++){
            if(i1+Directions.dh[d]==i2 && j1+Directions.dw[d]==j2){
                WallState newState = grid[i1][j1].getWallState(d)==WallState.NONE ? WallState.ACTIVE : WallState.NONE;
                setCellWallState(i1, j1, d, newState);
                return;
            }
        }
    }
    public void setWallState(int u, int v, WallState state){
        int i1 = u/w, j1 = u%w;
        int i2 = v/w, j2 = v%w;
        for(int d=0;d<4;d++){
            if(i1+Directions.dh[d]==i2 && j1+Directions.dw[d]==j2){
                setCellWallState(i1, j1, d, state);
                return;
            }
        }
    }
    public Cell getCell(int i, int j){
        return grid[i][j];
    }

    @Override
    protected void paintComponent(Graphics g) {
        final int strokeWidth=2;
        super.paintComponent(g);
        //Rectangle bound = g.getClipBounds();
        //System.out.println(String.format("paint %d %d %d %d",bound.x, bound.y, bound.height, bound.width));
        Graphics2D ctx=(Graphics2D)g.create();
        ctx.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        ctx.setColor(Color.LIGHT_GRAY);
        ctx.fillRect(0, 0, cellSize*w, cellSize*h);
        ctx.setColor(new Color(150, 150, 255));
        ctx.fillRect(cellSize*3/8, cellSize*3/8, cellSize/4, cellSize/4);
        ctx.setColor(new Color(200, 255, 200));
        ctx.fillRect(cellSize*(w-1), cellSize*(h-1), cellSize, cellSize);
        ctx.setColor(Color.BLACK);
        ctx.fillRect(0,0,cellSize*w,strokeWidth);
        ctx.fillRect(0,0,strokeWidth, cellSize*h);
        ctx.fillRect(0,cellSize*h-strokeWidth, cellSize*w, strokeWidth);
        ctx.fillRect(cellSize*w-strokeWidth,0,strokeWidth, cellSize*h);
        Color[] colors={Color.LIGHT_GRAY, Color.RED,Color.BLACK};
        for(int i=0;i<h;i++){
            for(int j=0;j<w;j++){
                ctx.setColor(colors[WallState.toInt(grid[i][j].getWallState(Directions.NORTH))]);
                ctx.fillRect(j*cellSize+strokeWidth,i*cellSize,cellSize-2*strokeWidth, strokeWidth);
                
                ctx.setColor(colors[WallState.toInt(grid[i][j].getWallState(Directions.EAST))]);
                ctx.fillRect((j+1)*cellSize-strokeWidth,i*cellSize+strokeWidth,strokeWidth, cellSize-2*strokeWidth);

                ctx.setColor(colors[WallState.toInt(grid[i][j].getWallState(Directions.SOUTH))]);
                ctx.fillRect(j*cellSize+strokeWidth,(i+1)*cellSize-strokeWidth,cellSize-2*strokeWidth, strokeWidth);

                ctx.setColor(colors[WallState.toInt(grid[i][j].getWallState(Directions.WEST))]);
                ctx.fillRect(j*cellSize,i*cellSize+strokeWidth,strokeWidth, cellSize-2*strokeWidth);
            }
        }
        for(int i=0;i+1<h;i++){
            for(int j=0;j+1<w;j++){
                int[] states={
                    WallState.toInt(grid[i][j].getWallState(Directions.SOUTH)),
                    WallState.toInt(grid[i][j].getWallState(Directions.EAST)),
                    WallState.toInt(grid[i+1][j+1].getWallState(Directions.NORTH)),
                    WallState.toInt(grid[i+1][j+1].getWallState(Directions.WEST))
                };
                int maxWallState=0;
                for(int k=0;k<4;k++) maxWallState=Math.max(maxWallState, states[k]);
                ctx.setColor(colors[maxWallState]);
                ctx.fillRect((j+1)*cellSize-strokeWidth,(i+1)*cellSize-strokeWidth,2*strokeWidth, 2*strokeWidth);
            }
        }
        if(cellPath!=null){
            ctx.setColor(Color.GREEN);
            for(int i=0;i+1<cellPath.size();i++){
                int u=cellPath.get(i), v=cellPath.get(i+1);
                int y1=Math.min(u/w, v/w), x1=Math.min(u%w, v%w);
                int y2=Math.max(u/w, v/w), x2=Math.max(u%w, v%w);
                ctx.fillRect(x1*cellSize+cellSize*2/5, y1*cellSize+cellSize*2/5, (x2-x1)*cellSize+cellSize/5, (y2-y1)*cellSize+cellSize/5);
            }
        }
        ctx.dispose();
    }
}
