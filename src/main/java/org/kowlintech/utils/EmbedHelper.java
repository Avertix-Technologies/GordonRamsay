package org.kowlintech.utils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.kowlintech.utils.constants.Global;

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

    public static MessageEmbed buildSuccessEmbed(String title, String description) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(title);
        eb.setDescription(description);
        eb.setColor(Global.COLOR);
        return eb.build();
    }

    public static MessageEmbed buildSuccessEmbed(String description) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Success!");
        eb.setDescription(description);
        eb.setColor(Global.COLOR);
        return eb.build();
    }

    public static MessageEmbed buildWaitingEmbed(String description) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setDescription(description);
        eb.setColor(Color.YELLOW);
        return eb.build();
    }
}
