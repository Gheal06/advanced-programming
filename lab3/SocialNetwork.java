import java.util.*;
public class SocialNetwork {
    protected ArrayList<Profile> profiles;
    protected ArrayList<ArrayList<Integer>> relationships;
    public SocialNetwork(){
        profiles=new ArrayList<Profile>();
        relationships=new ArrayList<ArrayList<Integer>>();
    }
    public ArrayList<Profile> getProfiles(){
        return profiles;
    }
    public ArrayList<ArrayList<Integer>> getRelationships(){
        return relationships;
    }
    public boolean addProfile(Profile profile){
        relationships.add(new ArrayList<Integer>());
        return profiles.add(profile);
    }
    public void clear(){
        profiles.clear();
        relationships.clear();
    }
    public boolean addRelationship(int u, int v, RelationshipType reltype){
        if(u<0 || u>=profiles.size() || v<0 || v>=profiles.size()) return false;
        profiles.get(u).setRelationshipStatus(profiles.get(v),reltype);
        profiles.get(v).setRelationshipStatus(profiles.get(u),reltype);
        relationships.get(u).add(v);
        relationships.get(v).add(u);
        return true;
    }
}
