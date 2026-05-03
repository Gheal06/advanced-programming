package com.gheal.java;

import java.awt.event.*;
public class GridMouseListener implements MouseListener {
    Main app;
    public GridMouseListener(Main app){
        this.app=app;
    }
    public void mousePressed(MouseEvent e) {
        
    }

    public void mouseReleased(MouseEvent e) {
        
    }

    public void mouseEntered(MouseEvent e) {
       
    }

    public void mouseExited(MouseEvent e) {
       
    }

    public void mouseClicked(MouseEvent e) {
        app.stopGenerating();
        int cell=app.g.getCellSize();
        int x=e.getX(), y=e.getY();

        int i=y/cell, j=x/cell, i2=-1, j2=-1;

        int xInCell=x%cell, yInCell=y%cell;

        if(xInCell*7<=cell){ /// EAST
            i2=i;
            j2=j-1;
        }
        if(yInCell*7<=cell){ /// NORTH
            i2=i-1;
            j2=j;
        }
        if((cell-xInCell)*7<=cell){ /// WEST
            i2=i;
            j2=j+1;
        }
        if((cell-yInCell)*7<=cell){ /// SOUTH
            i2=i+1;
            j2=j;
        }
        app.g.toggleWall(i, j, i2, j2);
    }
}
