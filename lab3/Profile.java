//package lab2;
public interface Profile extends Comparable<Profile>{
    public String getName();
    public void setName(String name);
    public int compareTo(Profile oth);
}
