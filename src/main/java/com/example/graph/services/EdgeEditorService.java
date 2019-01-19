package com.example.graph.services;

import com.example.graph.utils.GraphHolder;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.springframework.stereotype.Service;

@Service
public class EdgeEditorService {
    public Boolean isPossibleRemoveEdge(Integer from, Integer to) {
        Graph<Integer, DefaultEdge> graph = GraphHolder.getInstance().getGraph();
        if (graph.incomingEdgesOf(to).size() > 1) {
            graph.removeEdge(from, to);
            return true;
        }
        return false;
    }

    public Boolean isPossibleToAddEdge(Integer from, Integer to) {
        if (from == null || to == null) {
            return false;
        }
        Graph<Integer, DefaultEdge> graph = GraphHolder.getInstance().getGraph();
        graph.addEdge(from, to);
        return true;
    }
}
