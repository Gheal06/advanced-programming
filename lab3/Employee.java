public abstract class Employee extends Person{
    protected Company employer;
    public void setEmployer(Company employer){
        if(employer!=null) this.employer.removeEmployee(this);
        this.employer=employer;
        if(employer!=null) this.employer.addEmployee(this);
    }
    public Company getEmployer(){
        return this.employer;
    }
}
