import java.io.*;

public class Test {
    public static void main(String[] args) {
        /* Reading all the necessary information from the file
         */
        try {
            File file = new File("fsa.txt");
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);

            String line = reader.readLine();
            int size = line.length();
            String states = line.substring(8, size - 1);

            String[] states1 = states.split(",");
            for (String word : states1) System.out.println(word);

            line = reader.readLine();
            size = line.length();
            states = line.substring(7, size - 1);
            String[] alpha = states.split(",");
            for (String word : alpha) System.out.println(word);

            line = reader.readLine();
            size = line.length();
            states = line.substring(9, size - 1);
            String[] initSt = states.split(",");
            for (String word : initSt) System.out.println(word);

            line = reader.readLine();
            size = line.length();
            states = line.substring(8, size - 1);
            String[] finSt = states.split(",");
            for (String word : finSt) System.out.println(word);

            line = reader.readLine();
            size = line.length();
            states = line.substring(7, size - 1);
            String[] trans = states.split(",");
            for (String word : trans) System.out.println(word);


            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (StringIndexOutOfBoundsException | NullPointerException e) {
            System.out.println("Error:\nE5: Input file is malformed");
        }
    }
}
