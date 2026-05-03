package com.gheal.java;

import javax.swing.*;

import javax.imageio.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.TreeSet;
import java.util.Random;
public class Main {
    private int defaultMovementTime = 500;
    private int preferredCellSize;
    private int preferredMazeHeight;
    private int preferredMazeWidth;
    private int preferredGenSpeed;
    private int bunnies;
    private int robots;
    private GridGenerationThread gthread;
    private DaemonThread daemonThread;
    public Grid g;
    public SharedMemory shmem;
    public Main(int preferredCellSize, int preferredMazeHeight, int preferredMazeWidth, int preferredGenSpeed, int bunnies, int robots){
        this.preferredCellSize=preferredCellSize;
        this.preferredMazeHeight=preferredMazeHeight;
        this.preferredMazeWidth=preferredMazeWidth;
        this.preferredGenSpeed=preferredGenSpeed;
        this.bunnies=bunnies;
        this.robots=robots;
        shmem = new SharedMemory(preferredMazeHeight, preferredMazeWidth, bunnies, robots);
        gthread = new GridGenerationThread(g, preferredMazeHeight, preferredMazeWidth, preferredCellSize);
        g=new Grid(preferredMazeHeight, preferredMazeWidth, preferredCellSize, shmem);
    }
    public boolean isGenerating(){
        return gthread!=null && gthread.isAlive();
    }
    public boolean isPlaying(){
        return daemonThread!=null && daemonThread.isAlive();
    }
    public void stopGenerating(){
        if(gthread!=null) gthread.interrupt();
    }
    public void stopPlaying(){
        if(daemonThread!=null){
            shmem.finish();
            daemonThread.interrupt();
            daemonThread=null;
        }
    }
    private void generateMaze(ActionEvent e){
        stopGenerating();
        g.resize(preferredMazeHeight, preferredMazeWidth, preferredCellSize);
        gthread = new GridGenerationThread(g, preferredMazeHeight, preferredMazeWidth, preferredGenSpeed);
        gthread.start();
    }
    private void play(ActionEvent e){
        if(isGenerating()) stopGenerating();
        if(isPlaying()) stopPlaying();
        generateMaze(e);
        TreeSet<Integer> s = new TreeSet<Integer>();
        s.add((g.getGridHeight()/2)*g.getGridWidth()+g.getGridWidth()/2);
        int[] robotPos = new int[robots]; 
        int[] bunnyPos = new int[bunnies];
        Random rng = new Random(System.currentTimeMillis());
        for(int i=0;i<robotPos.length;i++){
            int u=rng.nextInt(g.getGridWidth()*g.getGridHeight());
            while(s.contains(u)){
                u=rng.nextInt(g.getGridWidth()*g.getGridHeight());
            }
            s.add(u);
            robotPos[i]=u;
            System.out.println(u);
        }
        for(int i=0;i<bunnyPos.length;i++){
            int u=rng.nextInt(g.getGridWidth()*g.getGridHeight());
            while(s.contains(u)){
                u=rng.nextInt(g.getGridWidth()*g.getGridHeight());
            }
            s.add(u);
            bunnyPos[i]=u;
            System.out.println(u);
        }
        shmem.reset(g.getGridWidth(), g.getGridWidth(), bunnies, robots);
        for(int i=0;i<bunnyPos.length;i++){
            shmem.setOccupant(bunnyPos[i]/g.getGridWidth(), bunnyPos[i]%g.getGridWidth(), Occupant.BUNNY);
            Bunny tmp = new Bunny();
            tmp.movementTime=defaultMovementTime;
            tmp.pos=bunnyPos[i];
            shmem.bunnyThreads.add(new BunnyThread(shmem, g,tmp));
        }
        for(int i=0;i<robotPos.length;i++){
            shmem.setOccupant(robotPos[i]/g.getGridWidth(), robotPos[i]%g.getGridWidth(), Occupant.ROBOT);
            Robot tmp = new Robot();
            tmp.movementTime=defaultMovementTime;
            tmp.pos=robotPos[i];
            shmem.robotThreads.add(new RobotThread(shmem, g,tmp));
        }
        daemonThread = new DaemonThread(shmem, g, 30000);
        daemonThread.start();
    }
    public JPanel makeTailPanel(){
        JPanel tl = new JPanel();
        tl.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 0));
        JButton playButton = new JButton("Play");
        playButton.addActionListener(e -> play(e));
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        tl.add(playButton);
        tl.add(exitButton);
        return tl;
    }
    public JPanel makeMaze(int cellSize){
        JPanel wrapper=new JPanel();
        wrapper.setLayout(new FlowLayout());
        wrapper.add(g);
        return wrapper;
    }
    public void generateFrame(){
        JFrame frame = new JFrame("Lab 9");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel output = new JPanel();
        output.setLayout(new BorderLayout());
        //  System.out.println(String.format("%d %d", Maze.getHeight(), Maze.getWidth()));
        output.add(makeMaze(preferredCellSize), BorderLayout.CENTER);
        output.add(makeTailPanel(), BorderLayout.SOUTH);
        frame.add(output, BorderLayout.CENTER);
        //frame.setLayout(null);
        frame.pack();
        frame.setVisible(true);
    }
    public static void main(String[] args) {
       Main app = new Main(30, 5, 5, 0, 10, 5);
       app.generateFrame();
    }
}
