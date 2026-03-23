package com.gheal.java;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;
public class Catalog{
    private String name = new String();
    private List<Item> items = new ArrayList<>();
    public Catalog(){}
    public Catalog(String name){
        this.name=name;
    }
    public Catalog(String nam, List<Item> items){
        this.name=name;
        this.items=items;
    }
    public String getName(){
        return name;
    }
    public List<Item> getItems(){
        return items;
    }
    public void setName(String name){
        this.name=name;
    }
    public void add(Item item) {
        items.add(item);
    }
    public Item findById(String id) {
        return items.stream()
        .filter(d -> d.getId().equals(id)).findFirst().orElse(null);
    }
    public void copy(Catalog oth){
        this.name=oth.name;
        this.items=oth.items;
    }
    /*private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException{
    
    }
    private void readObjectNoData() throws ObjectStreamException{
        throw ObjectStreamException;
    }*/
    /*public String toString(){
        StringBuilder sb=new StringBuilder();
        sb.append("[\n");
        for(Item i : items){
            sb.append(i);
            sb.append('\n');
        }
        sb.append(']');
        return sb.toString();
    }*/
}