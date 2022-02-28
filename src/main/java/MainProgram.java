import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MainProgram {

    public static void main(String[] args) throws FileNotFoundException {
        Graph graph = new Graph("C:/Users/a_luc/IdeaProjects/Bacon2ElectricBoogaloo/src/main/resources/moviedata.txt");
        System.out.println(graph.findPathToBacon("Bergqvist, Kjell"));
        System.out.println(graph.findPathToBacon("Bacon, Kevin (I)"));

    }




}
