package org.kowlintech.commands.owner;

import net.dv8tion.jda.api.EmbedBuilder;
import org.kowlintech.GordonRamsay;
import org.kowlintech.utils.ChangelogManager;
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
            String[] args2 = args1[1].split(" | ");
            changelogManager.createMessage(args2[0], args2[1]);
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Success!");
            eb.setColor(Global.COLOR);
            eb.setDescription("New changelog message added!");
            event.reply(eb.build());
        } else if(args[0].trim().equals("edit")) {
        } else if(args[0].trim().equals("delete")) {

        } else if(args[0].trim().equals("list")) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Most Recent Changelog Messages");
            ArrayList<IChangelogMessage> array = changelogManager.getLatestChangelogMessages();
            if(array.size() == 0) {
                eb.setDescription("There aren't any changelog messages recorded.");
            } else {
                for(IChangelogMessage message : array) {
                    eb.addField("**(" + message.getCreationDate() + ")** " + message.getTitle(), message.getDescription(), false);
                }
            }
        }
    }
}
