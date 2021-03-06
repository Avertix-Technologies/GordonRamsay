package org.kowlintech.commands.misc;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.sharding.ShardManager;
import org.kowlintech.GordonRamsay;
import org.kowlintech.utils.command.objects.Command;
import org.kowlintech.utils.command.objects.CommandEvent;
import org.kowlintech.utils.command.objects.CommandExecutor;
import org.kowlintech.utils.command.objects.enums.Category;
import org.kowlintech.utils.constants.Global;

import java.sql.SQLException;
import java.time.Instant;

@Command(name = "shardinfo", description = "Gets shard information.", category = Category.MISCELLANEOUS)
public class ShardInfoCommand implements CommandExecutor {

    @Override
    public void execute(CommandEvent event) throws SQLException {
        event.getJDA().getRestPing().queue(ping -> {
            EmbedBuilder eb = new EmbedBuilder();

            JDA.ShardInfo info = event.getJDA().getShardInfo();
            ShardManager manager = event.getJDA().getShardManager();

            String uptime;

            try {
                final long duration = (System.currentTimeMillis() - GordonRamsay.shard_start_times.get(info.getShardId()));

                final long years = duration / 31104000000L;
                final long months = duration / 2592000000L % 12;
                final long days = duration / 86400000L % 30;
                final long hours = duration / 3600000L % 24;
                final long minutes = duration / 60000L % 60;
                final long seconds = duration / 1000L % 60;

                uptime = (years == 0 ? "" : "**" + years + "** Years, ") + (months == 0 ? "" : "**" + months + "** Months, ") + (days == 0 ? "" : "**" + days + "** Days, ") + (hours == 0 ? "" : "**" + hours + "** Hours, ")
                        + (minutes == 0 ? "" : "**" + minutes + "** Minutes, ") + (seconds == 0 ? "" : "**" + seconds + "** Seconds, ");

                uptime = replaceLast(uptime, ", ", "");
                uptime = replaceLast(uptime, ",", " and");
            } catch (Exception ex) {
                uptime = "";
            }

            eb.setTitle("Shard Information");
            eb.setColor(Global.COLOR);
            eb.addField("Current Shard", "#" + info.getShardId(), true);
            eb.addField("Current Shard Uptime", (uptime.isEmpty() ? "**Currently Starting...**" : uptime), true);
            eb.addField("Total Shards", String.valueOf(manager.getShardsTotal()), true);
            eb.addField("Running Shards", String.valueOf(manager.getShardsRunning()), true);
            eb.setFooter("Ping: " + ping + "ms");
            eb.setTimestamp(Instant.now());
            event.reply(eb.build());
        });
    }

    private String replaceLast(final String text, final String regex, final String replacement) {
        return text.replaceFirst("(?s)(.*)" + regex, "$1" + replacement);
    }
}
