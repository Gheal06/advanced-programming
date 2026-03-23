package com.gheal.java;

import java.io.*;
import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

public class CatalogUtil {
    private static Configuration freemarkerCfg;
    public static Configuration getFreemarkerConfiguration(){
        return freemarkerCfg;
    }
    public static void initFreemarker(){
        freemarkerCfg = new Configuration(new Version(2,3,20));
        
        //freemarkerCfg.setClassForTemplateLoading(ReportCommand.class, "com.gheal.java");
        
        freemarkerCfg.setIncompatibleImprovements(new Version(2, 3, 20));
        freemarkerCfg.setDefaultEncoding("UTF-8");
        freemarkerCfg.setLocale(Locale.US);
        freemarkerCfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }
    public static void save(Catalog catalog, String path) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File(path),catalog);
    }
    public static String getString(Catalog catalog) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(catalog);
    }


    /// !!!! blocheaza stdin
    public static void printToScreen(Catalog catalog) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(System.out,catalog);
    }
    public static Catalog load(String path) throws InvalidCatalogException {
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            Catalog catalog = objectMapper.readValue(new File(path),Catalog.class);
            return catalog;
        } catch(IOException e){
            System.out.println(e);
            throw new InvalidCatalogException(e);
        }
    }
    public static void view(Item item) throws IOException{  
        if(item==null){
            throw new IOException("No such resources found");
        }
        Desktop desktop = Desktop.getDesktop();
        String location=item.getLocation();
        try{
            desktop.open(new File(location));
        }catch(IllegalArgumentException e){
            try{
                desktop.browse(new URI(location));
            }
            catch(URISyntaxException e2){
                throw new IOException("Invalid location provided");
            }
        }
    }
}