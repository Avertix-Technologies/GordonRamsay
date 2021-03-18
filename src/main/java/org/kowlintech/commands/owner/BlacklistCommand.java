package org.kowlintech.commands.owner;

import org.kowlintech.commands.owner.subcommand.BLAddSubCommand;
import org.kowlintech.commands.owner.subcommand.BLRemoveSubCommand;
import org.kowlintech.utils.command.objects.Command;
import org.kowlintech.utils.command.objects.CommandEvent;
import org.kowlintech.utils.command.objects.CommandExecutor;
import org.kowlintech.utils.command.objects.enums.Category;

import java.sql.SQLException;

@Command(name = "blacklist", description = "Blacklists a user.", aliases = {"bl"}, category = Category.OWNER, args = "<add/remove> <userid>")
public class BlacklistCommand implements CommandExecutor {

    @Override
    public void execute(CommandEvent event) throws SQLException {
        String[] args = event.getArgs().split(" ");
        String first = args[0].trim();
        if(first.equalsIgnoreCase("add")) {
            new BLAddSubCommand().execute(event);
            return;
        } else if(first.equalsIgnoreCase("remove")) {
            new BLRemoveSubCommand().execute(event);
            return;
        }

        event.reply("Usage: g.blacklist <add/remove>");
    }
}
