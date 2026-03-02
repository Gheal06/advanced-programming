//package lab2;
import java.util.*;

public class Main{

    public static void main(String args[]){
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
}
