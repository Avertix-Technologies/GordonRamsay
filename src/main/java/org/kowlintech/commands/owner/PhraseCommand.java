package org.kowlintech.commands.owner;

import org.kowlintech.commands.owner.subcommand.PhraseAddSubCommand;
import org.kowlintech.commands.owner.subcommand.PhraseRemoveSubCommand;
import org.kowlintech.utils.command.objects.Command;
import org.kowlintech.utils.command.objects.CommandEvent;
import org.kowlintech.utils.command.objects.CommandExecutor;
import org.kowlintech.utils.command.objects.enums.Category;

import java.sql.SQLException;

@Command(name = "phrase", category = Category.OWNER, description = "Phrase management command", args = "<add/remove>")
public class PhraseCommand implements CommandExecutor {

    @Override
    public void execute(CommandEvent event) throws SQLException {
        if(!event.getArgs().isEmpty()) {
            String[] args = event.getArgs().split(" ");
            String first = args[0].trim();
            if (first.equalsIgnoreCase("add")) {
                new PhraseAddSubCommand().execute(event);
            } else if(first.equalsIgnoreCase("remove")) {
                new PhraseRemoveSubCommand().execute(event);
            } else {
                event.reply("Arguments: add/remove");
                return;
            }
        } else {
            event.reply("Arguments: add/remove");
            return;
        }
    }
}
