package com.gheal.java;

import java.sql.*;
import java.util.*;
public class MovieDAO {
    public MovieDAO(){}
    public void insert(Movie movie) throws SQLException{
        Connection con = Database.getConnection();
        String sql="insert into movies(name, genre_id, release_date, duration, score) values (?,?,?,?,?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        GenreDAO gdao=new GenreDAO();
        ActorDAO adao=new ActorDAO();
        List<Integer> actorIds = new ArrayList<Integer>();
        Integer genreId=gdao.findByName(movie.getGenre()).getId();
        if(genreId==null) throw new SQLException("MovieDAO.java/insert(): Invalid genre name");
        for(Actor actor : movie.getActors()){
            Actor tmp=adao.findByName(actor.getName());
            if(tmp==null) 
                throw new SQLException("MovieDAO.java/insert(): Invalid actor name");
            actorIds.add(tmp.getId());
        }
        pstmt.setString(1,movie.getName());
        pstmt.setInt(2,genreId);
        pstmt.setDate(3,movie.getReleaseDate());
        pstmt.setInt(4,movie.getDuration());
        pstmt.setFloat(5,movie.getScore());
        pstmt.execute();
        movie=findByName(movie.getName());
        System.out.println(movie.getId());
        for(Integer actorId : actorIds){
            PreparedStatement stmt = con.prepareStatement("insert into performances(movie_id, actor_id) values(?,?)");
            stmt.setInt(1,movie.getId());
            stmt.setInt(2,(int)actorId);
            stmt.execute();
        }
        con.commit();
    }
    public List<Actor> getActorsIn(Movie movie) throws SQLException{
        Connection con = Database.getConnection();
        String sql="select actor_id from performances where movie_id = ? ";
        PreparedStatement pstmt = con.prepareStatement(sql);
        //System.out.println(movie.getId());
        pstmt.setInt(1,movie.getId());
        ResultSet rs=pstmt.executeQuery();
        List<Actor> actors=new ArrayList<Actor>();
        ActorDAO dao=new ActorDAO();
        while(rs.next()){
            //System.out.println(String.format("%d %d",movie.getId(), rs.getInt("actor_id")));
            actors.add(dao.findById(rs.getInt("actor_id")));
        }
        return actors;
    }
    public List<Movie> getMovies() throws SQLException{
        List<Movie> movieList=new ArrayList<Movie>();
        try{
            Connection con = Database.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from movies");
            while(rs.next()){
                Movie tmp = new Movie(rs);
                tmp.setActors(getActorsIn(tmp));
                //System.out.println(tmp.getActors());
                movieList.add(tmp);
            }
        } catch(SQLException e){
            System.out.println(e);
            return null;
        }
        return movieList;
    }
    public Movie findByName(String name) throws SQLException {
        try{
            Connection con = Database.getConnection();
            String sql = "select * from movies where name=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1,name);
            ResultSet rs=pstmt.executeQuery();
            if(rs.next()){
                Movie tmp=new Movie(rs);
                tmp.setActors(getActorsIn(tmp));
                return tmp;
            }
            return null;
        } catch(SQLException e){
            System.out.println(e);
            return null;
        }
    }
    public Movie findById(int id) throws SQLException {
        try{
            Connection con = Database.getConnection();
            String sql = "select * from movies where id=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,id);
            ResultSet rs=pstmt.executeQuery();
            if(rs.next()){
                Movie tmp=new Movie(rs);
                tmp.setActors(getActorsIn(tmp));
                return tmp;
            }
            return null;
        } catch(SQLException e){
            System.out.println(e);
            return null;
        }
    }
}
