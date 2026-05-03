package com.gheal.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;



public class DaemonThread extends Thread {
    SharedMemory shmem;
    Grid g;
    long maxTime;
    public DaemonThread(SharedMemory shmem, Grid g, long maxTime){
        this.shmem=shmem;
        this.g=g;
        this.maxTime=maxTime;
    }
    @Override
    public void run(){
        while(!g.generated){
            try{
                Thread.sleep(200);
            }catch(InterruptedException ex){
                System.out.println("Failed sleep");
                return;
            }
        }
        shmem.start();
        long start=System.currentTimeMillis();
        while(true){
            try{
                Thread.sleep(10);
            }catch(InterruptedException ex){
                System.out.println("Failed sleep");
                return;
            }
            g.repaint();
            if(shmem.isFinished()){
                g.repaint();
                return;
            }
            long currentTime=System.currentTimeMillis();
            System.out.println(String.format("Time elapsed: %.3f", (currentTime-start)*1e-3));
            if(currentTime-start>maxTime){
                shmem.finish();
                g.repaint();
                return;
            }
        }
    }    
}
