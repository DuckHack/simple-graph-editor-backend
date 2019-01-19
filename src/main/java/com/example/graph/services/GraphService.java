package com.example.graph.services;

import com.example.graph.exceptions.IncorrectFileContentException;
import com.example.graph.strategyPattern.GraphFileValidator;
import com.example.graph.utils.GraphHolder;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class GraphService {
    private GraphFileValidator graphFileValidator;

    @Autowired
    public GraphService(GraphFileValidator graphFileValidator) {
        this.graphFileValidator = graphFileValidator;
    }

    public void createGraph(String gotString) throws IncorrectFileContentException {
        List<List<Integer>> graphAsIntegerInts = decodeString(gotString);
        System.out.println(graphAsIntegerInts);
        Graph<Integer, DefaultEdge> createdGraph = getGraphObjFromStringLists(graphAsIntegerInts);
        GraphHolder.initGraphHolder(createdGraph);
    }

    private List<List<Integer>> decodeString(String gotString) throws IncorrectFileContentException {
        List<List<Integer>> graphAsIntegerInts;

        //extract file to List of String lists
        List<List<String>> graphAsStrings = Arrays.stream(gotString.split(System.getProperty("line.separator")))
                .map(line -> line.split("\\s+"))
                .map(Arrays::asList)
                .collect(Collectors.toList());

        //if is, parse all digits to integer
        if (graphFileValidator.isFormatCorrect(graphAsStrings)) {
            graphAsIntegerInts = graphAsStrings.stream()
                    .map(list -> list.stream().map(Integer::parseInt).collect(Collectors.toList())).collect(Collectors.toList());
            removeUselessInfo(graphAsIntegerInts);
        } else {
            System.out.println(String.format("Wrong format in graph:\n %s", gotString));
            throw new IncorrectFileContentException(String.format("File siring has not only digits, file content: \n%s", graphAsStrings.toString()));
        }
        return graphAsIntegerInts;
    }

    private void removeUselessInfo(List<List<Integer>> graphAsIntegerInts) {
        final int graphSizeDigitPoss = 0;
        final int vertexNeigNumPoss = 1;
        graphAsIntegerInts.remove(graphSizeDigitPoss);
        graphAsIntegerInts.forEach(l -> l.remove(vertexNeigNumPoss));
    }

    private Graph<Integer, DefaultEdge> getGraphObjFromStringLists(List<List<Integer>> graphAsIntegerInts) {
        Graph<Integer, DefaultEdge> graph = new SimpleDirectedGraph<>(DefaultEdge.class);
        for (List<Integer> vertexesList : graphAsIntegerInts
                ) {
            graph.addVertex(vertexesList.get(0));
        }
        for (List<Integer> vertexesList : graphAsIntegerInts
                ) {
            addEdges(graph, vertexesList);
        }
        return graph;
    }

    private void addEdges(Graph<Integer, DefaultEdge> graph, List<Integer> vertexes) {
        Integer startVertex = vertexes.get(0);
        for (Integer vertex : vertexes.subList(1, vertexes.size())
                ) {
            graph.addEdge(startVertex, vertex);
        }
    }
}
