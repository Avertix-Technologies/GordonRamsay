package org.kowlintech.commands.misc;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.sharding.ShardManager;
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

            eb.setTitle("Shard Information");
            eb.setColor(Global.COLOR);
            eb.addField("Current Shard", "#" + info.getShardId(), true);
            eb.addField("Total Shards", String.valueOf(manager.getShardsTotal()), true);
            eb.addField("Running Shards", String.valueOf(manager.getShardsRunning()), true);
            eb.setFooter("Ping: " + ping + "ms");
            eb.setTimestamp(Instant.now());
            event.reply(eb.build());
        });
    }
}
