package com.gheal.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;

public class ListCommand implements Command{
    public void run(Catalog catalog, BufferedReader stdin) throws InvalidCatalogException, IOException{
        if(catalog==null){
            throw new InvalidCatalogException(new Exception("null catalog provided"));
        }
        if(stdin==null){
            throw new IOException(new Exception("null stdin provided"));
        }
        try{
            System.out.println(CatalogUtil.getString(catalog));
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
}
