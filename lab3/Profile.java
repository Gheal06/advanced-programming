//package lab2;
public interface Profile extends Comparable<Profile>{
    public String getName();
    public void setName(String name);
    public int relationshipCount();
    public RelationshipType setRelationshipStatus(Profile oth, RelationshipType status); 
    public int compareTo(Profile oth);
    public String toString();
}
