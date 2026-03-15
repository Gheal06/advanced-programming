package com.gheal.java.lab4;

public class Street implements Comparable<Street>{
    private Intersection u;
    private Intersection v;
    private String name;
    private double len;
    public Street(){
        u=new Intersection();
        v=new Intersection();
        name=new String();
        len=0f;
    }
    public Street(Street oth){
        u=new Intersection(oth.u);
        v=new Intersection(oth.v);
        name=new String();
        len=oth.len;
    }
    public Street(Intersection from, Intersection to, String name, double len){
        u=new Intersection(from);
        v=new Intersection(to);
        this.name=name;
        this.len=len;
    }
    public void setFrom(Intersection u){
        this.u=new Intersection(u);
    }
    public Intersection getFrom(){
        return u;
    }
    public void setTo(Intersection u){
        this.v=new Intersection(u);
    }
    public Intersection getTo(){
        return v;
    }
    public String getName(){
        return name;
    }
    public void setName(String s){
        name=new String(s);
    }
    public void setLength(double f){
        len=f;
    }
    public double getLength(){
        return len;
    }
    public int compareTo(Street oth){
        if(this.len<oth.len) return -1;
        if(this.len>oth.len) return 1;
        return 0;
    }
    public String toString(){
        return String.format("Street %1$s %2$s: len=%3$.2f",u.toString(),v.toString(),len);
    }
}
