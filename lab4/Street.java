public class Street implements Comparable<Street>{
    private Intersection u;
    private Intersection v;
    private float len;
    public Street(){
        u=new Intersection();
        v=new Intersection();
        len=0f;
    }
    public Street(Street oth){
        u=new Intersection(oth.u);
        v=new Intersection(oth.v);
        len=oth.len;
    }
    public Street(Intersection from, Intersection to, float len){
        u=new Intersection(from);
        v=new Intersection(to);
        this.len=len;
    }
    public void setFrom(Intersection u){
        this.u=new Intersection(u);
    }
    public Intersection getFrom(){
        return u;
    }
    public void setTo(Intersection u){
        this.v=new Intersection(u);
    }
    public Intersection getTo(){
        return v;
    }
    public void setLength(float f){
        len=f;
    }
    public float getLength(){
        return len;
    }
    public int compareTo(Street oth){
        if(this.len<oth.len) return -1;
        if(this.len>oth.len) return 1;
        return 0;
    }
    public String toString(){
        return String.format("Street %1$s %2$s: len=%3$.2f",u.toString(),v.toString(),len);
    }
}
