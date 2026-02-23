package lab2;

public class Problem{
    public static void main(){ /// voi avea in viitor o clasa de main
        Location Iasi=new Location("Iasi",0.0f,0.0f);
        Location Bucuresti=new Location("Bucuresti",10.0f,10.0f);
        Road DN6=new Road("DN6",RoadType.NATIONAL,Iasi,Bucuresti,30.0f);
        
        System.out.println(Iasi);
        System.out.println(Bucuresti);
        System.out.println(DN6);
    }
}
