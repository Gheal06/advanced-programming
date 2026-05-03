package com.gheal.java;

public class RobotThread extends Thread {
    public Robot info;
    private Grid g;
    private SharedMemory shmem;
    public RobotThread(SharedMemory shmem, Grid g, Robot info){
        this.shmem=shmem;
        this.g=g;
        this.info=info;
    }
    @Override
    public void run(){
        while(true){
            try{
                Thread.sleep(info.movementTime);
            }catch(InterruptedException ex){
                System.out.println("Failed sleep");
                return;
            }
            if(shmem.isFinished()) return;
            /*
            synchronized(shmem){
                if(shmem.isFinished()) return;
                if(this.info.target<0){ /// pe euler tour
                    this.info.target--;
                    if(this.info.target==-g.getGridHeight()*g.getGridWidth()) this.info.target=-1;
                }
            }*/
        }
    }
}
