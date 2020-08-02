package org.kowlintech.utils.command.objects;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.function.Consumer;

public interface ICommandEvent {

    /**
     * @return The guild of which the command was executed in.
     */
    Guild getGuild();

    /**
     * @return The command arguments;
     */
    String getArgs();

    /**
     * @return The raw GuildMessageReceived event.
     */
    GuildMessageReceivedEvent getRawEvent();

    /**
     * @return The author/executor of the command.
     */
    Member getMember();

    /**
     * @return The channel of which the command was executed in.
     */
    TextChannel getChannel();

    /**
     * @return The JDA client.
     */
    JDA getJDA();

    /**
     * Replies with a String message to the command channel.
     */
    void reply(String message, Consumer<Message> success);

    /**
     * Replies with a String message to the command channel.
     */
    void reply(String message);

    /**
     * Replies with an MessageEmbed to the command channel.
     */
    void reply(MessageEmbed embed);

    /**
     * Replies with an MessageEmbed to the command channel.
     */
    void reply(MessageEmbed embed, Consumer<Message> success);
}
