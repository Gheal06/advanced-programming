//package lab2;
public abstract sealed class Location permits Airport, City{
    protected String name;
    protected float x, y;
    public Location(String name, float x, float y){ /// constructor cu toate proprietatile
        this.name=new String(name);
        this.x=x;
        this.y=y;
    }
    public Location(Location oth){ /// "copy" constructor
        this.name=oth.getName();
        this.x=oth.getX();
        this.y=oth.getY();
    }
    public Location(){ /// constructor default
        this.name=new String();
        this.x=0;
        this.y=0;
    }
    public void setX(float x){ /// setter x
        this.x=x;
    }
    public void setY(float y){ /// setter y
        this.y=y;
    }
    public void setName(String name){ /// setter nume
        this.name=new String(name);
    }
    public float getX(){ /// getter x
        return this.x;
    }
    public float getY(){ /// getter y
        return this.y;
    }
    public String getName(){ /// getter nume
        return new String(this.name);
    }
    public String toString(){ /// returneaza "name (x: x, y: y)"
        return (new StringBuilder().append(this.name).append("(x:").append(this.x).append(", y:").append(this.y)+")").toString();
    }
    @Override
    public boolean equals(Object oth){ /// override la metoda Object.equals()
        if(oth==null || !(oth instanceof Location)) return false;
        Location other = (Location) oth;
        return name.equals(other.getName()) && this.x==other.x && this.y==other.y;
    }
}
