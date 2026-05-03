package com.gheal.java;
import java.util.Random;

import com.gheal.java.WallState;
public class BunnyThread extends Thread {
    public Bunny info;
    private SharedMemory shmem;
    private Grid g;
    public BunnyThread(SharedMemory shmem, Grid g, Bunny info){
        this.shmem=shmem;
        this.g=g;
        this.info=info;
    }
    @Override
    public void run(){
        System.out.println(String.format("Bunny thread started running %d", info.pos));
        Random rng=new Random(System.currentTimeMillis());
        while(true){
            try{
                Thread.sleep(info.movementTime);
            }catch(InterruptedException ex){
                System.out.println("Failed sleep");
                return;
            }
            if(shmem.isFinished()) return;
            int i1=info.pos/g.getGridWidth(), j1=info.pos%g.getGridWidth();
            boolean found = false;
            synchronized(shmem){
                if(shmem.isFinished()) return;
                for(int i=0;!found && i<20;i++){
                    int d=rng.nextInt(4);
                    if(g.canMove(info.pos, d)){
                        int i2=i1+Directions.dh[d], j2=j1+Directions.dw[d];
                        if(shmem.getOccupants()[i2][j2]==Occupant.NOTHING){
                            System.out.println(String.format("%d %d %d %d", i1, j1, i2, j2));
                            System.out.println(String.format("%b", g.getGrid()[i1][j1].getWallState(d)==WallState.NONE));
                            info.pos=i2*g.getGridWidth()+j2;
                            shmem.moveTo(i1, j1, i2, j2);
                            found = true;
                        }
                    }
                }
            }
        }
    }
}
