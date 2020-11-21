package org.kowlintech.utils;

import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;

public class Messaging {

    public static void send(TextChannel channel, Message message) {
        RESTQueue.addToQueue(channel.sendMessage(message));
    }

    public static void send(TextChannel channel, String message) {
        RESTQueue.addToQueue(channel.sendMessage(message));
    }

    public static void send(TextChannel channel, MessageEmbed embed) {
        RESTQueue.addToQueue(channel.sendMessage(embed));
    }
}
