package graph;

import graph.enums.GraphType;
import graph.model.Edge;
import graph.model.Vertex;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.*;

/**
 * Created by Jakub Fortunka on 11.10.2016.
 */
@RunWith(Parameterized.class)
public class IGraphTest {
    /*
       undirectedGraph:
       1 <--(2)--> 3
       2 <--(4)--> 3
       2 <--(3)--> 4
       3 <--(4)--> 4
       4 <--(3)--> 1

       directedGraph:
       1 --(2)--> 3
       2 --(4)--> 3
       2 --(3)--> 4
       3 --(4)--> 4
       4 --(3)--> 1
     */

    private Vertex<String> firstNode = new Vertex<>(1,"");
    private Vertex<String> secondNode = new Vertex<>(2,"");
    private Vertex<String> thirdNode = new Vertex<>(3,"");
    private Vertex<String> fourthNode = new Vertex<>(4,"");

    private Edge<Integer> firstThirdEdge = new Edge<>(2, 1, 3, 2);
    private Edge<Integer> secondThirdEdge = new Edge<>(4, 2, 3, 4);
    private Edge<Integer> secondForthEdge = new Edge<>(3, 2, 4, 3);
    private Edge<Integer> thirdForthEdge = new Edge<>(4, 3, 4, 4);
    private Edge<Integer> forthFirstEdge = new Edge<>(3, 4, 1, 3);

    List<Edge<Integer>> usedEdges = Arrays.asList(firstThirdEdge, secondThirdEdge, secondForthEdge, thirdForthEdge, forthFirstEdge);

    private IGraph<Integer, String> undirectedGraph;

    private IGraph<Integer, String> directedGraph;

    public IGraphTest(Class<? extends IGraph> klazz) {
        if (klazz.equals(MatrixGraph.class)) {
            this.undirectedGraph = new MatrixGraph<>(GraphType.UNDIRECTED);
            this.directedGraph = new MatrixGraph<>(GraphType.DIRECTED);
        } else {
            this.undirectedGraph = new AdjacencyGraph<>(GraphType.UNDIRECTED);
            this.directedGraph = new AdjacencyGraph<>(GraphType.DIRECTED);
        }
    }

    @Parameterized.Parameters(name = "{index} {0}")
    public static Collection<Object[]> instancesToTest() {
        return Arrays.asList(
                new Object[]{MatrixGraph.class},
                new Object[]{AdjacencyGraph.class}
        );
    }

    @Before
    public void setUp() throws Exception {
        initGraphVertices(undirectedGraph);
        initGraphEdges(undirectedGraph);
        initGraphVertices(directedGraph);
        initGraphEdges(directedGraph);
    }

    private void initGraphVertices(IGraph<Integer, String> graph) {
        graph.addVertex(firstNode);
        graph.addVertex(secondNode);
        graph.addVertex(thirdNode);
        graph.addVertex(fourthNode);
    }

    private void initGraphEdges(IGraph<Integer, String> graph) {
        usedEdges.forEach(graph::addEdge);
        /*graph.addEdge(firstThirdEdge);
        graph.addEdge(secondForthEdge);
        graph.addEdge(secondThirdEdge);
        graph.addEdge(thirdForthEdge);*/
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getNeighboursDirected() throws Exception {
        assertThat(undirectedGraph.getNeighbours(thirdNode), containsInAnyOrder(firstNode, secondNode, fourthNode));
        assertThat(directedGraph.getNeighbours(thirdNode), containsInAnyOrder(fourthNode));
    }

    @Test
    public void getIncidentEdges() throws Exception {
        assertThat(undirectedGraph.getIncidentEdges(thirdNode), containsInAnyOrder(firstThirdEdge, secondThirdEdge, thirdForthEdge));
        assertThat(directedGraph.getIncidentEdges(thirdNode), containsInAnyOrder(thirdForthEdge));
    }

    @Test
    public void verticesCount() throws Exception {
        assertEquals(4, undirectedGraph.verticesCount());
        assertEquals(4, directedGraph.verticesCount());
    }

    @Test
    public void edgesCount() throws Exception {
        assertEquals(5, undirectedGraph.edgesCount());
        assertEquals(5, directedGraph.edgesCount());
    }

    @Test
    public void edgeBetweenVerticesExists() throws Exception {
        assertTrue(undirectedGraph.edgeBetweenVerticesExists(firstNode, thirdNode));
        assertFalse(undirectedGraph.edgeBetweenVerticesExists(firstNode, secondNode));
        assertTrue(directedGraph.edgeBetweenVerticesExists(firstNode, thirdNode));
        assertFalse(directedGraph.edgeBetweenVerticesExists(thirdNode, secondNode));
    }

    @Test
    public void getAllVertices() throws Exception {
        assertThat(undirectedGraph.getAllVertices(), containsInAnyOrder(firstNode, secondNode, thirdNode, fourthNode));
        assertThat(directedGraph.getAllVertices(), containsInAnyOrder(firstNode, secondNode, thirdNode, fourthNode));
    }

    @Test
    public void getAllEdges() throws Exception {
        List<Edge<Integer>> edges = undirectedGraph.getAllEdges();
        assertThat(undirectedGraph.getAllEdges(), containsInAnyOrder(usedEdges));
        assertThat(directedGraph.getAllEdges(), containsInAnyOrder(usedEdges));
    }
}