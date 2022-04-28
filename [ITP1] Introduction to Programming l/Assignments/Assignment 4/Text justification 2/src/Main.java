import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
        try {
            // Preparing to read input file
            File input = new File("input.txt");
            Scanner inputScan = new Scanner(input);

            String text = "";
            boolean textSuccess = true;
            int maxWidth = 0;
            boolean maxWidthSuccess = true;

            // Reading first line (text)
            try {
                text = inputScan.nextLine();
            } catch (NoSuchElementException e) { // In case of absence
                textSuccess = false;
            }

            // Reading second line (maxWidth)
            try {
                maxWidth = inputScan.nextInt();
            } catch (NoSuchElementException e) { // In case of absence
                maxWidthSuccess = false;
            }

            // Getting output
            String result = getJustificationResult(text, maxWidth);
            checkInput(text, maxWidth, textSuccess, maxWidthSuccess);

            // Writing output to file
            writer.write(result);


        } catch (FileNotFoundException e) {
            // Writing file not found exception to file
            writer.write(getExceptionMessage("file not found"));
        } catch (CustomException e) {
            // Writing other exceptions to file
            writer.write(getExceptionMessage(e.getMessage()));
        }

        writer.close();
    }

    public static void checkInput(String text, int maxWidth, boolean textSuccess, boolean maxWidthSuccess)
            throws CustomException {
        // Checking input data and throwing exceptions in case of incorrect data

        if (!textSuccess) {
            throw new EmptyFileException();
        }

        if (text.length() > 300) {
            throw new TextExceedsMaxWidthException();
        }

        if (!maxWidthSuccess) {
            throw new MaxWidthNotSpecifiedException();
        }

        if (maxWidth <= 0) {
            throw new NotPositiveMaxWidthException();
        }

        if (Arrays.asList(text.split(" ")).contains("")) {
            throw new ContainsEmptyWordException();
        }

        Pattern pattern = Pattern.compile("[a-zA-Z\\.\\,\\!\\?\\-\\:\\;\\(\\)\\'\\\" ]+");
        String[] matches = pattern.split(text);
        if (matches.length != 0) {
            throw new ContainsForbiddenSymbolException(matches[1]);
        }

        Object[] limit20Words = Arrays.stream(text.split(" ")).filter(word -> word.length() > 20).toArray();
        if (limit20Words.length != 0) {
            throw new Limit20SymbolsException((String) limit20Words[0]);
        }

        Object[] limitMaxWidthWords = Arrays.stream(text.split(" ")).filter(word -> word.length() > maxWidth).toArray();
        if (limitMaxWidthWords.length != 0) {
            throw new LimitMaxWidthSymbolsException((String) limitMaxWidthWords[0], maxWidth);
        }
    }

    public static String getExceptionMessage(String message) {
        // Formatting exception message
        return "Exception, " + message + "!";
    }

    public static String getJustificationResult(String text, int maxWidth) {
        String[] words = text.split(" ");

        StringBuilder result = new StringBuilder();

        ArrayList<String> currentLine = new ArrayList<>();
        int currentLength = 0;

        for (String word : words) {
            int l = word.length();

            if ((currentLength + l) > maxWidth) {
                // If line is full, add it to result
                result.append(getResultLine(currentLine, maxWidth, false));
                result.append("\n");

                // Clear line
                currentLength = 0;
                while (currentLine.size() > 0) {
                    currentLine.remove(0);
                }
            }

            currentLine.add(word);
            currentLength += l + 1;
        }
        result.append(getResultLine(currentLine, maxWidth, true));

        return result.toString();
    }

    public static String getResultLine(List<String> words, int maxWidth, boolean isLastLine) {
        int gapsCount = words.size() - 1;
        int gapsLength = 0;
        int gapsRemain = 0;

        // If line contains more than 1 word
        if (gapsCount != 0) {
            int wordsSum = 0;
            for (String word : words) {
                wordsSum += word.length();
            }

            gapsLength = (maxWidth - wordsSum) / gapsCount;
            gapsRemain = (maxWidth - wordsSum) % gapsCount;
        }

        // Adding words and spaces
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < words.size(); i++) {
            s.append(words.get(i));

            // If word is not last in line
            if (i != words.size() - 1) {
                // If line is last add only one problem
                if (isLastLine) {
                    s.append(" ");
                } else {
                    for (int j = 0; j < gapsLength; j++) {
                        s.append(" ");
                    }
                    if (gapsRemain > 0) {
                        s.append(" ");
                        gapsRemain -= 1;
                    }
                }
            }
        }

        return s.toString();
    }
}

class CustomException extends Exception {
    CustomException(String s) {
        super(s);
    }
}

class EmptyFileException extends CustomException {
    public EmptyFileException() {
        super("file is empty");
    }
}

class TextExceedsMaxWidthException extends CustomException {
    public TextExceedsMaxWidthException() {
        super("input exceeds text max size");
    }
}

class MaxWidthNotSpecifiedException extends CustomException {
    public MaxWidthNotSpecifiedException() {
        super("intended line width is not specified");
    }
}

class NotPositiveMaxWidthException extends CustomException {
    public NotPositiveMaxWidthException() {
        super("line width cannot be negative or zero");
    }
}

class ContainsEmptyWordException extends CustomException {
    public ContainsEmptyWordException() {
        super("input contains an empty word");
    }
}

class ContainsForbiddenSymbolException extends CustomException {
    public ContainsForbiddenSymbolException(String symbol) {
        super(String.format("input contains forbidden symbol '%s'", symbol));
    }
}

class Limit20SymbolsException extends CustomException {
    public Limit20SymbolsException(String word) {
        super(String.format("'%s' exceeds the limit of 20 symbols", word));
    }
}

class LimitMaxWidthSymbolsException extends CustomException {
    public LimitMaxWidthSymbolsException(String word, int maxWidth) {
        super(String.format("'%s' exceeds %d symbols", word, maxWidth));
    }
}