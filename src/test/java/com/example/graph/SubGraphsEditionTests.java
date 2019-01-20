package com.example.graph;

import com.example.graph.exceptions.CustomSubGraphEditingException;
import com.example.graph.services.SubGraphsReplaceService;
import com.example.graph.utils.GraphHolder;
import com.example.graph.utils.SimpleGraphBuilder;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.Silent.class)
@SpringBootTest
public class SubGraphsEditionTests {

    @InjectMocks
    private SubGraphsReplaceService subGraphsReplaceService;

    @Before
    public void setUp() {
        SimpleGraphBuilder.buildGraph();
    }

    @Test(expected = CustomSubGraphEditingException.class)
    public void shouldNotReplaceWithNullArgs() throws CustomSubGraphEditingException {
        Integer firstNode = null;
        Integer secondNode = 9;
        subGraphsReplaceService.tryToReplaceSubGraphs(firstNode, secondNode);
    }

    @Test(expected = CustomSubGraphEditingException.class)
    public void shouldNotReplaceWithPassedRootNode() throws CustomSubGraphEditingException {
        Integer firstNode = 0;
        Integer secondNode = 1;
        subGraphsReplaceService.tryToReplaceSubGraphs(firstNode, secondNode);
    }

    @Test(expected = CustomSubGraphEditingException.class)
    public void shouldNotReplaceWithMutualParents() throws CustomSubGraphEditingException {
        Integer firstNode = 1;
        Integer secondNode = 2;
        subGraphsReplaceService.tryToReplaceSubGraphs(firstNode, secondNode);
    }

    @Test
    public void shouldReplaceSubGraphs() throws CustomSubGraphEditingException {
        List<Integer> vertexes = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        Graph<Integer, DefaultEdge> graph = new SimpleDirectedGraph<>(DefaultEdge.class);
        graph.addVertex(vertexes.get(0));
        graph.addVertex(vertexes.get(1));
        graph.addVertex(vertexes.get(2));
        graph.addVertex(vertexes.get(3));
        graph.addVertex(vertexes.get(4));
        graph.addVertex(vertexes.get(5));
        graph.addVertex(vertexes.get(6));
        graph.addVertex(vertexes.get(7));
        graph.addVertex(vertexes.get(8));
        graph.addVertex(vertexes.get(9));

        graph.addEdge(vertexes.get(0), vertexes.get(1));
        graph.addEdge(vertexes.get(0), vertexes.get(3));

        graph.addEdge(vertexes.get(2), vertexes.get(8));

        graph.addEdge(vertexes.get(3), vertexes.get(5));
        graph.addEdge(vertexes.get(3), vertexes.get(6));
        graph.addEdge(vertexes.get(3), vertexes.get(7));

        graph.addEdge(vertexes.get(4), vertexes.get(9));
        graph.addEdge(vertexes.get(3), vertexes.get(2));
        graph.addEdge(vertexes.get(1), vertexes.get(4));


        String targetGraph = graph.toString();
        subGraphsReplaceService.tryToReplaceSubGraphs(2, 4);

        assertEquals(targetGraph, GraphHolder.getInstance().getGraph().toString());
    }
}
