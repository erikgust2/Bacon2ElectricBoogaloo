import java.util.ArrayList;

public class Actor {
    private String name;
    private ArrayList<String> movies;
    private int baconDistance;

    public Actor(String name, ArrayList<String> movies){
        this.name = name;
        this.movies.addAll(movies);
    }

    public void setBaconDistance(int baconDistance) {
        this.baconDistance = baconDistance;
    }
    public void addMove(String movie){
        movies.add(movie);
    }
    public String getName(){
        return name;
    }
    public ArrayList<String> getMovies(){
        if(movies.size() == 0){
            return null;
        }
        ArrayList<String> output = new ArrayList<>();
        output.addAll(movies);
        return output;
    }
}
