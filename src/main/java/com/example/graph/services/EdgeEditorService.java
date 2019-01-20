package com.example.graph.services;

import com.example.graph.exceptions.CustomEdgeEditionException;
import com.example.graph.utils.GraphHolder;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.springframework.stereotype.Service;

@Service
public class EdgeEditorService {
    public void tryToAddEdge(Integer from, Integer to) throws CustomEdgeEditionException {
        if (from == null || to == null) {
            throw new CustomEdgeEditionException("Passed values of <from> or <to> is null");
        }

        Graph<Integer, DefaultEdge> graph = GraphHolder.getInstance().getGraph();
        if (!graph.containsVertex(from) && !graph.containsVertex(to)) {
            throw new CustomEdgeEditionException("Can't add new vertexes and edge separately from graph");
        }

        if (graph.containsVertex(from) && !graph.containsVertex(to)) {
            graph.addVertex(to);
        } else if (!graph.containsVertex(from) && graph.containsVertex(to)) {
            graph.addVertex(from);
        }
        graph.addEdge(from, to);
    }

    public void tryToRemoveEdge(Integer from, Integer to) throws CustomEdgeEditionException {
        if (from == null || to == null) {
            throw new CustomEdgeEditionException("Passed values of <from> or <to> is null");
        }
        Graph<Integer, DefaultEdge> graph = GraphHolder.getInstance().getGraph();

        if (!graph.containsEdge(from, to)) {
            throw new CustomEdgeEditionException(String.format("Graph doesn't contain deleting edge with start:%d and target:%d vertexes", from, to));
        }

        if (graph.incomingEdgesOf(to).size() > 1) {
            graph.removeEdge(from, to);
        } else {
            throw new CustomEdgeEditionException(String.format("Can't remove edge from node:%d, to node:%d", from, to));
        }
    }


}
