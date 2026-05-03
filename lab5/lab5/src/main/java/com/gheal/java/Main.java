package com.gheal.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.Random;
import java.util.ArrayList;

public class Main {
    public static String catalog_path="/Users/gheal/Documents/catalog.json";
    public int bkt(int n, int m, boolean[][] sets){
        int[] setMasks=new int[n];
        for(int i=0;i<n;i++){
            setMasks[i]=0;
            for(int j=0;j<m;j++){
                if(sets[i][j])
                    setMasks[i]|=(1<<j);
            }
        }
        int minSetCover=n, maxCovered=0;
        for(int msk=0;msk<(1<<n);msk++){
            int pop=0,covered=0;
            for(int i=0;i<n;i++){
                if((msk>>i&1)==1){
                    pop++;
                    covered|=setMasks[i];
                }
            }
            if(covered>maxCovered){
                maxCovered=covered;
                minSetCover=n;
            }
            if(covered==maxCovered) minSetCover=Math.min(minSetCover, pop);
        }
        return minSetCover;
    }
    public int heuristic(int n, int m, boolean[][] sets, int iters){
        int ans=n;
        Random rng=new Random();
        for(int iter=0;iter<iters;iter++){
            boolean[] currentSet = new boolean[m];
            int setCover=0;
            for(int step=0;step<n;step++){
                int maxImprovement=0;
                ArrayList<Integer> improvementPos=new ArrayList<Integer>();
                for(int j=0;j<n;j++){
                    int improvement=0;
                    for(int k=0;k<m;k++){
                        if(sets[j][k] && !currentSet[k]) improvement++;
                    }
                    if(improvement>maxImprovement){
                        maxImprovement=improvement;
                        improvementPos.clear();
                    }
                    if(improvement==maxImprovement){
                        improvementPos.add(j);
                    }
                }
                if(maxImprovement==0) continue;
                setCover++;
                int toImprove=improvementPos.get(rng.nextInt(improvementPos.size()));

                for(int k=0;k<m;k++)
                    currentSet[k]|=sets[toImprove][k];
            }
            ans=Math.min(ans,setCover);
        }
        return ans;
    }
    public void advanced(int n, int m, int iterCnt){
        Random rng=new Random();
        int sumbkt=0, sumheuristic=0;
        boolean[][] sets =new boolean[n][m];
        for(int iter=0;iter<iterCnt;iter++){
            for(int i=0;i<n;i++)
                for(int j=0;j<m;j++)
                    sets[i][j]=rng.nextBoolean();
            sumbkt+=bkt(n,m,sets);
            sumheuristic+=heuristic(n,m,sets,10);
        }
        System.out.println(String.format("Minimum set cover for %d entries and %d tags: ",n,m));
        System.out.println(String.format("Bkt average: %.2f | Heuristic average: %.2f | Average error: %.2f%%",
                                                 1.0*sumbkt/iterCnt, 1.0*sumheuristic/iterCnt, 100.0*sumheuristic/sumbkt-100));
    }
    public static void main(String args[]) {
        Main app = new Main();
        //app.testCreateSave();
        //app.testLoadView();
        CatalogUtil.initFreemarker();
        app.advanced(20, 30, 100);
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