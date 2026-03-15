package com.gheal.java.lab4;

public class Pair implements Comparable<Pair>{
    public int first, second;
    public Pair(){
        first=second=0;
    }
    public Pair(int first, int second){
        this.first=first;
        this.second=second;
    }
    public int compareTo(Pair oth){
        if(this.first<oth.first) return -1;
        if(this.first>oth.first) return 1;
        if(this.second<oth.second) return -1;
        if(this.second>oth.second) return 1;
        return 0;
    }
}