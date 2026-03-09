import java.util.HashMap;
import java.util.TreeSet;
public class Company extends ProfileImpl{

    @Override
    public String toString(){
        return String.format("Company Profile %1$s, Relationship count: %2$d",name,relationshipCount());
    }

    protected TreeSet<Employee> employees;
    public Company(){
        this.relationships=new HashMap<Profile,RelationshipType>();
    }
    public Company(String name){
        this.name=name;
        this.relationships=new HashMap<Profile,RelationshipType>();
    }
    public boolean addEmployee(Employee p){
        return employees.add(p);
    }
    public boolean removeEmployee(Employee p){
        return employees.remove(p);
    }
    public TreeSet<Employee> getEmployees(){
        return employees;
    }
}