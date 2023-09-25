package lapr.project.graphbase;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

/**
 * The type Graph test.
 */
class GraphTest {
    /**
     * The Instance.
     */
    Graph<String, String> instance = new Graph<>(true) ;

    /**
     * Instantiates a new Graph test.
     */
    public GraphTest() {
    }

    /**
     * Test of numVertices method, of class Graph.
     */
    @Test
    public void testNumVertices() {
        System.out.println("Test numVertices");

        Assertions.assertTrue((instance.numVertices()==0));

        instance.insertVertex("A");
        Assertions.assertTrue( (instance.numVertices()==1));

        instance.insertVertex("B");
        Assertions.assertTrue( (instance.numVertices()==2));

        instance.removeVertex("A");
        Assertions.assertTrue( (instance.numVertices()==1));

        instance.removeVertex("B");
        Assertions.assertTrue((instance.numVertices()==0));
    }

    /**
     * Test of vertices method, of class Graph.
     */
    @Test
    public void testVertices() {
        System.out.println("Test vertices");

        Iterator<String> itVerts = instance.vertices().iterator();

        Assertions.assertFalse(itVerts.hasNext());

        instance.insertVertex("A");
        instance.insertVertex("B");

        itVerts = instance.vertices().iterator();

        Assertions.assertTrue((itVerts.next().compareTo("A")==0));
        Assertions.assertTrue((itVerts.next().compareTo("B")==0));

        instance.removeVertex("A");

        itVerts = instance.vertices().iterator();
        Assertions.assertEquals((itVerts.next().compareTo("B")), 0);

        instance.removeVertex("B");

        itVerts = instance.vertices().iterator();
        Assertions.assertFalse(itVerts.hasNext());
    }

    /**
     * Test of numEdges method, of class Graph.
     */
    @Test
    public void testNumEdges() {
        System.out.println("Test numEdges");

        Assertions.assertTrue((instance.numEdges()==0));

        instance.insertEdge("A","B","Edge1",6);
        Assertions.assertTrue((instance.numEdges()==1));

        instance.insertEdge("A","C","Edge2",1);
        Assertions.assertTrue((instance.numEdges()==2));

        instance.removeEdge("A","B");
        Assertions.assertTrue((instance.numEdges()==1));

        instance.removeEdge("A","C");
        Assertions.assertTrue((instance.numEdges()==0));
    }

    /**
     * Test of edges method, of class Graph.
     */
    @Test
    public void testEdges() {
        System.out.println("Test Edges");

        Iterator<Edge<String,String>> itEdge = instance.edges().iterator();

        Assertions.assertTrue((!itEdge.hasNext()));

        instance.insertEdge("A","B","Edge1",6);
        instance.insertEdge("A","C","Edge2",1);
        instance.insertEdge("B","D","Edge3",3);
        instance.insertEdge("C","D","Edge4",4);
        instance.insertEdge("C","E","Edge5",1);
        instance.insertEdge("D","A","Edge6",2);
        instance.insertEdge("E","D","Edge7",1);
        instance.insertEdge("E","E","Edge8",1);

        itEdge = instance.edges().iterator();

        itEdge.next(); itEdge.next();
        Assertions.assertEquals(itEdge.next().getElement(), "Edge3");

        itEdge.next(); itEdge.next();
        Assertions.assertEquals(itEdge.next().getElement(), "Edge6");

        instance.removeEdge("A","B");

        itEdge = instance.edges().iterator();
        Assertions.assertEquals(itEdge.next().getElement(), "Edge2");

        instance.removeEdge("A","C"); instance.removeEdge("B","D");
        instance.removeEdge("C","D"); instance.removeEdge("C","E");
        instance.removeEdge("D","A"); instance.removeEdge("E","D");
        instance.removeEdge("E","E");
        itEdge = instance.edges().iterator();
        Assertions.assertTrue( (!itEdge.hasNext()));
    }

