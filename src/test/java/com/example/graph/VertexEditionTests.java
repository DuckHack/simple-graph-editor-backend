package com.example.graph;

import com.example.graph.exceptions.CustomVertexEditionException;
import com.example.graph.exceptions.GraphHolderNotInitilizedException;
import com.example.graph.services.VertexesEditorService;
import com.example.graph.utils.GraphHolder;
import com.example.graph.utils.SimpleGraphBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.Silent.class)
@SpringBootTest
public class VertexEditionTests {

    @InjectMocks
    private VertexesEditorService vertexesEditorService;

    @Before
    public void setUp() {
        SimpleGraphBuilder.buildGraph();
    }

    @Test
    public void shouldAddVertex() throws CustomVertexEditionException, GraphHolderNotInitilizedException {
        Integer from = 6;
        Integer newNode = 10;

        vertexesEditorService.tryToAddNode(from, newNode);

        assertTrue(GraphHolder.getInstance().getGraph().containsVertex(newNode));
        assertTrue(!GraphHolder.getInstance().getGraph().incomingEdgesOf(newNode).isEmpty());
    }

    @Test(expected = CustomVertexEditionException.class)
    public void shouldNotAddNullVertex() throws CustomVertexEditionException, GraphHolderNotInitilizedException {
        Integer from = 6;
        Integer newNode = null;

        vertexesEditorService.tryToAddNode(from, newNode);
    }

    @Test
    public void shouldRemoveVertex() throws CustomVertexEditionException, GraphHolderNotInitilizedException {
        Integer removeNode = 9;
        vertexesEditorService.tryToRemoveNode(removeNode);
        assertFalse(GraphHolder.getInstance().getGraph().containsVertex(removeNode));
    }

    @Test(expected = CustomVertexEditionException.class)
    public void shouldNotRemoveVertexWhichIsNotLeaf() throws CustomVertexEditionException, GraphHolderNotInitilizedException {
        Integer removeNode = 4;
        vertexesEditorService.tryToRemoveNode(removeNode);
    }

    @Test(expected = CustomVertexEditionException.class)
    public void shouldNotRemoveNullVertex() throws CustomVertexEditionException, GraphHolderNotInitilizedException {
        Integer removeNode = null;
        vertexesEditorService.tryToRemoveNode(removeNode);
    }


}
