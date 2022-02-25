import java.util.ArrayList;

public class Actor {
    private String name;

    private int baconDistance;

    public Actor(String name){
        this.name = name;

    }

    public void setBaconDistance(int baconDistance) {
        this.baconDistance = baconDistance;
    }

    public String getName(){
        return name;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Actor){
            return ((Actor) o).getName().equals(this.name);
        }
        return false;
    }



}
