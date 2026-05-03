package com.gheal.java;

import java.util.ArrayList;
import java.util.TreeSet;

import com.gheal.java.Occupant;

import java.util.ArrayDeque;


public class SharedMemory {
    private int h;
    private int w;
    private int remainingBunnies;
    private boolean finished = false;
    private boolean[][] claimed;
    private int[][] seen;
    private Occupant[][] occupants;
    public ArrayList<BunnyThread> bunnyThreads;
    public ArrayList<RobotThread> robotThreads;
    public TreeSet<Integer> bunnySightings;
    private ArrayDeque<Integer>[] robotStrategies;
    public SharedMemory(int h, int w, int bunnies, int robots){
        this.h=h;
        this.w=w;
        finished = false;
        seen = new int[h][w];
        occupants = new Occupant[h][w];
        for(int i=0;i<h;i++) for(int j=0;j<w;j++) occupants[i][j]=Occupant.NOTHING;
        remainingBunnies = bunnies;
        bunnySightings = new TreeSet<Integer>();
        bunnyThreads = new ArrayList<BunnyThread>();
        robotThreads = new ArrayList<RobotThread>();
    }
    public void reset(int h, int w, int bunnies, int robots){
        this.h=h;
        this.w=w;
        finished = false;
        seen = new int[h][w];
        occupants = new Occupant[h][w];
        for(int i=0;i<h;i++) for(int j=0;j<w;j++) occupants[i][j]=Occupant.NOTHING;
        remainingBunnies = bunnies;
        bunnySightings = new TreeSet<Integer>();
        bunnyThreads = new ArrayList<BunnyThread>();
        robotThreads = new ArrayList<RobotThread>();
    }
    public Occupant[][] getOccupants(){
        return occupants;
    }

    /// fiecare robot va urmari maxim un bunny sighting a.i. suma distantelor sa fie minima
    /// o euristica destul de primitiva si implementata cu best-ul dintre mai multe strategii random
    /// optim se face cu flux maxim de cost minim, nu am avut timp sa implementez
    public synchronized void regenStrategies(){
        long pw=1, ans=0;
        for(int i=0; i<1000;i++){

        }
    }
    public boolean isFinished(){
        return finished; // atomic
    }
    public synchronized void start(){
        for(int i=0;i<bunnyThreads.size();i++)
            bunnyThreads.get(i).start();
        for(int i=0;i<robotThreads.size();i++)
            robotThreads.get(i).start();
    }
    public synchronized void finish(){
        finished = true;
    }
    public void setOccupant(int i, int j, Occupant occupant){
        occupants[i][j]=occupant;
    }
    public void moveTo(int i, int j, int i2, int j2){
        Occupant from=occupants[i][j];
        Occupant to=occupants[i2][j2];
        if(from == Occupant.BUNNY && i2==seen.length/2 && j2==seen[0].length/2){
            occupants[i2][j2]=Occupant.BUNNY;
            occupants[i][j]=Occupant.NOTHING;
            finish();
            return;
        }
        if(to == Occupant.ROBOT){
            assert(from!=Occupant.ROBOT);
            occupants[i][j]=Occupant.NOTHING;
        }
        else if(to == Occupant.BUNNY){
            assert(from!=Occupant.BUNNY);
            occupants[i2][j2]=from;
            occupants[i][j]=Occupant.NOTHING;
            for(int k=0;k<bunnyThreads.size();k++){
                if(bunnyThreads.get(k).info.pos==i2*w+j2){
                    bunnyThreads.get(k).interrupt();
                    remainingBunnies--;
                    if(remainingBunnies==0) finish();
                }
            }
            regenStrategies();
        }
        else{
            occupants[i2][j2]=from;
            occupants[i][j]=Occupant.NOTHING;
        }
    }

    public void addSeen(int i, int j, int cnt){
        seen[i][j]+=cnt;
    }
}
