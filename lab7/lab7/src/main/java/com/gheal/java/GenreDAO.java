package com.gheal.java;

import java.sql.*;
import java.util.*;
public class GenreDAO {
    public GenreDAO(){}
    public void insert(Genre genre) throws SQLException{
        Connection con = Database.getConnection();
        String sql="insert into genres(name) values (?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1,genre.getName());
        pstmt.execute();
        con.commit();
    }
    public List<Genre> getGenres() throws SQLException{
        List<Genre> genreList=new ArrayList<Genre>();
        try{
            Connection con = Database.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from genres");
            while(rs.next()){
                genreList.add(new Genre(rs.getInt("id"),rs.getString("name")));
            }
        } catch(SQLException e){
            System.out.println(e);
            return null;
        }
        return genreList;
    }
    public Genre findByName(String name){
        try{
            Connection con = Database.getConnection();
            String sql = "select * from genres where name=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1,name);
            ResultSet rs=pstmt.executeQuery();
            return rs.next() ? new Genre(rs.getInt("id"), rs.getString("name")) : null;
        } catch(SQLException e){
            System.out.println(e);
            return null;
        }
    }
    public Genre findById(int id){
        try{
            Connection con = Database.getConnection();
            String sql = "select * from genres where id=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,id);
            ResultSet rs=pstmt.executeQuery();
            return rs.next() ? new Genre(rs.getInt("id"), rs.getString("name")) : null;
        } catch(SQLException e){
            System.out.println(e);
            return null;
        }
    }
}
