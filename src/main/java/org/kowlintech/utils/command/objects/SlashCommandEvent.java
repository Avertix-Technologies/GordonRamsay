package org.kowlintech.utils.command.objects;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;

import java.util.function.Consumer;

public class SlashCommandEvent {

    private Guild guild;
    private String args;
    private net.dv8tion.jda.api.events.interaction.SlashCommandEvent rawEvent;
    private JDA jda;
    private Member member;
    private TextChannel channel;

    public SlashCommandEvent(Guild guild, String args, net.dv8tion.jda.api.events.interaction.SlashCommandEvent rawEvent, JDA jda, Member member, TextChannel channel) {
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
    public Guild getGuild() {
        return guild;
    }

    /**
     * @return The command arguments;
     */
    public String getArgs() {
        return args;
    }

    /**
     * @return The raw GuildMessageReceived event.
     */
    public net.dv8tion.jda.api.events.interaction.SlashCommandEvent getRawEvent() {
        return rawEvent;
    }

    /**
     * @return The author/executor of the command.
     */
    public Member getMember() {
        return member;
    }

    /**
     * @return The channel of which the command was executed in.
     */
    public TextChannel getChannel() {
        return channel;
    }

    /**
     * @return The JDA client.
     */
    public JDA getJDA() {
        return jda;
    }

    /**
     * Replies with a String message to the command channel.
     */
    public void reply(String message, Consumer<Message> success) {
        getChannel().sendMessage(message).queue(m -> success.accept(m));
    }

    /**
     * Replies with a String message to the command channel.
     */
    public void reply(String message) {
        getChannel().sendMessage(message).queue();
    }

    /**
     * Replies with an MessageEmbed to the command channel.
     */
    public void reply(MessageEmbed embed) {
        getChannel().sendMessage(embed).queue();
    }

    /**
     * Replies with an MessageEmbed to the command channel.
     */
    public void reply(MessageEmbed embed, Consumer<Message> success) {
        getChannel().sendMessage(embed).queue(m -> success.accept(m));
    }
}
