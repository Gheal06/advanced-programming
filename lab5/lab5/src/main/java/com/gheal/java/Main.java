package com.gheal.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;


public class Main {
    public static String catalog_path="/Users/gheal/Documents/catalog.json";
    public static void main(String args[]) {
        Main app = new Main();
        //app.testCreateSave();
        //app.testLoadView();
        CatalogUtil.initFreemarker();
        app.shell();
    }

    private void testCreateSave() {
        Catalog catalog =
        new Catalog("MyRefs");
        var book = new Item("knuth67", "The Art of Computer Programming", "/Users/gheal/Documents/ceva.txt");
        var article = new Item("jvm25", "The Java Virtual Machine Specification", "https://docs.oracle.com/javase/specs/jvms/se25/html/index.html");
        var article2 = new Item("java25", "The Java Language Specification", "https://docs.oracle.com/javase/specs/jls/se25/jls25.pdf");
        catalog.add(book);
        catalog.add(article);
        catalog.add(article2);
        try{
            CatalogUtil.save(catalog, catalog_path);
        }
        catch(IOException e){
            System.out.println(e);
            System.out.println("IOException in Main.testCreateSave()");
        }
    }

    private void testLoadView() {
        try{
            Catalog catalog = CatalogUtil.load(catalog_path);
            CatalogUtil.view(catalog.findById("java25"));
        }
        catch(InvalidCatalogException e){
            System.out.println(e);
            System.out.println("InvalidCatalogException in Main.testLoadView()");
        }
        catch(IOException e){
            System.out.println(e);
            System.out.println("IOException in Main.testLoadView()");
        }
    }
    private void shell(){
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        Catalog catalog = new Catalog();
        while(true){
            //System.out.println(catalog);
            System.out.print("Enter command (load, view, list, report): ");
            try{
                String line=stdin.readLine();
                Command cmd = null;
                if(line.toLowerCase().equals("load"))
                    cmd = new LoadCommand();
                else if(line.toLowerCase().equals("view"))
                    cmd = new ViewCommand();
                else if(line.toLowerCase().equals("list"))
                    cmd = new ListCommand();
                else if(line.toLowerCase().equals("report"))
                    cmd = new ReportCommand();
                else
                    continue;
                try{
                    cmd.run(catalog, stdin);
                }
                catch(IOException e){
                    System.out.println("IOException in Command.run()");
                    System.out.println(e);
                }
                catch(InvalidCatalogException e){
                    System.out.println("InvalidCatalogException in Command.run()");
                    System.out.println(e);
                }
            }
            catch(IOException e){
                System.out.println(e);
            }   
        }
    }
}