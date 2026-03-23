package com.gheal.java;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;
import java.io.File;
import java.io.Writer;
import java.util.*;
public class ReportCommand implements Command{
    public void run(Catalog catalog, BufferedReader stdin) throws InvalidCatalogException, IOException{
        if(catalog==null){
            throw new InvalidCatalogException(new Exception("null catalog provided"));
        }
        if(stdin==null){
            throw new IOException(new Exception("null stdin provided"));
        }
        Template template = CatalogUtil.getFreemarkerConfiguration().getTemplate("template.ftl");
        Map<String, Object> input = new HashMap<String,Object>();
        input.put("catalog", catalog);
        Writer fileWriter = new FileWriter(new File("output.html"));
        try{
            template.process(input,fileWriter);
        }
        catch(Exception e){
            System.out.println(e);
        }
        finally{
            fileWriter.close();
        }
    }
}