    /**
     * Test of getEdge method, of class Graph.
     */
    @Test
    public void testGetEdge() {
        System.out.println("Test getEdge");

        instance.insertEdge("A","B","Edge1",6);
        instance.insertEdge("A","C","Edge2",1);
        instance.insertEdge("B","D","Edge3",3);
        instance.insertEdge("C","D","Edge4",4);
        instance.insertEdge("C","E","Edge5",1);
        instance.insertEdge("D","A","Edge6",2);
        instance.insertEdge("E","D","Edge7",1);
        instance.insertEdge("E","E","Edge8",1);

        Assertions.assertNull(instance.getEdge("A", "E"));

        Assertions.assertEquals(instance.getEdge("B", "D").getElement(), "Edge3");
        Assertions.assertNull(instance.getEdge("D", "B"));

        instance.removeEdge("D","A");
        Assertions.assertNull(instance.getEdge("D", "A"));

        Assertions.assertEquals(instance.getEdge("E", "E").getElement(), "Edge8");
    }

    /**
     * Test of endVertices method, of class Graph.
     */
    @Test
    public void testEndVertices() {
        System.out.println("Test endVertices");

        instance.insertEdge("A","B","Edge1",6);
        instance.insertEdge("A","C","Edge2",1);
        instance.insertEdge("B","D","Edge3",3);
        instance.insertEdge("C","D","Edge4",4);
        instance.insertEdge("C","E","Edge5",1);
        instance.insertEdge("D","A","Edge6",2);
        instance.insertEdge("E","D","Edge7",1);
        instance.insertEdge("E","E","Edge8",1);


        //assertTrue("endVertices should be null", instance.endVertices(edge0)==null);

        Edge<String,String> edge1 = instance.getEdge("A","B");
        //vertices = instance.endVertices(edge1);
        Assertions.assertEquals(instance.endVertices(edge1)[0], "A");
        Assertions.assertEquals(instance.endVertices(edge1)[1], "B");
    }

    /**
     * Test of opposite method, of class Graph.
     */
    @Test
    public void testOpposite() {
        System.out.println("Test opposite");

        instance.insertVertex("A");
        instance.insertVertex("B");
        instance.insertVertex("C");
        instance.insertVertex("D");
        instance.insertVertex("E");

        instance.insertEdge("A","B","Edge1",6);
        instance.insertEdge("A","C","Edge2",1);
        instance.insertEdge("B","D","Edge3",3);
        instance.insertEdge("C","D","Edge4",4);
        instance.insertEdge("C","E","Edge5",1);
        instance.insertEdge("D","A","Edge6",2);
        instance.insertEdge("E","D","Edge7",1);
        instance.insertEdge("E","E","Edge8",1);

        Edge<String,String> edge5 = instance.getEdge("C","E");
        String vert = instance.opposite("A", edge5);
        Assertions.assertNull(vert);

        Edge<String,String> edge1 = instance.getEdge("A","B");
        vert = instance.opposite("A", edge1);
        Assertions.assertEquals(vert, "B");

        Edge<String,String> edge8 = instance.getEdge("E","E");
        vert = instance.opposite("E", edge8);
        Assertions.assertEquals(vert, "E");
    }

    /**
     * Test of outDegree method, of class Graph.
     */
    @Test
    public void testOutDegree() {
        System.out.println("Test outDegree");

        instance.insertVertex("A");
        instance.insertVertex("B");
        instance.insertVertex("C");
        instance.insertVertex("D");
        instance.insertVertex("E");

        instance.insertEdge("A","B","Edge1",6);
        instance.insertEdge("A","C","Edge2",1);
        instance.insertEdge("B","D","Edge3",3);
        instance.insertEdge("C","D","Edge4",4);
        instance.insertEdge("C","E","Edge5",1);
        instance.insertEdge("D","A","Edge6",2);
        instance.insertEdge("E","D","Edge7",1);
        instance.insertEdge("E","E","Edge8",1);

        int outdeg = instance.outDegree("G");
        Assertions.assertEquals(-1, outdeg);

        outdeg = instance.outDegree("A");
        Assertions.assertEquals(outdeg, 2);

        outdeg = instance.outDegree("B");
        Assertions.assertEquals(outdeg, 1);

        outdeg = instance.outDegree("E");
        Assertions.assertEquals(outdeg, 2);
    }

