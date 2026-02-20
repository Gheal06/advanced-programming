/*
    Gheorghies Alexandru, 2A1
*/
public class lab1{
    public static void main(String args[]){
        System.out.println("p1:");
        System.out.println("Hello World!");
        
        System.out.println("p2:");

        String languages[] = {"C", "C++", "C#", "Python", "Go", "Rust", "JavaScript", "PHP", "Swift", "Java"};
        for(int i=0;i<languages.length;i++)
            System.out.println(languages[i]);
        
        System.out.println("p3:");

        int n = (int) (Math.random() * 1_000_000);
        n*=3;
        n+=0b10101;
        n+=0xff;
        n*=6;
        System.out.println(n);
        System.out.println("normal control digit: "+control(n));
        System.out.println("fast control digit: "+control_fast(n));
        int result=control_fast(n);
        
        System.out.println("p4:");

        System.out.println("Willy-nilly, this semester I will learn " + languages[result]);
    }
    public static int digitsum(int n){
        int s=0;
        while(n>0){
            s+=n%10;
            n/=10;
        }
        return s;
    }
    public static int control(int n){
        while(n>10) n=digitsum(n);
        return n;
    }
    public static int control_fast(int n){
        if(n==0) return 0;
        if(n%9==0) return 9;
        return n%9;
    }
    
}