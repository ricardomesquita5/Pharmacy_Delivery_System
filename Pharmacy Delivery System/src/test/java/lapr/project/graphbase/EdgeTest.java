package lapr.project.graphbase;

import lapr.project.model.Scooter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * The type Edge test.
 */
class EdgeTest {

    /**
     * The Instance.
     */
    Edge<String, String> instance = new Edge<>() ;

    /**
     * Instantiates a new Edge test.
     */
    public EdgeTest() {
    }

    /**
     * Test of getElement method, of class Edge.
     */
    @Test
    public void testGetElement() {
        System.out.println("getElement");

        String expResult = null;
        Assertions.assertEquals(expResult, instance.getElement());

        Edge<String, String> instance1 = new Edge<>("edge1",1.0,null,null);
        expResult = "edge1";
        Assertions.assertEquals(expResult, instance1.getElement());
    }

    /**
     * Test of getWeight method, of class Edge.
     */
    @Test
    public void testGetWeight() {
        System.out.println("getWeight");

        double expResult = 0.0;
        Assertions.assertEquals(expResult, instance.getWeight(), 0.0);
    }

    /**
     * Test of setWeight method, of class Edge.
     */
    @Test
    public void testSetWeight() {
        System.out.println("setWeight");
        double ew = 2.0;
        instance.setWeight(ew);

        double expResult = 2.0;
        Assertions.assertEquals(expResult, instance.getWeight(), 2.0);
    }

    /**
     * Test of getVOrig method, of class Edge.
     */
    @Test
    public void testGetVOrig() {
        System.out.println("getVOrig");

        Object expResult = null;
        Assertions.assertEquals(expResult, instance.getVOrig());

        Vertex<String, String> vertex1 = new Vertex<>(1,"Vertex1");
        Edge<String, String> otherEdge = new Edge<>("edge1",1.0,vertex1,vertex1);
        Assertions.assertEquals(vertex1.getElement(), otherEdge.getVOrig());
    }

    /**
     * Testhash code.
     */
    @Test
    void testhashCode(){
        Scooter s=new Scooter("3","2","1","4");
        Edge<String, String> instance1 = new Edge<>() ;
        Assertions.assertEquals(this.instance.hashCode(), instance1.hashCode());
        Assertions.assertNotEquals(this.instance.hashCode(), s.hashCode());
    }

    /**
     * Test of getVDest method, of class Edge.
     */
    @Test
    public void testGetVDest() {
        System.out.println("getVDest");

        Object expResult = null;
        Assertions.assertEquals(expResult, instance.getVDest());

        Vertex<String, String> vertex1 = new Vertex<>(1,"Vertex1");
        Edge<String, String> otherEdge = new Edge<>("edge1",1.0,vertex1,vertex1);
        Assertions.assertEquals(vertex1.getElement(), otherEdge.getVDest());
    }

    /**
     * Test of getEndpoints method, of class Edge.
     */
    @Test
    public void testGetEndpoints() {
        System.out.println("getEndpoints");

        Vertex<String, String> vertex1 = new Vertex<>(1,"Vertex1");

        Edge<String, String> instance2 = new Edge(1.0,1.0,vertex1,vertex1) ;

        String[] expResult1 = {"Vertex1","Vertex1"};
        Assertions.assertArrayEquals(expResult1, instance2.getEndpoints());
    }

    /**
     * Test of equals method, of class Edge.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");

        Assertions.assertNotEquals(instance, null);

        Assertions.assertEquals(instance, instance);

        Assertions.assertEquals(instance.clone(), instance);

        Vertex<String, String> vertex1 = new Vertex<>(1,"Vertex1");
        Edge<String, String> otherEdge = new Edge<>("edge1",1.0,vertex1,vertex1);

        Assertions.assertNotEquals(otherEdge, instance);
    }

    /**
     * Test of clone method, of class Edge.
     */
    @Test
    public void testClone() {
        System.out.println("clone");

        Vertex<String, String> vertex1 = new Vertex<>(1,"Vertex1");
        Edge<String, String> otherEdge = new Edge<>("edge1",1.0,vertex1,vertex1);

        Edge instClone = otherEdge.clone();

        Assertions.assertSame(otherEdge.getElement(), instClone.getElement());
        Assertions.assertEquals(instClone.getWeight(), otherEdge.getWeight());

        String[] expResult = otherEdge.getEndpoints();
        Assertions.assertArrayEquals(expResult, instClone.getEndpoints());
    }

    /**
     * Test of toString method, of class Edge.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Vertex<String, String> vertex1 = new Vertex<>(1,"Vertex1");
        Edge<String, String> instance2 = new Edge("edge1",1.0,vertex1,vertex1);

        String expResult = "(edge1) - 1.0 - Vertex1";
        String result = instance2.toString().trim();
        Assertions.assertEquals(expResult, result);
    }

}