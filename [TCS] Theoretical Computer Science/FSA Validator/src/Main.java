import java.io.*;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(new File("fsa.txt"));
        BufferedWriter writer = new BufferedWriter(new FileWriter("result.txt"));
        try {
            FiniteStateAutomata fsa = new FiniteStateAutomata(input);
            writer.append(fsa.isComplete() ? "FSA is complete" : "FSA is incomplete");
            fsa.checkForWarnings(writer);
        } catch (InputException e) {
            writer.append("Error:\n").append(e.getMessage());
        }
        writer.close();
    }
}
