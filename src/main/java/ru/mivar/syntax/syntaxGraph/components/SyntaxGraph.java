package ru.mivar.syntax.syntaxGraph.components;

import java.util.*;

public class SyntaxGraph {

    private Set<Vertex> vertices = new HashSet<>();
    private Set<Edge> edges = new HashSet<>();
    private Map<SentenceWord, Set<Vertex>> wordVerticesMap = new HashMap<>();

    public SyntaxGraph() {
    }

    public Set<Vertex> getVertices() {
        return vertices;
    }

    public Set<Edge> getEdges() {
        return edges;
    }

    void addVertex(Vertex vertex) {
        SentenceWord sentenceWord = vertex.getWord();
        if (!wordVerticesMap.containsKey(sentenceWord)) {
            wordVerticesMap.put(sentenceWord, new HashSet<>());
        }
        wordVerticesMap.get(sentenceWord).add(vertex);
    }

    void addEdge(Edge edge) {
        edges.add(edge);
    }

    public Map<SentenceWord, Set<Vertex>> getWordVerticesMap() {
        return wordVerticesMap;
    }
}
