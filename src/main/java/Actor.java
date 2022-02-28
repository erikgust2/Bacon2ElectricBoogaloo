import java.util.ArrayList;

public class Actor {
    private final String name;
    private int baconNumber;

    public Actor(String name) {
        this.name = name;
    }

    public void setBaconDistance(int baconDistance) {
        this.baconNumber = baconDistance;
    }

    public String getName() {
        return name;
    }

    public int getBaconNumber() {
        return baconNumber;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Actor) {
            return ((Actor) o).getName().equals(this.name);
        }
        return false;
    }


}
