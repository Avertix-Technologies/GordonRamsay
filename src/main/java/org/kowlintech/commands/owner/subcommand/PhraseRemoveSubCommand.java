package org.kowlintech.commands.owner.subcommand;

import org.kowlintech.GordonRamsay;
import org.kowlintech.utils.command.objects.CommandEvent;
import org.kowlintech.utils.command.objects.EconomyPhrase;
import org.kowlintech.utils.command.objects.SubCommandExecutor;
import org.kowlintech.utils.command.objects.enums.PhraseType;

import java.sql.SQLException;

public class PhraseRemoveSubCommand implements SubCommandExecutor {

    @Override
    public void execute(CommandEvent event) throws SQLException {
        String[] args = event.getArgs().split(" ");

        if(args.length >= 3) {
            PhraseType type;
            if(args[1].trim().equalsIgnoreCase("shovel")) {
                type = PhraseType.SHOVEL;
            } else if(args[1].trim().equalsIgnoreCase("crime")) {
                type = PhraseType.CRIME;
            } else if(args[1].trim().equalsIgnoreCase("ask")) {
                type = PhraseType.ASK;
            } else {
                event.reply("`" + args[2].trim().toLowerCase() + "` is not a valid PhraseType.");
                return;
            }

            try {
                boolean bool = GordonRamsay.getPhraseManager().deletePhrase(type, Integer.valueOf(args[2].trim()));

                if(bool) {
                    event.reply(":white_check_mark: Removed phrase with id `" + args[2].trim() + "` from the database, " + event.getMember().getAsMention() + "!");
                } else {
                    event.reply(":x: That phrase was not found in the database, "  + event.getMember().getAsMention() + ".");
                }
                return;
            } catch (Exception ex) {
                event.reply("There was an error while removing that phrase to the database.\n```java\n\n" + ex.getMessage() + "```");
                ex.printStackTrace();
                return;
            }
        } else {
            event.reply("Usage: " + GordonRamsay.config.getProperty("prefix") + "phrase remove <shovel/crime/ask> <id>");
            return;
        }
    }
}
