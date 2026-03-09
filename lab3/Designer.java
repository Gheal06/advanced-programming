public class Designer extends Employee{

    @Override
    public String toString(){
        return String.format("Designer Profile %1$s, Relationship count: %2$d",name,relationshipCount());
    }

    protected int experience;
    public boolean setExperience(int experience){
        if(experience<0 || experience > 99) return false;
        this.experience=experience;
        return true;
    }
    public int getExperience(){
        return this.experience;
    }
}
