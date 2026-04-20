package com.gheal.java;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.*;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@RestController
@RequestMapping("/api")
public class MovieController{
    @GetMapping("/movies")
    public List<Movie> getMovies(){
        //ObjectMapper om = new ObjectMapper();
        try{
            List<Movie> movies=(new MovieDAO()).getMovies();
            return movies;
        } catch(Exception e){}
        return null;
    }
    @PostMapping("/add-movie")
    public String addMovie(@RequestParam("name") String name, 
                         @RequestParam("genre") String genre, 
                         @RequestParam("y") String year, 
                         @RequestParam("m") String month, 
                         @RequestParam("d") String day,
                         @RequestParam("score") String Score, 
                         @RequestParam("duration") String duration){
        try{
            Date date = new Date(Integer.parseInt(year)-1900, Integer.parseInt(month)-1, Integer.parseInt(day));
            Movie newMovie = new Movie(name,genre,date,Integer.parseInt(duration), Float.parseFloat(Score));
            (new MovieDAO()).insert(newMovie);
        } catch(Exception e){
            return "Failed to insert movie";
        }
        return "Movie inserted successfully";
    }

    @PutMapping("/update-movie")
    public String updateMovie(@RequestParam("id") String id,
                         @RequestParam("name") String name, 
                         @RequestParam("genre") String genre, 
                         @RequestParam("y") String year, 
                         @RequestParam("m") String month, 
                         @RequestParam("d") String day,
                         @RequestParam("score") String Score, 
                         @RequestParam("duration") String duration){
        int updated=0;
        try{
            Date date = new Date(Integer.parseInt(year)-1900, Integer.parseInt(month)-1, Integer.parseInt(day));
            Movie newMovie = new Movie(name,genre,date,Integer.parseInt(duration), Float.parseFloat(Score));
            updated=(new MovieDAO()).update(Integer.parseInt(id), newMovie);
        } catch(Exception e){
            return "Failed to update movie";
        }
        return String.format("Performed %d updates", updated);
    }
    
    
    @PatchMapping("/update-score")
    public String updateScore(@RequestParam("name") String name, @RequestParam("newScore") float newScore){
        //ObjectMapper om = new ObjectMapper();
        int updated=0;
        try{
            updated=(new MovieDAO()).updateScoreByName(name,newScore);
        } catch(Exception e){}
        return String.format("Updated %d movies", updated);
    }
    @DeleteMapping("/delete-movie")
    public String deleteMovie(@RequestParam("name") String name){
        //ObjectMapper om = new ObjectMapper();
        int removed=0;
        try{
            removed=(new MovieDAO()).deleteByName(name);
        } catch(Exception e){}
        return String.format("Removed %d movies", removed);
    }
}