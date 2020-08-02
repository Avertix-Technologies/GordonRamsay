package org.kowlintech.commands.owner;

import org.kowlintech.utils.InsultManager;
import org.kowlintech.utils.command.objects.Command;
import org.kowlintech.utils.command.objects.CommandEvent;
import org.kowlintech.utils.command.objects.CommandExecutor;
import org.kowlintech.utils.command.objects.enums.Category;

import java.sql.SQLException;

@Command(name = "manageinsults", category = Category.OWNER, description = "Manages insults for the g.insult command.", args = "<add/delete> <text/id>")
public class ManageInsultsCommand implements CommandExecutor {

    private InsultManager insultManager = new InsultManager();

    @Override
    public void execute(CommandEvent event) {
        if(event.getArgs().trim().isEmpty()) {
            event.reply("Please give an argument!");
            return;
        }

        String[] args = event.getArgs().split(" ");

        if(args[0].trim().equalsIgnoreCase("add")) {
            if(args.length <= 1) {
                event.reply("Please give an insult to add.");
                return;
            } else {
                String messages = "";
                String[] arrayOfString = args;
                int j = args.length;

                for (int i = 1; i < j; ++i) {
                    String arg = arrayOfString[i];
                    messages = messages + arg + " ";
                }

                try {
                    insultManager.addInsult(messages.trim());
                    event.reply("Successfully added insult!");
                } catch (SQLException e) {
                    event.reply("An error occurred while adding the insult.");
                    e.printStackTrace();
                }
            }
        } else if(args[0].trim().equalsIgnoreCase("delete")) {
            if(args.length <= 1) {
                event.reply("Please give an insult id to remove.");
                return;
            } else {
                try {
                    insultManager.deleteInsult(Integer.parseInt(args[1]));
                    event.reply("Successfully deleted insult!");
                } catch (SQLException e) {
                    event.reply("An error occurred while deleted the insult.");
                    e.printStackTrace();
                }
            }
        } else {
            event.reply("That isn't a valid argument.");
        }
    }
}