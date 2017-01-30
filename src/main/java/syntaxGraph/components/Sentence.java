package syntaxGraph.components;

public class Sentence {
    private final int id;
    private final String text;

    public Sentence(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }
}
