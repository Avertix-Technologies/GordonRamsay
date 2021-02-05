package org.kowlintech.commands.owner.subcommand;

import org.kowlintech.GordonRamsay;
import org.kowlintech.utils.command.objects.CommandEvent;
import org.kowlintech.utils.command.objects.EconomyPhrase;
import org.kowlintech.utils.command.objects.SubCommandExecutor;
import org.kowlintech.utils.command.objects.enums.PhraseType;

import java.sql.SQLException;

public class PhraseAddSubCommand implements SubCommandExecutor {

    @Override
    public void execute(CommandEvent event) throws SQLException {
        String[] args = event.getArgs().split(" ");

        if(args.length >= 4) {
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

            boolean fate;

            if(Boolean.valueOf(args[2].trim()) != null) {
                fate = Boolean.valueOf(args[2].trim());
            } else {
                event.reply("`" + args[3].trim() + "` is not a valid boolean.");
                return;
            }

            String messages = "";
            String[] arrayOfString = args;
            int j = args.length;

            for(int i = 3; i < j; ++i) {
                String arg = arrayOfString[i];
                messages = messages + arg + " ";
            }

            try {
                EconomyPhrase phrase = GordonRamsay.getPhraseManager().addPhrase(type, messages.trim(), fate);

                event.reply(":white_check_mark: Added phrase with id `" + phrase.getId() + "` and type `" + type.toString() + "` to the database, " + event.getMember().getAsMention() + "!");
            } catch (Exception ex) {
                event.reply("There was an error while adding that phrase to the database.\n```java\n\n" + ex.getMessage() + "```");
                ex.printStackTrace();
                return;
            }
        } else {
            event.reply("Usage: " + GordonRamsay.config.getProperty("prefix") + "phrase add <shovel/crime/ask> <fate> <phrase ({m} for amount of money)>");
            return;
        }
    }
}
