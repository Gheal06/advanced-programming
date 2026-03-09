import java.sql.Date;
import java.util.HashMap;
public class Person extends ProfileImpl{
    protected Date birthdate;
    public Person(){
        this.relationships=new HashMap<Profile,RelationshipType>();
    }
    public Person(String name){
        this.name=name;
        this.relationships=new HashMap<Profile,RelationshipType>();
    }
    public Person(String name, Date birthdate){
        this.name=name;
        this.birthdate=birthdate;
        this.relationships=new HashMap<Profile,RelationshipType>();
    }
    public Date getBirthdate(){
        return birthdate;
    }
    public void setBirthdate(Date d){
        birthdate=d;
    }
}