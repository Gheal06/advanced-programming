/** 
    This Class implements Tarjan's Algorithm

    https://cp-algorithms.com/graph/cutpoints.html
*/
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;
public final class Tarjan{
    private int[] tin, low, bicompCnt;
    private boolean[] visited;
    private int n, m, timer, connectedComponentCount;
    private ArrayList<ArrayList<Integer>> edg;
    private ArrayList<Integer> criticalNodes;
    private ArrayList<ArrayList<Integer>> biconnectedComponents;
    private Stack<Pair> stack;
    public int getNodeCount(){
        return n;
    }
    public int getEdgeCount(){
        return m;
    }
    public int getConnectedComponentCount(){
        return connectedComponentCount;
    }
    public ArrayList<Integer> getCriticalNodes(){
        return criticalNodes;
    }
    public ArrayList<ArrayList<Integer>> getBiconnectedComponents(){
        return biconnectedComponents;
    }
    private void processComponent(int u, int p){
        //System.out.println("ez");
        int x,y;
        HashSet<Integer> currComp = new HashSet<Integer>();
        do{
            x=stack.peek().first;
            y=stack.peek().second;
            currComp.add(x);
            currComp.add(y);
            stack.pop();
        } while(stack.size()>0 && (x!=u || y!=p));
        ArrayList<Integer> buff=new ArrayList<Integer>();
        for(Integer node : currComp){
            buff.add(node);
            ++bicompCnt[node];
        }
        biconnectedComponents.add(buff);
    }
    private void dfs(int u, int p){
        //System.out.println(u);
        visited[u]=true;
        tin[u]=low[u]=timer++;
        int children=0;
        for(int v : edg.get(u)){
            if(v==p) continue;
            if(visited[v]){
                //System.out.println(String.format("u:%1$d v:%2$d p:%3$d",u,v,p));
                
                //System.out.println(String.format("low[u]:%1$d tin[v]:%2$d",low[u],tin[v]));
                low[u]=Math.min(low[u],tin[v]);
                //System.out.println(String.format("low[u]:%1$d tin[v]:%2$d",low[u],tin[v]));
            }
            else{
                stack.push(new Pair(v,u));
                dfs(v,u);
                low[u]=Math.min(low[u],low[v]);
                //System.out.println(String.format("u:%1$d v:%2$d p:%3$d",u,v,p));
                //System.out.println(String.format("low[v]:%1$d tin[u]:%2$d",low[v],tin[u]));
                if(low[v]>=tin[u]){
                    processComponent(v,u);
                }
                ++children;
            }
        }
    }
    public Tarjan(ArrayList<ArrayList<Integer>> edges){
        n=edges.size();
        edg=new ArrayList<ArrayList<Integer>>();
        for(int i=0;i<n;i++){
            edg.add(new ArrayList<Integer>(edges.get(i)));
            //for(int v : edg.get(i))
            //    System.out.println(String.format("%1$d %2$d",i,v));
        }
        //System.out.println(n);
        tin=new int[n];
        low=new int[n];
        bicompCnt=new int[n];
        visited=new boolean[n];
        timer=0;
        connectedComponentCount=0;
        stack=new Stack<Pair>();
        criticalNodes=new ArrayList<Integer>();
        biconnectedComponents=new ArrayList<ArrayList<Integer>>();
        for(int i=0;i<n;i++){
            tin[i]=low[i]=-1;
            visited[i]=false;
            m+=edg.get(i).size();
        }
        m/=2;
        //System.out.println(m);
        for(int i=0;i<n;i++){
            if(!visited[i]){
                dfs(i,-1);
                connectedComponentCount++;
            }
        }
        for(int i=0;i<n;i++){
            if(bicompCnt[i]>1)
                criticalNodes.add(i);
        }
        //for(int i=0;i<n;i++){
        //    System.out.println(String.format("%1$d %2$d",tin[i],low[i]));
        //}
    }
}
