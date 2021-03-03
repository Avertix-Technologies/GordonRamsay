package org.kowlintech.commands.owner.subcommand;

import org.kowlintech.GordonRamsay;
import org.kowlintech.utils.FeedbackManager;
import org.kowlintech.utils.command.objects.CommandEvent;
import org.kowlintech.utils.command.objects.SubCommandExecutor;

import java.sql.SQLException;

public class MFBUserSubCommand implements SubCommandExecutor {

    @Override
    public void execute(CommandEvent event) throws SQLException {
        String[] args = event.getArgs().split(" ");
        if(args.length >= 2) {
            FeedbackManager manager = GordonRamsay.getFeedbackManager();

            if(manager.isUserBlacklisted(Long.valueOf(args[1].trim()))) {
                event.reply("Successfully removed blacklisted user!");
                manager.toggleBlacklistUser(Long.valueOf(args[1].trim()));
            } else {
                event.reply("Successfully blacklisted user!");
                manager.toggleBlacklistUser(Long.valueOf(args[1].trim()));
            }
        }
    }
}
