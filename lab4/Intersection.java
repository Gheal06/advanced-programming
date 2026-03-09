public class Intersection {
    private String name;
    public Intersection(){
        name=new String();
    }
    public Intersection(Intersection oth){
        name=new String(oth.getName());
    }

    public Intersection(String name){
        this.name=new String(name);
    }
    public String getName(){
        return name;
    }
    public void setname(String s){
        name=new String(s);
    }
    public int hashCode(){
        if(name==null) return 0;
        return name.hashCode();
    }
    public String toString(){
        return name;
    }
}
