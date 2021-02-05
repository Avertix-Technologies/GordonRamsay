package org.kowlintech.utils.command.objects;

import org.kowlintech.utils.command.objects.enums.PhraseType;

public class EconomyPhrase {

    private String text;
    private boolean fate;
    private int id;
    private PhraseType type;

    public EconomyPhrase(String text, boolean fate, int id, PhraseType type) {
        this.text = text;
        this.fate = fate;
        this.id = id;
        this.type = type;
    }

    /**
     * @return The text of the phrase.
     */
    public String getText() {
        return text;
    }

    /**
     * @return The fate of the phrase.
     */
    public boolean getFate() {
        return fate;
    }

    /**
     * @return The id of the phrase.
     */
    public int getId() {
        return id;
    }

    /**
     * @return The type of phrase.
     */
    public PhraseType getType() {
        return type;
    }
}
