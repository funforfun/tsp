package syntaxGraph;

import dbExecutor.TExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import textParser.TextParser;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class SyntaxGraphBuilder {

    private static final Logger LOGGER = LogManager.getLogger(SyntaxGraphBuilder.class.getName());

    private final Connection connection;

    public SyntaxGraphBuilder(Connection connection) {
        this.connection = connection;
    }

    public void run(String text) throws Exception {
        List<String> sentences = TextParser.splitOnSentences(text);

        int x = 1;
        for (String sentence : sentences) {
            List<String> wordsFromSentence = TextParser.splitOnWords(sentence);

            findInitialFormsForWords(wordsFromSentence);
            x++;
        }
    }


    private void findInitialFormsForWords(List<String> wordsFromSentence) throws SQLException {

        Set<String> setWords = new HashSet<>(wordsFromSentence);
        String wordsAsString = "['" + String.join("','", setWords) + "']";
        LOGGER.info("testQuery2");
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

        int x = 1;

    }
}
