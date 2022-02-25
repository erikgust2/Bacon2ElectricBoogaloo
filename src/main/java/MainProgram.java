import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MainProgram {

    public void makeGraph(String fileName) /*throws FileNotFoundException*/ {
      /*  try {
            FileReader reader = new FileReader(fileName);
            BufferedReader in = new BufferedReader(reader);
            String line = null;
            line = in.readLine();
            while line 1= null{

                String[] tokens = line.split(";");
                String command = tokens[0];
                if(command.equals("a")){
                    String actor = tokens[1];
                }
                if(command.equals("t")){
                    String movie = tokens[1];
                }

                line = in.readLine();

                int genresInt = Integer.parseInt(line);
                Set<String> genres = new HashSet<>();
                for (int j = 0; j < genresInt; j++) {
                    //loop för genre
                    String genre = in.readLine();
                    genres.add(genre);
                }
                Recording r = new Recording(title, artist, year, genres);
                recordings.add(new Recording(title, artist, year, genres));
                line = in.readLine();
            }
            reader.close();
            in.close();

        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File not found!");
        } catch (IOException e) {
            System.err.println("IO-fel! " + e.getMessage());
        }

    }*/


    }
}
