package com.example.graph.utils;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

public class GraphHolder {
    private static GraphHolder graphHolder;
    private static Graph<Integer, DefaultEdge> holdedGraph;

    private GraphHolder() {
    }

    public static void initGraphHolder(Graph<Integer, DefaultEdge> graph) {
        holdedGraph = graph;
    }

    public static GraphHolder getInstance() {
        if (graphHolder == null) {
            synchronized (GraphHolder.class) {
                if (graphHolder == null) {
                    graphHolder = new GraphHolder();
                }
            }
        }
        return graphHolder;
    }

    public Graph<Integer, DefaultEdge> getGraph() {
        return holdedGraph;
    }

}
