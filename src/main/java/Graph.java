import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Graph {

    private final HashMap<String, ArrayList<Actor>> graphMap;
    private final HashSet<Actor> actors;

    /**
     * Konstruktor
     *
     * @param file Textfilen som innehåller skådespelare och filmer
     */
    public Graph(String file) {
        graphMap = new HashMap<>();
        actors = new HashSet<>();
        buildGraph(file);
    }

    private void buildGraph(String file) {

    }

    private void addActorsFromMovie(String movieName) {

    }

}
