package lapr.project.graphbase;

import java.lang.reflect.Array;
import java.util.Objects;


/**
 * The type Edge.
 *
 * @param <V> the type parameter
 * @param <E> the type parameter
 * @author DEI -ESINF
 */
public class Edge<V, E> implements Comparable {

    /**
     * The Edge's element.
     */
    private E element;           // Edge information
    /**
     * The Edge's weight.
     */
    private double weight;       // Edge weight
    /**
     * The Edge's vOrig.
     */
    private Vertex<V, E> vOrig;  // vertex origin
    /**
     * The Edge's vDest.
     */
    private Vertex<V, E> vDest;  // vertex destination

    /**
     * Instantiates a new Edge.
     */
    public Edge() {
        element = null;
        weight = 0.0;
        vOrig = null;
        vDest = null;
    }

    /**
     * Instantiates a new Edge.
     *
     * @param eInf the e inf
     * @param ew   the ew
     * @param vo   the vo
     * @param vd   the vd
     */
    public Edge(E eInf, double ew, Vertex<V, E> vo, Vertex<V, E> vd) {
        element = eInf;
        weight = ew;
        vOrig = vo;
        vDest = vd;
    }

    /**
     * Gets weight.
     *
     * @return the weight
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Gets element.
     *
     * @return the element
     */
    public E getElement() {
        return element;
    }

    /**
     * Sets weight.
     *
     * @param ew the ew
     */
    public void setWeight(double ew) {
        weight = ew;
    }

    /**
     * Gets v orig.
     *
     * @return the v orig
     */
    public V getVOrig() {
        if (this.vOrig != null)
            return vOrig.getElement();
        return null;
    }

    /**
     * Gets v dest.
     *
     * @return the v dest
     */
    public V getVDest() {
        if (this.vDest != null)
            return vDest.getElement();
        return null;
    }

    /**
     * Get endpoints v [ ].
     *
     * @return the v [ ]
     */
    public V[] getEndpoints() {

        V oElem = null;
        V dElem = null;
        V typeElem = null;

        if (this.vOrig != null)
            oElem = vOrig.getElement();

        if (this.vDest != null)
            dElem = vDest.getElement();

        if (oElem == null && dElem == null)
            return (V[]) Array.newInstance(typeElem.getClass(), 0);

        if (oElem != null)          // To get type
            typeElem = oElem;

        if (dElem != null)
            typeElem = dElem;

        V[] endverts = (V[]) Array.newInstance(typeElem.getClass(), 2);

        endverts[0] = oElem;
        endverts[1] = dElem;

        return endverts;
    }

    @Override
    public boolean equals(Object otherObj) {

        if (this == otherObj) {
            return true;
        }

        if (otherObj == null || this.getClass() != otherObj.getClass()) {
            return false;
        }

        return confirm((Edge<V, E>) otherObj);
    }

    /**
     * Confirm boolean.
     *
     * @param otherEdge the other edge
     * @return the boolean
     */
    public boolean confirm(Edge<V, E> otherEdge) {
        if ((this.vOrig == null && otherEdge.vOrig != null) ||
                (this.vOrig != null && otherEdge.vOrig == null))
            return false;

        if ((this.vDest == null && otherEdge.vDest != null) ||
                (this.vDest != null && otherEdge.vDest == null))
            return false;

        if (this.vOrig != null && !this.vOrig.equals(otherEdge.vOrig))
            return false;

        if (this.vDest != null && !this.vDest.equals(otherEdge.vDest))
            return false;

        if (this.weight != otherEdge.weight)
            return false;

        if (this.element != null && otherEdge.element != null)
            return this.element.equals(otherEdge.element);

        return true;
    }

    @Override
    public Edge<V, E> clone() {
        Edge<V, E> newEdge = new Edge<>();
        newEdge.element = element;
        newEdge.weight = weight;
        newEdge.vOrig = vOrig;
        newEdge.vDest = vDest;
        return newEdge;
    }

    @Override
    public int hashCode() {
        return Objects.hash(element, getWeight(), vOrig, vDest);
    }

    @Override
    public String toString() {
        String st;
        if (element != null)
            st = "      (" + element + ") - ";
        else
            st = "\t ";

        if (weight != 0)
            st += weight + " - " + vDest.getElement() + "\n";
        else
            st += vDest.getElement() + "\n";

        return st;
    }

    @Override
    public int compareTo(Object otherObject) {

        Edge<V, E> other = (Edge<V, E>) otherObject;
        if (this.weight < other.weight) return -1;
        if (this.weight == other.weight) return 0;
        return 1;
    }
}
