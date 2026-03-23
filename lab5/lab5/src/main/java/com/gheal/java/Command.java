package com.gheal.java;
import java.io.BufferedReader;
import java.io.IOException;

public interface Command{
    public void run(Catalog catalog, BufferedReader stdin) throws InvalidCatalogException, IOException;
}
