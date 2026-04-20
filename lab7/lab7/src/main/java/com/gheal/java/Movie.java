package com.gheal.java;

import java.util.*;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
public class Movie {
    private Integer id;
    private String name;
    private String genre;
    private Date releaseDate;
    private Integer duration;
    private Float score;
    private List<Actor> actors = new ArrayList<Actor>(); 
    public String toString(){
        return String.format("%s (Score: %.2f, Genre: %s, Release Year: %d), actors: ",
                                    name, score, genre, releaseDate.getYear()+1900)+actors.toString();
    }
    public Movie(String name){
        this.name=name;
    }
    public Movie(ResultSet rs) throws SQLException{
        this.id=rs.getInt("id");
        this.name=rs.getString("name");
        GenreDAO gdao=new GenreDAO();
        this.genre=gdao.findById(rs.getInt("genre_id")).getName();
        this.releaseDate=rs.getDate("release_date");
        this.duration=rs.getInt("duration");
        this.score=rs.getFloat("score");
    }
    public Movie(Integer id, String name, String genre, Date releaseDate, Integer duration, Float score, List<Actor> actors){
        this.id=id;
        this.name=name;
        this.genre=genre;
        this.releaseDate=releaseDate;
        this.duration=duration;
        this.score=score;
        this.actors=actors;
    }
    public Movie(String name, String genre, Date releaseDate, Integer duration, Float score){
        this.name=name;
        this.genre=genre;
        this.releaseDate=releaseDate;
        this.duration=duration;
        this.score=score;
    }
    public Integer getId(){
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
    public String getGenre(){
        return genre;
    }
    public void setGenre(String genre){
        this.genre=genre;
    }
    public Date getReleaseDate(){
        return releaseDate;
    }
    public void setReleaseDate(Date date){
        this.releaseDate=date;
    }
    public Integer getDuration(){
        return duration;
    }
    public void setDuration(Integer duration){
        this.duration=duration;
    }
    public Float getScore(){
        return score;
    }
    public void setScore(Float score){
        this.score=score;
    }
    public List<Actor> getActors(){
        return actors;
    }
    public void setActors(List<Actor> actors){
        this.actors=actors;
    }
    public void addActor(Actor actor){
        actors.add(actor);   
    }
}
