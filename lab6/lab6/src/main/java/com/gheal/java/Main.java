package com.gheal.java;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;
import org.postgresql.*;
import java.sql.*;
import com.zaxxer.hikari.*;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class Main {
    public void createTables(){
        try{
            Connection conn=Database.getConnection();
            Statement stmt=conn.createStatement();
            stmt.execute("DROP VIEW IF EXISTS movies_by_score_desc");
            stmt.execute("DROP TABLE IF EXISTS performances");
            stmt.execute("DROP TABLE IF EXISTS movies");
            stmt.execute("DROP TABLE IF EXISTS actors");
            stmt.execute("DROP TABLE IF EXISTS genres");
            stmt.execute("CREATE TABLE genres (id bigserial primary key, name varchar(64) unique not null)");
            stmt.execute("CREATE TABLE movies (id bigserial primary key, genre_id integer, name varchar(64) not null, release_date date, duration integer, score float(2), constraint fk_genre_id foreign key (genre_id) references genres(id))");
            stmt.execute("CREATE TABLE actors (id bigserial primary key, name varchar(64) not null)");
            stmt.execute("CREATE TABLE performances (movie_id integer, actor_id integer, constraint fk_film_id foreign key (movie_id) references movies(id), constraint fk_actor_id foreign key (actor_id) references actors(id))");
            stmt.execute("CREATE VIEW movies_by_score_desc AS select * from movies order by score desc");
            conn.commit();
            Database.closeConnection();
        } catch(SQLException e){
            System.out.println(e);
            return;
        }
        System.out.println("Tables created successfully");
    }
    public void populateGenres(){
        GenreDAO dao = new GenreDAO();
        try{
            dao.insert(new Genre("Action"));
            dao.insert(new Genre("Drama"));
            dao.insert(new Genre("Thriller"));
            dao.insert(new Genre("Fiction"));
            dao.insert(new Genre("Science Fiction"));
            dao.insert(new Genre("Documentary"));
            dao.insert(new Genre("Comedy"));
            for(Genre tmp : dao.getGenres()){
                System.out.println(String.format("%d %s",tmp.getId(), tmp.getName()));
            }
        } catch(SQLException e){
            System.out.println(e);
            return;
        }
        System.out.println("Table <genres> populated successfully");
        
    }
    public void populateActors(){
        ActorDAO dao = new ActorDAO();
        try{
            dao.insert(new Actor("Leonardo DiCaprio"));
            dao.insert(new Actor("Jennifer Aniston"));
            dao.insert(new Actor("Angelina Jolie"));
            dao.insert(new Actor("Brad Pitt"));
            dao.insert(new Actor("Robert Downey Jr."));
            dao.insert(new Actor("Dwayne Johnson"));
            dao.insert(new Actor("Megan Fox"));
            for(Actor tmp : dao.getActors()){
                System.out.println(String.format("%d %s",tmp.getId(), tmp.getName()));
            }
        } catch(SQLException e){
            System.out.println(e);
            return;
        }
        System.out.println("Table <actors> populated successfully");
    }
    public void populateMovies(){
        MovieDAO dao = new MovieDAO();
        try{
            Movie morbius=new Movie("Morbius", "Action", new Date(2020-1900,3-1,05), 104, Float.valueOf((float)4.5));
            morbius.addActor(new Actor("Jennifer Aniston"));
            morbius.addActor(new Actor("Dwayne Johnson"));
            morbius.addActor(new Actor("Megan Fox"));
            Movie transformers=new Movie("Transformers", "Thriller", new Date(2009-1900,9-1,24), 112, Float.valueOf((float)6.7));
            transformers.addActor(new Actor("Brad Pitt"));
            transformers.addActor(new Actor("Robert Downey Jr."));
            transformers.addActor(new Actor("Megan Fox"));
            Movie titanic=new Movie("Titanic", "Drama", new Date(1995-1900,6-1,13), 153, Float.valueOf((float)8.1));
            titanic.addActor(new Actor("Leonardo DiCaprio"));
            titanic.addActor(new Actor("Angelina Jolie"));
            titanic.addActor(new Actor("Brad Pitt"));
            dao.insert(morbius);
            dao.insert(transformers);
            dao.insert(titanic);
            for(Movie tmp : dao.getMovies()){
                System.out.println(tmp);
            }
        } catch(SQLException e){
            System.out.println(e);
            return;
        }
        System.out.println("Table <movies> populated successfully");
        /*try{
            Statement stmt=Database.getConnection().createStatement();
            ResultSet rs=stmt.executeQuery("select * from performances");
            while(rs.next()){
                System.out.println(String.format("%d %d",rs.getInt(1),rs.getInt(2)));
            }
        }
        catch(SQLException e){}*/
    }
    public void makeReport() throws IOException{
        Configuration freemarkerCfg;
        freemarkerCfg = new Configuration(new Version(2,3,20));
        freemarkerCfg.setIncompatibleImprovements(new Version(2, 3, 20));
        freemarkerCfg.setDefaultEncoding("UTF-8");
        freemarkerCfg.setLocale(Locale.US);
        freemarkerCfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        Template template = freemarkerCfg.getTemplate("template.ftl");
        Map<String, Object> input = new HashMap<String,Object>();
        try{
            List<Movie> movies = new ArrayList<Movie>();
            Connection conn=Database.getConnection();
            Statement stmt=conn.createStatement();
            ResultSet rs=stmt.executeQuery("select * from movies_by_score_desc");
            MovieDAO mdao=new MovieDAO();
            while(rs.next()){
                Movie tmp=new Movie(rs);
                tmp.setActors(mdao.getActorsIn(tmp));
                movies.add(tmp);
            }
            input.put("movies",movies);
        }
        catch(SQLException e){
            System.out.println(e);
            System.out.println("Failed to generate template");
            return;
        }
        Writer fileWriter = new FileWriter(new File("output.html"));;
        try{
            template.process(input,fileWriter);
        }
        catch(Exception e){
            System.out.println(e);
        }
        finally{
            fileWriter.close();
        }
    }
    public static void main(String args[]) {
        //System.out.println("ezluci");
        Main app = new Main();
        app.createTables();
        app.populateGenres();
        app.populateActors();
        app.populateMovies();
        try{
            app.makeReport();
        } catch(IOException e){
            System.out.println(e);
        }
        Database.closeConnection();
    }
}
