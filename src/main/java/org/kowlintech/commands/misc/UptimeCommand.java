package org.kowlintech.commands.misc;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import org.kowlintech.utils.constants.Global;

import java.lang.management.ManagementFactory;

public class UptimeCommand extends Command {

    public UptimeCommand(Category category) {
        this.name = "uptime";
        this.guildOnly = true;
        this.help = "Shows the bot uptime.";
        this.category = category;
    }

    @Override
    protected void execute(CommandEvent event) {
        final long duration = ManagementFactory.getRuntimeMXBean().getUptime();

        final long years = duration / 31104000000L;
        final long months = duration / 2592000000L % 12;
        final long days = duration / 86400000L % 30;
        final long hours = duration / 3600000L % 24;
        final long minutes = duration / 60000L % 60;
        final long seconds = duration / 1000L % 60;

        String uptime = (years == 0 ? "" : "**" + years + "** Years, ") + (months == 0 ? "" : "**" + months + "** Months, ") + (days == 0 ? "" : "**" + days + "** Days, ") + (hours == 0 ? "" : "**" + hours + "** Hours, ")
                + (minutes == 0 ? "" : "**" + minutes + "** Minutes, ") + (seconds == 0 ? "" : "**" + seconds + "** Seconds, ");

        uptime = replaceLast(uptime, ", ", "");
        uptime = replaceLast(uptime, ",", " and");

        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Uptime");
        embed.setDescription(uptime);
        embed.setColor(Global.COLOR);

        event.reply(embed.build());
    }

    private String replaceLast(final String text, final String regex, final String replacement) {
        return text.replaceFirst("(?s)(.*)" + regex, "$1" + replacement);
    }
}
