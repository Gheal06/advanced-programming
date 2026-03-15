package com.gheal.java.lab4;
import java.util.*;
import java.util.stream.IntStream;
import com.github.javafaker.Faker;
import org.graph4j.*;
import org.graph4j.spanning.*;
import org.graph4j.traversal.*;
public class Main {
    public Main(){}
    public boolean checkDistinct(ArrayList<Intersection> intersections){
            
        ArrayList<String> names = new ArrayList<String>();
        for(Intersection i : intersections)
            names.add(i.getName());
        //for(String name : names){
        //    System.out.println(name);
        //}
        System.out.flush();
        Collections.sort(names,(a, b) -> a.compareTo(b));
        for(int i=0;i+1<names.size();i++){
            if(names.get(i).compareTo(names.get(i+1))==0)
                return false;
        }
        return true;
    }
    public void mandatory(){

        System.out.println("-- Mandatory -- ");
        LinkedList<Street> streets=new LinkedList<Street>();
        HashSet<Intersection> intersections=new HashSet<Intersection>();
        ArrayList<Intersection> intersectionArr = new ArrayList<Intersection>();
        IntStream.rangeClosed(0, 9).forEach((i)->{
            intersections.add(new Intersection(""+i));
            intersectionArr.add(new Intersection(""+i));
        });
        for(Intersection i : intersections){
            System.out.println(i);
        }
        System.out.println();
        if(!checkDistinct(intersectionArr)){
            System.out.println("Names are not distinct");
            return;
        }
        Random rnd=new Random();
        int n=intersections.size();
        
        while(streets.size()<20){
            int u=rnd.nextInt(n);
            int v=rnd.nextInt(n);
            double f=rnd.nextDouble()*1000;
            streets.add(new Street(intersectionArr.get(u),intersectionArr.get(v),"",f));
        }

        ArrayList<Street> streetArr = new ArrayList<Street>();
        for(Street s : streets) streetArr.add(s); 
        Collections.sort(streetArr);

       for(Street s : streetArr) System.out.println(s);

    }
    public void homework(int numNodes, int numEdges, int mstCnt){

        System.out.println("-- Homework -- ");
        Faker faker = new Faker(Locale.CHINA);
        Random rnd = new Random();
        Intersection[] intersections = new Intersection[numNodes];
        ArrayList<Intersection> intersectionArr = new ArrayList<Intersection>();
        HashSet<String> names=new HashSet<String>();
        while(names.size()<numNodes){
            String tmp=faker.address().city();
            if(names.contains(tmp)) continue;
            int i=names.size();
            names.add(tmp);
            intersections[i] = new Intersection(tmp);
            //System.out.println(tmp);
            intersectionArr.add(intersections[i]);
        }
        System.out.println();
        if(!checkDistinct(intersectionArr)){
            System.out.println("Names are not distinct");
            return;
        }

        Street[] streets = new Street[numEdges];
        IntStream.rangeClosed(0,numEdges-1).forEach((i)->{
            streets[i]=new Street();
            streets[i].setName(faker.address().streetName());
        });
        TreeMap<Pair,Integer> edgeId = new TreeMap<Pair,Integer>();

        Graph g=GraphBuilder.numVertices(numNodes).buildGraph();
        
        int i=0;
        while(g.numEdges()<numEdges){
            int u=rnd.nextInt(numNodes);
            int v=rnd.nextInt(numNodes);
            if(u==v || edgeId.containsKey(new Pair(u,v)) || edgeId.containsKey(new Pair(v,u))) continue;
            double w=rnd.nextDouble()*1000;
            g.addEdge(u,v,w);
            edgeId.put(new Pair(u,v),i);
            streets[i].setFrom(intersections[u]);
            streets[i].setTo(intersections[v]);
            streets[i].setLength(w);
            i++;
        }
        WeightedSpanningTreeIterator mst = new WeightedSpanningTreeIterator(g);
        int mstId=1;
        while(mstId<=mstCnt && mst.hasNext()){
            Collection<Edge> edges=mst.next();
            if(edges.size()!=numNodes-1){
                System.out.println("Graph is not connected");
                return;
            }
            double totalCost=0;
            for(Edge e : edges)
                totalCost+=e.weight();
            System.out.println(String.format("Mst %d (total cost %.3f):",mstId,totalCost));
            int maxcnt=30;
            for(Edge e : edges){
                System.out.print(String.format("(%d,%d); ",e.source(),e.target()));
                maxcnt--;
                if(maxcnt==0){
                    System.out.println("...");
                    break;
                }
            }
            if(maxcnt>0) System.out.println("");
            mstId++;
        }
    }
    public boolean isMetric(int numNodes, double[][] weights){
        int n=numNodes;
        for(int i=0;i<n;i++) /// dist(u,v)==0 <=> u==v
            for(int j=0;j<n;j++)
                if((weights[i][j]==0)!=(i==j))
                    return false;
        for(int i=0;i<n;i++) /// dist(u,v)==dist(v,u)
            for(int j=0;j<n;j++)
                if(weights[i][j]!=weights[j][i])
                    return false;
        for(int i=0;i<n;i++) /// dist(u,v)+dist(v,w)>=dist(u,w)
            for(int j=0;j<n;j++)
                for(int k=0;k<n;k++)
                    if(weights[i][j]+weights[j][k]<weights[i][k])
                        return false;
        return true;
    }

