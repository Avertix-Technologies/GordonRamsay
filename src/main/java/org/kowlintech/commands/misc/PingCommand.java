package org.kowlintech.commands.misc;

import org.kowlintech.utils.command.objects.Command;
import org.kowlintech.utils.command.objects.CommandEvent;
import org.kowlintech.utils.command.objects.CommandExecutor;
import org.kowlintech.utils.command.objects.enums.Category;

import java.sql.SQLException;

@Command(name = "ping", category = Category.MISCELLANEOUS, description = "Gets the bot's latency.")
public class PingCommand implements CommandExecutor {

    @Override
    public void execute(CommandEvent event) throws SQLException {
        event.reply("Pong! **" + event.getJDA().getGatewayPing() + "ms**");
    }
}
