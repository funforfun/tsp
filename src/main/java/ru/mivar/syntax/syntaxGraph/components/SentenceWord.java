package ru.mivar.syntax.syntaxGraph.components;

public class SentenceWord {
    private final String word;
    private final String initialForm;

    public SentenceWord(String word, String initialForm) {
        this.word = word;
        this.initialForm = initialForm;
    }

    public String getWord() {
        return word;
    }

    public String getInitialForm() {
        return initialForm;
    }
}
