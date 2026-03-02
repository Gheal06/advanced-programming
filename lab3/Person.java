public class Person implements Profile{
    private String name;
    public Person(String name){
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