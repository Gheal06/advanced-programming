package com.gheal.java;
import java.awt.Graphics;

import javax.swing.*;

public class ConstantAspectRatioPanel extends JPanel{
    protected int targetAspectHeight;
    protected int targetAspectWidth;
    public ConstantAspectRatioPanel(int targetAspectHeight, int targetAspectWidth){
        this.targetAspectHeight=targetAspectHeight;
        this.targetAspectWidth=targetAspectWidth;
    }   
    public void setTargetAspectHeight(int targetAspectHeight){
        this.targetAspectHeight=targetAspectHeight;
    } 
    public void setTargetAspectWidth(int targetAspectWidth){
        this.targetAspectWidth=targetAspectWidth;
    } 
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(getHeight()*targetAspectWidth>getWidth()*targetAspectHeight)
            super.setSize(getWidth(), getWidth());
        else if(getHeight()*targetAspectWidth<getWidth()*targetAspectHeight)
            super.setSize(getHeight(), getHeight());
    }
}
