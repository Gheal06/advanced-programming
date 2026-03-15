package com.gheal.java.lab4;
public class DSUEdge implements Comparable<DSUEdge>{
    public int u, v;
    public double w;
    public DSUEdge(){
        this.u=0;
        this.v=0;
        this.w=0;
    }
    public DSUEdge(int u, int v, double w){
        this.u=u;
        this.v=v;
        this.w=w;
    }
    public int compareTo(DSUEdge oth){
        if(this.w<oth.w) return -1;
        if(this.w>oth.w) return 1;
        if(this.u<oth.u) return -1;
        if(this.u>oth.u) return 1;
        if(this.v<oth.v) return -1;
        if(this.v>oth.v) return 1;
        return 0;
    }
}