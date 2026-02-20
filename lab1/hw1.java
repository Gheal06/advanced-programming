package hw1;
import java.io.File;                  
import java.io.FileNotFoundException;
import java.util.Scanner;
/**

    Gheorghies Alexandru, 2A1

*/
public class hw1{
    public static String charset="$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/\\|()1{}[]?-_+~<>i!lI;:,\"^`'. ";
    public static int cutoff=50;
    public static void buildString(short b[][]){
        long t1 = System.nanoTime();
        StringBuilder ans = new StringBuilder();
        for(int i=0;i<b.length;i++){
            for(int j=0;j<b[i].length;j++){
                //System.out.println(b[i][j]);
                int col=(int)Math.floor((float)b[i][j]/256*charset.length());

                //System.out.println(i+" "+j+" "+col);
                ans.append(charset.charAt(col));
            }
            ans.append("\n");
        }
        long t2 = System.nanoTime();
        if(b.length>=cutoff) System.out.println("Time elapsed: "+(t2-t1)+"ns ("+(float)(t2-t1)/1000000+" ms)");
        else System.out.println(ans);
    }
    public static void printBoundingBox(byte b[][], byte targetl, byte targetr){ /// consider ca figura contine celulele din matrice cu culoarea intre targetl si targetr
        long t1 = System.nanoTime();
        StringBuilder ans = new StringBuilder();
        int n=b.length,minx=n-1,miny=n-1,maxx=0,maxy=0;
        for(int i=0;i<b.length;i++){
            for(int j=0;j<b[i].length;j++){
                if(b[i][j]>=targetl && b[i][j]<=targetr){
                    minx=Math.min(minx,i);
                    miny=Math.min(miny,j);
                    maxx=Math.max(maxx,i);
                    maxy=Math.max(maxy,j);
                }
            }
        }
        if(minx<=maxx && miny<=maxy){
            for(int i=0;i<b.length;i++){
                for(int j=0;j<b[i].length;j++){
                    if((i==minx || i==maxx) && (j>=miny && j<=maxy)) ans.append("1");
                    else if((i>=minx && i<=maxx) && (j==miny || j==maxy)) ans.append("1");
                    else ans.append("0");
                }
                ans.append("\n");
            }
        }
        long t2 = System.nanoTime();
        if(b.length>=cutoff) System.out.println("Time elapsed: "+(t2-t1)+"ns ("+(float)(t2-t1)/1000000+" ms)");
        else{
            if(!(minx<=maxx && miny<=maxy)){
                System.out.println("No figure found");
                return;
            }
            System.out.println("Bounding box: ("+minx+", "+miny+") -> ("+maxx+", "+maxy+")");
            System.out.println(ans);
        }
    }
    public static void main(String args[]){
        if(args.length!=2){
            System.out.println("Usage: java hw1.java <n> <circle/rectangle/boundingbox>");
            return;
        }
        int n=Integer.parseInt(args[0]);
        if(args[1].equals("boundingbox")){
            if(n<1){
                System.out.println("n must be at least 1");
                return;
            }
            System.out.println("Please enter a binary matrix of size "+n+" x "+n+". Cells containing a value of 1 are considered part of the geometric shape.");
            byte fig[][]=new byte[n][n];
            Scanner cin=new Scanner(System.in);
            for(int i=0;i<n;i++){
                String buf=cin.nextLine();
                for(int j=0;j<n && j<buf.length();j++)
                    fig[i][j]=(byte)buf.charAt(j);
            }
            printBoundingBox(fig,(byte)49,(byte)49);
        }
        else{
            if(n<10){
                System.out.println("n must be at least 10");
                return;
            }
            int m=n;
            short b[][] = new short[n][m];
            if(args[1].equals("rectangle")){
                int ax=n/4,ay=n/3,bx=n/4*3,by=n/3*2;
                for(int i=0;i<n;i++){
                    for(int j=0;j<m;j++){
                        if(i>=ax && i<=bx && j>=ay && j<=by)
                            b[i][j]=(short)15;
                        else b[i][j]=(short)240;
                    }
                }
                buildString(b);
            }
            else if(args[1].equals("circle")){
                int cx=n/2, cy=n/2, r=n/4;
                for(int i=0;i<n;i++){
                    for(int j=0;j<m;j++){
                        int dist=(i-cx)*(i-cx)+(j-cy)*(j-cy);
                        if(dist>=r*r && dist<(r+1)*(r+1))
                            b[i][j]=(short)240;
                        else b[i][j]=(short)15;
                    }
                }
                buildString(b);
            }
            else{
                System.out.println("Usage: java hw1.java <n> <circle/rectangle>");
                return;
            }
        }
    }
}