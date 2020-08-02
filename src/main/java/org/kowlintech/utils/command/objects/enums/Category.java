package org.kowlintech.utils.command.objects.enums;

public enum Category {

    MISCELLANEOUS("Miscellaneous"),
    FUN("Fun"),
    MODERATION("Moderation"),
    OWNER("Owner");

    private String name;

    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
