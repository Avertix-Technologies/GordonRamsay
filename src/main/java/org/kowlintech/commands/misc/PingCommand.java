package org.kowlintech.commands.misc;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class PingCommand extends Command {

    public PingCommand(Category category) {
        this.name = "ping";
        this.help = "Gets the bot's latency";
        this.guildOnly = true;
        this.category = category;
    }

    @Override
    protected void execute(CommandEvent event) {
        event.reply("Pong! **" + event.getJDA().getGatewayPing() + "ms**");
    }
}
