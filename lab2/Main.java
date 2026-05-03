//package lab2;
import java.util.*;


public class Main{
    private static ArrayList<Road> roadInfo = new ArrayList<Road> ();
    private static ArrayList<Integer> roadTo = new ArrayList<Integer> ();
    private static ArrayList<Location> locationInfo = new ArrayList<Location>();
    private static ArrayList<ArrayList<Integer>> edges;
    public static float getDistance(int u, int v){ /// returns euclidean distance between locations u and v
        return (float)Math.hypot(locationInfo.get(u).getX()-locationInfo.get(v).getX(),locationInfo.get(u).getY()-locationInfo.get(v).getY());
    }
    public static void validate(){ /// validates problem instance
        int n=edges.size();
        assert roadInfo.size() == roadTo.size() : "roadInfo and roadTo have different lengths";
        for(int u=0;u<n;u++){
            ArrayList<Integer> edg=edges.get(u);
            for(int j=0;j<edg.size();j++){
                assert edg.get(j)>=0 && edg.get(j)<roadInfo.size() : "edges contains an invalid edge id";
                Road e=roadInfo.get(edg.get(j));
                int v=roadTo.get(edg.get(j));
                assert v>=0 && v<n : "Invalid location id in roadTo";
                assert locationInfo.get(u).equals(e.getFrom()) : "Invalid from";
                assert locationInfo.get(v).equals(e.getTo()) : "Invalid to";
                assert e.getLen()>=getDistance(u,v) : "Length of road is impossibly small";
            }
        }
    }
    public static void addCity(String name, int population, float x, float y){ /// function to add city
        City tmp=new City(name, population, x, y);
        locationInfo.add(tmp);
    }
    public static void addRoad(String name, RoadType tp, int u, int v, float extralen){ /// function to add road
        assert u<locationInfo.size() && v<locationInfo.size() : "Invalid location ids provided";
        float len=getDistance(u,v);
        Road tmp=new Road(name, tp, locationInfo.get(u), locationInfo.get(v), len+extralen);
        edges.get(u).add(roadInfo.size());
        roadInfo.add(tmp);
        roadTo.add(v);
    }
    static class Pair{ /// pair for dijkstra's algorithm
        public float first; /// current distance from node 0
        public int second; /// node id
        Pair(float first, int second){
            this.first=first;
            this.second=second;
        }
        Pair(){
            this.first=0f;
            this.second=0;
        }
    }
    static class Cmp implements Comparator<Pair> {
        @Override
        public int compare(Pair p1, Pair p2)
        {
            if(p1.first==p2.first) return p1.second-p2.second;
            if(p1.first<p2.first) return -1;
            if(p1.first>p2.first) return 1;
            return 0;
        }
    }
    public static void solve(){
        PriorityQueue<Pair> pq=new PriorityQueue<>(new Cmp());
        int n=locationInfo.size();
        float[] dist = new float[n]; /// distance from node 0
        int[] prv = new int [n]; /// previous node in path from node 0
        for(int i=0;i<n;i++)
            prv[i]=-1;
        Road[] prvRoad = new Road [n]; /// previous road in path from node 0
        for(int i=0;i<n;i++) dist[i]=Float.MAX_VALUE;
        dist[0]=0;
        pq.add(new Pair(dist[0],0));
        while(pq.size()>0){
            int u=pq.poll().second;
            for(int i=0;i<edges.get(u).size();i++){
                int id=edges.get(u).get(i);
                Road e=roadInfo.get(id);
                float newDist=dist[u]+e.getLen();
                int v=roadTo.get(id);
                if(newDist<dist[v]){
                    prv[v]=u;
                    prvRoad[v]=e;
                    dist[v]=newDist;
                    pq.add(new Pair(dist[v],v));
                }
            }
        }
        if(prv[n-1]==-1){ /// no paths found
            System.out.println("No paths found");
            return;
        }
        int u=n-1;
        ArrayList<Road> roads=new ArrayList<Road>();
        while(u!=0){
            roads.add(prvRoad[u]);
            u=prv[u];
        }
        for(int i=0;i*2+1<roads.size();i++){
            Road tmp=roads.get(i);
            roads.set(i,roads.get(roads.size()-i-1));
            roads.set(roads.size()-i-1,tmp);
        }

        if(roads.size()>20) 
            System.out.println("...");
        for(int i=0;i<roads.size();i++){
            if(roads.size()-i<=20)
                System.out.println(roads.get(i));
        }
        System.out.println("Total length: "+dist[n-1]);
    }
    public static void main(String args[]){ /// main function
        long start_ts=System.currentTimeMillis();
        int n, m;
        if(args.length!=2){
            System.out.println("Usage: java Main.java n m");
            return;
        }
        n=Integer.parseInt(args[0]);
        edges = new ArrayList<ArrayList<Integer>>();
        for(int i=0;i<n;i++)
            edges.add(new ArrayList<Integer>());
        m=Integer.parseInt(args[1]);

        Random rnd = new Random();
        
        for(int i=0;i<n;i++){
            float x=(float)(rnd.nextDouble()*1e4);
            float y=(float)(rnd.nextDouble()*1e4);
            int population=rnd.nextInt(100,(int)1e5);
            String name=""+i;
            addCity(name, population, x, y);
        }
        for(int i=0;i<m;i++){
            float extralen=(float)(rnd.nextDouble()*10);
            RoadType tp=RoadType.UNSET;
            String name=""+i;
            int u=rnd.nextInt(n);
            int v=rnd.nextInt(n);
            addRoad(name, tp, u, v, extralen);
        }
        long end_ts=System.currentTimeMillis();
        System.out.println("Generation completed: "+(end_ts-start_ts)+"ms");
        end_ts^=start_ts; start_ts^=end_ts; end_ts^=start_ts; /// xorswap(end_ts, start_ts); 
        
        validate();
        end_ts=System.currentTimeMillis();
        System.out.println("Validation completed: "+(end_ts-start_ts)+"ms");
        end_ts^=start_ts; start_ts^=end_ts; end_ts^=start_ts; /// xorswap(end_ts, start_ts); 
        
        solve();
        end_ts=System.currentTimeMillis();
        System.out.println("Solution completed: "+(end_ts-start_ts)+"ms");
        end_ts^=start_ts; start_ts^=end_ts; end_ts^=start_ts; /// xorswap(end_ts, start_ts); 
    }
}
