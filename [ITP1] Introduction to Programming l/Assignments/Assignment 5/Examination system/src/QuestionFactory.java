import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.Arrays;

public class QuestionFactory {

    public Question createQuestion(String questionType, Element element) {
        int difficulty = Integer.parseInt(element.getElementsByTagName("difficulty").item(0).getTextContent());
        String questionText = element.getElementsByTagName("questiontext").item(0).getTextContent();

        Question newQuestion;
        switch (questionType) {
            case ("short"):
                String answers = element.getElementsByTagName("answers").item(0).getTextContent();
                ArrayList<String> answersList = new ArrayList<>(Arrays.asList(answers.split(",")));
                newQuestion = new ShortQuestion(difficulty, questionText, answersList);
                break;
            case ("truefalse"):
                String answerTF = element.getElementsByTagName("answer").item(0).getTextContent();
                newQuestion = new TrueFalseQuestion(difficulty, questionText, Boolean.parseBoolean(answerTF));
                break;
            case ("essay"):
                String answerEssay = element.getElementsByTagName("answer").item(0).getTextContent();
                newQuestion = new EssayQuestion(difficulty, questionText, answerEssay);
                break;
            case ("multichoice"):
                String single = element.getElementsByTagName("single").item(0).getTextContent();

                ArrayList<String> options = new ArrayList<>();
                for (int i = 0; i < element.getElementsByTagName("answer").getLength(); i++) {
                    options.add(element.getElementsByTagName("answer").item(i).getTextContent());
                }

                String solution = element.getElementsByTagName("solution").item(0).getTextContent();
                ArrayList<Integer> solutionList = new ArrayList<>();
                for (String sol : solution.split(",")) solutionList.add(Integer.parseInt(sol));

                newQuestion = new MultiChoiceQuestion(difficulty, questionText, Boolean.parseBoolean(single), options, solutionList);
                break;

            default:
                newQuestion = null;
        }

        return newQuestion;
    }
}