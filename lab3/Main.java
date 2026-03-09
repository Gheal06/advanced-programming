//package lab2;
import java.util.*;

public class Main{
    public static void mandatory(){
        System.out.println("-- Mandatory --");
        ArrayList<Profile> l = new ArrayList<Profile>();
        l.add(new Person("Matei"));
        l.add(new Person("Andrei"));
        l.add(new Person("Stefan"));
        l.add(new Company("Microsoft"));
        l.add(new Company("Oracle"));
        l.add(new Company("Anthropic"));
        Collections.sort(l);
        for(Profile p : l){
            System.out.println(p.getName());
        }
    }
    public static void genGraph(SocialNetwork g, int n, int m){

        System.out.println("-- Generating graph -- ");
        long timestamp=System.currentTimeMillis();
        Random rnd = new Random();
        g.clear();
        for(int i=0;i<n;i++){
            int ptype=rnd.nextInt(2);
            String name=String.format("%1$d",i);
            switch(ptype){
                case 0: g.addProfile(new Person(name)); break;
                case 1: g.addProfile(new Company(name)); break;
            }

        }
        TreeSet<Pair> edges = new TreeSet<Pair>();
        while(edges.size()<m){
            int u=rnd.nextInt(n);
            int v=rnd.nextInt(n);
            int edgtype=rnd.nextInt(RelationshipType.values().length);
            if(u!=v && !edges.contains(new Pair(u,v)) && !edges.contains(new Pair(v,u))){
                g.addRelationship(u,v,RelationshipType.values()[edgtype]);
                edges.add(new Pair(u,v));
                //System.out.println(String.format("%1$d %2$d",Math.min(u,v),Math.max(u,v)));
            } 
        }
        System.out.println(String.format("Time elapsed: %1$d ms",System.currentTimeMillis()-timestamp));
    }
    public static void homework(SocialNetwork g){
        System.out.println("-- Homework -- ");
        long timestamp=System.currentTimeMillis();
        ArrayList<Profile> sorted=new ArrayList<Profile>(g.getProfiles());
        Collections.sort(sorted, new Comparator<Profile>(){
            public int compare(Profile profile1, Profile profile2){
                int importance1 = profile1.relationshipCount();
                int importance2 = profile2.relationshipCount();
                return importance2 - importance1; /// in decreasing order of relationships
            }
        });
        System.out.println(String.format("Profile count: %1$d",sorted.size()));
        int maxprint=20;
        for(Profile profile : sorted){
            System.out.println(profile);
            maxprint--;
            if(maxprint==0){
                System.out.println("...");
                break;
            }
        }
        System.out.println(String.format("Time elapsed: %1$d ms",System.currentTimeMillis()-timestamp));
    }
    public static void advanced(SocialNetwork g){
        System.out.println("-- Advanced -- ");
        long timestamp=System.currentTimeMillis();
        Tarjan solve = new Tarjan(g.getRelationships());
        System.out.println(String.format("Nodes: %1$d, Edges: %2$d",solve.getNodeCount(),solve.getEdgeCount()));
        int maxprint=15;
        if(solve.getConnectedComponentCount()>1){
            System.out.println(String.format("Critical Nodes: 0 (Graph has %1$d (> 1) connected components)",
                                             solve.getConnectedComponentCount()));
        }
        else{
            System.out.println(String.format("Critical Nodes: %1$d",solve.getCriticalNodes().size()));
            for(int i : solve.getCriticalNodes()){
                System.out.println(g.getProfiles().get(i));
                maxprint--;
                if(maxprint==0){
                    System.out.println("...");
                    break;
                }
            }
        }
        maxprint=15;
        System.out.println(String.format("Biconnected Components: %1$d",solve.getBiconnectedComponents().size()));
        for(ArrayList<Integer> list : solve.getBiconnectedComponents()){
            if(list.size()<=15) System.out.println(list);
            else System.out.println(String.format("[Biconnected component of size %1$d]",list.size()));
            maxprint--;
            if(maxprint==0){
                System.out.println("...");
                break;
            }
        }
        maxprint=15;
        System.out.println(String.format("Time elapsed: %1$d ms",System.currentTimeMillis()-timestamp));
    }
    public static void main(String args[]){
        mandatory();
        if(args.length!=2){
            System.out.println("Usage: java Main.java profileCount relationshipCount");
            return;
        }
        int n=Integer.parseInt(args[0]), m=Integer.parseInt(args[1]);
        if(n<1 || m<0){
            System.out.println("Usage: java Main.java profileCount relationshipCount");
            return;
        }
        if((long)m>(long)n*(n-1)/2){
            System.out.println("Too many edges");
            return;
        }
        SocialNetwork g = new SocialNetwork();
        genGraph(g,n,m);
        homework(g);
        advanced(g);

    }
}
