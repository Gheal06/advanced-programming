package hw1;
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
    public static void main(String args[]){
        int n=20000;
        int m=n;
        int ax=3,ay=2,bx=10,by=17;
        short b[][] = new short[n][m];
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(i>=ax && i<=bx && j>=ay && j<=by)
                    b[i][j]=(short)15;
                else b[i][j]=(short)240;
            }
        }
        buildString(b);

        int cx=10, cy=10, r=8;
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
    
}