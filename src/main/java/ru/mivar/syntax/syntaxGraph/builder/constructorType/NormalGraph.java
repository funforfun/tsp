package ru.mivar.syntax.syntaxGraph.builder.constructorType;


import ru.mivar.syntax.syntaxGraph.components.Sentence;
import ru.mivar.syntax.syntaxGraph.components.SyntaxGraph;

import java.util.Map;

public class NormalGraph implements IConstructorGraph{
    public NormalGraph() throws Exception {
        throw new Exception("Not realized yet");
    }

    @Override
    public Map<Sentence, SyntaxGraph> run(String text) {
        return null;
    }
}
