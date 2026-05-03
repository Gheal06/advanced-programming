package com.gheal.java;
import java.util.ArrayList;
public class SaveState {
    public int preferredCellSize;
    public int preferredMazeHeight;
    public int preferredMazeWidth;
    public int preferredGenSpeed;
    public Cell[][] grid;
    public ArrayList<Integer> cellPath;
    public SaveState(){}
    public SaveState(int preferredCellSize, int preferredMazeHeight, int preferredMazeWidth, int preferredGenSpeed, Cell[][] grid, ArrayList<Integer> cellPath){
        this.preferredCellSize=preferredCellSize;
        this.preferredMazeHeight=preferredMazeHeight;
        this.preferredMazeWidth=preferredMazeWidth;
        this.preferredGenSpeed=preferredGenSpeed;
        this.grid=grid;
        this.cellPath=cellPath;
    }
}
