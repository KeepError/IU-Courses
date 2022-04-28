import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import static java.lang.Math.abs;

public class ClockHands {
    public static boolean check(String line) {
        String[] parts = line.split(":");
        if (parts.length != 2) {
            return false;
        }

        if ((parts[0].length() != 2) || (parts[1].length() != 2)) {
            return false;
        }

        int h, m;
        try {
            h = Integer.parseInt(parts[0]);
            m = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            return false;
        }

        if ((h < 0) || (h > 23) || (m < 0) || (m > 59)) {
            return false;
        }

        return true;
    }

    public static int getDegree(String line) {
        String[] parts = line.split(":");

        int hour = Integer.parseInt(parts[0]) % 12 * 5;
        int minute = Integer.parseInt(parts[1]);

        int d = abs(hour - minute) * 6;

        if (d > 180) {
            d = 360 - d;
        }

        return d;
    }

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        Scanner s = new Scanner(new FileReader("input.txt"));
        String line = s.nextLine();

        PrintWriter writer = new PrintWriter("output.txt");

        if (check(line)) {
            writer.println(getDegree(line));
        } else {
            writer.println("Wrong format");
        }

        writer.close();

    }
}