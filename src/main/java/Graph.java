import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Graph {


    private final static String BACON = "Bacon, Kevin (I)";
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
        actors.put("Bagge, Anders", new ArrayList<>());
        actors.get("Bagge, Anders").add("Beck");
        movies.put("Beck", new ArrayList<>());
        movies.get("Beck").add("Bagge, Anders");
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
                        if (line == null) {
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
        if(!actors.containsKey(startActor)){
            return "No such actor in the database!";
        }
        if (startActor.equals(BACON)) {
            List<String> path = computePath(startActor, null);
            return buildPathString(path);
        }
        Queue<String> queue = new LinkedList<>();
        HashSet<String> visited = new HashSet<>();
        HashMap<String, String> steps = new HashMap<>();
        queue.add(startActor);
        String current = startActor;

        while (!queue.isEmpty()) {
            current = queue.poll();
            if (current.equals(BACON)) {
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
        String current = BACON;
        if (startActor.equals(BACON)) {
            path.add(BACON);
            return path;
        }
        if (!steps.containsKey(BACON)) {
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
        if(path.size() == 0){
            return "This actor does not have a Bacon-number";
        }
        StringBuilder output = new StringBuilder();
        if (path.size() / 2 == 1) {
            output.append("\"" + path.get(0) + "\" is " + path.size() / 2 + " step away from Kevin B. The path is ");
        } else {
            output.append("\"" + path.get(0) + "\" is " + path.size() / 2 + " steps away from Kevin B. The path is ");
        }
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
