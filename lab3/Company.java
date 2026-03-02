public class Company implements Profile{
    private String name;
    public Company(String name){
        this.name=name;
    }
    public String getName(){
        return name;
    }
    public void setName(String s){
        name=s;
    }
    @Override
    public int compareTo(Profile oth){
        return this.name.compareTo(oth.getName());
    }
}