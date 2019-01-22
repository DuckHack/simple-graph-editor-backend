package com.example.graph.controllers;

import com.example.graph.exceptions.*;
import com.example.graph.services.EdgeEditorService;
import com.example.graph.services.GraphService;
import com.example.graph.services.SubGraphsReplaceService;
import com.example.graph.services.VertexesEditorService;
import com.example.graph.utils.GraphHolder;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/graph")
public class GraphController {
    private GraphService graphService;
    private VertexesEditorService vertexesEditorService;
    private EdgeEditorService edgeEditorService;
    private SubGraphsReplaceService subGraphsReplaceService;

    @Autowired
    public GraphController(GraphService graphService,
                           VertexesEditorService vertexesEditorService,
                           EdgeEditorService edgeEditorService,
                           SubGraphsReplaceService subGraphsReplaceService) {
        this.graphService = graphService;
        this.vertexesEditorService = vertexesEditorService;
        this.edgeEditorService = edgeEditorService;
        this.subGraphsReplaceService = subGraphsReplaceService;
    }

    @PostMapping("/")
    public String createGraph(@RequestBody String graphJson) throws IncorrectFileContentException, GraphHolderNotInitilizedException {
        String path = "$.file";
        DocumentContext documentContext = JsonPath.parse(graphJson);
        String graphString = documentContext.read(path, String.class);
        graphService.createGraph(graphString);
        return GraphHolder.getInstance().getGraph().toString();
    }

    @PostMapping("/addNode/{from}/{newNode}")
    public String addNode(@PathVariable(value = "from") Integer from,
                          @PathVariable(value = "newNode") Integer newNode) throws CustomVertexEditionException, GraphHolderNotInitilizedException {
        vertexesEditorService.tryToAddNode(from, newNode);
        return GraphHolder.getInstance().getGraph().toString();
    }

    @PostMapping("/removeNode/{node}")
    public String removeNode(@PathVariable(value = "node") Integer node) throws CustomVertexEditionException, GraphHolderNotInitilizedException {
        vertexesEditorService.tryToRemoveNode(node);
        return GraphHolder.getInstance().getGraph().toString();
    }

    @PostMapping("addEdge/{from}/{to}")
    public String addEdge(@PathVariable(value = "from") Integer from,
                          @PathVariable(value = "to") Integer to) throws CustomEdgeEditionException, GraphHolderNotInitilizedException {
        edgeEditorService.tryToAddEdge(from, to);
        return GraphHolder.getInstance().getGraph().toString();
    }

    @PostMapping("/removeEdge/{from}/{to}")
    public String removeEdge(@PathVariable(value = "from") Integer from,
                             @PathVariable(value = "to") Integer to) throws CustomEdgeEditionException, GraphHolderNotInitilizedException {
        edgeEditorService.tryToRemoveEdge(from, to);
        return GraphHolder.getInstance().getGraph().toString();
    }

    @PostMapping("/replaceSubs/{firstNode}/{secondNode}")
    public String replaceSubs(@PathVariable(value = "firstNode") Integer firstNode,
                              @PathVariable(value = "secondNode") Integer secondNode) throws CustomSubGraphEditingException, GraphHolderNotInitilizedException {
        subGraphsReplaceService.tryToReplaceSubGraphs(firstNode, secondNode);
        return GraphHolder.getInstance().getGraph().toString();
    }
}
