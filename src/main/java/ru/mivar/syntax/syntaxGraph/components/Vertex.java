package ru.mivar.syntax.syntaxGraph.components;

/**
 * TODO:
 * добавить коллекции edgesIn, edgesOut, edges, siblings
 */
public class Vertex {
    private final SyntaxGraph graph;
    private final SentenceWord word;

    public Vertex(SentenceWord word, SyntaxGraph graph) {
        this.word = word;
        graph.addVertex(this);
        this.graph = graph;
    }

    public SyntaxGraph getGraph() {
        return graph;
    }

    public SentenceWord getWord() {
        return word;
    }
}