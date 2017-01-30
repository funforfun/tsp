package syntaxGraph.builder;

import syntaxGraph.builder.constructorType.NormalGraph;
import syntaxGraph.builder.constructorType.PlainGraph;
import syntaxGraph.components.Sentence;
import syntaxGraph.components.SyntaxGraph;

import java.sql.Connection;
import java.util.Map;

public class Builder {

    public static Map<Sentence, SyntaxGraph> buildPlainGraph(String text, Connection connection) throws Exception {
        PlainGraph plainGraph = new PlainGraph(connection);
        return plainGraph.run(text);
    }

    public static Map<Sentence, SyntaxGraph> buildNormalGraph(String text) throws Exception {
        throw new Exception(NormalGraph.class.getName() + " is not implemented yet");
    }
}
