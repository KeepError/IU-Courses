import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        XMLParser parser = new XMLParser();
        try {
            List<Question> questions = parser.readInput();
            Question.reorderQuestions(questions);
            String prePrint = "==============Exam==============\n";
            prePrint += Question.print(questions);
            prePrint +="==========Exam answers==========\n";
            prePrint += Question.printWithAnswers(questions);
            output(prePrint);
        }catch (NullPointerException e){
            output("Wrong formatted input file");
        }
    }

    public static void output(String text){
        try {
            FileWriter myWriter = new FileWriter("output.txt");
            myWriter.write(text);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("Exception: Couldn't output!");
        }
    }
}