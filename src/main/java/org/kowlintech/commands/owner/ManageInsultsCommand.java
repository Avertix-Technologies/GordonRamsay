package org.kowlintech.commands.owner;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import org.kowlintech.GordonRamsay;
import org.kowlintech.utils.Insult;
import org.kowlintech.utils.InsultManager;

import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class ManageInsultsCommand extends Command {

    private InsultManager insultManager;

    public ManageInsultsCommand(Category category, InsultManager insultManager) {
        this.name = "manageinsults";
        this.guildOnly = true;
        this.help = "Manages insults for the g.insult command.";
        this.category = category;
        this.arguments = "<add/delete> <text/id>";
        this.aliases = new String[]{"minsults"};
        this.ownerCommand = true;
        this.insultManager = insultManager;
    }

    @Override
    protected void execute(CommandEvent event) {
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
                    event.replySuccess("Successfully added insult!");
                } catch (SQLException e) {
                    event.replyError("An error occurred while adding the insult.");
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
                    event.replySuccess("Successfully deleted insult!");
                } catch (SQLException e) {
                    event.replyError("An error occurred while deleted the insult.");
                    e.printStackTrace();
                }
            }
        } else {
            event.reply("That isn't a valid argument.");
        }
    }
}