package com.example.graph;

import com.example.graph.exceptions.IncorrectFileContentException;
import com.example.graph.services.GraphService;
import com.example.graph.strategyPattern.SimpleGraphValidator;
import com.example.graph.utils.GraphHolder;
import com.example.graph.utils.SimpleGraphBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.Silent.class)
@SpringBootTest
public class GraphBuildingTests {

    @InjectMocks
    private GraphService graphService;

    @Spy
    private SimpleGraphValidator simpleGraphValidator;

    @Before
    public void setUp() {
        SimpleGraphBuilder.buildGraph();
    }

    @Test(expected = IncorrectFileContentException.class)
    public void shouldNotCreateGraphWhileNullPassing() throws IncorrectFileContentException {
        String nullString = null;
        graphService.createGraph(nullString);
    }

    @Test
    public void shouldBuildGraphFromCorrectFile() throws IncorrectFileContentException {
        String path = "test_graph.txt";
        String testGraph = GraphHolder.getInstance().getGraph().toString();
        String gotString = getGraphFileAsString(path);
        graphService.createGraph(gotString);
        String newCreatedGraph = GraphHolder.getInstance().getGraph().toString();
        assertEquals(testGraph, newCreatedGraph);
    }

    @Test(expected = IncorrectFileContentException.class)
    public void shouldNotBuildGraphFromFileWithLetters() throws IncorrectFileContentException {
        String path = "test_graph_with_lettersInside.txt";
        String gotString = getGraphFileAsString(path);
        graphService.createGraph(gotString);
    }

    @Test(expected = IncorrectFileContentException.class)
    public void shouldNotBuildGraphWithWrongGraphSizeGiven() throws IncorrectFileContentException {
        String path = "test_graph_wrong_graph_size.txt";
        String gotString = getGraphFileAsString(path);
        graphService.createGraph(gotString);
    }

    @Test(expected = IncorrectFileContentException.class)
    public void shouldNotBuildGraphWithFewDigitsFirstLineFile() throws IncorrectFileContentException {
        String path = "test_graph_few_digits_first_line.txt";
        String gotString = getGraphFileAsString(path);
        graphService.createGraph(gotString);
    }

    @Test(expected = IncorrectFileContentException.class)
    public void shouldNotBuildGraphWithWrongVertexNumberGivenAsSecondDigitInLine() throws IncorrectFileContentException {
        String path = "test_graph_wrong_second_digit.txt";
        String gotString = getGraphFileAsString(path);
        graphService.createGraph(gotString);
    }


    private String getGraphFileAsString(String path) {
        File file = null;
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        String gotString = "";


        ClassLoader classLoader = getClass().getClassLoader();

        try {
            file = new File(classLoader.getResource(path).getFile());
        } catch (Exception e) {
            System.out.println("Error reading graph test file");
        }

        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
        } catch (Exception e) {
            System.out.println("Error fileReader creating");
        }


        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try {
            line = bufferedReader.readLine();
            do {
                stringBuilder.append(line);
                stringBuilder.append(System.getProperty("line.separator"));
                line = bufferedReader.readLine();
            } while (line != null);
            System.out.println(stringBuilder.toString());
            gotString = stringBuilder.toString();
        } catch (Exception e) {
            System.out.println("Error reading from fileReader");
        }
        return gotString;
    }
}