    /**
     * Test of inDegree method, of class Graph.
     */
    @Test
    public void testInDegree() {
        System.out.println("Test inDegree");

        instance.insertVertex("A");
        instance.insertVertex("B");
        instance.insertVertex("C");
        instance.insertVertex("D");
        instance.insertVertex("E");

        instance.insertEdge("A","B","Edge1",6);
        instance.insertEdge("A","C","Edge2",1);
        instance.insertEdge("B","D","Edge3",3);
        instance.insertEdge("C","D","Edge4",4);
        instance.insertEdge("C","E","Edge5",1);
        instance.insertEdge("D","A","Edge6",2);
        instance.insertEdge("E","D","Edge7",1);
        instance.insertEdge("E","E","Edge8",1);

        int indeg = instance.inDegree("G");
        Assertions.assertEquals(-1, indeg);

        indeg = instance.inDegree("A");
        Assertions.assertEquals(indeg, 1);

        indeg = instance.inDegree("D");
        Assertions.assertEquals(indeg, 3);

        indeg = instance.inDegree("E");
        Assertions.assertEquals(indeg, 2);
    }

    /**
     * Test of outgoingEdges method, of class Graph.
     */
    @Test
    public void testOutgoingEdges() {
        System.out.println(" Test outgoingEdges");

        instance.insertVertex("A");
        instance.insertVertex("B");
        instance.insertVertex("C");
        instance.insertVertex("D");
        instance.insertVertex("E");

        instance.insertEdge("A","B","Edge1",6);
        instance.insertEdge("A","C","Edge2",1);
        instance.insertEdge("B","D","Edge3",3);
        instance.insertEdge("C","D","Edge4",4);
        instance.insertEdge("C","E","Edge5",1);
        instance.insertEdge("D","A","Edge6",2);
        instance.insertEdge("E","D","Edge7",1);
        instance.insertEdge("E","E","Edge8",1);

        Iterator<Edge<String,String>> itEdge = instance.outgoingEdges("C").iterator();
        Edge<String,String> first = itEdge.next();
        Edge<String,String> second = itEdge.next();
        Assertions.assertTrue(
                ( (first.getElement().equals("Edge4") && second.getElement().equals("Edge5")) ||
                        (first.getElement().equals("Edge5") && second.getElement().equals("Edge4")) ));

        instance.removeEdge("E","E");

        itEdge = instance.outgoingEdges("E").iterator();
        Assertions.assertEquals(itEdge.next().getElement(), "Edge7");

        instance.removeEdge("E","D");

        itEdge = instance.outgoingEdges("E").iterator();
        Assertions.assertTrue((!itEdge.hasNext()));
    }

    /**
     * Test of incomingEdges method, of class Graph.
     */
    @Test
    public void testIncomingEdges() {

        instance.insertVertex("A");
        instance.insertVertex("B");
        instance.insertVertex("C");
        instance.insertVertex("D");
        instance.insertVertex("E");

        instance.insertEdge("A","B","Edge1",6);
        instance.insertEdge("A","C","Edge2",1);
        instance.insertEdge("B","D","Edge3",3);
        instance.insertEdge("C","D","Edge4",4);
        instance.insertEdge("C","E","Edge5",1);
        instance.insertEdge("D","A","Edge6",2);
        instance.insertEdge("E","D","Edge7",1);
        instance.insertEdge("E","E","Edge8",1);

        Iterator<Edge<String,String>> itEdge = instance.incomingEdges("D").iterator();

        Assertions.assertEquals(itEdge.next().getElement(), "Edge3");
        Assertions.assertEquals(itEdge.next().getElement(), "Edge4");
        Assertions.assertEquals(itEdge.next().getElement(), "Edge7");

        itEdge = instance.incomingEdges("E").iterator();

        Assertions.assertEquals(itEdge.next().getElement(), "Edge5");
        Assertions.assertEquals(itEdge.next().getElement(), "Edge8");

        instance.removeEdge("E","E");

        itEdge = instance.incomingEdges("E").iterator();

        Assertions.assertEquals(itEdge.next().getElement(), "Edge5");

        instance.removeEdge("C","E");

        itEdge = instance.incomingEdges("E").iterator();
        Assertions.assertTrue((!itEdge.hasNext()));
    }

