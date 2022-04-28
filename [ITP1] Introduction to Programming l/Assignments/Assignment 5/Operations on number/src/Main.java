import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        File input = new File("input.txt");
        Scanner inputScan = new Scanner(input);

        BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));

        ArrayList<Integer> operations;
        ArrayList<Number> numbers;
        double divisionNumber;

        try {
            operations = readOperations(inputScan);
            numbers = readNumbers(inputScan);
            divisionNumber = readDivisionNumber(inputScan, operations.contains(3));
            writer.write(performOperations(operations, numbers, divisionNumber));
        } catch (CustomException e) {
            writer.write(e.getMessage());
        }

        writer.close();
    }

    public static ArrayList<Integer> readOperations(Scanner inputScan) throws NonExistingOperationException, ListOfOperationsInvalidLengthException, InvalidDataException {
        String line = inputScan.nextLine();
        ArrayList<Integer> result = new ArrayList<>();
        for (String num : line.split(" ")) {
            if (num.length() == 0) {
                continue;
            }
            int number;
            try {
                number = Integer.parseInt(num);
            } catch (NumberFormatException e) {
                throw new InvalidDataException();
            }
            if (number < 1 || number > 6) {
                throw new NonExistingOperationException();
            }
            result.add(number);
        }
        if (result.size() < 1 || result.size() > 10) {
            throw new ListOfOperationsInvalidLengthException();
        }
        return result;
    }

    public static ArrayList<Number> readNumbers(Scanner inputScan) throws ListOfNumbersInvalidLengthException, InvalidDataException {
        String line = inputScan.nextLine();
        ArrayList<Number> result = new ArrayList<>();
        String[] parts = line.split(",* +");
        try {
            for (String num : parts) {
                if (num.length() == 0) {
                    continue;
                }
                num = num.toLowerCase();
                Number number;
                if (num.contains("l")) {
                    number = Long.parseLong(num.substring(0, num.length() - 1));
                    if (!Objects.equals(number.toString(), num.substring(0, num.length() - 1))) {
                        throw new InvalidDataException();
                    }
                } else if (num.contains("f")) {
                    number = Float.parseFloat(num);
                    if (!Objects.equals(number.toString(), num.substring(0, num.length() - 1))) {
                        throw new InvalidDataException();
                    }
                } else if (num.contains("bi")) {
                    number = new BigInteger(num.substring(0, num.length() - 2));
                } else if (num.contains("bd")) {
                    number = new BigDecimal(num.substring(0, num.length() - 2));
                } else if (num.contains(".")) {
                    number = Double.parseDouble(num);
                    if (!Objects.equals(number.toString(), num)) {
                        throw new InvalidDataException();
                    }
                } else {
                    number = Integer.parseInt(num);
                    if (!Objects.equals(number.toString(), num)) {
                        throw new InvalidDataException();
                    }
                }
                result.add(number);
            }
        } catch (NumberFormatException e) {
            throw new InvalidDataException();
        }

        if (result.size() < 1 || result.size() > 20) {
            throw new ListOfNumbersInvalidLengthException();
        }
        return result;
    }

    public static double readDivisionNumber(Scanner inputScan, boolean operationsHaveDivision) throws InvalidDataException {
        double number = 0.0;
        try {
            String line = inputScan.nextLine();
            if (!operationsHaveDivision) {
                throw new InvalidDataException();
            }
            number = Double.parseDouble(line);
        } catch (NoSuchElementException e) {
            if (operationsHaveDivision) {
                throw new InvalidDataException();
            }
        }
        return number;
    }

    public static String performOperations(ArrayList<Integer> operations, ArrayList<Number> numbers, double divisionNumber) {
        StringBuilder result = new StringBuilder();
        Double sum, mult;
        for (int operation : operations) {
            switch (operation) {
                case (1):
                    sum = 0.0;
                    for (Number number : numbers) sum += number.doubleValue();
                    result.append(sum);
                    break;
                case (2):
                    mult = 1.0;
                    for (Number number : numbers) mult *= number.doubleValue();
                    result.append(mult);
                    break;
                case (4):
                    sum = 0.0;
                    for (Number number : numbers) sum += Math.abs(number.doubleValue());
                    result.append(sum / numbers.size());
                    break;
                case (3):
                    try {
                        numbers = performDivision(numbers, divisionNumber);
                        result.append(joinList(numbers));
                    } catch (DivisionByZeroException e) {
                        result.append(e.getMessage());
                    }
                    break;
                case (5):
                    try {
                        numbers = performSquareRoot(numbers);
                        result.append(joinList(numbers));
                    } catch (SquareRootNegativeValueException e) {
                        result.append(e.getMessage());
                    }
                    break;
                case (6):
                    numbers = deleteNegativeValues(numbers);
                    result.append(joinList(numbers));
                    break;
            }
            result.append("\n");
        }
        return result.toString();
    }

    public static String joinList(ArrayList<Number> numbers) {
        StringBuilder s = new StringBuilder();
        s.append(numbers.get(0));
        for (int i = 1; i < numbers.size(); i++) {
            s.append(", ").append(numbers.get(i));
        }
        return s.toString();
    }

    public static ArrayList<Number> performDivision(ArrayList<Number> list, double divisionNumber) throws DivisionByZeroException {
        if (divisionNumber == 0) {
            throw new DivisionByZeroException();
        }
        return (ArrayList<Number>) list.stream().map(number -> (Number) (number.doubleValue() / divisionNumber)).collect(Collectors.toList());
    }

    public static ArrayList<Number> performSquareRoot(ArrayList<Number> list) throws SquareRootNegativeValueException {
        for (Number number : list) {
            if (number.doubleValue() < 0) {
                throw new SquareRootNegativeValueException();
            }
        }
        return (ArrayList<Number>) list.stream().map(number -> (Number) (Math.sqrt(number.doubleValue()))).collect(Collectors.toList());
    }

    public static ArrayList<Number> deleteNegativeValues(ArrayList<Number> list) {
        return (ArrayList<Number>) list.stream().filter(number -> number.doubleValue() >= 0).collect(Collectors.toList());
    }
}


abstract class CustomException extends Exception {
    CustomException(String s) {
        super(s);
    }
}

class NonExistingOperationException extends CustomException {
    public NonExistingOperationException() {
        super("Exception: Non-existing operation");
    }
}

class ListOfOperationsInvalidLengthException extends CustomException {
    public ListOfOperationsInvalidLengthException() {
        super("Exception: The list of operations has an invalid length");
    }
}

class ListOfNumbersInvalidLengthException extends CustomException {
    public ListOfNumbersInvalidLengthException() {
        super("Exception: The list of numbers has an invalid length");
    }
}

class InvalidDataException extends CustomException {
    public InvalidDataException() {
        super("Exception: Invalid data");
    }
}

class SquareRootNegativeValueException extends CustomException {
    public SquareRootNegativeValueException() {
        super("Exception: Square root cannot be calculated for negative value");
    }
}

class DivisionByZeroException extends CustomException {
    public DivisionByZeroException() {
        super("Exception: Division by 0");
    }
}