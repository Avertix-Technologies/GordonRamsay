package org.kowlintech.listeners;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.kowlintech.utils.constants.Global;

import java.awt.*;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

public class JoinLeaveListener extends ListenerAdapter {

    @Override
    public void onGuildJoin(GuildJoinEvent event) {

        TextChannel log = event.getJDA().getTextChannelById(Global.JOINLEAVE_LOGS);
        Guild guild = event.getGuild();

        for(String id : Global.BLACKLIST) {
            if(id == event.getGuild().getOwnerId()) {
                EmbedBuilder eb = new EmbedBuilder();

                eb.setTitle("Auto-Left Blacklisted Guild");
                eb.addField("Name", guild.getName(), true);
                eb.addField("ID", guild.getId(), true);
                eb.addField("Owner", guild.getOwner().getUser().getName() + "#" + guild.getOwner().getUser().getDiscriminator(), true);
                eb.addField("Member Count", "" + guild.getMembers().size(), true);
                eb.addField("Channel Count", "" + guild.getChannels().size(), true);
                eb.addField("Verification Level", guild.getVerificationLevel().toString(), true);
                eb.addField("Created At", guild.getTimeCreated().format(DateTimeFormatter.ISO_LOCAL_DATE), true);
                eb.setThumbnail(guild.getIconUrl());
                eb.setColor(Color.RED);
                eb.setTimestamp(Instant.now());

                log.sendMessage(eb.build()).queue();

                event.getGuild().leave();
            }
        }

        EmbedBuilder eb = new EmbedBuilder();

        eb.setTitle("Joined Guild");
        eb.addField("Name", guild.getName(), true);
        eb.addField("ID", guild.getId(), true);
        eb.addField("Owner", guild.getOwner().getUser().getName() + "#" + guild.getOwner().getUser().getDiscriminator(), true);
        eb.addField("Member Count", "" + guild.getMembers().size(), true);
        eb.addField("Channel Count", "" + guild.getChannels().size(), true);
        eb.addField("Verification Level", guild.getVerificationLevel().toString(), true);
        eb.addField("Created At", guild.getTimeCreated().format(DateTimeFormatter.ISO_LOCAL_DATE), true);
        eb.setThumbnail(guild.getIconUrl());
        eb.setColor(Color.GREEN);
        eb.setTimestamp(Instant.now());

        log.sendMessage(eb.build()).queue();
    }

    @Override
    public void onGuildLeave(GuildLeaveEvent event) {
        TextChannel log = event.getJDA().getTextChannelById(Global.JOINLEAVE_LOGS);
        Guild guild = event.getGuild();

        EmbedBuilder eb = new EmbedBuilder();

        eb.setTitle("Left Guild");
        eb.addField("Name", guild.getName(), true);
        eb.addField("ID", guild.getId(), true);
        eb.addField("Owner", guild.getOwner().getUser().getName() + "#" + guild.getOwner().getUser().getDiscriminator(), true);
        eb.addField("Member Count", "" + guild.getMembers().size(), true);
        eb.addField("Channel Count", "" + guild.getChannels().size(), true);
        eb.addField("Verification Level", guild.getVerificationLevel().toString(), true);
        eb.addField("Created At", guild.getTimeCreated().format(DateTimeFormatter.ISO_LOCAL_DATE), true);
        eb.setThumbnail(guild.getIconUrl());
        eb.setColor(Color.RED);
        eb.setTimestamp(Instant.now());

        log.sendMessage(eb.build()).queue();
    }

    @Override
    public void onReady(ReadyEvent event) {
        for(Guild guild : event.getJDA().getGuilds()) {
            for(String id : Global.BLACKLIST) {
                TextChannel log = event.getJDA().getTextChannelById(Global.JOINLEAVE_LOGS);

                EmbedBuilder eb = new EmbedBuilder();

                eb.setTitle("Auto-Left Blacklisted Guild");
                eb.addField("Name", guild.getName(), true);
                eb.addField("ID", guild.getId(), true);
                eb.addField("Owner", guild.getOwner().getUser().getName() + "#" + guild.getOwner().getUser().getDiscriminator(), true);
                eb.addField("Member Count", "" + guild.getMembers().size(), true);
                eb.addField("Channel Count", "" + guild.getChannels().size(), true);
                eb.addField("Verification Level", guild.getVerificationLevel().toString(), true);
                eb.addField("Created At", guild.getTimeCreated().format(DateTimeFormatter.ISO_LOCAL_DATE), true);
                eb.setThumbnail(guild.getIconUrl());
                eb.setColor(Color.RED);
                eb.setTimestamp(Instant.now());

                log.sendMessage(eb.build()).queue();

                guild.leave();
            }
        }
    }
}
