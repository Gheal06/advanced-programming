import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

public class Designer extends Employee{


    @Override
    public String toString(){
        return String.format("Designer Profile %1$s, Relationship count: %2$d",name,relationshipCount());
    }

    protected int experience;
    public Designer(){}
    public Designer(String name){
        this.name=name;
        relationships=new HashMap<Profile,RelationshipType>();
    }
    public Designer(String name, Date birthdate){
        this.name=name;
        this.birthdate=birthdate;
        relationships=new HashMap<Profile,RelationshipType>();
    }

    public boolean setExperience(int experience){
        if(experience<0 || experience > 99) return false;
        this.experience=experience;
        return true;
    }
    public int getExperience(){
        return this.experience;
    }
}
