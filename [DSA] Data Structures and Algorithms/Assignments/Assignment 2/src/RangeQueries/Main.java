package RangeQueries;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ParseException {
        RangeMap<Date, Integer> rangeMap = new BTreeRangeMap<>();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        for (int i = 0; i < n; i++) {
            String first = scanner.next();
            if (first.equals("REPORT")) {
                readReport(rangeMap, scanner);
            } else {
                readOperation(first, rangeMap, scanner);
            }
        }
    }

    public static void readOperation(String dateString, RangeMap<Date, Integer> rangeMap, Scanner scanner) throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        String action = scanner.next();
        int amount = scanner.nextInt();
        Integer value = rangeMap.lookup(date);
        int currentAmount = 0;
        if (value != null) {
            currentAmount += value;
        }
        if (action.equals("DEPOSIT")) {
            rangeMap.add(date, currentAmount + amount);
        } else if (action.equals("WITHDRAW")) {
            rangeMap.add(date, currentAmount - amount);
        }
    }

    public static void readReport(RangeMap<Date, Integer> rangeMap, Scanner scanner) throws ParseException {
        scanner.next();
        Date from = new SimpleDateFormat("yyyy-MM-dd").parse(scanner.next());
        scanner.next();
        Date to = new SimpleDateFormat("yyyy-MM-dd").parse(scanner.next());
        List<Integer> amounts = rangeMap.lookupRange(from, to);
        int sum = amounts.stream().mapToInt(Integer::intValue).sum();
        System.out.println(sum);
    }
}
