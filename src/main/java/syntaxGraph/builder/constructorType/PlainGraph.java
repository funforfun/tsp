package syntaxGraph.builder.constructorType;

import dbExecutor.TExecutor;
import org.apache.commons.lang.math.IntRange;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import syntaxGraph.builder.Sentence;
import syntaxGraph.builder.SyntaxGraphBuilder;
import syntaxGraph.components.Edge;
import syntaxGraph.components.SentenceWord;
import syntaxGraph.components.SyntaxGraph;
import syntaxGraph.components.Vertex;
import textParser.TextParser;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class PlainGraph {

    private static final Logger LOGGER = LogManager.getLogger(SyntaxGraphBuilder.class.getName());

    private final Connection connection;

    public PlainGraph(Connection connection) {
        this.connection = connection;
    }

    public void run(String text) throws Exception {
        LOGGER.info("Start building syntax graphs...");
        List<String> sentencesStr = TextParser.splitOnSentences(text);
        List<Sentence> sentences = new ArrayList<>();
        ListIterator<String> iterator = sentencesStr.listIterator();
        while (iterator.hasNext()) {
            sentences.add(new Sentence(iterator.nextIndex(), iterator.next().trim()));
        }

        Map<Sentence, SyntaxGraph> sentenceToSyntaxGraph = new HashMap<>();
        for (Sentence sentence : sentences) {
            LinkedList<String> wordsFromSentence = TextParser.splitOnWords(sentence.getText());
            Map<String, Set<String>> wordToInitialForms = findInitialFormsForWords(wordsFromSentence);
            sentenceToSyntaxGraph.putIfAbsent(sentence, buildFlatSyntaxGraph(wordsFromSentence, wordToInitialForms));
        }
        LOGGER.info("Syntax graphs builds");
        int x = 1;
    }

    private SyntaxGraph buildFlatSyntaxGraph(List<String> wordsFromSentence, Map<String, Set<String>> wordToInitialForms) {

        SyntaxGraph syntaxGraph = new SyntaxGraph();
        ListIterator<String> iter = wordsFromSentence.listIterator(0);

        Map<Integer, Set<Vertex>> indexToVertices = new HashMap<>();
        while (iter.hasNext()) {

            Integer current = iter.nextIndex();
            Set<Vertex> verticesStart;
            if (indexToVertices.containsKey(current)) {
                verticesStart = indexToVertices.get(current);
                iter.next();
            } else {
                String word = iter.next();
                Set<String> initialForms = wordToInitialForms.get(word);
                verticesStart = initialForms.stream()
                        .map((item) -> new Vertex(new SentenceWord(word, item), syntaxGraph))
                        .collect(Collectors.toSet());
                indexToVertices.put(current, verticesStart);
            }

            if (iter.hasNext()) {
                Integer next = iter.nextIndex();
                Set<Vertex> verticesEnd;
                if (indexToVertices.containsKey(next)) {
                    verticesEnd = indexToVertices.get(next);
                    iter.next();
                } else {
                    String word = iter.next();
                    Set<String> initialForms = wordToInitialForms.get(word);
                    verticesEnd = initialForms.stream()
                            .map((item) -> new Vertex(new SentenceWord(word, item), syntaxGraph))
                            .collect(Collectors.toSet());
                    indexToVertices.put(next, verticesEnd);
                }

                verticesStart.forEach((vertexStart) -> verticesEnd.forEach((vertexEnd) -> new Edge(vertexStart, vertexEnd)));
                iter.previous();
            }
        }


        return syntaxGraph;
    }


    private Map<String, Set<String>> findInitialFormsForWords(List<String> wordsFromSentence) throws SQLException {
        Set<String> setWords = new HashSet<>(wordsFromSentence);
        String wordsAsString = "['" + String.join("','", setWords) + "']";
        String query =
                "with swords as (select unnest(array" + wordsAsString + ") word )\n" +
                        "    select         s.word, mw.id as mword_id, f.id as form_id, mw2.word, f.values_agg, (select array_agg(value) from values where id in (select unnest(f.values_agg) ) )  as values\n" +
                        "        from     swords s\n" +
                        "            left join mwords     mw  on s.word = mw.word\n" +
                        "            left join forms     f   on mw.id = f.mword_id\n" +
                        "            left join mwords     mw2 on f.initial_form_id = mw2.id";
        Map<String, Set<String>> wordToInitialForms = TExecutor.execQuery(
                connection,
                query,
                (result) -> {
                    Map<String, Set<String>> map = new HashMap<>();
                    while (result.next()) {
                        if (map.containsKey(result.getString(1))) {
                            map.get(result.getString(1)).add(result.getString(4));
                        } else {
                            Set<String> initialForms = new HashSet<>();
                            initialForms.add(result.getString(4));
                            map.put(result.getString(1), initialForms);
                        }
                    }
                    return map;
                }
        );

        return wordToInitialForms;
    }
}
