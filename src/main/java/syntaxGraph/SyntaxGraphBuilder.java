package syntaxGraph;

import org.languagetool.AnalyzedSentence;
import org.languagetool.AnalyzedToken;
import org.languagetool.AnalyzedTokenReadings;
import org.languagetool.JLanguageTool;
import org.languagetool.language.Russian;
import textParser.TextParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SyntaxGraphBuilder {
    public SyntaxGraphBuilder() {

    }

    public void run(String text) throws IOException {
        List<String> sentences = TextParser.splitOnSentences(text);

        for (String sentence : sentences) {
            buildSentenceSyntaxModel(sentence);
        }
    }

    private String buildSentenceSyntaxModel(String sentence) throws IOException {
        List<String> words = TextParser.splitOnWords(sentence);

        return "";
    }
}
