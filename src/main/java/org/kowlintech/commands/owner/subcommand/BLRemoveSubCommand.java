package org.kowlintech.commands.owner.subcommand;

import org.kowlintech.GordonRamsay;
import org.kowlintech.utils.BlacklistManager;
import org.kowlintech.utils.command.objects.CommandEvent;
import org.kowlintech.utils.command.objects.SubCommandExecutor;

import java.sql.SQLException;

public class BLRemoveSubCommand implements SubCommandExecutor {

    @Override
    public void execute(CommandEvent event) throws SQLException {
        String[] args = event.getArgs().split(" ");
        if(isLong(args[1].trim())) {
            BlacklistManager manager = GordonRamsay.getBlacklistManager();
            Long userid = Long.parseLong(args[1].trim());
            if(manager.getBlacklist().contains(userid)) {
                manager.removeUser(userid);
                event.reply("Successfully removed user from blacklist.");
                return;
            } else {
                event.reply("That user isn't blacklisted.");
                return;
            }
        } else {
            event.reply("You must provide a `long`.");
            return;
        }
    }

    public static boolean isLong(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            long l = Long.parseLong(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
