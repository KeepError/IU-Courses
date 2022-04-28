package CarRentalCompany;

import java.util.Scanner;

public class Main_C {
    public static void main(String[] args) {
        IPriorityQueue<Integer, String> binaryHeap = new BinaryHeap<>();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        for (int i = 0; i < n; i++) {
            String action = scanner.next();
            if (action.equals("ADD")) {
                String branchName = scanner.next();
                int penalty = scanner.nextInt();
                binaryHeap.insert(new Pair<>(penalty, branchName));
            }
            if (action.equals("PRINT_MIN")) {
                Pair<Integer, String> minPair = binaryHeap.extractMin();
                System.out.println(minPair.getValue());
            }
        }
    }
}
