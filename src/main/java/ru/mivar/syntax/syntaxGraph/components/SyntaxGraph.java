package ru.mivar.syntax.syntaxGraph.components;

import java.util.HashSet;
import java.util.Set;

public class SyntaxGraph {
    private Set<Vertex> vertices = new HashSet<>();
    private Set<Edge> edges = new HashSet<>();

    public Set<Vertex> getVertices() {
        return vertices;
    }

    public Set<Edge> getEdges() {
        return edges;
    }

    void addVertex(Vertex vertex) {
        vertices.add(vertex);
    }

    void addEdge(Edge edge){
        edges.add(edge);
    }
}
