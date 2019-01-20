package com.example.graph.utils;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import java.util.Arrays;
import java.util.List;

public class SimpleGraphBuilder {
    public static void buildGraph() {
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

        graph.addEdge(vertexes.get(1), vertexes.get(2));
        graph.addEdge(vertexes.get(2), vertexes.get(8));

        graph.addEdge(vertexes.get(3), vertexes.get(4));
        graph.addEdge(vertexes.get(3), vertexes.get(5));
        graph.addEdge(vertexes.get(3), vertexes.get(6));
        graph.addEdge(vertexes.get(3), vertexes.get(7));

        graph.addEdge(vertexes.get(4), vertexes.get(9));
        GraphHolder.initGraphHolder(graph);
    }
}
