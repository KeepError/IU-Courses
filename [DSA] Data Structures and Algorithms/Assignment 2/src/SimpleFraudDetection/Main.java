package SimpleFraudDetection;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws ParseException {
        HashMap<Date, ArrayList<Double>> data = new HashMap<>();

        Scanner scanner = new Scanner(System.in);
        int expensesCount = scanner.nextInt();
        int trailingDaysCount = scanner.nextInt();

        for (int i = 0; i < expensesCount; i++) {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(scanner.next());
            String stringMoney = scanner.next().substring(1);
            Double money = Double.parseDouble(stringMoney);
            ArrayList<Double> expenses = data.getOrDefault(date, new ArrayList<>());
            expenses.add(money);
            data.put(date, expenses);
        }

        List<Date> sortedDates = getSortedDates(data.keySet());

        Client client = new Client(trailingDaysCount);
        for (Date date : sortedDates) {
            client.addNextExpenses(date, data.get(date));
        }

        System.out.println(client.getFraudsCount());
    }

    static int getDatesDifference(Date firstDate, Date secondDate) {
        long differenceMs = secondDate.getTime() - firstDate.getTime();
        return (int) TimeUnit.DAYS.convert(differenceMs, TimeUnit.MILLISECONDS);
    }

    /**
     * Sorting dates with counting sort
     *
     * @param dates dates to sort
     * @return sorted dates
     */
    static List<Date> getSortedDates(Set<Date> dates) {
        Date minDate = Collections.min(dates);
        Date maxDate = Collections.max(dates);
        int diffDays = getDatesDifference(minDate, maxDate);
        Date[] datesTable = new Date[diffDays + 1];

        for (Date date : dates) {
            int days = getDatesDifference(minDate, date);
            datesTable[days] = date;
        }

        List<Date> result = new ArrayList<>();
        for (Date date : datesTable) {
            if (!(date == null)) {
                result.add(date);
            }
        }
        return result;
    }
}
