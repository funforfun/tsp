package ru.mivar.syntax.syntaxGraph.builder;


import ru.mivar.syntax.syntaxGraph.builder.constructorType.NormalGraph;
import ru.mivar.syntax.syntaxGraph.builder.constructorType.LinearGraph;
import ru.mivar.syntax.syntaxGraph.components.Sentence;
import ru.mivar.syntax.syntaxGraph.components.SyntaxGraph;

import java.sql.Connection;
import java.util.Map;

public class Builder {

    public static Map<Sentence, SyntaxGraph> buildLinearGraph(String text, Connection connection) throws Exception {
        LinearGraph linearGraph = new LinearGraph(connection);
        return linearGraph.run(text);
    }

    public static Map<Sentence, SyntaxGraph> buildNormalGraph(String text) throws Exception {
        throw new Exception(NormalGraph.class.getName() + " is not implemented yet");
    }
}
