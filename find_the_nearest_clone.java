import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class Solution {

    // Complete the findShortest function below.

    /*
     * For the unweighted graph, <name>:
     *
     * 1. The number of nodes is <name>Nodes.
     * 2. The number of edges is <name>Edges.
     * 3. An edge exists between <name>From[i] to <name>To[i].
     *
     */
    static int findShortest(int graphNodes, int[] graphFrom, int[] graphTo, long[] ids, int val) {
        // solve here

        HashMap<Integer, List<Integer>> edges = new HashMap<>();
        for (int i = 0; i < graphFrom.length; i++) {
            if (edges.containsKey(graphFrom[i])) {
                List<Integer> value = edges.get(graphFrom[i]);
                value.add(graphTo[i]);
            } else {
                List<Integer> newValue = new ArrayList<>();
                newValue.add(graphTo[i]);
                edges.put(graphFrom[i], newValue);
            }

            if (edges.containsKey(graphTo[i])) {
                List<Integer> value = edges.get(graphTo[i]);
                value.add(graphFrom[i]);
            } else {
                List<Integer> newValue = new ArrayList<>();
                newValue.add(graphFrom[i]);
                edges.put(graphTo[i], newValue);
            }
        }

        System.out.println("Complete graph creation");
        
        int initial = 0;
        for (int j = 0; j < ids.length; j++) {
            if (ids[j] == val) {
                initial = j;
                break;
            }
        }

        System.out.println("Initial: " + String.valueOf(initial));

        boolean[] visited = new boolean[ids.length];

        // 0th - item index, 1st - count path
        Integer[] start={initial, 0};

        // Create a queue for BFS 
        LinkedList<Integer[]> queue = new LinkedList<>();
  
        // Mark the current node as visited and enqueue it 
        visited[initial] = true; 
        queue.add(start);
        
        while (!queue.isEmpty()) {

            Integer[] entry = queue.poll();
            List<Integer> edge = edges.get(entry[0]+1);
            
            for (Integer neighbor : edge) {

                int neighborIndex = neighbor - 1;
                if (!visited[neighborIndex]){
                    if (ids[neighborIndex] == val) {
                      return entry[1] + 1;
                    }

                    Integer[] update = {neighborIndex, entry[1] + 1};

                    queue.add(update);
                    visited[neighborIndex] = true;
                }
            }
        }

        return -1;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] graphNodesEdges = scanner.nextLine().split(" ");
        int graphNodes = Integer.parseInt(graphNodesEdges[0].trim());
        int graphEdges = Integer.parseInt(graphNodesEdges[1].trim());

        int[] graphFrom = new int[graphEdges];
        int[] graphTo = new int[graphEdges];

        for (int i = 0; i < graphEdges; i++) {
            String[] graphFromTo = scanner.nextLine().split(" ");
            graphFrom[i] = Integer.parseInt(graphFromTo[0].trim());
            graphTo[i] = Integer.parseInt(graphFromTo[1].trim());
        }

        long[] ids = new long[graphNodes];

        String[] idsItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < graphNodes; i++) {
            long idsItem = Long.parseLong(idsItems[i]);
            ids[i] = idsItem;
        }

        int val = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int ans = findShortest(graphNodes, graphFrom, graphTo, ids, val);

        bufferedWriter.write(String.valueOf(ans));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}
