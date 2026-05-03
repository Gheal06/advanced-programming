package com.gheal.java;

public class DSU{
    private int[] p, r;
    private int n; 
    public DSU(int n){
        this.n=n;
        p=new int[n];
        r=new int[n];
        for(int i=0;i<n;i++){
            p[i]=i;
            r[i]=1;
        }
    }
    public int find(int u){
        return p[u]=(u==p[u]?u:find(p[u]));
    }

    public boolean union(int u, int v){
        u=find(u);
        v=find(v);
        if(u==v) return false;
        if(r[u]>r[v]){
            u^=v;
            v^=u;
            u^=v;
        }
        p[u]=v;
        r[v]+=r[u];
        return true;
    }
}