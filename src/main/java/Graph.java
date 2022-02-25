import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Graph {

    //Filmen är nyckel i form av en sträng
    private final HashMap<String, ArrayList<Actor>> graphMap;
    private final HashSet<String> actors; //namnet på skådelspelaren
    private final HashMap<String, ArrayList<String>> movies; //titeln på filmen, sedan namnet på skådespelare

    /**
     * Konstruktor
     *
     * @param file   Textfilen som innehåller skådespelare och filmer
     *
     */
    public Graph(String file) {
        movies = new HashMap<>();
        graphMap = new HashMap<>();
        actors = new HashSet<>();
        buildGraph(file);
    }

    private void buildGraph(String file) {

    }

    private void addActorsFromMovie(String movieName) {

    }

    public void makeGraph(String fileName) throws FileNotFoundException {
        try {
            FileReader reader = new FileReader(fileName);
            BufferedReader in = new BufferedReader(reader);
            String line = null;
            line = in.readLine();
            while (line != null) {
                String[] tokens = line.split(">");
                String command = tokens[0];
                if (command.equals("<a")) {
                    Actor actor = new Actor(tokens[1]);
                    actors.add(actor.getName());
                    line = in.readLine();
                    tokens = line.split(">");
                    command = tokens[0];
                    while (!command.equals("<a")) {
                        if (movies.containsKey(tokens[1])) {
                            movies.get(tokens[1]).add(actor.getName());
                        } else {
                            movies.put(tokens[1], new ArrayList<String>());
                            movies.get(tokens[1]).add(actor.getName());
                        }
                        line = in.readLine();
                        tokens = line.split(">");
                        command = tokens[0];
                    }
                }
                line = in.readLine();
            }
            reader.close();
            in.close();

        } catch (
                FileNotFoundException e) {
            throw new FileNotFoundException("File not found!");
        } catch (
                IOException e) {
            System.err.println("IO-fel! " + e.getMessage());
        }


    }
}