    /**
     * Test of insertVertex method, of class Graph.
     */
    @Test
    public void testInsertVertex() {
        System.out.println("Test insertVertex");

        instance.insertVertex("A");
        instance.insertVertex("B");
        instance.insertVertex("C");
        instance.insertVertex("D");
        instance.insertVertex("E");

        Iterator <String> itVert = instance.vertices().iterator();

        Assertions.assertEquals(itVert.next(), "A");
        Assertions.assertEquals(itVert.next(), "B");
        Assertions.assertEquals(itVert.next(), "C");
        Assertions.assertEquals(itVert.next(), "D");
        Assertions.assertEquals(itVert.next(), "E");
    }

    /**
     * Test of insertEdge method, of class Graph.
     */
    @Test
    public void testInsertEdge() {
        System.out.println("Test insertEdge");

        Assertions.assertTrue( (instance.numEdges()==0));

        instance.insertEdge("A","B","Edge1",6);
        Assertions.assertTrue((instance.numEdges()==1));

        instance.insertEdge("A","C","Edge2",1);
        Assertions.assertTrue((instance.numEdges()==2));

        instance.insertEdge("B","D","Edge3",3);
        Assertions.assertTrue((instance.numEdges()==3));

        instance.insertEdge("C","D","Edge4",4);
        Assertions.assertTrue((instance.numEdges()==4));

        instance.insertEdge("C","E","Edge5",1);
        Assertions.assertTrue((instance.numEdges()==5));

        instance.insertEdge("D","A","Edge6",2);
        Assertions.assertTrue((instance.numEdges()==6));

        instance.insertEdge("E","D","Edge7",1);
        Assertions.assertTrue((instance.numEdges()==7));

        instance.insertEdge("E","E","Edge8",1);
        Assertions.assertTrue((instance.numEdges()==8));

        Iterator <Edge<String,String>> itEd = instance.edges().iterator();

        itEd.next(); itEd.next();
        Assertions.assertEquals(itEd.next().getElement(), "Edge3");
        itEd.next(); itEd.next();
        Assertions.assertEquals(itEd.next().getElement(), "Edge6");
    }

    /**
     * Test of removeVertex method, of class Graph.
     */
    @Test
    public void testRemoveVertex() {
        System.out.println("Test removeVertex");

        instance.insertVertex("A");
        instance.insertVertex("B");
        instance.insertVertex("C");
        instance.insertVertex("D");
        instance.insertVertex("E");

        instance.removeVertex("C");
        Assertions.assertTrue((instance.numVertices()==4));

        Iterator<String> itVert = instance.vertices().iterator();
        Assertions.assertEquals(itVert.next(), "A");
        Assertions.assertEquals(itVert.next(), "B");
        Assertions.assertEquals(itVert.next(), "D");
        Assertions.assertEquals(itVert.next(), "E");

        instance.removeVertex("A");
        Assertions.assertTrue( (instance.numVertices()==3));

        itVert = instance.vertices().iterator();
        Assertions.assertEquals(itVert.next(), "B");
        Assertions.assertEquals(itVert.next(), "D");
        Assertions.assertEquals(itVert.next(), "E");

        instance.removeVertex("E");
        Assertions.assertTrue((instance.numVertices()==2));

        itVert = instance.vertices().iterator();

        Assertions.assertEquals(itVert.next(), "B");
        Assertions.assertEquals(itVert.next(), "D");

        instance.removeVertex("B"); instance.removeVertex("D");
        Assertions.assertTrue((instance.numVertices()==0));
    }

