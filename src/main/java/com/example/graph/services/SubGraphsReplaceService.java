package com.example.graph.services;

import com.example.graph.exceptions.CustomSubGraphEditingException;
import com.example.graph.utils.GraphHolder;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SubGraphsReplaceService {
    public void tryToReplaceSubGraphs(Integer firstNode, Integer secondNode) throws CustomSubGraphEditingException {
        if (firstNode == null || secondNode == null) {
            throw new CustomSubGraphEditingException("Some of passed values, first or second node is null");
        }

        Set<Integer> firstRootNode = getRootNode(firstNode);
        Set<Integer> secondRootNode = getRootNode(secondNode);

        if (firstRootNode == null
                || secondRootNode == null
                || firstRootNode.contains(firstNode)
                || secondRootNode.contains(secondNode)) {
            throw new CustomSubGraphEditingException("Can't perform replacement, one of given nodes is root node");
        }

        if (Collections.singletonList(firstRootNode).get(0).equals(Collections.singletonList(secondRootNode).get(0))) {
            throw new CustomSubGraphEditingException("Passed nodes have mutual root node");
        }

        replaceSubGraphs(firstNode, secondNode);
    }


    private Set<Integer> getRootNode(Integer checkNode) {
        Set<Integer> sources = new HashSet<>();
        sources.add(checkNode);
        Set<Integer> checkNodes = new HashSet<>(checkNode);
        Set<DefaultEdge> incomingEdges = GraphHolder.getInstance().getGraph().incomingEdgesOf(checkNode);

        while (!incomingEdges.isEmpty()) {

            for (DefaultEdge defaultEdge : incomingEdges
                    ) {
                checkNodes.add(getSource(defaultEdge));
            }

            for (Integer node : checkNodes
                    ) {
                incomingEdges = getIncomingEdges(node);
            }

            if (incomingEdges.isEmpty()) {
                break;
            }
            sources = new HashSet<>(checkNodes);
            checkNodes.clear();
        }
        if (Collections.singletonList(sources).get(0).equals(checkNode)) {
            sources.clear();
        }
        return sources;
    }

    private Set<DefaultEdge> getIncomingEdges(Integer node) {
        return GraphHolder.getInstance().getGraph().incomingEdgesOf(node);
    }

    private Integer getSource(DefaultEdge defaultEdge) {
        return GraphHolder.getInstance().getGraph().getEdgeSource(defaultEdge);
    }

    private void replaceSubGraphs(Integer firstNode, Integer secondNode) {
        Graph<Integer, DefaultEdge> graph = GraphHolder.getInstance().getGraph();
        List<DefaultEdge> firstNodeIncomeEdges = new ArrayList<>(graph.incomingEdgesOf(firstNode));
        List<DefaultEdge> secondNodeIncomeEdges = new ArrayList<>(graph.incomingEdgesOf(secondNode));
        replaceEdges(firstNode, firstNodeIncomeEdges, secondNodeIncomeEdges);
        replaceEdges(secondNode, secondNodeIncomeEdges, firstNodeIncomeEdges);
    }

    private void replaceEdges(Integer node, List<DefaultEdge> actualEdges, List<DefaultEdge> newEdges) {
        Graph<Integer, DefaultEdge> graph = GraphHolder.getInstance().getGraph();

        for (DefaultEdge addEdge : newEdges
                ) {
            graph.addEdge(graph.getEdgeSource(addEdge), node);
        }

        for (DefaultEdge removeEdge : actualEdges) {
            graph.removeEdge(removeEdge);
        }
    }
}
