import java.util.*;
import java.util.stream.IntStream;
public class Main {
    private LinkedList<Street> streets;
    private HashSet<Intersection> intersections;
    public Main(){
        streets=new LinkedList<Street>();
        intersections=new HashSet<Intersection>();
    }
    public void addStreet(Street s){
        streets.add(s);
    }
    public void addIntersection(Intersection i){
        intersections.add(i);
    }
    public boolean checkDistinct(){
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
    public void Mandatory(){
        System.out.println("-- Mandatory -- ");
        ArrayList<Intersection> intersectionArr = new ArrayList<Intersection>();
        IntStream.rangeClosed(0, 9).forEach((i)->{
            intersections.add(new Intersection(""+i));
            intersectionArr.add(new Intersection(""+i));
        });
        for(Intersection i : intersections){
            System.out.println(i);
        }
        System.out.println();
        if(!checkDistinct()){
            System.out.println("Names are not distinct");
            return;
        }
        Random rnd=new Random();
        int n=intersections.size();
        
        while(streets.size()<20){
            int u=rnd.nextInt(n);
            int v=rnd.nextInt(n);
            float f=rnd.nextFloat()*1000;
            streets.add(new Street(intersectionArr.get(u),intersectionArr.get(v),f));
        }

        ArrayList<Street> streetArr = new ArrayList<Street>();
        for(Street s : streets) streetArr.add(s); 
        Collections.sort(streetArr);

       for(Street s : streetArr) System.out.println(s);

    }
    public static void main(String[] args){
        Main solve=new Main();
        solve.Mandatory();
    }
}
