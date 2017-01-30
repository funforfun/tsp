package ru.mivar.syntax.syntaxGraph.builder.constructorType;


import ru.mivar.syntax.syntaxGraph.components.Sentence;
import ru.mivar.syntax.syntaxGraph.components.SyntaxGraph;

import java.util.Map;

public interface IConstructorGraph {

    Map<Sentence, SyntaxGraph> run(String text) throws Exception;
}
