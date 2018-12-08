package ee.aoc;

import java.util.*;
import java.util.List;

import static ee.aoc.FileUtil.readLinesFromFile;
import static java.util.stream.Collectors.toList;

public class Day7 {
    private static final String fileName = "src/main/resources/7_day_input.txt";

    public static HashMap<String, List<String>> toGraph(List<String> input) {
        HashMap<String, List<String>> graph = new HashMap<>();

        for (String line : input) {
            String node = line.substring(5, 6);
            String nextNode = line.substring(36, 37);
            if (graph.containsKey(node)) {
                List<String> nextNodes = graph.get(node);
                nextNodes.add(nextNode);
                Collections.sort(nextNodes);
                graph.put(node, nextNodes);
            } else {
                List<String> nextNodes = new ArrayList<>();
                nextNodes.add(nextNode);
                graph.put(node, nextNodes);
            }
        }

        return graph;
    }

    public static List<String> findRootNodes(HashMap<String, List<String>> nodes) {
        List<String> roots = new ArrayList<>();
        for (String key : nodes.keySet()) {
            if (nodeHasNoPredecessors(key, nodes)) {
                roots.add(key);
            }
        }
        return roots;
    }

    public static boolean nodeHasNoPredecessors(String node, HashMap<String, List<String>> nodes) {
        for (String key : nodes.keySet()) {
            if (nodes.get(key).contains(node)) {
                return false;
            }
        }
        return true;
    }

    public static String topologicalSort(HashMap<String, List<String>> graphInput) {
        HashMap<String, List<String>> graph = (HashMap<String, List<String>>) graphInput.clone();
        List<String> nextToTraverse = findRootNodes(graph);
        List<String> traversalOrder = new ArrayList<>();

        while (nextToTraverse.size() > 0) {
            // pop node out and add it to the traversal order
            String node = nextToTraverse.get(0);
            nextToTraverse.remove(0);
            traversalOrder.add(node);

            // Add all the successors to the list to be traversed and pop the node out of the graph
            List<String> nextNodes = graph.get(node);
            graph.remove(node);

            if (nextNodes != null) {
                for (String nextNode : nextNodes) {
                    if (nodeHasNoPredecessors(nextNode, graph)) {
                        nextToTraverse.add(nextNode);
                    }
                }
                Collections.sort(nextToTraverse);
            }
        }

        return String.join("", traversalOrder);

    }

    public static int completeStepsTime(HashMap<String, List<String>> graphInput) {
        HashMap<String, List<String>> graph = (HashMap<String, List<String>>) graphInput.clone();
        int time = -1;
        List<String> workList = findRootNodes(graph);
        List<Worker> workers = createWorkers(5);

        while (!graph.isEmpty() || anyOneWorking(workers)) {
            for (Worker worker: getWorkers(workers, false)) {
                if (worker.endtime == time) {
                    List<String> nextNodes = graph.get(worker.task);
                    graph.remove(worker.task);
                    worker.task = null;
                    worker.endtime = 0;

                    if (nextNodes != null) {
                        for (String nextNode : nextNodes) {
                            if (nodeHasNoPredecessors(nextNode, graph)) {
                                workList.add(nextNode);
                            }
                        }
                        Collections.sort(workList);
                    }
                }
            }

            if (!workList.isEmpty()) {
                for (Worker worker : getWorkers(workers, true)) {
                        String node = workList.get(0);
                        workList.remove(0);
                        startWork(worker, node, time);
                    if (workList.isEmpty()) {
                        break;
                    }
                }
            }
            time ++;
        }

        return time;
    }

    private static class Worker {
        int endtime;
        String task;
    }

    public static List<Worker> createWorkers(int cnt){
        List<Worker> workers = new ArrayList<>();
        for (int i = 0; i < cnt; i++) {
            workers.add(new Worker());
        }
        return workers;
    }

    public static List<Worker> getWorkers(List<Worker> workers, boolean free) {
        return workers.stream().filter(w -> (w.task == null) == free).collect(toList());
    }

    public static void startWork(Worker worker, String node, int currentTime) {
        worker.task = node;
        worker.endtime = getNodeTime(node) + currentTime;
    }

    private static int getNodeTime(String node) {
        // Integer value of A is 65
        int offSet = 4;
        return (int) node.toCharArray()[0] - offSet;
    }

    public static boolean anyOneWorking(List<Worker> workers) {
        return !getWorkers(workers, false).isEmpty();
    }

    public static void main(String[] args) {
        List<String> input = readLinesFromFile(fileName);
        HashMap<String, List<String>> graph = toGraph(input);

        System.out.println("Answer 1: " + topologicalSort(graph));
        System.out.println("Answer 2: " + completeStepsTime(graph));
    }
}
