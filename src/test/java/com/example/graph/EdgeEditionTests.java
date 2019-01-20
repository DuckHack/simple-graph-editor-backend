package com.example.graph;

import com.example.graph.exceptions.CustomEdgeEditionException;
import com.example.graph.services.EdgeEditorService;
import com.example.graph.utils.GraphHolder;
import com.example.graph.utils.SimpleGraphBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.Silent.class)
@SpringBootTest
public class EdgeEditionTests {

    @InjectMocks
    private EdgeEditorService edgeEditorService;

    @Before
    public void setUp() {
        SimpleGraphBuilder.buildGraph();
    }

    @Test(expected = CustomEdgeEditionException.class)
    public void shouldNotAddEdgeWithNullPassedVertex() throws CustomEdgeEditionException {
        Integer from = null;
        Integer to = 10;
        edgeEditorService.tryToAddEdge(from, to);
    }

    @Test
    public void shouldAddNewEdge() throws CustomEdgeEditionException {
        Integer from = 7;
        Integer to = 10;
        edgeEditorService.tryToAddEdge(from, to);
        assertTrue(GraphHolder.getInstance().getGraph().containsEdge(from, to));
    }

    @Test(expected = CustomEdgeEditionException.class)
    public void shouldNotAddSeparateSubGraph() throws CustomEdgeEditionException {
        Integer from = 10;
        Integer to = 11;
        edgeEditorService.tryToAddEdge(from, to);
    }

    @Test(expected = CustomEdgeEditionException.class)
    public void shouldNotDeleteEdgeWithNullVertex() throws CustomEdgeEditionException {
        Integer from = null;
        Integer to = 10;
        edgeEditorService.tryToRemoveEdge(from, to);
    }

    @Test(expected = CustomEdgeEditionException.class)
    public void shouldNotDeleteNotExistingEdge() throws CustomEdgeEditionException {
        Integer from = 12;
        Integer to = 10;
        edgeEditorService.tryToRemoveEdge(from, to);
    }

    @Test(expected = CustomEdgeEditionException.class)
    public void shouldNotDeleteSingleEdgeForVertex() throws CustomEdgeEditionException {
        Integer from = 4;
        Integer to = 9;
        edgeEditorService.tryToRemoveEdge(from, to);
    }
}
