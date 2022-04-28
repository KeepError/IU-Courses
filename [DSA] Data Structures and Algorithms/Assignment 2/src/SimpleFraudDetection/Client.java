package SimpleFraudDetection;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Class for client frauds detection
 * @author Egor Kuziakov
 */
public class Client {
    private final LinkedList<Double> trailingDaysExpenses;
    private Date lastDate = null;
    private final int capacity;
    private int fraudsCount = 0;

    public Client(int capacity) {
        this.trailingDaysExpenses = new LinkedList<>();
        this.capacity = capacity;
    }

    /**
     * @param expense expense to be added
     */
    private void addExpense(double expense) {
        if (trailingDaysExpenses.size() == capacity) {
            trailingDaysExpenses.pop();
        }
        trailingDaysExpenses.add(expense);
    }

    /**
     * @param dateExpenses expenses
     * @return expenses sum
     */
    private double getSum(ArrayList<Double> dateExpenses) {
        double sum = 0.0;
        for (double expense : dateExpenses) {
            sum += expense;
        }
        return sum;
    }

    /**
     * Sorting with quick sort
     *
     * @param values list of numbers
     * @return sorted
     */
    private List<Double> getSortedValues(List<Double> values) {
        if (values.size() == 0) {
            return values;
        }
        double pivot = values.get(0);
        values.remove(0);
        List<Double> leftValues = values.stream().filter((value) -> value <= pivot).collect(Collectors.toList());
        List<Double> rightValues = values.stream().filter((value) -> value > pivot).collect(Collectors.toList());

        List<Double> result = new ArrayList<>(getSortedValues(leftValues));
        result.add(pivot);
        result.addAll(getSortedValues(rightValues));
        return result;
    }

    /**
     * @param values list of numbers
     * @return median
     */
    private double getMedian(LinkedList<Double> values) {
        int size = values.size();
        List<Double> valuesList = getSortedValues(new ArrayList<>(values));

        double median;
        if (size % 2 != 0) {
            median = valuesList.get(size / 2);
        } else {
            median = (valuesList.get(size / 2 - 1) + valuesList.get(size / 2)) / 2.0;
        }
        return median;
    }

    /**
     * adds next expenses by date
     *
     * @param date         date
     * @param dateExpenses expenses
     */
    public void addNextExpenses(Date date, ArrayList<Double> dateExpenses) {
        if (lastDate != null) {
            int days = (int) TimeUnit.DAYS.convert(date.getTime() - lastDate.getTime(), TimeUnit.MILLISECONDS);

            for (int i = 0; i < Math.min(days - 1, capacity - 1); i++) {
                addExpense(0);
            }
        }

        lastDate = date;

        double expensesSum = getSum(dateExpenses);

        if (trailingDaysExpenses.size() < capacity) {
            addExpense(expensesSum);
            return;
        }

        double trailingDaysMedian = getMedian(trailingDaysExpenses);
        double dailySum = 0;
        for (double expense : dateExpenses) {
            dailySum += expense;
            if (dailySum >= trailingDaysMedian * 2) {
                fraudsCount += 1;
            }
        }

        addExpense(expensesSum);
    }

    /**
     * @return frauds count
     */
    public int getFraudsCount() {
        return fraudsCount;
    }
}
