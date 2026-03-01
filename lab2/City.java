//package lab2;
public final class City extends Location{
    protected int population;
    public City(String name, int population, float x, float y){ /// constructor cu toate proprietatile
        this.name=new String(name);
        this.population=population;
        this.x=x;
        this.y=y;
    }
    public City(Airport oth){ /// "copy" constructor
        this.name=oth.getName();
        this.x=oth.getX();
        this.y=oth.getY();
    }
    public City(){ /// constructor default
        this.name=new String();
        this.population=0;
        this.x=0;
        this.y=0;
    }
    public int getPopulation(){
        return population;
    }
    public void setPopulation(int population){
        this.population=population;
    }
    @Override
    public String toString(){ /// returneaza "City name (x: x, y: y, population: population)"
        return (new StringBuilder().append("City ")
                                   .append(this.name)
                                   .append("(x:")
                                   .append(this.x)
                                   .append(", y:")
                                   .append(this.y)
                                   .append(", population:")
                                   .append(this.population)
                                   .append(")")).toString();
    }
    @Override
    public boolean equals(Object oth){ /// override la metoda Object.equals()
        if(oth==null || !(oth instanceof City)) return false;
        City other = (City) oth;
        return name.equals(other.getName()) && this.x==other.x && this.y==other.y && this.population==other.population;
    }
}
