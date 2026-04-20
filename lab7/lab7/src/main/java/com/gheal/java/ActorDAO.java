package com.gheal.java;

import java.sql.*;
import java.util.*;
public class ActorDAO {
    public ActorDAO(){}
    public void insert(Actor actor) throws SQLException{
        Connection con = Database.getConnection();
        String sql="insert into actors(name) values (?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1,actor.getName());
        pstmt.execute();
        con.commit();
    }
    public List<Actor> getActors() throws SQLException{
        List<Actor> actorList=new ArrayList<Actor>();
        try{
            Connection con = Database.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from actors");
            while(rs.next()){
                actorList.add(new Actor(rs.getInt("id"),rs.getString("name")));
            }
        } catch(SQLException e){
            System.out.println(e);
            return null;
        }
        return actorList;
    }
    public Actor findByName(String name) throws SQLException {
        try{
            Connection con = Database.getConnection();
            String sql = "select * from actors where name=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1,name);
            ResultSet rs=pstmt.executeQuery();
            return rs.next() ? new Actor(rs.getInt("id"), rs.getString("name")) : null;
        } catch(SQLException e){
            System.out.println(e);
            return null;
        }
    }
    public Actor findById(int id) throws SQLException {
        try{
            Connection con = Database.getConnection();
            String sql = "select * from actors where id=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,id);
            ResultSet rs=pstmt.executeQuery();
            return rs.next() ? new Actor(rs.getInt("id"), rs.getString("name")) : null;
        } catch(SQLException e){
            System.out.println(e);
            return null;
        }
    }
}
