package org.kowlintech.utils.command.objects;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.function.Consumer;

public class CommandEvent implements ICommandEvent {

    private Guild guild;
    private String args;
    private GuildMessageReceivedEvent rawEvent;
    private JDA jda;
    private Member member;
    private TextChannel channel;

    public CommandEvent(Guild guild, String args, GuildMessageReceivedEvent rawEvent, JDA jda, Member member, TextChannel channel) {
        this.guild = guild;
        this.args = args;
        this.rawEvent = rawEvent;
        this.jda = jda;
        this.member = member;
        this.channel = channel;
    }

    /**
     * @return The guild of which the command was executed in.
     */
    @Override
    public Guild getGuild() {
        return guild;
    }

    /**
     * @return The command arguments;
     */
    @Override
    public String getArgs() {
        return args;
    }

    /**
     * @return The raw GuildMessageReceived event.
     */
    @Override
    public GuildMessageReceivedEvent getRawEvent() {
        return rawEvent;
    }

    /**
     * @return The author/executor of the command.
     */
    @Override
    public Member getMember() {
        return member;
    }

    /**
     * @return The channel of which the command was executed in.
     */
    @Override
    public TextChannel getChannel() {
        return channel;
    }

    /**
     * @return The JDA client.
     */
    @Override
    public JDA getJDA() {
        return jda;
    }

    /**
     * Replies with a String message to the command channel.
     */
    @Override
    public void reply(String message, Consumer<Message> success) {
        getChannel().sendMessage(message).queue(m -> success.accept(m));
    }

    /**
     * Replies with a String message to the command channel.
     */
    @Override
    public void reply(String message) {
    }

    /**
     * Replies with an MessageEmbed to the command channel.
     */
    @Override
    public void reply(MessageEmbed embed) {

    }

    /**
     * Replies with an MessageEmbed to the command channel.
     */
    @Override
    public void reply(MessageEmbed embed, Consumer<Message> success) {
        getChannel().sendMessage(embed).queue(m -> success.accept(m));
    }
}
