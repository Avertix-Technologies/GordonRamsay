package org.kowlintech.commands.misc;

import net.dv8tion.jda.api.EmbedBuilder;
import org.kowlintech.utils.command.objects.Command;
import org.kowlintech.utils.command.objects.CommandEvent;
import org.kowlintech.utils.command.objects.CommandExecutor;
import org.kowlintech.utils.command.objects.enums.Category;
import org.kowlintech.utils.constants.Global;

@Command(name = "ping", category = Category.MISCELLANEOUS, description = "Gets the bot's latency.")
public class PingCommand implements CommandExecutor {

    @Override
    public void execute(CommandEvent event) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setDescription("Pinging...");
        eb.setColor(Global.COLOR);
        event.getJDA().getRestPing().queue(aLong -> event.reply(eb.build(), message -> {
            eb.setTitle("Pong!");
            eb.setDescription("**REST API:** " + aLong + "ms\n**Gateway:** " + event.getJDA().getGatewayPing() + "ms");
            message.editMessage(eb.build()).queue();
        }));
    }
}
