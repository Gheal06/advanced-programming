package com.gheal.java;

import java.sql.*;

import com.zaxxer.hikari.*;
import org.postgresql.*;

public class Database {
    private static HikariConfig config = null;
    private static HikariDataSource ds = null;
    private static Connection conn = null;
    private Database() {}
    public static Connection getConnection(){
        if(config == null)
            config();
        if(ds == null)
            ds = new HikariDataSource(config);
        if(conn == null){
            try {
                conn=ds.getConnection();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
        return conn;
    }
    private static void config(){
        config = new HikariConfig();
        config.setDataSourceClassName("org.postgresql.ds.PGSimpleDataSource");
        config.setAutoCommit(false);
        config.setUsername("postgres");
        config.setPassword("password");
        config.addDataSourceProperty("databaseName", "postgres");
        config.addDataSourceProperty("serverName", "localhost");
        //config.setJdbcUrl("jdbc:postgres://localhost:5432/postgres");
        //config.setUsername("postgres"); config.setPassword("password");
        config.setMaximumPoolSize(32);
        config.setConnectionTimeout(30000);
        ds=new HikariDataSource(config);
    }
    public static void closeConnection() {
        if(conn==null) return;
        try{
            conn.close();
            conn = null;
            ds.close();
            ds = null;
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }
}