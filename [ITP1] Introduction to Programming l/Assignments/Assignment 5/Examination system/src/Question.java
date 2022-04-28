import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

abstract public class Question {
    int difficulty;
    String questionText;

    public Question(int difficulty, String questionText) {
        this.difficulty = difficulty;
        this.questionText = questionText;
    }

    public String toStringWithoutAnswers() {
        return questionText;
    }

    public String toStringWithAnswers() {
        return questionText;
    }

    public static void reorderQuestions(List<Question> questions) {
        questions.sort(Comparator.comparing(question -> question.difficulty));
    }

    public static String print(List<Question> questions) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < questions.size(); i++) {
            result.append(i + 1).append(") ").append(questions.get(i).toStringWithoutAnswers());
            result.append("\n");
            if (i < questions.size() - 1) {
                result.append("\n");
            }
        }
        result.append("\n\n");
        return result.toString();
    }

    public static String printWithAnswers(List<Question> questions) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < questions.size(); i++) {
            result.append(i + 1).append(") ").append(questions.get(i).toStringWithAnswers());
            result.append("\n");
            if (i < questions.size() - 1) {
                result.append("\n");
            }
        }
        return result.toString();
    }
}


class ShortQuestion extends Question {
    ArrayList<String> answers;

    public ShortQuestion(int difficulty, String questionText, ArrayList<String> answers) {
        super(difficulty, questionText);
        this.answers = answers;
    }

    @Override
    public String toStringWithoutAnswers() {
        StringBuilder result = new StringBuilder(super.toStringWithoutAnswers());
        result.append("\nAnswer: ____________________");
        return result.toString();
    }

    @Override
    public String toStringWithAnswers() {
        StringBuilder result = new StringBuilder(super.toStringWithoutAnswers());
        result.append("\nAccepted answers: ").append(answers);
        return result.toString();
    }
}

class TrueFalseQuestion extends Question {
    boolean answer;

    public TrueFalseQuestion(int difficulty, String questionText, boolean answer) {
        super(difficulty, questionText);
        this.answer = answer;
    }

    @Override
    public String toStringWithoutAnswers() {
        StringBuilder result = new StringBuilder(super.toStringWithoutAnswers());
        result.append("\nAnswer: true false (circle the right answer)");
        return result.toString();
    }

    @Override
    public String toStringWithAnswers() {
        StringBuilder result = new StringBuilder(super.toStringWithoutAnswers());
        result.append("\nAnswer: ").append(answer);
        return result.toString();
    }
}

class EssayQuestion extends Question {
    String answer;

    public EssayQuestion(int difficulty, String questionText, String answer) {
        super(difficulty, questionText);
        this.answer = answer;
    }

    @Override
    public String toStringWithoutAnswers() {
        StringBuilder result = new StringBuilder(super.toStringWithoutAnswers());
        result.append("\n\n\n\n");
        return result.toString();
    }

    @Override
    public String toStringWithAnswers() {
        StringBuilder result = new StringBuilder(super.toStringWithoutAnswers());
        result.append("\n").append(answer).append("\nNote: To be checked manually");
        return result.toString();
    }
}

class MultiChoiceQuestion extends Question {
    boolean single;
    ArrayList<String> options;
    ArrayList<Integer> solutions;

    public MultiChoiceQuestion(int difficulty, String questionText, boolean single, ArrayList<String> options, ArrayList<Integer> solutions) {
        super(difficulty, questionText);
        this.single = single;
        this.options = options;
        this.solutions = solutions;
    }

    @Override
    public String toStringWithoutAnswers() {
        StringBuilder result = new StringBuilder(super.toStringWithoutAnswers());
        for (int i = 0; i < options.size(); i++) {
            result.append("\n").append("    ").append(i + 1).append(") ").append(options.get(i));
        }
        return result.toString();
    }

    @Override
    public String toStringWithAnswers() {
        StringBuilder result = new StringBuilder(super.toStringWithoutAnswers());
        for (int i = 0; i < options.size(); i++) {
            result.append("\n").append(solutions.contains(i + 1) ? " -> " : "    ").append(i + 1).append(") ").append(options.get(i));
        }
        return result.toString();
    }
}