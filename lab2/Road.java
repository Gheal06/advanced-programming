//package lab2;
//import lab2.Location;
//import lab2.RoadType;
public class Road {
    private String name;
    private RoadType type;
    private Location from, to;
    private float len;
    public Road(){ /// constructor default
        name=new String();
        type=RoadType.UNSET;
    }
    public Road(String name, RoadType type, Location from, Location to, float len){ /// constructor cu toate proprietatile
        this.name=new String(name);
        this.type=type;
        this.from=from;
        this.to=to;
        this.len=len;
    }

    public Road(Road oth){ /// "copy" constructor
        this.name=oth.getName();
        this.type=oth.getType();
        this.from=oth.getFrom();
        this.to=oth.getTo();
        this.len=oth.len;
    }
    public String toString(){ /// returneaza "Road name (from.name -> to.name): len"
        return (new StringBuilder().append("Road ")
                                   .append(this.name)
                                   .append(" (")
                                   .append(this.from.getName())
                                   .append(" -> ")
                                   .append(this.to.getName())
                                   .append("): ")
                                   .append(len)).toString();
    }
    @Override
    public boolean equals(Object oth){ /// override la metoda Road.equals()
        if(oth==null || !(oth instanceof Road)) return false;
        Road other = (Road) oth;
        return this.name.equals(other.getName()) && 
               this.type==other.type && this.len==other.getLen() && 
               this.from.equals(other.getFrom()) && 
               this.to.equals(other.getTo());
    }
    public String getName(){
        return new String(name);
    }
    public RoadType getType(){
        return type;
    }
    public Location getFrom(){
        return from;
    }
    public Location getTo(){
        return to;
    }
    public float getLen(){
        return len;
    }

    public void setName(String name){
        this.name=new String(name);
    }
    public void setType(RoadType type){
        this.type=type;
    }
    public void setFrom(Location from){
        this.from=from;
    }
    public void setTo(Location to){
        this.to=to;
    }
    public void setLen(float len){
        this.len=len;
    }
}
