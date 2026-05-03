package com.gheal.java;

import javax.swing.*;
import javax.imageio.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
public class Main {
    private int preferredCellSize;
    private int preferredMazeHeight;
    private int preferredMazeWidth;
    private int preferredGenSpeed;
    private GridGenerationThread gthread;
    public Grid g;
    public Main(int preferredCellSize, int preferredMazeHeight, int preferredMazeWidth, int preferredGenSpeed){
        this.preferredCellSize=preferredCellSize;
        this.preferredMazeHeight=preferredMazeHeight;
        this.preferredMazeWidth=preferredMazeWidth;
        this.preferredGenSpeed=preferredGenSpeed;
        gthread = new GridGenerationThread(g, preferredMazeHeight, preferredMazeWidth, preferredCellSize);
        g=new Grid(preferredMazeHeight, preferredMazeWidth, preferredCellSize);
    }
    public boolean isGenerating(){
        return gthread!=null && gthread.isAlive();
    }
    public void stopGenerating(){
        if(gthread!=null) gthread.interrupt();
    }
    /*public JPanel createPanelFromCell(Cell c, int cellSize){
        ConstantAspectRatioPanel output = new ConstantAspectRatioPanel(1,1);
        output.setSize(cellSize, cellSize);
        output.setBackground(new Color(0x00, 0xAA, 0x00));
        output.setBorder(BorderFactory.createLineBorder(Color.black));
        return output;
    }*/
    private void updateMazeSize(ActionEvent e, String snewh, String sneww, String snewspeed){
        
        int h,w,speed;
        try{
            h=Integer.parseInt(snewh);
            w=Integer.parseInt(sneww);
            speed=Integer.parseInt(snewspeed);
        }catch(NumberFormatException ex){
            return;
        }
        if(h>=2 && w>=2 && h<=15 && w<=15){
            stopGenerating();
            preferredMazeHeight=h;
            preferredMazeWidth=w;
            preferredGenSpeed=speed;
            gthread = new GridGenerationThread(g, preferredMazeHeight, preferredMazeWidth, preferredGenSpeed);
            g.resize(h, w, this.preferredCellSize);
            System.out.println("Drawing maze...");
        }
    }
    private void generateMaze(ActionEvent e){
        stopGenerating();
        updateMazeSize(e, String.format("%d", preferredMazeHeight), String.format("%d", preferredMazeWidth), String.format("%d", preferredGenSpeed));
        gthread = new GridGenerationThread(g, preferredMazeHeight, preferredMazeWidth, preferredGenSpeed);
        gthread.start();
    }
    private void validatePath(ActionEvent e){
        stopGenerating();
        if(g.validatePath()) g.repaint();
    }
    private void loadMaze(ActionEvent e){
        stopGenerating();
        try{
            ObjectMapper wr = new ObjectMapper();
            SaveState newState=wr.readValue(new File("maze.json"),SaveState.class);
            this.preferredCellSize=newState.preferredCellSize;
            this.preferredMazeWidth=newState.preferredMazeWidth;
            this.preferredMazeHeight=newState.preferredMazeHeight;
            this.preferredGenSpeed=newState.preferredGenSpeed;
            this.gthread=null;
            this.g.reinit(newState.preferredCellSize, newState.grid, newState.cellPath);
        }catch(IOException ex){
            System.out.println("Load failed");
            System.out.println(ex.getMessage());
            return;
        }
    } 
    private void saveMaze(ActionEvent e){
        stopGenerating();
        Rectangle componentRect = g.getBounds();
        BufferedImage bufferedImage = new BufferedImage(componentRect.width, componentRect.height, BufferedImage.TYPE_INT_ARGB);
        g.paint(bufferedImage.getGraphics());
        try{
            ObjectMapper wr = new ObjectMapper();
            SaveState save = new SaveState(preferredCellSize, preferredMazeHeight, preferredMazeWidth, preferredGenSpeed, g.getGrid(), g.getCellPath());
            wr.writeValue(new File("maze.json"), save);
            ImageIO.write(bufferedImage, "png", new File("maze.png"));
        }catch(IOException ex){
            System.out.println("Save failed");
            System.out.println(ex.getMessage());
            return;
        }
    } 
    private JPanel makeHeadPanel(){
        JPanel hd = new JPanel();
        hd.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        JLabel htext = new JLabel("Maze height:");
        JLabel wtext = new JLabel("Maze width:");
        JLabel speedtext = new JLabel("Generation speed (ms):");
        
        JTextField hfield = new JTextField("Maze height");
        JTextField wfield = new JTextField("Maze width");
        JTextField speedField = new JTextField("Generation speed");
        hfield.setText(String.format("%d",preferredMazeHeight));
        wfield.setText(String.format("%d",preferredMazeWidth));
        speedField.setText(String.format("%d",preferredGenSpeed));
        JButton genButton = new JButton("Save preferences");
        hd.add(htext);
        hd.add(hfield);
        hd.add(wtext);
        hd.add(wfield);
        hd.add(speedtext);
        hd.add(speedField);
        hd.add(genButton);
        genButton.addActionListener(e -> updateMazeSize(e, hfield.getText(), wfield.getText(), speedField.getText()));
        //hd.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        return hd;
    }
    public JPanel makeTailPanel(){
        JPanel tl = new JPanel();
        tl.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 0));
        JButton createButton = new JButton("Create");
        createButton.addActionListener(e -> generateMaze(e));
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> updateMazeSize(e, String.format("%d", preferredMazeHeight), String.format("%d", preferredMazeWidth), String.format("%d", preferredGenSpeed)));
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveMaze(e));
        JButton loadButton = new JButton("Load");
        loadButton.addActionListener(e -> loadMaze(e));
        JButton validateButton = new JButton("Validate");
        validateButton.addActionListener(e -> validatePath(e));
        tl.add(createButton);
        tl.add(resetButton);
        tl.add(validateButton);
        tl.add(saveButton);
        tl.add(loadButton);
        tl.add(exitButton);
        return tl;
    }
    public JPanel makeMaze(int cellSize){
        JPanel wrapper=new JPanel();
        wrapper.setLayout(new FlowLayout());
        wrapper.add(g);
        g.addMouseListener(new GridMouseListener(this));
        return wrapper;
    }
    public void generateFrame(){
        JFrame frame = new JFrame("Lab 8");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel output = new JPanel();
        output.setLayout(new BorderLayout());
        //  System.out.println(String.format("%d %d", Maze.getHeight(), Maze.getWidth()));
        output.add(makeHeadPanel(), BorderLayout.NORTH);
        output.add(makeMaze(preferredCellSize), BorderLayout.CENTER);
        output.add(makeTailPanel(), BorderLayout.SOUTH);
        frame.add(output, BorderLayout.CENTER);
        //frame.setLayout(null);
        frame.pack();
        frame.setVisible(true);
    }
    public static void main(String[] args) {
       Main app = new Main(60, 10, 10, 200);
       app.generateFrame();
    }
}
