package com.example.graph.services;

import com.example.graph.exceptions.CustomVertexEditionException;
import com.example.graph.exceptions.GraphHolderNotInitilizedException;
import com.example.graph.utils.GraphHolder;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class VertexesEditorService {

    public void tryToAddNode(Integer from, Integer newNode) throws CustomVertexEditionException, GraphHolderNotInitilizedException {
        if (from == null || newNode == null) {
            throw new CustomVertexEditionException("Passed values of <from> or <to> is null");

        }
        Graph<Integer, DefaultEdge> graph = GraphHolder.getInstance().getGraph();
        graph.addVertex(newNode);
        graph.addEdge(from, newNode);
    }

    public void tryToRemoveNode(Integer node) throws CustomVertexEditionException, GraphHolderNotInitilizedException {
        if (node == null) {
            throw new CustomVertexEditionException("Passed values of <from> or <to> is null");
        }

        Graph<Integer, DefaultEdge> graph = GraphHolder.getInstance().getGraph();

        if (graph.outDegreeOf(node) == 0) {
            Set<DefaultEdge> incomingEdgesOf = new HashSet<>(graph.incomingEdgesOf(node));
            graph.removeAllEdges(incomingEdgesOf);
            graph.removeVertex(node);
        } else {
            throw new CustomVertexEditionException(String.format("Passed node:%d is not a leaf", node));

        }
    }
}
