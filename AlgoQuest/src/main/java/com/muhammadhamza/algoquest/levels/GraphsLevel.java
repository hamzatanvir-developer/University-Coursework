package com.muhammadhamza.algoquest.levels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class GraphsLevel {

    private final Map<String, List<String>> graph;

    public GraphsLevel() {
        graph = new HashMap<>();

        addVertex("A");
        addVertex("B");
        addVertex("C");
        addVertex("D");
        addVertex("E");

        addEdge("A", "B");
        addEdge("A", "D");
        addEdge("B", "C");
        addEdge("B", "E");
        addEdge("C", "E");
    }

    public void addVertex(String vertex) {
        graph.putIfAbsent(vertex, new ArrayList<>());
    }

    public void addEdge(String source, String destination) {
        addVertex(source);
        addVertex(destination);

        graph.get(source).add(destination);
        graph.get(destination).add(source);
    }

    public String bfs(String start) {
        if (!graph.containsKey(start)) {
            return "Start vertex not found";
        }

        Set<String> visited = new LinkedHashSet<>();
        Queue<String> queue = new LinkedList<>();

        visited.add(start);
        queue.add(start);

        while (!queue.isEmpty()) {
            String current = queue.remove();

            for (String neighbor : graph.get(current)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        return String.join(" → ", visited);
    }

    public String dfs(String start) {
        if (!graph.containsKey(start)) {
            return "Start vertex not found";
        }

        Set<String> visited = new LinkedHashSet<>();
        dfsRecursive(start, visited);
        return String.join(" → ", visited);
    }

    private void dfsRecursive(String vertex, Set<String> visited) {
        visited.add(vertex);

        for (String neighbor : graph.get(vertex)) {
            if (!visited.contains(neighbor)) {
                dfsRecursive(neighbor, visited);
            }
        }
    }

    public String getAdjacencyListText() {
        StringBuilder result = new StringBuilder();

        for (String vertex : graph.keySet()) {
            result.append(vertex)
                    .append(" → ")
                    .append(graph.get(vertex))
                    .append("\n");
        }

        return result.toString();
    }

    public String getConceptExplanation() {
        return """
                GRAPH CONCEPT

                Simple Meaning:
                A graph stores relationships between different items.

                Visual Example:

                A ---- B ---- C
                |      |      |
                D      E

                Main Parts:

                Vertex:
                A point or node in a graph.
                Example: A, B, C, D, E

                Edge:
                A connection between two vertices.
                Example: A -- B

                Neighbor:
                A directly connected vertex.

                Adjacency List:
                A way to store each vertex with its connected neighbors.

                Example:
                A → [B, D]
                B → [A, C, E]

                Time Complexity:
                BFS = O(V + E)
                DFS = O(V + E)

                V means number of vertices.
                E means number of edges.
                """;
    }

    public String getImportantPoints() {
        return """
                IMPORTANT GRAPH POINTS

                • Graph is used to represent relationships.
                • A graph contains vertices and edges.
                • Vertex means node or point.
                • Edge means connection between two vertices.
                • Graph can be directed or undirected.
                • Graph can be weighted or unweighted.
                • Adjacency list stores every vertex with its neighbors.
                • BFS means Breadth First Search.
                • BFS uses Queue.
                • DFS means Depth First Search.
                • DFS uses recursion or Stack.
                • visited set/list prevents repeated visits and infinite loops.
                • BFS and DFS take O(V + E) time.
                """;
    }

    public String getRealLifeExamples() {
        return """
                REAL-LIFE GRAPH EXAMPLES

                1. Google Maps:
                   Cities are vertices and roads are edges.

                2. Social media:
                   People are vertices and friendships are edges.

                3. Computer networks:
                   Devices are vertices and connections are edges.

                4. Airline routes:
                   Airports are vertices and flights are edges.

                5. Recommendation systems:
                   Users and products can be connected.

                6. Web pages:
                   Pages are vertices and links are edges.
                """;
    }

    public String getAddVertexCode() {
        return """
                ADD VERTEX

                Meaning:
                Add vertex means adding a new node/point to the graph.

                Manual Logic:

                public void addVertex(String vertex) {
                    graph.putIfAbsent(vertex, new ArrayList<>());
                }

                Step-by-step:
                1. Take vertex name.
                2. Check if vertex already exists.
                3. If it does not exist, create an empty neighbor list.
                4. Store vertex inside graph.

                Example:
                addVertex("A")

                Result:
                A → []

                Time Complexity:
                Average O(1), because HashMap is used.
                """;
    }

    public String getAddEdgeCode() {
        return """
                ADD EDGE

                Meaning:
                Add edge means connecting two vertices.

                Undirected Graph Example:
                A -- B

                Manual Logic:

                public void addEdge(String source, String destination) {
                    addVertex(source);
                    addVertex(destination);

                    graph.get(source).add(destination);
                    graph.get(destination).add(source);
                }

                Step-by-step:
                1. Make sure source vertex exists.
                2. Make sure destination vertex exists.
                3. Add destination in source neighbor list.
                4. Add source in destination neighbor list.

                Example:
                addEdge("A", "B")

                Result:
                A → [B]
                B → [A]

                Time Complexity:
                Usually O(1) for simple insertion.
                """;
    }

    public String getBfsCode() {
        return """
                BFS: BREADTH FIRST SEARCH

                Meaning:
                BFS visits nearby nodes first.

                Data Structure Used:
                Queue

                Example:
                Start from A.

                A has neighbors B and D.
                BFS visits A, then B and D, then their neighbors.

                Manual Logic:

                Set<String> visited = new LinkedHashSet<>();
                Queue<String> queue = new LinkedList<>();

                visited.add(start);
                queue.add(start);

                while (!queue.isEmpty()) {
                    String current = queue.remove();

                    for (String neighbor : graph.get(current)) {
                        if (!visited.contains(neighbor)) {
                            visited.add(neighbor);
                            queue.add(neighbor);
                        }
                    }
                }

                Step-by-step:
                1. Add start vertex to queue.
                2. Mark start as visited.
                3. Remove one vertex from queue.
                4. Add all unvisited neighbors to queue.
                5. Continue until queue becomes empty.

                Time Complexity:
                O(V + E)
                """;
    }

    public String getDfsCode() {
        return """
                DFS: DEPTH FIRST SEARCH

                Meaning:
                DFS goes deep first before coming back.

                Data Structure Used:
                Recursion or Stack

                Example:
                Start from A.
                DFS may go A → B → C → E before coming back.

                Manual Recursive Logic:

                private void dfsRecursive(String vertex, Set<String> visited) {
                    visited.add(vertex);

                    for (String neighbor : graph.get(vertex)) {
                        if (!visited.contains(neighbor)) {
                            dfsRecursive(neighbor, visited);
                        }
                    }
                }

                Step-by-step:
                1. Visit current vertex.
                2. Mark it as visited.
                3. Pick an unvisited neighbor.
                4. Go deeper recursively.
                5. Backtrack when no unvisited neighbor remains.

                Time Complexity:
                O(V + E)
                """;
    }

    public String getAdjacencyListCode() {
        return """
                ADJACENCY LIST

                Meaning:
                Adjacency list stores every vertex with its connected neighbors.

                Java Structure:

                Map<String, List<String>> graph = new HashMap<>();

                Example:
                A → [B, D]
                B → [A, C, E]
                C → [B, E]
                D → [A]
                E → [B, C]

                Why adjacency list is useful:
                • It saves memory for sparse graphs.
                • It is easy to get neighbors of any vertex.
                • It works well with BFS and DFS.

                Alternative:
                Adjacency matrix can also store graph connections,
                but it uses more memory for large sparse graphs.
                """;
    }

    public String getGraphTypesCode() {
        return """
                TYPES OF GRAPHS

                1. Undirected Graph:
                   Edge has no direction.
                   Example: A -- B

                2. Directed Graph:
                   Edge has direction.
                   Example: A → B

                3. Weighted Graph:
                   Edge has cost or weight.
                   Example: A --5-- B

                4. Unweighted Graph:
                   Edge has no cost.

                5. Connected Graph:
                   Every vertex can be reached from another vertex.

                6. Disconnected Graph:
                   Some vertices are separated.

                7. Cyclic Graph:
                   Graph contains a cycle.

                8. Acyclic Graph:
                   Graph has no cycle.
                """;
    }

    public String getEdgeCases() {
        return """
                GRAPH EDGE CASES

                1. Missing vertex:
                   If start vertex does not exist, BFS/DFS cannot start.

                2. Duplicate edge:
                   Same connection may be added multiple times if not checked.

                3. Self-loop:
                   Vertex connects to itself.
                   Example: A → A

                4. Disconnected graph:
                   BFS/DFS from one vertex may not visit all vertices.

                5. Cycle:
                   Graph may contain loops.
                   visited list prevents infinite traversal.

                6. Empty graph:
                   There are no vertices or edges.

                7. Directed edge:
                   A → B does not always mean B → A.

                8. Weighted edge:
                   Algorithms must consider edge cost.

                9. Large graph:
                   Use efficient storage like adjacency list.

                10. Null neighbor list:
                    Always make sure vertex exists before accessing neighbors.
                """;
    }
}