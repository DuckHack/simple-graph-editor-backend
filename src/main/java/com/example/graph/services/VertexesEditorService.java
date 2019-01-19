package com.example.graph.services;

import com.example.graph.utils.GraphHolder;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.springframework.stereotype.Service;

@Service
public class VertexesEditorService {

    public Boolean isPossibleAddNode(Integer node) {
        Graph<Integer, DefaultEdge> graph = GraphHolder.getInstance().getGraph();
        Integer degree = graph.degreeOf(node);
        System.out.println("degree -> " + degree);
        return null;
    }

    public Boolean isPossibleRemoveNode(Integer node) {
        Graph<Integer, DefaultEdge> graph = GraphHolder.getInstance().getGraph();

        if (graph.degreeOf(node) == 1) {
            graph.removeVertex(node);
            return true;
        }

        System.out.println("incomingEdgesOf -> " + graph.incomingEdgesOf(node));
        return false;
    }
}