    /**
     * Test of removeEdge method, of class Graph.
     */
    @Test
    public void testRemoveEdge() {
        System.out.println("Test removeEdge");

        Assertions.assertTrue((instance.numEdges()==0));

        instance.insertEdge("A","B","Edge1",6);
        instance.insertEdge("A","C","Edge2",1);
        instance.insertEdge("B","D","Edge3",3);
        instance.insertEdge("C","D","Edge4",4);
        instance.insertEdge("C","E","Edge5",1);
        instance.insertEdge("D","A","Edge6",2);
        instance.insertEdge("E","D","Edge7",1);
        instance.insertEdge("E","E","Edge8",1);

        Assertions.assertTrue((instance.numEdges()==8));

        instance.removeEdge("E","E");
        Assertions.assertTrue((instance.numEdges()==7));

        Iterator <Edge<String,String>> itEd = instance.edges().iterator();

        itEd.next(); itEd.next();
        Assertions.assertEquals(itEd.next().getElement(), "Edge3");
        itEd.next(); itEd.next();
        Assertions.assertEquals(itEd.next().getElement(), "Edge6");

        instance.removeEdge("C","D");
        Assertions.assertTrue((instance.numEdges()==6));

        itEd = instance.edges().iterator();
        itEd.next(); itEd.next();
        Assertions.assertEquals(itEd.next().getElement(), "Edge3");
        Assertions.assertEquals(itEd.next().getElement(), "Edge5");
        Assertions.assertEquals(itEd.next().getElement(), "Edge6");
        Assertions.assertEquals(itEd.next().getElement(), "Edge7");
    }

    /**
     * Test of toString method, of class Graph.
     */
    @Test
    public void testClone() {
        System.out.println("Test Clone");

        instance.insertEdge("A","B","Edge1",6);
        instance.insertEdge("A","C","Edge2",1);
        instance.insertEdge("B","D","Edge3",3);
        instance.insertEdge("C","D","Edge4",4);
        instance.insertEdge("C","E","Edge5",1);
        instance.insertEdge("D","A","Edge6",2);
        instance.insertEdge("E","D","Edge7",1);
        instance.insertEdge("E","E","Edge8",1);

        Graph<String,String> instClone = instance.clone();

        Assertions.assertEquals(instClone.numVertices(), instance.numVertices());
        Assertions.assertEquals(instClone.numEdges(), instance.numEdges());

        //vertices should be equal
        Iterator<String> itvertClone = instClone.vertices().iterator();
        for (String s : instance.vertices()) Assertions.assertEquals(itvertClone.next(), s);
    }

    /**
     * Test equals.
     */
    @Test
    public void testEquals() {
        System.out.println("Test Equals");

        instance.insertEdge("A","B","Edge1",6);
        instance.insertEdge("A","C","Edge2",1);
        instance.insertEdge("B","D","Edge3",3);
        instance.insertEdge("C","D","Edge4",4);
        instance.insertEdge("C","E","Edge5",1);
        instance.insertEdge("D","A","Edge6",2);
        instance.insertEdge("E","D","Edge7",1);
        instance.insertEdge("E","E","Edge8",1);

        Assertions.assertNotNull(instance);

        Assertions.assertEquals(instance, instance);

        Assertions.assertEquals(instance.clone().toString(), instance.toString());

        Graph<String,String> other = instance.clone();

        other.removeEdge("E","E");
        Assertions.assertNotEquals(other, instance);

        other.insertEdge("E","E","Edge8",1);
        Assertions.assertEquals(other.toString(), instance.toString());

        other.removeVertex("D");
        Assertions.assertNotEquals(other, instance);

    }
}