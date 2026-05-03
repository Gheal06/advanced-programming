package com.gheal.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GridGenerationThread extends Thread {
    Grid g;
    int preferredGenSpeed;
    int preferredMazeHeight;
    int preferredMazeWidth;
    public GridGenerationThread(Grid g, int preferredMazeHeight, int preferredMazeWidth, int preferredGenSpeed){
        this.g=g;
        this.preferredGenSpeed=preferredGenSpeed;
        this.preferredMazeHeight=preferredMazeHeight;
        this.preferredMazeWidth=preferredMazeWidth;
    }
    @Override
    public void run(){
        System.out.println("Generating maze..."); 
        int n=preferredMazeHeight, m=preferredMazeWidth;
        DSU dsu = new DSU(n*m);
        Random rng = new Random(System.currentTimeMillis());
        ArrayList<DSUEdge> edges = new ArrayList<DSUEdge>();
        ArrayList<DSUEdge> actualUpdates = new ArrayList<DSUEdge>();
        for(int u=0;u<n*m;u++){
            for(int d=0;d<2;d++){ // sunt suficiente doar doua directii, nu are sens sa introduc fiecare muchie de doua ori
                int x=u/m, y=u%m;
                int nx = x + Directions.dh[d], ny = y + Directions.dw[d];
                if(nx>=0 && nx<n && ny>=0 && ny<m){
                    double w = rng.nextDouble();
                    edges.add(new DSUEdge(u, nx*m+ny, w));
                }
            }
        }
        Collections.sort(edges);
        for(DSUEdge edg : edges){
            int u=edg.u, v=edg.v;
            if(dsu.union(u,v)){
                actualUpdates.add(edg);
                System.out.println(String.format("%d %d", u, v));
            }
        }
        for(int i=0;i<=actualUpdates.size();i++){
            if(i>0){
                g.setWallState(actualUpdates.get(i-1).u, actualUpdates.get(i-1).v, WallState.NONE);
            }
            if(i<actualUpdates.size()){
                g.setWallState(actualUpdates.get(i).u, actualUpdates.get(i).v, WallState.REMOVING);
            }
            try{
                Thread.sleep(preferredGenSpeed);
            }catch(InterruptedException ex){
                System.out.println("Thread sleep failed");
                return;
            }
        }
    }    
}
