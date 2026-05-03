package com.gheal.java;
public class Cell{
    private WallState[] wallStates = new WallState[4];
    Cell(){
        for(int i=0;i<4;i++)
            wallStates[i]=WallState.ACTIVE;
    }
    public WallState[] getWallStates(){
        return wallStates;
    }
    public WallState getWallState(int pos){
        return wallStates[pos];
    }
    public void setWallState(int pos, WallState newState){
        wallStates[pos]=newState;
    }
};
