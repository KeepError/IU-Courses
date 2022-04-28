package CarRentalCompany;

/**
 * Test class for PriorityQueue
 */
public class PriorityQueueTest {
    public static void main(String[] args) {
        IPriorityQueue<Integer, String> binaryHeap = new BinaryHeap<>();
        binaryHeap.insert(new Pair<>(10, "A"));
        binaryHeap.insert(new Pair<>(11, "B"));
        binaryHeap.insert(new Pair<>(9, "C"));
        binaryHeap.insert(new Pair<>(1, "D"));
        printTest("D", binaryHeap.extractMin().getValue());
        printTest("C", binaryHeap.findMin().getValue());
        printTest("C", binaryHeap.findMin().getValue());

        binaryHeap.insert(new Pair<>(10000, "AA"));
        printTest("C", binaryHeap.findMin().getValue());
        binaryHeap.decreaseKey(new Pair<>(10000, "AA"), 0);
        printTest("AA", binaryHeap.findMin().getValue());

        for (int i = -10; i < 0; i++) {
            binaryHeap.insert(new Pair<>(i, Integer.toString(i)));
        }
        for (int i = -10; i < 0; i++) {
            String min = binaryHeap.extractMin().getValue();
            printTest(Integer.toString(i), min);
        }
    }

    static void printTest(String expected, String received) {
        System.out.println("Expected: '" + expected + "'\tReceived: '" + received + "'");
    }
}
