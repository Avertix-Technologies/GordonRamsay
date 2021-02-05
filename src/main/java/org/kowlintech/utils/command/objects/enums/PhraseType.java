package org.kowlintech.utils.command.objects.enums;

public enum PhraseType {

    SHOVEL("Shovel"),
    CRIME("Crime"),
    ASK("Ask");

    private String name;

    PhraseType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
