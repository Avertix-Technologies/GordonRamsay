package org.kowlintech.commands.misc;

import org.kowlintech.utils.command.objects.Command;
import org.kowlintech.utils.command.objects.CommandEvent;
import org.kowlintech.utils.command.objects.CommandExecutor;
import org.kowlintech.utils.command.objects.enums.Category;

@Command(name = "invite", category = Category.MISCELLANEOUS, description = "Get's the bot invite link.")
public class InviteCommand implements CommandExecutor {

    @Override
    public void execute(CommandEvent event) {
        event.reply("<https://discordapp.com/oauth2/authorize?client_id=528984558629027841&scope=bot&permissions=271969398>");
    }
}
