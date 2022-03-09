/*
 * Skapad i grupp.
 * Gruppmedlemmar:
 * Erik Gustafsson, ergu 5366
 * Anton Lückner, anlu6569
 */

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
     * @param file Fil-sökväg till textfilen som innehåller skådespelare och filmer
     *
     * @throws FileNotFoundException om fil-sökvägen av någon anledning inte fungerar
     */
    public Graph(String file) throws FileNotFoundException {
        movies = new HashMap<>();
        actors = new HashMap<>();
        buildGraph(file);
    }

    /**
     * Hjälpmetod till konstruktorn
     *
     * Tar filen med all data i plaintext och läser in den. Bygger stegvis upp grafen med filmer och skådespelare
     * som noder.
     *
     * @param fileName Sökväg till textfilen som innehåller skådespelare och filmer. Skickas från konstruktorn
     *
     * @throws FileNotFoundException Om fil-sökvägen av någon anledning inte fungerar
     */
    private void buildGraph(String fileName) throws FileNotFoundException {
        actors.put("Bagge, Anders", new ArrayList<>());
        actors.get("Bagge, Anders").add("Beck");
        movies.put("Beck", new ArrayList<>());
        movies.get("Beck").add("Bagge, Anders");
        try {
            FileReader reader = new FileReader(fileName);
            BufferedReader in = new BufferedReader(reader);
            String line;
            line = in.readLine();
            while (line != null) {
                String[] tokens = line.split(">");
                String command = tokens[0];
                if (command.equals("<a")) {

                    String actor = tokens[1];
                    if (!actors.containsKey(actor)) {
                        actors.put(actor, new ArrayList<>());
                    }
                    line = in.readLine();
                    tokens = line.split(">");
                    command = tokens[0];
                    while (!command.equals("<a")) {
                        if (movies.containsKey(tokens[1])) {
                            actors.get(actor).add(tokens[1]);
                            movies.get(tokens[1]).add(actor);
                        } else {
                            actors.get(actor).add(tokens[1]);
                            movies.put(tokens[1], new ArrayList<>());
                            movies.get(tokens[1]).add(actor);
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

    /**
     * En bredden-först-sökning som börjar vid skådespelaren och sedan söker sig
     * igenom grafen tills den hittar bacon eller får slut på anslutningar.
     *
     * @param startActor Skådespelaren vars relation till Kevin Bacon skall undersökas. Matas in med formatet
     *                   "Efternamn, Förnamn", exempelvis "De Niro, Robert"
     *
     * @return Vägen från vald skådespelare till bacon returneras som en sträng med hjälp av metoden "buildPathString"
     *         Om skådespelaren inte finns i databasen kommer detta att returneras av metoden som en sträng.
     *         Om det saknas ett baconnummer returneras detta också som en sträng.
     */
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
        String current;

        while (!queue.isEmpty()) {
            current = queue.poll();
            if (current.equals(BACON)) {
                break;
            }
            //visited.add(current);
            if (actors.containsKey(current)) {
                for (String movie : actors.get(current)) {
                    if (!steps.containsKey(movie)) {
                        queue.add(movie);
                        steps.putIfAbsent(movie, current);
                    }
                }
            } else if (movies.containsKey(current)) {
                for (String actor : movies.get(current)) {
                    if (!steps.containsKey(actor)) {
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

    /**
     * Hjälpmetod till findPathToBacon som tar HashMap-en av steg, börjar vid Bacon och stegar bakåt till angiven
     * skådespelare. Bygger en lista av noder som passerats igenom och returnerar den reverserad. Om Kevin Bacon inte
     * finns returneras en tom lista. Om den bara innehåller Kevin Bacon (alltså att han då var startnoden vid
     * sökningen) så returneras en lista med bara honom i.
     *
     * @param startActor Skådespelaren som baconnumret beräknas utifrån. Matas in med formatet
     *                   "Efternamn, Förnamn", exempelvis "De Niro, Robert"
     *
     * @param steps En HashMap av noder som stegats igenom för att nå Kevin Bacon. Nyckeln är noden som man kom till
     *              och värdet är noden man gick ifrån.
     *
     * @return En lista av alla noder på den kortaste vägen från angiven skådespelare till Kevin Bacon
     */
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

    /**
     * Hjälpmetod till findPathToBacon som tar den färdigtraverserade rutten och konverterar den till en sträng.
     *
     * Exempelkörning vid input "De Niro, Robert":
     * "De Niro, Robert" is 1 step away from Kevin B. The path is <a>Bacon, Kevin
     *
     * @param path En Lista med alla noder på den kortaste vägen från angiven Skådespelare till Kevin Bacon
     *
     * @return Den traverserade vägen på strängformat med det totala bacon-numret angivet.
     */
    private String buildPathString(List<String> path) {
        if(path.size() == 0){
            return "This actor does not have a Bacon-number";
        }
        StringBuilder output = new StringBuilder();
        if (path.size() / 2 == 1) {
            output.append("\"").append(path.get(0)).append("\" is ").append(path.size() / 2).append(" step away from Kevin B. The path is ");
        } else {
            output.append("\"").append(path.get(0)).append("\" is ").append(path.size() / 2).append(" steps away from Kevin B. The path is ");
        }
        for (String element : path) {
            if (actors.containsKey(element)) {
                output.append("<a> ").append(element).append(" </a>");
            }
            if (movies.containsKey(element)) {
                output.append("<t> ").append(element).append(" </t>");
            }
        }
        return output.toString();
    }

}
