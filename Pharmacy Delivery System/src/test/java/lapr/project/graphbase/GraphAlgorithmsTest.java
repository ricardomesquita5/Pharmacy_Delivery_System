package lapr.project.graphbase;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * The type Graph algorithms test.
 */
class GraphAlgorithmsTest {


    /**
     * The Complete map.
     */
    static Graph<String,String> completeMap = new Graph<>(false);

    /**
     * The Incomplete map.
     */
    static Graph<String,String> incompleteMap = new Graph<>(false);

    /**
     * The GraphAlgorithms ga.
     */
    private GraphAlgorithms ga;

    /**
     * Sets up.
     *
     * @throws Exception the exception
     */
    @BeforeAll
    public static void setUp() throws Exception {

        completeMap.insertVertex("Porto");
        completeMap.insertVertex("Braga");
        completeMap.insertVertex("Vila Real");
        completeMap.insertVertex("Aveiro");
        completeMap.insertVertex("Coimbra");
        completeMap.insertVertex("Leiria");

        completeMap.insertVertex("Viseu");
        completeMap.insertVertex("Guarda");
        completeMap.insertVertex("Castelo Branco");
        completeMap.insertVertex("Lisboa");
        completeMap.insertVertex("Faro");

        completeMap.insertEdge("Porto","Aveiro","A1",75);
        completeMap.insertEdge("Porto","Braga","A3",60);
        completeMap.insertEdge("Porto","Vila Real","A4",100);
        completeMap.insertEdge("Viseu","Guarda","A25",75);
        completeMap.insertEdge("Guarda","Castelo Branco","A23",100);
        completeMap.insertEdge("Aveiro","Coimbra","A1",60);
        completeMap.insertEdge("Coimbra","Lisboa","A1",200);
        completeMap.insertEdge("Coimbra","Leiria","A34",80);
        completeMap.insertEdge("Aveiro","Leiria","A17",120);
        completeMap.insertEdge("Leiria","Lisboa","A8",150);

        completeMap.insertEdge("Aveiro","Viseu","A25",85);
        completeMap.insertEdge("Leiria","Castelo Branco","A23",170);
        completeMap.insertEdge("Lisboa","Faro","A2",280);

        incompleteMap = completeMap.clone();

        incompleteMap.removeEdge("Aveiro","Viseu");
        incompleteMap.removeEdge("Leiria","Castelo Branco");
        incompleteMap.removeEdge("Lisboa","Faro");
    }

    /**
     * Tear down class.
     */
    @AfterAll
    public static void tearDownClass() {
    }


    /**
     * Instantiates a new Graph algorithms test.
     */
    public GraphAlgorithmsTest() {
        this.ga = new GraphAlgorithms();
    }

    /**
     * Test of shortestPath method, of class GraphAlgorithms.
     */
    @Test
    public void testShortestPath() {
        System.out.println("Test of shortest path");

        LinkedList<String> shortPath = new LinkedList<>();
        double lenpath=0;
        lenpath=GraphAlgorithms.shortestPath(completeMap,"Porto","LX",shortPath);
        Assertions.assertEquals(lenpath, 0);

        lenpath=GraphAlgorithms.shortestPath(incompleteMap,"Porto","Faro",shortPath);
        Assertions.assertEquals(lenpath, 0);

        lenpath=GraphAlgorithms.shortestPath(completeMap,"Porto","Porto",shortPath);
        Assertions.assertEquals(shortPath.size(), 1);

        lenpath=GraphAlgorithms.shortestPath(incompleteMap,"Porto","Lisboa",shortPath);
        Assertions.assertEquals(lenpath, 335);

        Iterator<String> it = shortPath.iterator();

        Assertions.assertEquals(it.next().compareTo("Porto"), 0);
        Assertions.assertEquals(it.next().compareTo("Aveiro"), 0);
        Assertions.assertEquals(it.next().compareTo("Coimbra"), 0);
        Assertions.assertEquals(it.next().compareTo("Lisboa"), 0);

        lenpath=GraphAlgorithms.shortestPath(incompleteMap,"Braga","Leiria",shortPath);
        Assertions.assertEquals(lenpath, 255);

        it = shortPath.iterator();

        Assertions.assertEquals(it.next().compareTo("Braga"), 0);
        Assertions.assertEquals(it.next().compareTo("Porto"), 0);
        Assertions.assertEquals(it.next().compareTo("Aveiro"), 0);
        Assertions.assertEquals(it.next().compareTo("Leiria"), 0);

        shortPath.clear();
        lenpath=GraphAlgorithms.shortestPath(completeMap,"Porto","Castelo Branco",shortPath);
        Assertions.assertEquals(lenpath, 335);
        Assertions.assertEquals(shortPath.size(), 5);

        it = shortPath.iterator();

        Assertions.assertEquals(it.next().compareTo("Porto"), 0);
        Assertions.assertEquals(it.next().compareTo("Aveiro"), 0);
        Assertions.assertEquals(it.next().compareTo("Viseu"), 0);
        Assertions.assertEquals(it.next().compareTo("Guarda"), 0);
        Assertions.assertEquals(it.next().compareTo("Castelo Branco"), 0);

        //Changing Edge: Aveiro-Viseu with Edge: Leiria-C.Branco
        //should change shortest path between Porto and Castelo Branco

        completeMap.removeEdge("Aveiro", "Viseu");
        completeMap.insertEdge("Leiria","Castelo Branco","A23",170);
        shortPath.clear();
        lenpath=GraphAlgorithms.shortestPath(completeMap,"Porto","Castelo Branco",shortPath);
        Assertions.assertEquals(lenpath, 365);
        Assertions.assertEquals(shortPath.size(), 4);

        it = shortPath.iterator();

        Assertions.assertEquals(it.next().compareTo("Porto"), 0);
        Assertions.assertEquals(it.next().compareTo("Aveiro"), 0);
        Assertions.assertEquals(it.next().compareTo("Leiria"), 0);
        Assertions.assertEquals(it.next().compareTo("Castelo Branco"), 0);

    }
}