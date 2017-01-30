package syntaxGraph.builder;

import syntaxGraph.builder.constructorType.NormalGraph;
import syntaxGraph.builder.constructorType.PlainGraph;
import syntaxGraph.components.SyntaxGraph;

import java.sql.Connection;

public class SyntaxGraphBuilder {

    public static void buildPlainGraph(String text, Connection connection) throws Exception {
        PlainGraph plainGraph = new PlainGraph(connection);
        plainGraph.run(text);
    }

    public static void buildNormalGraph(String text, Connection connection) throws Exception {
        throw new Exception(NormalGraph.class.getName() + " is not implemented yet");
    }
}
