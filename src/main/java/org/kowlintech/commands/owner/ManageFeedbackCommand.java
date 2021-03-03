package org.kowlintech.commands.owner;

import org.kowlintech.commands.owner.subcommand.MFBGuildSubCommand;
import org.kowlintech.commands.owner.subcommand.MFBUserSubCommand;
import org.kowlintech.utils.command.objects.Command;
import org.kowlintech.utils.command.objects.CommandEvent;
import org.kowlintech.utils.command.objects.CommandExecutor;
import org.kowlintech.utils.command.objects.enums.Category;

import java.sql.SQLException;

@Command(name = "managefeedback", aliases = {"mfb"}, description = "Manages the feedback command.", category = Category.OWNER, args = "<toggleguild/toggleuser>")
public class ManageFeedbackCommand implements CommandExecutor {

    @Override
    public void execute(CommandEvent event) throws SQLException {
        String[] args = event.getArgs().split(" ");
        String first = args[0].trim().toLowerCase();

        if(first.equalsIgnoreCase("toggleguild")) {
            new MFBGuildSubCommand().execute(event);
            return;
        } else if(first.equalsIgnoreCase("toggleuser")) {
            new MFBUserSubCommand().execute(event);
            return;
        } else {
            event.reply("That isn't a valid argument.");
            return;
        }
    }
}
