import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

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
                if(line.startsWith("<a>Bacon, Kevin (I)")){
                    System.out.println("hi");
                }
                String[] tokens = line.split(">");
                String command = tokens[0];
                if (command.equals("<a")) {

                    Actor actor = new Actor(tokens[1]);
                    if (!actors.containsKey(actor.getName())) {
                        actors.put(actor.getName(), new ArrayList<String>());
                    }
                    line = in.readLine();
                    tokens = line.split(">");
                    command = tokens[0];
                    while (!command.equals("<a")) {
                        if (movies.containsKey(tokens[1])) {
                            actors.get(actor.getName()).add(tokens[1]);
                            movies.get(tokens[1]).add(actor.getName());
                        } else {
                            actors.get(actor.getName()).add(tokens[1]);
                            movies.put(tokens[1], new ArrayList<String>());
                            movies.get(tokens[1]).add(actor.getName());
                        }
                        line = in.readLine();
                        if(line == null){
                            break;
                        }
                        tokens = line.split(">");
                        command = tokens[0];
                    }
                }

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


    public String findPathToBacon(String startActor) {
        if (startActor.equals(bacon.getName())) {
            return startActor + 0;
        }
        Queue<String> queue = new LinkedList<>();
        HashSet<String> visited = new HashSet<>();
        HashMap<String, String> steps = new HashMap<>();
        queue.add(startActor);
        String current = startActor;

        while (!queue.isEmpty()) {
            current = queue.poll();
            if(current.equals(bacon.getName())){
                break;
            }
            visited.add(current);
            if (actors.containsKey(current)) {
                for (String movie : actors.get(current)) {
                    if (!visited.contains(movie)) {
                        queue.add(movie);
                        steps.putIfAbsent(movie, current);
                    }
                }
            } else if (movies.containsKey(current)) {
                for (String actor : movies.get(current)) {
                    if (!visited.contains(actor)) {
                        queue.add(actor);
                        steps.putIfAbsent(actor, current);
                    }
                }
            } else {
                throw new IllegalArgumentException("Error");
            }

        }
        List<String> path = computePath(startActor, steps);


        return buildPathString(path);
    }

    private List<String> computePath(String startActor, HashMap<String, String> steps) {
        List<String> path = new ArrayList<>();
        String current = bacon.getName();
        if (startActor.equals(bacon.getName())) {
            path.add(bacon.getName());
            return path;
        }
        if (!steps.containsKey(bacon.getName())) {
            return path;
        }
        while (!current.equals(startActor)) {
            path.add(current);
            current = steps.get(current);
        }
        path.add(startActor);
        Collections.reverse(path);
        return path;
    }

    private String buildPathString(List<String> path) {
        StringBuilder output = new StringBuilder();
        output.append("\"" + path.get(0) + "\" is " + path.size() / 2 + " steps away from Kevin B. The path is ");
        for (String element : path) {
            if (actors.containsKey(element)) {
                output.append("<a> " + element + " </a>");
            }
            if (movies.containsKey(element)) {
                output.append("<t> " + element + " </t>");
            }
        }
        return output.toString();
    }
    //"De Niro, Robert" is 1 step away from Kevin B. The path is <a>Bacon, Kevin
    //? Trump, Donald J.
    //      "Trump, Donald J." is 2 steps away from Kevin B. The path is <a>Bacon, Kevin
    //(I)<a><t>Diner (1982)<t><a>Daly, Tim (I)<a><t>The Associate (1996)<t><a>Trump, Donald J.</a>


}
