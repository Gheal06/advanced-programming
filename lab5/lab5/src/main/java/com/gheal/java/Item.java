package com.gheal.java;

import java.io.Serializable;
import java.util.*;
public class Item implements Serializable {
    private String id = new String();
    private String title = new String();
    private String location = new String(); // file name or Web page

    private Map<String, Object> tags = new HashMap<>();

    public Item(String id, String title, String location){
        this.id=id;
        this.title=title;
        this.location=location;
    }
    public Item(){}
    public void setTag(String key, Object obj) { /// de verificat ca key nu e "id", "title" sau "location"
        tags.put(key, obj);
    }
    public void removeTag(String key){
        tags.remove(key);
    }
    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id=id;
    }

    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title=title;
    }

    public String getLocation(){
        return location;
    }
    public void setLocation(String location){
        this.location=location;
    }
    public Map<String,Object> getTags(){
        return tags;
    }
}