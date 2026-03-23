package com.gheal.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LoadCommand implements Command{
    public void run(Catalog catalog, BufferedReader stdin) throws InvalidCatalogException, IOException{
        if(stdin==null){
            throw new IOException(new Exception("null stdin provided"));
        }
        System.out.print("Enter file path to load from: ");
        try{
            String path=stdin.readLine();
            catalog.copy(CatalogUtil.load(path));
        }
        catch(IOException e){
            System.out.println(e);
        }
        if(catalog!=null)
            System.out.println("Catalog loaded successfully");
        else
            System.out.println("Failed to load catalog");
    }
}
