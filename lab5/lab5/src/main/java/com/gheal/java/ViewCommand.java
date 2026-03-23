package com.gheal.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;

public class ViewCommand implements Command{
    public void run(Catalog catalog, BufferedReader stdin) throws InvalidCatalogException, IOException{
        if(catalog==null){
            throw new InvalidCatalogException(new Exception("null catalog provided"));
        }
        if(stdin==null){
            throw new IOException(new Exception("null stdin provided"));
        }
        System.out.print("Enter item name: ");
        try{
            String itemId=stdin.readLine();
            CatalogUtil.view(catalog.findById(itemId));
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
}
