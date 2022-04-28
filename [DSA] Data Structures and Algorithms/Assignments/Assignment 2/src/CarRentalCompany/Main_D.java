package CarRentalCompany;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main_D {
    public static void main(String[] args) {
        IGraph<String, Double> graph = new Graph<>();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        Map<String, Double> pointsPenalties = new HashMap<>();
        for (int i = 0; i < n; i++) {
            String action = scanner.next();
            if (action.equals("ADD")) {
                String pointName = scanner.next();
                double penalty = scanner.nextDouble();
                pointsPenalties.put(pointName, penalty);
                graph.insertVertex(pointName);
            }
            if (action.equals("CONNECT")) {
                String firstPointName = scanner.next();
                String secondPointName = scanner.next();
                double distance = scanner.nextDouble();
                graph.insertEdge(firstPointName, secondPointName, distance / (pointsPenalties.get(firstPointName) + pointsPenalties.get(secondPointName)));
            }
            if (action.equals("PRINT_MIN")) {
                System.out.println(getMinimumSpanningTreeString(graph));
            }
        }
    }

    /**
     * @param graph graph
     * @return string with connections in MSG in format <vertex1>:<vertex2> ...
     */
    static String getMinimumSpanningTreeString(IGraph<String, Double> graph) {
        IPriorityQueue<Double, String> binaryHeap = new BinaryHeap<>();
        List<String> vertices = graph.getVertices();

        Map<String, String> verticesParents = new HashMap<>();
        Map<String, Double> verticesValues = new HashMap<>();
        for (int i = 0; i < vertices.size(); i++) {
            double value = i == 0 ? 0 : Double.MAX_VALUE;
            binaryHeap.insert(new Pair<>(value, vertices.get(i)));
            verticesValues.put(vertices.get(i), value);
        }

        while (!binaryHeap.isEmpty()) {
            Pair<Double, String> minElement = binaryHeap.extractMin();
            verticesValues.remove(minElement.getValue());

            Map<String, Double> connected = graph.getConnections(minElement.getValue());
            for (Map.Entry<String, Double> connectionPair : connected.entrySet()) {
                String connVertex = connectionPair.getKey();
                double connWeight = connectionPair.getValue();

                if (!verticesValues.containsKey(connVertex)) continue;

                double connCurrentValue = verticesValues.get(connVertex);
                if (connWeight < connCurrentValue) {
                    binaryHeap.decreaseKey(new Pair<>(connCurrentValue, connVertex), connWeight);
                    verticesValues.put(connVertex, connWeight);
                    verticesParents.put(connVertex, minElement.getValue());
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (String vertex : verticesParents.keySet()) {
            sb.append(vertex).append(":").append(verticesParents.get(vertex)).append(" ");
        }
        return sb.toString();
    }
}
