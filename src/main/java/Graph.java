import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Graph {

    //Filmen är nyckel i form av en sträng
    private final Actor bacon = new Actor("Bacon, Kevin (I)");
    private final HashMap<String, ArrayList<String>> actors; //namn på skådespelare som nyckel, lista av filmer som värde
    private final HashMap<String, ArrayList<String>> movies; //titeln på filmen, sedan namnet på skådespelare

    /**
     * Konstruktor
     *
     * @param file Textfilen som innehåller skådespelare och filmer
     */
    public Graph(String file) throws FileNotFoundException {
        movies = new HashMap<>();
        actors = new HashMap<>();
        buildGraph(file);
    }

    private void buildGraph(String fileName) throws FileNotFoundException {
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
                    if(!actors.containsKey(actor.getName())){
                        actors.put(actor.getName(), new ArrayList<String>());
                    }
                    line = in.readLine();
                    tokens = line.split(">");
                    command = tokens[0];
                    while (!command.equals("<a")) {
                        if (movies.containsKey(tokens[1])) {
                            actors.get(actor.getName()).add(tokens[1]);
                            movies.get(tokens[1]).add(actor.getName()); // lägg till i actors
                        } else {
                            actors.get(actor.getName()).add(tokens[1]);
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

    private void addActorsFromMovie(String movieName) {

    }

    public String findPathToBacon(String actor) {
        Actor searchActor = new Actor((actor));
        if (searchActor.equals(bacon)) {
            return searchActor.toString();
        }
     return searchActor.toString();
    }
    //"De Niro, Robert" is 1 step away from Kevin B. The path is <a>Bacon, Kevin


}
