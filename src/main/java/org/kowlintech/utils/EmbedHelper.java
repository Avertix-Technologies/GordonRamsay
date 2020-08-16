package org.kowlintech.utils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

public class EmbedHelper {

    public static MessageEmbed buildErrorEmbed(String title, String description) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(title);
        eb.setDescription(description);
        eb.setColor(Color.RED);
        return eb.build();
    }

    public static MessageEmbed buildErrorEmbed(String description) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Error");
        eb.setDescription(description);
        eb.setColor(Color.RED);
        return eb.build();
    }
}
