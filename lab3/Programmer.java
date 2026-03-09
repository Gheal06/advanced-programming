import java.util.ArrayList;
import java.util.HashMap;
import java.sql.Date;
public class Programmer extends Employee{
    @Override
    public String toString(){
        return String.format("Programmer Profile %1$s, Relationship count: %2$d",name,relationshipCount());
    }

    protected ArrayList<String> skills;
    public Programmer(){
        skills=new ArrayList<String>();
    }
    public Programmer(String name){
        this.name=name;
        skills=new ArrayList<String>();
        relationships=new HashMap<Profile,RelationshipType>();
    }
    public Programmer(String name, Date birthdate){
        this.name=name;
        this.birthdate=birthdate;
        skills=new ArrayList<String>();
        relationships=new HashMap<Profile,RelationshipType>();
    }
    public boolean addSkill(String skillName){
        return skills.add(skillName);
    }
    public boolean removeSkill(String skillName){
        return skills.remove(skillName);
    }
}
