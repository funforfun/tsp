package textParser;

import org.languagetool.AnalyzedSentence;
import org.languagetool.AnalyzedToken;
import org.languagetool.AnalyzedTokenReadings;
import org.languagetool.JLanguageTool;
import org.languagetool.language.Russian;
import org.languagetool.rules.RuleMatch;
import org.languagetool.tokenizers.Tokenizer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TextParser {
    private Map<String, List<String>> textLemms = new HashMap<>();

    public TextParser() {
    }

    public void run(String text) {
        try {
            Russian lang = new Russian();

            JLanguageTool langTool = new JLanguageTool(lang);
            langTool.setListUnknownWords(true);

            long timeStart = System.currentTimeMillis();

            pullLemmasFromText(langTool, text);


            Tokenizer tokenizer = lang.getWordTokenizer();
            List<String> tokenizedWords = tokenizer.tokenize(text);

            List<RuleMatch> matches = langTool.check(text);

            for (RuleMatch match : matches) {
                System.out.println("Potential error at characters " +
                        match.getFromPos() + "-" + match.getToPos() + ": " +
                        match.getMessage());
                System.out.println("Suggested correction(s): " +
                        match.getSuggestedReplacements());
            }

            List<String> unknownWords = langTool.getUnknownWords();
            long timeEnd = System.currentTimeMillis();
            System.out.println("Time checker: " + (timeEnd - timeStart) / 1000.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void pullLemmasFromText(JLanguageTool langTool, String text) throws IOException {

        List<String> sentences = langTool.sentenceTokenize(text);
        AnalyzedSentence analyzedSentence = langTool.getRawAnalyzedSentence(text);
        AnalyzedTokenReadings[] analyzedTokens = langTool.getAnalyzedSentence(text).getTokensWithoutWhitespace();
        List<String> lemmas = new ArrayList<>();

        for (AnalyzedTokenReadings analyzedToken : analyzedTokens) {
            List<AnalyzedToken> readings = analyzedToken.getReadings();
            //  todo: разобраться:
            lemmas.addAll(readings.stream().map(AnalyzedToken::getLemma).collect(Collectors.toList()));
            // todo: alternative
//            for (AnalyzedToken reading : readings) {
//                lemmas.add(reading.getLemma());
//            }
        }
        textLemms.put(text, lemmas);
    }
}
