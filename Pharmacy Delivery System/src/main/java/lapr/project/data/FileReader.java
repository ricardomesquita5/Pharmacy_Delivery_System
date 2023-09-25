package lapr.project.data;

import lapr.project.graphbase.Graph;
import lapr.project.graphbase.GraphAlgorithms;
import lapr.project.model.Calculator;
import lapr.project.model.CoordinatesElevation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * The type File reader.
 */
public class FileReader {

    /**
     * The Error.
     */
    private final String error = "File with invalid data.";

    /**
     * The coordinates Graph.
     */
    private Graph<String, Integer> coordinatesGraph;

    /**
     * The l.
     */
    private final List<String> l = new LinkedList<>();
    /**
     * The coordinates Elevations.
     */
    private final List<CoordinatesElevation> coordinatesElevations = new LinkedList<>();

    /**
     * Instantiates a new File reader.
     */
    public FileReader() {
        this.coordinatesGraph = new Graph<>(true);
    }

    /**
     * Gets graph.
     *
     * @return the graph
     * @throws IOException the io exception
     */
    public Graph<String, Integer> getGraph() throws IOException {
        this.coordinatesGraph = new Graph<>(true);
        String coordinatesFile = "Coordinates.txt";
        this.insertVertices(coordinatesFile);
        String connectionsFile = "Connections.txt";
        this.insertEdges(connectionsFile);
        return this.coordinatesGraph;
    }

    /**
     * Gets coordinates graph.
     *
     * @return the coordinates graph
     */
    public Graph<String, Integer> getCoordinatesGraph() {
        return this.coordinatesGraph;
    }

    /**
     * Gets list of vertices.
     *
     * @return the list of vertices
     */
    public List<String> getListOfVertices() {
        List<String> list = this.l;
        return list;
    }

    /**
     * Insert vertices.
     *
     * @param fileName the file name
     * @throws IOException the io exception
     */
    public void insertVertices(String fileName) throws IOException {
        try {
            List<String> list = Files.lines(Paths.get(fileName)).collect(Collectors.toList());
            for (String value : list) {
                String[] data = value.split(";");
                this.l.add(data[0]);
            }
            for (String s : l.subList(0, l.size())) {
                if (this.coordinatesGraph.validVertex(s)) {
                    this.coordinatesGraph.insertVertex(s);
                }
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(error);
        } finally {
            Files.lines(Paths.get(fileName)).close();
        }
    }

    /**
     * Insert edges.
     *
     * @param fileName the file name
     * @throws IOException the io exception
     */
    public void insertEdges(String fileName) throws IOException {
        try {
            List<String> list = Files.lines(Paths.get(fileName)).collect(Collectors.toList());
            for (String s : list.subList(0, list.size())) {
                String[] data = s.split(";");
                if (data.length == 2) {
                    String[] firstCoordinates = GraphAlgorithms.splitCoordinates(data[0].trim());
                    String[] secondCoordinates = GraphAlgorithms.splitCoordinates(data[1].trim());
                    double x = Calculator.calculateDistance(Double.parseDouble(firstCoordinates[0]),
                            Double.parseDouble(firstCoordinates[1]), Double.parseDouble(secondCoordinates[0]),
                            Double.parseDouble(secondCoordinates[1]));
                    this.coordinatesGraph.insertEdge(data[0].trim(), data[1].trim(), 1, x);
                } else {
                    throw new IllegalArgumentException(error);
                }
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(error);
        } finally {
            Files.lines(Paths.get(fileName)).close();
        }
    }

    /**
     * Save coordinates elevation.
     *
     * @param fileName the file name
     * @throws IOException the io exception
     */
    public void saveCoordinatesElevation(String fileName) throws IOException {
        try {
            List<String> list = Files.lines(Paths.get(fileName)).collect(Collectors.toList());
            for (String s : list.subList(0, list.size())) {
                String[] data = s.split(";");
                if (data.length == 3) {
                    this.coordinatesElevations.add(new CoordinatesElevation(data[0].trim(), Integer.parseInt(data[1].trim()), data[2].trim()));
                } else {
                    throw new IllegalArgumentException(error);
                }
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(error);
        } finally {
            Files.lines(Paths.get(fileName)).close();
        }
    }

    /**
     * Gets time in milisec.
     *
     * @param file the file
     * @return the time in milisec
     * @throws FileNotFoundException the file not found exception
     */
    public double getTimeInMilisec(File file) throws FileNotFoundException {
        Scanner sc = new Scanner(file);
        sc.nextLine();
        sc.nextLine();
        sc.nextLine();
        sc.nextLine();
        sc.nextLine();
        sc.nextLine();
        sc.nextLine();
        sc.nextLine();
        String s = sc.nextLine();
        sc.close();

        return Double.parseDouble(s);
    }

    /**
     * Get elevation by coordinates int.
     *
     * @param coordinates the coordinates
     * @return the int
     */
    public int getElevationByCoordinates(String coordinates) {
        for (CoordinatesElevation ce : coordinatesElevations) {
            if (ce.getGpsCoordinates().equals(coordinates)) {
                return ce.getElevation();
            }
        }
        return 0;
    }

    /**
     * Get street by coordinates string.
     *
     * @param coordinates the coordinates
     * @return the string
     */
    public String getStreetByCoordinates(String coordinates) {
        for (CoordinatesElevation ce : coordinatesElevations) {
            if (ce.getGpsCoordinates().equals(coordinates)) {
                return ce.getStreet();
            }
        }
        return null;
    }
}
