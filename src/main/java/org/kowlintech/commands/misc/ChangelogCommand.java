package org.kowlintech.commands.misc;

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

@Command(name = "changelog", description = "Views the changelog.", category = Category.MISCELLANEOUS)
public class ChangelogCommand implements CommandExecutor {

    @Override
    public void execute(CommandEvent event) throws SQLException {
        ChangelogManager changelogManager = GordonRamsay.getChangelogManager();

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Most Recent Changelog Messages");
        eb.setColor(Global.COLOR);
        ArrayList<IChangelogMessage> array = changelogManager.getLatestChangelogMessages();
        if(array.size() == 0) {
            eb.setDescription("There aren't any changelog messages recorded.");
        } else {
            for(IChangelogMessage message : array) {
                eb.addField((eb.getFields().size() + 1) + ". " + message.getTitle(), message.getDescription(), false);
            }
        }
        event.reply(eb.build());
    }
}