    public ArrayList<DSUEdge> getMST(int numNodes, double[][] weights){
        int n=numNodes;
        ArrayList<DSUEdge> edges = new ArrayList<DSUEdge>();
        for(int i=0;i<n;i++)
            for(int j=0;j<i;j++)
                edges.add(new DSUEdge(i,j,weights[i][j]));
        Collections.sort(edges);
        DSU d = new DSU(n);
        ArrayList<DSUEdge> mst = new ArrayList<DSUEdge>();
        for(DSUEdge e : edges){
            if(d.union(e.u,e.v)==true)
                mst.add(e);
        }
        return mst;
    }
    public ArrayList<DSUEdge> mstTSP(int numNodes, double[][] weights){
        int n=numNodes;
        ArrayList<DSUEdge> mst=getMST(n,weights);
        Graph g=GraphBuilder.numVertices(n).buildGraph();
        for(DSUEdge e : mst) g.addEdge(e.u, e.v);
        DFSIterator it = new DFSIterator(g,0);
        int last=it.next().vertex();
        mst=new ArrayList<DSUEdge>();
        while(it.hasNext()){
            int nxt=it.next().vertex();
            mst.add(new DSUEdge(last,nxt,weights[last][nxt]));
            last=nxt;
        }
        mst.add(new DSUEdge(last,0,weights[last][0]));
        return mst;
    }
    public ArrayList<DSUEdge> christofidesTSP(int numNodes, double[][] weights){
        return null;
    }
    public ArrayList<DSUEdge> bktTSP(int numNodes, double[][] weights){
        System.out.println("here");
        int n=numNodes,optlast=-1,optmsk=-1;
        double dp[][]=new double[n][1<<n];
        for(int i=0;i<n;i++) for(int msk=0;msk<(1<<n);msk++) dp[i][msk]=1e18;
        dp[0][1]=0;
        for(int msk=1;msk<(1<<n);msk+=2){
            for(int u=0;u<n;u++){
                if((msk>>u&1)==0) continue;
                for(int v=0;v<n;v++){
                    if((msk>>v&1)==0){
                        dp[v][msk|(1<<v)]=Math.min(dp[v][msk|(1<<v)],dp[u][msk]+weights[u][v]);
                    }
                }
            }
        }
        double ans=1e18;
        for(int i=0;i<n;i++){
            if(dp[i][(1<<n)-1]+weights[i][0]<ans){
                ans=dp[i][(1<<n)-1]+weights[i][0];
                optmsk=(1<<n)-1;
                optlast=i;
            }
        }
        ArrayList<DSUEdge> path=new ArrayList<DSUEdge>();
        path.add(new DSUEdge(optlast,0,weights[optlast][0]));
        while(optmsk!=1){
            boolean found=false;
            for(int prv=0;prv<n;prv++){
                if((optmsk>>prv&1)==1 && prv!=optlast && dp[prv][optmsk^(1<<optlast)]+weights[prv][optlast]==dp[optlast][optmsk]){
                    path.add(new DSUEdge(prv,optlast,weights[prv][optlast]));
                    optmsk^=(1<<optlast);
                    optlast=prv;
                    found=true;
                    break;
                }
            }
            assert(found);
        }
        Collections.reverse(path);
        return path;
    }
    private void printTSP(ArrayList<DSUEdge> ans, String algType, boolean printEdges){
        System.out.print(algType);
        if(ans==null){
            System.out.println(": no solution found");
            return;
        }
        double totalCost=0;
        for(DSUEdge e : ans)
            totalCost+=e.w;
        System.out.println(String.format(" (Total cost: %.3f):",totalCost));
        if(printEdges){
            System.out.print(ans.get(0).u);
            for(DSUEdge e : ans){
                System.out.print(String.format(" -> %2$d",e.w,e.v));
            }
            System.out.println("");
        }
    }
    public void advanced(int numNodes){
        System.out.println("-- Advanced --");
        if(numNodes<3){
            System.out.println("Graph is too small");
            return;
        }
        int n=numNodes;
        Random rnd=new Random();
        Pair[] pts=new Pair[n];
        double[][] weights=new double[n][n];
        for(int i=0;i<n;i++) pts[i]=new Pair(rnd.nextInt(-10000,10000),rnd.nextInt(-10000,10000));
        for(int i=0;i<n;i++){
            for(int j=i;j<n;j++){
                weights[i][j]=weights[j][i]=Math.hypot(pts[i].first-pts[j].first,pts[i].second-pts[j].second);
            }
        }
        printTSP(bktTSP(n,weights),"Backtracking", true);
        printTSP(mstTSP(n,weights),"MST-Based", true);
        printTSP(christofidesTSP(n,weights),"Christofides", true);

    }
    public static void main(String[] args){
        Faker f = new Faker();
        // KruskalMinimumSpanningTree mlc=new KruskalMinimumSpanningTree();
        Main solve=new Main();
        solve.mandatory();
        solve.homework(190,1000,30);
        solve.advanced(20);
    }
}
