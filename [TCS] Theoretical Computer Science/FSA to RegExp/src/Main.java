import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(new File("input.txt"));
        BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
        try {
            FiniteStateAutomata fsa = new FiniteStateAutomata(input);
            writer.append(fsa.getRegExp());
        } catch (InputException e) {
            writer.append("Error:\n").append(e.getMessage());
        }
        writer.close();
    }
}
