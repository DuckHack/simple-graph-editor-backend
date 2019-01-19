package com.example.graph.controllers;

import com.example.graph.exceptions.IncorrectFileContentException;
import com.example.graph.services.EdgeEditorService;
import com.example.graph.services.GraphService;
import com.example.graph.services.VertexesEditorService;
import com.example.graph.utils.GraphHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/graph")
public class GraphController {
    private GraphService graphService;
    private VertexesEditorService vertexesEditorService;
    private EdgeEditorService edgeEditorService;

    @Autowired
    public GraphController(GraphService graphService, VertexesEditorService vertexesEditorService, EdgeEditorService edgeEditorService) {
        this.graphService = graphService;
        this.vertexesEditorService = vertexesEditorService;
        this.edgeEditorService = edgeEditorService;
    }

    @PostMapping("/")
    public String createGraph(@RequestParam("file") MultipartFile file) throws IncorrectFileContentException {
        String gotString = null;
        try {
            gotString = new String(file.getBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.out.println("Error extracting text data, " + e);
            return null;
        }
        graphService.createGraph(gotString);
        return GraphHolder.getInstance().getGraph().toString();
    }

    @PostMapping("/addNode/{node}")
    public Boolean addNode(@PathVariable(value = "node") Integer node) {
        return vertexesEditorService.isPossibleAddNode(node);
    }

    @PostMapping("/removeNode/{node}")
    public Boolean removeNode(@PathVariable(value = "node") Integer node) {
        return vertexesEditorService.isPossibleRemoveNode(node);
    }

    @PostMapping("addEdge/{from}/{to}")
    public Boolean addEdge(@PathVariable(value = "from") Integer from,
                           @PathVariable(value = "to") Integer to) {
        return edgeEditorService.isPossibleToAddEdge(from, to);
    }

    @PostMapping("/removeEdge/{from}/{to}")
    public Boolean removeEdge(@PathVariable(value = "from") Integer from,
                              @PathVariable(value = "to") Integer to) {
        return edgeEditorService.isPossibleRemoveEdge(from, to);
    }
}
