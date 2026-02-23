package lab2;

public class Road {
    private String name;
    private RoadType type;
    private Location from, to;
    private float len;
    public Road(){ /// constructor default
        name=new String();
        type=RoadType.UNSET;
        from=new Location();
        to=new Location();
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
    public String toString(){ /// returneaza name (from.name -> to.name)
        return (new StringBuilder().append(this.name)
                                   .append(" (")
                                   .append(this.from.getName())
                                   .append(" -> ")
                                   .append(this.to.getName())+")").toString();
    }
    public String getName(){
        return new String(name);
    }
    public RoadType getType(){
        return type;
    }
    public Location getFrom(){
        return new Location(from);
    }
    public Location getTo(){
        return new Location(to);
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
        this.from=new Location(from);
    }
    public void setTo(Location to){
        this.to=new Location(to);
    }
    public void getLen(float len){
        this.len=len;
    }
}
