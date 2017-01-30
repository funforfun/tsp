package syntaxGraph.components;

public class Edge {

    private final SyntaxGraph graph;

    private final Vertex start;
    private final Vertex end;

    public Edge(Vertex start, Vertex end) {
        if(start.getGraph() != end.getGraph()){
            throw new RuntimeException("Vertices from different graphs");
        }
        this.start = start;
        this.end = end;
        graph = start.getGraph();
        graph.addEdge(this);
    }

    public SyntaxGraph getGraph() {
        return graph;
    }

    public Vertex getStart() {
        return start;
    }

    public Vertex getEnd() {
        return end;
    }
}
