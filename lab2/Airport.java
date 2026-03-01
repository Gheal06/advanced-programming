//package lab2;
public final class Airport extends Location{
    protected int capacity;
    public Airport(String name, int capacity, float x, float y){ /// constructor cu toate proprietatile
        this.name=new String(name);
        this.capacity=capacity;
        this.x=x;
        this.y=y;
    }
    public Airport(Airport oth){ /// "copy" constructor
        this.name=oth.getName();
        this.x=oth.getX();
        this.y=oth.getY();
    }
    public Airport(){ /// constructor default
        this.name=new String();
        this.capacity=0;
        this.x=0;
        this.y=0;
    }
    public int getCapacity(){
        return capacity;
    }
    public void setCapacity(int capacity){
        this.capacity=capacity;
    }
    @Override
    public String toString(){ /// returneaza "Airport name (x: x, y: y, capacity: capacity)"
        return (new StringBuilder().append("Airport ")
                                   .append(this.name)
                                   .append("(x:")
                                   .append(this.x)
                                   .append(", y:")
                                   .append(this.y)
                                   .append(", capacity:")
                                   .append(this.capacity)
                                   .append(")")).toString();
    }
    @Override
    public boolean equals(Object oth){ /// override la metoda Object.equals()
        if(oth==null || !(oth instanceof Airport)) return false;
        Airport other = (Airport) oth;
        return name.equals(other.getName()) && this.x==other.x && this.y==other.y && this.capacity==other.capacity;
    }
}
