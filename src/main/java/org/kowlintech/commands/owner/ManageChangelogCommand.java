package org.kowlintech.commands.owner;

import net.dv8tion.jda.api.EmbedBuilder;
import org.kowlintech.GordonRamsay;
import org.kowlintech.utils.ChangelogManager;
import org.kowlintech.utils.EmbedHelper;
import org.kowlintech.utils.command.objects.Command;
import org.kowlintech.utils.command.objects.CommandEvent;
import org.kowlintech.utils.command.objects.CommandExecutor;
import org.kowlintech.utils.command.objects.IChangelogMessage;
import org.kowlintech.utils.command.objects.enums.Category;
import org.kowlintech.utils.constants.Global;

import java.sql.SQLException;
import java.util.ArrayList;

@Command(name = "managechangelog", category = Category.OWNER, description = "Manages the changelog system.", args = "<create/edit/delete/list>")
public class ManageChangelogCommand implements CommandExecutor {

    @Override
    public void execute(CommandEvent event) throws SQLException {
        ChangelogManager changelogManager = GordonRamsay.getChangelogManager();

        String[] args = event.getArgs().split(" ");
        if(args[0].trim().equals("create")) {
            String[] args1 = event.getArgs().split("create");
            String[] args2 = args1[1].trim().split("%s");
            changelogManager.createMessage(args2[0].trim(), args2[1].trim());
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Success!");
            eb.setColor(Global.COLOR);
            eb.setDescription("New changelog message added!");
            event.reply(eb.build());
        } else if(args[0].trim().equals("edit")) {
            if(changelogManager.getMessage(Long.parseLong(args[1])) != null) {
                String[] args1 = event.getArgs().split("edit " + args[1]);
                String[] args2 = args1[1].trim().split("%s");
                changelogManager.editMessage(Long.parseLong(args[1]), args2[0].trim(), args2[1].trim());
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("Success!");
                eb.setDescription("Changelog message has been edited!");
                eb.setColor(Global.COLOR);
                event.reply(eb.build());
            } else {
                event.reply(EmbedHelper.buildErrorEmbed("That changelog message doesn't exist."));
            }
        } else if(args[0].trim().equals("delete")) {
            if(changelogManager.getMessage(Long.parseLong(args[1])) != null) {
                changelogManager.deleteMessage(Long.parseLong(args[1]));
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("Success!");
                eb.setDescription("Message deleted from changelog!");
                eb.setColor(Global.COLOR);
                event.reply(eb.build());
            } else {
                event.reply(EmbedHelper.buildErrorEmbed("That changelog message doesn't exist."));
            }
        } else if(args[0].trim().equals("list")) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Most Recent Changelog Messages");
            eb.setColor(Global.COLOR);
            ArrayList<IChangelogMessage> array = changelogManager.getLatestChangelogMessages();
            if(array.size() == 0) {
                eb.setDescription("There aren't any changelog messages recorded.");
            } else {
                for(IChangelogMessage message : array) {
                    eb.addField("**(" + message.getCreationDate() + ")** " + message.getTitle(), message.getDescription(), false);
                }
            }
            event.reply(eb.build());
        } else {
            event.reply("Arguments: create/edit/delete/list");
        }
    }
}
