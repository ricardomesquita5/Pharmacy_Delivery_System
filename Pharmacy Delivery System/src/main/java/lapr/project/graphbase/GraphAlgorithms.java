package lapr.project.graphbase;

import lapr.project.data.FileReader;
import lapr.project.model.Calculator;

import java.io.IOException;
import java.util.LinkedList;


/**
 * The type Graph algorithms.
 */
public class GraphAlgorithms {

    /**
     * The GraphAlgorithms's FileReader.
     */
    private static final FileReader fr = new FileReader();

    /**
     * Instantiates a new Graph algorithms.
     */
    public GraphAlgorithms() {
        //do nothing
    }

    /**
     * Min dist graph graph.
     *
     * @return the graph
     * @throws IOException the io exception
     */
    public static Graph<String, Integer> minDistGraph() throws IOException {
        Graph<String, Integer> newGraph = new Graph<>(true);
        fr.insertVertices("Coordinates.txt");
        for (int k = 0; k < fr.getListOfVertices().size(); k++) {
            for (int i = 0; i < fr.getListOfVertices().size(); i++) {
                if (i != k) {
                    String[] coord1 = splitCoordinates(fr.getListOfVertices().get(k));
                    String[] coord2 = splitCoordinates(fr.getListOfVertices().get(i));
                    newGraph.insertEdge(fr.getListOfVertices().get(i), fr.getListOfVertices().get(k), 1, Calculator.calculateDistance(Double.parseDouble(coord1[0]), Double.parseDouble(coord1[1]), Double.parseDouble(coord2[0]), Double.parseDouble(coord2[1])));
                }
            }
        }
        return newGraph;
    }

    /**
     * Split coordinates string [ ].
     *
     * @param coordinates the coordinates
     * @return the string [ ]
     */
    public static String[] splitCoordinates(String coordinates) {
        return coordinates.split(",");
    }

    /**
     * Computes shortest-path distance from a source vertex to all reachable
     * vertices of a graph g with nonnegative edge weights
     * This implementation uses Dijkstra's algorithm
     *
     * @param <V>      the type parameter
     * @param <E>      the type parameter
     * @param g        Graph instance
     * @param vOrig    Vertex that will be the source of the path
     * @param vertices the vertices
     * @param visited  set of discovered vertices
     * @param pathKeys minimum path vertices keys
     * @param dist     minimum distances
     */
    protected static <V, E> void shortestPathLength(Graph<V, E> g, V vOrig, V[] vertices,
                                                    boolean[] visited, int[] pathKeys, double[] dist) {
        int vkey = g.getKey(vOrig);
        dist[vkey] = 0;
        while (vkey != -1) {
            vOrig = vertices[vkey];
            visited[vkey] = true;
            for (V vAdj : g.adjVertices(vOrig)) {
                int vkeyAdj = g.getKey(vAdj);
                Edge<V, E> edge = g.getEdge(vOrig, vAdj);
                if (!visited[vkeyAdj] && dist[vkeyAdj] > dist[vkey] + edge.getWeight()) {
                    dist[vkeyAdj] = dist[vkey] + edge.getWeight();
                    pathKeys[vkeyAdj] = vkey;
                }
            }
            double minDist = Double.MAX_VALUE;
            vkey = -1;
            for (int i = 0; i < g.numVertices(); i++) {
                if (!visited[i] && dist[i] < minDist) {
                    minDist = dist[i];
                    vkey = i;
                }
            }
        }
    }

    /**
     * Extracts from pathKeys the minimum path between voInf and vdInf
     * The path is constructed from the end to the beginning
     *
     * @param <V>      the type parameter
     * @param <E>      the type parameter
     * @param g        Graph instance
     * @param vOrig    information of the Vertex origin
     * @param vDest    information of the Vertex destination
     * @param verts    the verts
     * @param pathKeys minimum path vertices keys
     * @param path     stack with the minimum path (correct order)
     */
    protected static <V, E> void getPath(Graph<V, E> g, V vOrig, V vDest, V[] verts, int[] pathKeys, LinkedList<V> path) {

        if (!vOrig.equals(vDest)) {
            path.push(vDest);
            int vKey = g.getKey(vDest);
            int prevVKey = pathKeys[vKey];
            vDest = verts[prevVKey];

            getPath(g, vOrig, vDest, verts, pathKeys, path);
        } else {
            path.push(vOrig);
        }
    }

    /**
     * Shortest path double.
     *
     * @param <V>       the type parameter
     * @param <E>       the type parameter
     * @param g         the g
     * @param vOrig     the v orig
     * @param vDest     the v dest
     * @param shortPath the short path
     * @return the double
     */
//shortest-path between vOrig and vDest
    public static <V, E> double shortestPath(Graph<V, E> g, V vOrig, V vDest, LinkedList<V> shortPath) {

        if (!g.validVertex(vOrig) || !g.validVertex(vDest))
            return 0;

        int nverts = g.numVertices();
        boolean[] visited = new boolean[nverts];
        int[] pathKeys = new int[nverts];
        double[] dist = new double[nverts];
        V[] vertices = g.allkeyVerts();

        for (int i = 0; i < nverts; i++) {
            dist[i] = Double.MAX_VALUE;
            pathKeys[i] = -1;
        }
        shortestPathLength(g, vOrig, vertices, visited, pathKeys, dist);
        double lengthPath = dist[g.getKey(vDest)];

        if (lengthPath != Double.MAX_VALUE) {

            getPath(g, vOrig, vDest, vertices, pathKeys, shortPath);
            return lengthPath;
        }

        return 0;
    }
}

