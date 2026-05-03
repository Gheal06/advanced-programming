package com.gheal.java;

public enum WallState {
    NONE,
    REMOVING,
    ACTIVE;
    public static int toInt(WallState s){
        if(s==NONE) return 0;
        if(s==REMOVING) return 1;
        if(s==ACTIVE) return 2;
        return -1;
    } 
}
