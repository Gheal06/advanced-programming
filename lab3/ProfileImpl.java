import java.util.HashMap;

public abstract class ProfileImpl implements Profile{
    protected String name;
    protected HashMap<Profile,RelationshipType> relationships;
    public String getName() {
        return this.name;
    }

    public void setName(String var1) {
        this.name = var1;
    }
    public int relationshipCount(){
        return relationships.size();
    }
    public RelationshipType setRelationshipStatus(Profile oth, RelationshipType status){
        if(status==RelationshipType.NONE){
            return relationships.remove(oth);
        }
        else{
            return relationships.put(oth,status);
        }
    }
    public String toString(){
        return String.format("Standard Profile %1$s, Relationship count: %2$d",name,relationshipCount());
    }
    @Override
    public int compareTo(Profile var1) {
        return this.name.compareTo(var1.getName());
    }
}
