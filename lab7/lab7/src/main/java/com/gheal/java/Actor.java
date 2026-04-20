package com.gheal.java;

public class Actor {
    Integer id;
    String name;
    public Actor(Integer id, String name){
        this.id=id;
        this.name=name;
    }
    public Actor(String name){
        this.name=name;
    }
    public int getId(){
        return id;
    }
    public void setId(Integer id){
        this.id=id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public String toString(){
        return this.name;
    }
}
