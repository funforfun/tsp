package syntaxGraph.builder.constructorType;

import syntaxGraph.components.Sentence;
import syntaxGraph.components.SyntaxGraph;

import java.util.Map;

public interface IConstructorGraph {

    Map<Sentence, SyntaxGraph> run(String text) throws Exception;
}
