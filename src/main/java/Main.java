import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class Main {



    public static void main(String[] args) throws FileNotFoundException {
        Graph graph = new Graph("C:/Users/Erik Gustafsson/Documents/Skolarbete/ALDA/Bacon2ElectricBoogaloo/src/main/resources/moviedata.txt");
        searchLoop(graph);
        //System.out.println(graph.findPathToBacon("Bergqvist, Kjell"));
        //System.out.println(graph.findPathToBacon("Bacon, Kevin (I)"));
        //System.out.println(graph.findPathToBacon("De Niro, Robert"));
        System.out.println("Bye!");
    }

    private static void searchLoop(Graph graph) { //Är detta nödvändigt?
        Scanner input = new Scanner(System.in);
        String search = "";
        while (!search.equals("end")) {
            System.out.println("?");
            search = input.nextLine();
            if (!search.equals("end")) {
                System.out.println(graph.findPathToBacon(search));
            }
        }
    }


}
