package com.gheal.java;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;
import org.postgresql.*;
import java.sql.*;
import com.zaxxer.hikari.*;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import org.springframework.boot.jdbc.*;

public class Client {
    public static void main(String[] args) {
        RestClient client = RestClient.create();
        System.out.println(client.get().uri("http://localhost:8080/api/movies").retrieve().body(String.class));
    }
}
