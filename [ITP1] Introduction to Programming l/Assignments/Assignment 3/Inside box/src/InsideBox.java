import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class InsideBox {
    public static int getMax(int[] values) {
        int max = 0;
        for (int i = 0; i < values.length; i++) {
            if (values[i] > max) {
                max = values[i];
            }
        }
        return max;
    }

    public static int[] addIndex(int index, int[] indexes) {
        int[] newIndexes = new int[indexes.length + 1];
        newIndexes[0] = index;
        System.arraycopy(indexes, 0, newIndexes, 1, newIndexes.length - 1);
        return newIndexes;
    }

    public static boolean checkBoxes(int[] lastBox, int[] currentBox) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    if ((i != j) && (i != k) && (j != k)) {
                        if ((lastBox[i] > currentBox[0]) &&
                                (lastBox[j] > currentBox[1]) &&
                                (lastBox[k] > currentBox[2])) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static int solve(int[][] boxes, int[] indexes) {
        int[] resultValues = new int[boxes.length];
        int resultValuesIndex = 0;

        for (int i = 0; i < boxes.length; i++) {
            int finalI = i;
            if (Arrays.stream(indexes).anyMatch(num -> finalI == num)) {
                continue;
            }

            if (indexes.length == 0) {
                resultValues[resultValuesIndex] = solve(boxes, addIndex(i, indexes));
                resultValuesIndex++;
            } else {
                if (checkBoxes(boxes[indexes[0]], boxes[i])) {
                    resultValues[resultValuesIndex] = solve(boxes, addIndex(i, indexes));
                    resultValuesIndex++;
                }
            }

        }

        if (Arrays.stream(resultValues).allMatch(num -> num == 0)) {
            return indexes.length;
        } else {
            return getMax(resultValues);
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner s = new Scanner(new FileReader("input.txt"));
        String line = s.nextLine();

        String[] parts = line.split("(\\[)|(\\] \\[)|(\\])");
        int[][] boxes = new int[parts.length - 1][3];

        for (int i = 1; i < parts.length; i++) {
            String[] lineParts = parts[i].split(" ");
            for (int j = 0; j < 3; j++) {
                boxes[i - 1][j] = Integer.parseInt(lineParts[j]);
            }
        }

        int[] noIndexes = {};

        PrintWriter writer = new PrintWriter("output.txt");

        writer.println(solve(boxes, noIndexes));

        writer.close();
    }
}